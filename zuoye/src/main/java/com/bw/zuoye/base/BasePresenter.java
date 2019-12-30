package com.bw.zuoye.base;

public abstract class BasePresenter<V> {
     protected V view;

    public BasePresenter() {
        initModel();
    }

    protected abstract void initModel();

    public BasePresenter(V view) {
        this.view = view;
    }

    public void attach(V view) {
        this.view = view;
    }

    public void detach(){
        view = null;
    }
}
