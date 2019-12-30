package com.bw.gouwu.base;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bw.gouwu.contract.IContract;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IContract.IView,View.OnClickListener {
    public P mPresenter;
    public EventBus eventBus;
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
            bind = ButterKnife.bind(this);
            eventBus = EventBus.getDefault();
            initViews();
            mPresenter = getPresenter();
            if (mPresenter != null) {
                mPresenter.onAttch(this);
                startCoding();
            }
        }
    }
    //EventBus那个页面想用那个页面调用这个方法就行
    public void registerEventbus() {
        eventBus.register(this);
    }

    public abstract P getPresenter();

    public abstract int getLayoutId();

    public abstract void initViews();

    public abstract void startCoding();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDeAttch();
        mPresenter = null;
        if (eventBus.isRegistered(this)) {
            eventBus.unregister(this);
        }
        if (bind != null) {
            bind.unbind();
        }
    }
}
