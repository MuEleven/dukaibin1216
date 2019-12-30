package com.bw.gouwu.model;

import com.bw.gouwu.contract.IContract;
import com.bw.gouwu.utils.NetUtils;

import java.util.Map;


/**
 * description: lvxinxin
 * author: 吕新新
 * date: 2019/12/5 15:26
 * update: $date$
 */
public class ModelImpl implements IContract.IModel {

    //无参数的Get请求
    @Override
    public void doGetNoParams(String url, Class cls, final IContract.ModelCallback callback) {
        NetUtils.getInstance().getInfo(url, cls, new NetUtils.NetCallback() {
            @Override
            public void onSuccess(Object o) {
                callback.onSuccess(o);
            }

            @Override
            public void onError(String str) {
                callback.onError(str);
            }
        });
    }

    @Override
    public void doGetParams(String url, Class cls, Map<String, Object> map, final IContract.ModelCallback callback) {
            NetUtils.getInstance().getHeaderInfo(url, map, cls, new NetUtils.NetCallback() {
                @Override
                public void onSuccess(Object o) {
                    callback.onSuccess(o);
                }

                @Override
                public void onError(String str) {
                    callback.onError(str);
                }
            });
    }

    @Override
    public void doPostParams(String url, Class cls, Map<String, Object> map, IContract.ModelCallback callback) {

    }
}
