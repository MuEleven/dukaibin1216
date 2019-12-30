package com.bw.dukaibin1216.presenter;

import com.bw.dukaibin1216.base.BasePresenter;
import com.bw.dukaibin1216.contract.IContart;
import com.bw.dukaibin1216.model.ModelImpl;

public class PresenterImpl extends BasePresenter {
    private ModelImpl mModel;

    @Override
    protected void initModel() {
        mModel = new ModelImpl();
    }

    @Override
    public void startRequest(String url) {
        mModel.getInfo(url, new IContart.ModelCallback() {
            @Override
            public void onSuccess(String jsonStr) {
                getView().onSuccess(jsonStr);
            }

            @Override
            public void onError(String error) {
                getView().onError(error);
            }
        });
    }
}
