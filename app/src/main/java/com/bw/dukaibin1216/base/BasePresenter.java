package com.bw.dukaibin1216.base;


import com.bw.dukaibin1216.contract.IContart;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends IContart.IView> implements IContart.IPresenter {
    private WeakReference<V> mWeakV;

    public BasePresenter() {
        initModel();
    }

    public void onAttchView(V v) {
        mWeakV = new WeakReference<>(v);
    }

    public void onDeAttchView() {
        if (mWeakV != null) {
            mWeakV.clear();
            mWeakV = null;
        }
    }

    protected V getView() {
        return mWeakV.get();
    }

    abstract protected void initModel();
}
