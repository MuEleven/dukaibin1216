package com.bawei.day16_demo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.bawei.day16_demo.adapter.MyOneJiAdapter;
import com.bawei.day16_demo.adapter.MyTwoJiAdapter;
import com.bawei.day16_demo.bean.MyData;
import com.bawei.day16_demo.url.MyUrls;
import com.bawei.day16_demo.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView one_Ji_Recy;
    private RecyclerView two_Ji_Recy;
    private List<MyData.ResultBean> mOneList = new ArrayList<>();
    private List<MyData.ResultBean.SecondCategoryVoBean> mTwoList = new ArrayList<>();
    private MyOneJiAdapter mOneAdapter;
    private MyTwoJiAdapter mTwoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        mOneAdapter = new MyOneJiAdapter(mOneList, this);
        one_Ji_Recy.setAdapter(mOneAdapter);
        mTwoAdapter = new MyTwoJiAdapter(mTwoList, this);
        two_Ji_Recy.setAdapter(mTwoAdapter);
        NetUtils.getInstance().getInfo(MyUrls.SHOPPING_ITEM, MyData.class, new NetUtils.NetCallback() {
            @Override
            public void onSuccess(Object o) {
                if (o instanceof MyData) {
                    mOneList.addAll(((MyData) o).getResult());
                    mOneAdapter.notifyDataSetChanged();
                    mTwoList.addAll(mOneList.get(0).getSecondCategoryVo());
                    mTwoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onError(String str) {

            }
        });
        mOneAdapter.setItemClick(new MyOneJiAdapter.ItemClick() {
            @Override
            public void setIndex(int position) {
                mOneAdapter.setColorPosition(position);
                mTwoList.clear();
                mTwoList.addAll(mOneList.get(position).getSecondCategoryVo());
                mTwoAdapter.notifyDataSetChanged();
            }
        });
    }

    private void initView() {
        one_Ji_Recy = (RecyclerView) findViewById(R.id.one_Ji_Recy);
        one_Ji_Recy.setLayoutManager(new LinearLayoutManager(this));
        two_Ji_Recy = (RecyclerView) findViewById(R.id.Two_Ji_Recy);
        two_Ji_Recy.setLayoutManager(new GridLayoutManager(this, 3));
    }
}
