package com.bw.zuoye.view.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bw.zuoye.R;
import com.bw.zuoye.model.bean.Bean;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{
    //
    private List<Bean.DataBean> data;

    public MyAdapter(List<Bean.DataBean> data) {

        this.data = data;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null);
        return new MyViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Bean.DataBean dataBean = data.get(position);
        holder.name.setText(dataBean.getGoods_name());

        Glide.with(holder.img).load(dataBean.getGoods_thumb())
                .into(holder.img);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickLintLner != null) {
                    onItemClickLintLner.OnItemClick(holder.name.toString());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView name;
        private ImageView img;

        public MyViewHolder(View itemView) {
             super(itemView);
            name = itemView.findViewById(R.id.tv_name);
            img = itemView.findViewById(R.id.iv_img);
        }
     }

    OnItemClickLintLner onItemClickLintLner;

    public void setOnItemClickLintLner(OnItemClickLintLner onItemClickLintLner) {
        this.onItemClickLintLner = onItemClickLintLner;
    }

    public interface OnItemClickLintLner{
        void OnItemClick(String str);
     }
}
