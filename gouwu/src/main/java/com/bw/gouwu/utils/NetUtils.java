package com.bw.gouwu.utils;

import com.bw.gouwu.api.ApiService;
import com.bw.gouwu.url.MyUrls;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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

    //没有请求头参数
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

    //有请求头参数的
    public void getHeaderInfo(String url, Map<String, Object> map, final Class cls, final NetCallback callback) {
        apiService.getHeadrInfo(url, map).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws IOException {
                        if (callback != null) {
                            Gson gson = new Gson();
                            Object obj = gson.fromJson(responseBody.string(), cls);
                            callback.onSuccess(obj);
                        }
                    }
                });
    }

    public interface NetCallback<T> {
        void onSuccess(T t);

        void onError(String str);
    }

}
