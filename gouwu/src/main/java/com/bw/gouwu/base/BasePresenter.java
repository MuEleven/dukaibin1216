package com.bw.gouwu.base;

import com.bw.gouwu.contract.IContract;

import java.lang.ref.WeakReference;

public abstract class BasePresenter<V extends IContract.IView> implements IContract.IPresenter{
    private WeakReference<V> mWeakReference;

    public BasePresenter() {
        initModel();
    }

    public abstract void initModel();

    public void onAttch(V v) {
        mWeakReference = new WeakReference<>(v);
    }

    public void onDeAttch() {
        if (mWeakReference != null) {
            mWeakReference.clear();
            mWeakReference = null;
        }
    }

    public V getV() {
        return mWeakReference.get();
    }
}
