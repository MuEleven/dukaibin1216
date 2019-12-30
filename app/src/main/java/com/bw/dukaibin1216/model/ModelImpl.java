package com.bw.dukaibin1216.model;

import com.bw.dukaibin1216.base.BasePresenter;
import com.bw.dukaibin1216.contract.IContart;
import com.bw.dukaibin1216.tools.NetUtils;

import java.util.WeakHashMap;

public class ModelImpl implements IContart.IModel {
    @Override
    public void getInfo(String url, final IContart.ModelCallback callback) {
//        NetUtils.getInstance().postInfo(url, new NetUtils.NetCallback<>() {
//            @Override
//            public void onSuccess(Object o) {
//            }
//            @Override
//            public void onError(String str) {
//            }
//        });
    }
}
