package com.bw.gouwu.presenter;

import com.bw.gouwu.base.BasePresenter;
import com.bw.gouwu.contract.IContract;
import com.bw.gouwu.model.ModelImpl;

import java.util.Map;



/**
 * description: lvxinxin
 * author: 吕新新
 * date: 2019/12/5 15:35
 * update: $date$
 */

public class PresenterImpl extends BasePresenter {
    private ModelImpl model;
    @Override
    public void initModel() {
        model = new ModelImpl();
    }

    @Override
    public void startGetNoParamsRequest(String url, Class cls) {
        model.doGetNoParams(url, cls, new IContract.ModelCallback() {
            @Override
            public void onSuccess(Object o) {
                getV().onSuccess(o);
            }

            @Override
            public void onError(String error) {
                getV().onError(error);
            }
        });
    }

    @Override
    public void startGetRequest(String url, Class cls, Map<String, Object> map) {
        model.doGetParams(url, cls, map, new IContract.ModelCallback() {
            @Override
            public void onSuccess(Object o) {
                getV().onSuccess(o);
            }

            @Override
            public void onError(String error) {
                getV().onSuccess(error);
            }
        });
    }

    @Override
    public void doPostParams(String url, Class cls, Map<String, Object> map, IContract.ModelCallback callback) {

    }
}
