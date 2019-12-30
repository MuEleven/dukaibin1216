package com.bw.zuoye.contract;

import com.bw.zuoye.model.bean.Bean;

public interface IMainContract {

    interface IView{
        void onMainSuccess(Bean bean);
        void onMainFailure(Throwable throwable);
    }

    interface IPresenter{
        void getMainData();
    }

    interface IModel{
        void getMainData(IModelCallBack iModelCallBack);

        interface IModelCallBack{
            void onMainSuccess(Bean bean);
            void onMainFailure(Throwable throwable);
        }
    }
}
