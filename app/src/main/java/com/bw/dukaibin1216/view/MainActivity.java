package com.bw.dukaibin1216.view;
/* 作者 杜凯宾
   时间 19年12.16
   功能Glide圆形图片展示,
   viewpager+fragment+tablayout实现联动
* */

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bw.dukaibin1216.R;
import com.bw.dukaibin1216.adapter.FragmentAdapter;
import com.bw.dukaibin1216.fragment.AllFragment;
import com.bw.dukaibin1216.fragment.CompletedFragment;
import com.bw.dukaibin1216.fragment.EvaluationFragment;
import com.bw.dukaibin1216.fragment.PaymentFragment;
import com.bw.dukaibin1216.fragment.ReceiptFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    //初始化变量
    private ImageView image;
    private TextView login;
    private TabLayout tablay;
    private ViewPager vview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        //findviewbyid,找到控件
        image = (ImageView) findViewById(R.id.image);
        login = (TextView) findViewById(R.id.login);
        tablay = (TabLayout) findViewById(R.id.tablay);
        vview = (ViewPager) findViewById(R.id.vview);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ShowActivity.class);
                startActivity(intent);
                String url = "http://172.17.8.100/images/small/default/user.jpg";
                Glide.with(MainActivity.this)
                        .load(url)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(image);
            }
        });
        vview = (ViewPager) findViewById(R.id.vview);
        tablay = (TabLayout) findViewById(R.id.tablay);
        String[] titles = {"全部", "待付款", "待收货", "待评价", "已完成"};

        Fragment fragment1 = new AllFragment();
        Fragment fragment2 = new PaymentFragment();
        Fragment fragment3 = new ReceiptFragment();
        Fragment fragment4 = new EvaluationFragment();
        Fragment fragment5 = new CompletedFragment();
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(fragment1);
        fragments.add(fragment2);
        fragments.add(fragment3);
        fragments.add(fragment4);
        fragments.add(fragment5);

        FragmentPagerAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments, titles);
        vview.setAdapter(adapter);
        tablay.setupWithViewPager(vview);
    }
}
