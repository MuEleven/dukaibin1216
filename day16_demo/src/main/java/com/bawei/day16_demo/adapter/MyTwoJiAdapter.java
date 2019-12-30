package com.bawei.day16_demo.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bawei.day16_demo.R;
import com.bawei.day16_demo.bean.MyData;

import java.util.List;

import io.reactivex.annotations.NonNull;

public class MyTwoJiAdapter extends RecyclerView.Adapter<MyTwoJiAdapter.Holder> {
    private List<MyData.ResultBean.SecondCategoryVoBean> mList;
    private Context mContext;

    public MyTwoJiAdapter(List<MyData.ResultBean.SecondCategoryVoBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.item_two_ji, null);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int i) {
        holder.mTv.setText(mList.get(i).getName() + "");
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private ImageView mImage;
        private TextView mTv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mImage = itemView.findViewById(R.id.Two_Image);
            mTv = itemView.findViewById(R.id.Two_Text);
        }
    }
}
