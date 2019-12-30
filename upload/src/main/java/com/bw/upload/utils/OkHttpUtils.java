package com.bw.upload.utils;

import android.os.Handler;
import android.os.Message;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * description: lvxinxin
 * author: 吕新新
 * date: 2019/12/2 13:34
 * update: $date$
 */
public class OkHttpUtils {

    private OkHttpClient mOkHttpClient;
    private HttpLoggingInterceptor interceptor;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1://成功
                    mCallback.onSuccess(msg.obj.toString());
                    break;
                case 2://失败
                    mCallback.onError(msg.obj.toString());
                    break;
            }
        }
    };
    private OkCallback mCallback;

    //构造函数做一些初始化的操作
    private OkHttpUtils() {
        //添加了日志拦截器
        interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        mOkHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();
    }

    private static class OkHolder {
        private static final OkHttpUtils utils = new OkHttpUtils();
    }

    public static OkHttpUtils getInstance() {
        return OkHolder.utils;
    }

    //同步的get请求
    public void syncGet(final String url, OkCallback callback) {
        mCallback = callback;
        new Thread(new Runnable() {
            @Override
            public void run() {
                Request request = new Request.Builder().url(url).build();
                String jsonStr = null;
                try {
                    jsonStr = mOkHttpClient.newCall(request).execute().body().string();
                    mHandler.sendMessage(mHandler.obtainMessage(0, jsonStr));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

    }

    //同步的Post请求
    public void syncPost(String url, FormBody formBody) throws IOException {
        Request request = new Request.Builder().url(url).method("Post", formBody).build();
        mOkHttpClient.newCall(request).execute().body().string();
    }

    //异步的Get请求

    public void asyncGet(String url, OkCallback callback) {
        mCallback = callback;
        Request request = new Request.Builder().url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendMessage(mHandler.obtainMessage(2, e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mHandler.sendMessage(mHandler.obtainMessage(1, response.body().string()));
            }
        });
    }

    //异步的Post请求
    public void asyncPost(String url, FormBody formBody, OkCallback callback) {
        mCallback = callback;
        Request request = new Request.Builder().method("POST", formBody).url(url).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendMessage(mHandler.obtainMessage(2, e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mHandler.sendMessage(mHandler.obtainMessage(1, response.body().string()));
            }
        });
    }

    //上传图片
    public void uploadImage(String url, File file, OkCallback callback) {
        mCallback = callback;
        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", "abc.jpg", RequestBody.create(MediaType.parse("image/jpg"), file))
                .build();
        Request request = new Request.Builder().url(url).method("POST", body)
                .addHeader("userId", "10931")
                .addHeader("sessionId", "157650409547810931")
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                mHandler.sendMessage(mHandler.obtainMessage(2, e.getMessage()));
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                mHandler.sendMessage(mHandler.obtainMessage(1, response.body().string()));
            }
        });


    }

    public interface OkCallback {
        void onSuccess(String jsonStr);

        void onError(String error);
    }

}
