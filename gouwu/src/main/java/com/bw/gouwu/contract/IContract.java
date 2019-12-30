package com.bw.gouwu.contract;

import java.util.Map;

/**
 * description: lvxinxin
 * author: 吕新新
 * date: 2019/12/5 15:05
 * update: $date$
 */
public interface IContract {
    interface IModel {
        //没有参数的时候
        void doGetNoParams(String url, Class cls, ModelCallback callback);

        void doGetParams(String url, Class cls, Map<String, Object> map, ModelCallback callback);

        void doPostParams(String url, Class cls, Map<String, Object> map, ModelCallback callback);
    }

    interface IView<T> {
        void onSuccess(T t);

        void onError(String error);
    }

    interface IPresenter {
        void startGetNoParamsRequest(String url, Class cls);

        void startGetRequest(String url, Class cls, Map<String, Object> map);

        void doPostParams(String url, Class cls, Map<String, Object> map, ModelCallback callback);
    }

    interface ModelCallback<T> {
        void onSuccess(T t);

        void onError(String error);
    }
}
