package com.bw.zuoye.presenter;

import com.bw.zuoye.base.BasePresenter;
import com.bw.zuoye.contract.IMainContract;
import com.bw.zuoye.model.MainModel;
import com.bw.zuoye.model.bean.Bean;

public class MainPresenter extends BasePresenter<IMainContract.IView> implements IMainContract.IPresenter{

    private MainModel mainModel;

    @Override
    protected void initModel() {
        mainModel = new MainModel();
    }

    @Override
    public void getMainData() {
        mainModel.getMainData(new IMainContract.IModel.IModelCallBack() {
            @Override
            public void onMainSuccess(Bean bean) {
                view.onMainSuccess(bean);
            }

            @Override
            public void onMainFailure(Throwable throwable) {
                view.onMainFailure(throwable);
            }
        });
    }
}
