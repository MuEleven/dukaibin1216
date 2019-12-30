package com.bw.gouwu;

import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bw.gouwu.adapter.MyBigAdapter;
import com.bw.gouwu.base.BaseActivity;
import com.bw.gouwu.base.BasePresenter;
import com.bw.gouwu.bean.MyData;
import com.bw.gouwu.presenter.PresenterImpl;
import com.bw.gouwu.url.MyUrls;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    @BindView(R.id.recy_View)
    RecyclerView mRecy;
    private List<MyData.ResultBean> mList = new ArrayList<>();
    private MyBigAdapter mAdapter;
    @BindView(R.id.All_check)
    CheckBox All_check;
    @BindView(R.id.All_Price)
    TextView All_Price;
    @BindView(R.id.All_Count)
    TextView All_Count;

    @Override
    public BasePresenter getPresenter() {
        return new PresenterImpl();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {
        All_check.setOnClickListener(this);
        mRecy.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void startCoding() {
        mAdapter = new MyBigAdapter(this, mList);
        mRecy.setAdapter(mAdapter);
        Map<String, Object> map = new HashMap<>();
        map.put("userId", "10931");
        map.put("sessionId", "157707184952510931");
        mPresenter.startGetRequest(MyUrls.SHOPPINGCARD, MyData.class, map);
        mAdapter.setCallback(new MyBigAdapter.ShoppingCallback() {
            @Override
            public void bigCheckClick(int bigIndex) {
                boolean b = mAdapter.setBigCheck(bigIndex);
                mAdapter.setBigCheckStatus(bigIndex, !b);
                mAdapter.notifyDataSetChanged();
                allCalculation();
            }

            @Override
            public void smallCheckClick(int bigIndex, int smallIndex) {
                boolean status = mList.get(bigIndex).getShoppingCartList().get(smallIndex).isStatus();
                mAdapter.setSmallCheck(bigIndex, smallIndex, !status);
                mAdapter.notifyDataSetChanged();
                allCalculation();
            }

            @Override
            public void smallCheckClickCount(int bigIndex, int smallIndex, int number) {
                mList.get(bigIndex).getShoppingCartList().get(smallIndex).setCount(number);
                mAdapter.notifyDataSetChanged();
                allCalculation();
            }
        });
    }

    @Override
    public void onSuccess(Object o) {
        if (o instanceof MyData){
            mList.addAll(((MyData)o).getResult());
            mAdapter.notifyDataSetChanged();
       }
    }

@Override
public void onError(String error) {

        }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.All_check:
                boolean allChecked = mAdapter.isAllChecked();
                mAdapter.setAllChecked(!allChecked);
                All_check.setChecked(!allChecked);
                mAdapter.notifyDataSetChanged();
                All_Count.setText("总数量：" + mAdapter.allNumber());
                All_Price.setText("总价格：" + mAdapter.allPrice());
                break;
        }
    }

    private void allCalculation() {
        boolean allChecked = mAdapter.isAllChecked();
        All_check.setChecked(allChecked);
        All_Count.setText("总数量：" + mAdapter.allNumber());
        All_Price.setText("总价格：" + mAdapter.allPrice());
    }
}
