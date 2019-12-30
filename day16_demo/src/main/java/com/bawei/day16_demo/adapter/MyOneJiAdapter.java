package com.bawei.day16_demo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bawei.day16_demo.R;
import com.bawei.day16_demo.bean.MyData;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class MyOneJiAdapter extends RecyclerView.Adapter<MyOneJiAdapter.Holder> {

    private List<MyData.ResultBean> mList;
    private Context mContext;
    private int mColorPosition = 0;

    public MyOneJiAdapter(List<MyData.ResultBean> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(mContext, R.layout.item_one_ji, null);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        holder.mTv.setText(mList.get(i).getName() + "");
        if (mColorPosition == i) {
            holder.mTv.setTextColor(Color.RED);
        } else {
            holder.mTv.setTextColor(Color.BLACK);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) {
                    click.setIndex(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private TextView mTv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.One_Text);
        }
    }

    public interface ItemClick {
        void setIndex(int position);
    }

    private ItemClick click;

    public void setItemClick(ItemClick click) {
        this.click = click;
    }

    public void setColorPosition(int position) {
        this.mColorPosition = position;
        notifyDataSetChanged();
    }
}
