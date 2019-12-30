package com.bawei.day16_demo.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.bawei.day16_demo.api.ApiService;
import com.bawei.day16_demo.app.MyApp;
import com.bawei.day16_demo.url.MyUrls;
import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetUtils {

    private ApiService apiService;

    private NetUtils() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        SharedPreferences lvxx = MyApp.mContext.getSharedPreferences("lvxx", Context.MODE_PRIVATE);
                        String userId = lvxx.getString("userId", "");
                        String sessionId = lvxx.getString("sessionId", "");
                        if (TextUtils.isEmpty(userId) || TextUtils.isEmpty(sessionId)) {
                            return chain.proceed(chain.request());
                        } else {
                            Request request = chain.request().newBuilder()
                                    .addHeader("userId", userId)
                                    .addHeader("sessionId", sessionId)
                                    .build();
                            Log.e("userid", userId);
                            Log.e("sessionId", sessionId);
                            return chain.proceed(request);
                        }
                    }
                })
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyUrls.BASEURL)
                .client(okHttpClient)
                //结合Rxjava使用
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                //结合Gson使用
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    private static class NetHolder {
        private static final NetUtils netUtils = new NetUtils();
    }

    public static NetUtils getInstance() {
        return NetHolder.netUtils;
    }

    public void getInfo(String url, final Class cls, final NetCallback callback) {
        apiService.getInfoNoParams(url).subscribeOn(Schedulers.io())//被观察者在什么线程上工作
                .observeOn(AndroidSchedulers.mainThread())//观察者相应一般都是在主线程
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Gson gson = new Gson();
                        try {
                            Object object = gson.fromJson(responseBody.string(), cls);
                            if (callback != null) {
                                callback.onSuccess(object);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (callback != null) {
                            callback.onError(e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void postInfo(String url, Map<String, Object> map, final Class cls, final NetCallback callback) {
        apiService.postInfoParams(url, map).subscribeOn(Schedulers.io())//被观察者在什么线程上工作
                .observeOn(AndroidSchedulers.mainThread())//观察者相应一般都是在主线程
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (callback != null) {
                            try {
                                Gson gson = new Gson();
                                Object o = gson.fromJson(responseBody.string(), cls);
                                callback.onSuccess(o);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getParamsInfo(String url, Map<String, Object> map, final Class cls, final NetCallback callback) {
        apiService.getInfoParams(url, map).subscribeOn(Schedulers.io())//被观察者在什么线程上工作
                .observeOn(AndroidSchedulers.mainThread())//观察者相应一般都是在主线程
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        if (callback != null) {
                            try {
                                Gson gson = new Gson();
                                Object o = gson.fromJson(responseBody.string(), cls);
                                callback.onSuccess(o);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public interface NetCallback<T> {
        void onSuccess(T t);

        void onError(String str);
    }

}
