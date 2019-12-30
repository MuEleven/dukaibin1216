package com.bw.zuoye.view.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.zuoye.R;
import com.bw.zuoye.base.BaseActivity;
import com.bw.zuoye.contract.IMainContract;
import com.bw.zuoye.model.bean.Bean;
import com.bw.zuoye.presenter.MainPresenter;
import com.bw.zuoye.view.adapter.MyAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity<MainPresenter> implements IMainContract.IView {


    @BindView(R.id.rv)
    RecyclerView recyclerView;

    @Override
    protected MainPresenter providePresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initData() {
        mPresenter.getMainData();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onMainSuccess(Bean bean) {
        //获取bean数据
        List<Bean.DataBean> data = bean.getData();
        //布局管理器
        GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity.this, 2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        //适配器
        MyAdapter myAdapter = new MyAdapter(data);
        recyclerView.setAdapter(myAdapter);
        //点击事件
        myAdapter.setOnItemClickLintLner(new MyAdapter.OnItemClickLintLner() {
            @Override
            public void OnItemClick(String str) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onMainFailure(Throwable throwable) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
