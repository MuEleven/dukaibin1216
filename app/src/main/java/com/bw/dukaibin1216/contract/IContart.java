package com.bw.dukaibin1216.contract;

public interface IContart {

    interface IModel {
        void getInfo(String url, ModelCallback callback);
    }

    interface IView {
        void onSuccess(String jsonStr);

        void onError(String error);
    }

    interface IPresenter {
        void startRequest(String url);
    }

    interface ModelCallback {
        void onSuccess(String jsonStr);

        void onError(String error);
    }
}
