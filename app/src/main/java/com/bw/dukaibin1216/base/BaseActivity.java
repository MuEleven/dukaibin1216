package com.bw.dukaibin1216.base;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.bw.dukaibin1216.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayout();
}
