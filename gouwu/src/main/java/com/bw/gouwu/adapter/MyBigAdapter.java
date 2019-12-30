package com.bw.gouwu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bw.gouwu.R;
import com.bw.gouwu.bean.MyData;
import com.bw.gouwu.weight.MyAddOrRemoveView;

import java.util.List;


public class MyBigAdapter extends RecyclerView.Adapter<MyBigAdapter.Holder> {

    private Context mContext;
    private List<MyData.ResultBean> mList;

    public MyBigAdapter(Context mContext, List<MyData.ResultBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.recy_big, null);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int i) {
        holder.mBigTv.setText(mList.get(i).getCategoryName() + "");
        SmallAdapter adapter = new SmallAdapter(mList.get(i).getShoppingCartList(), i);
        holder.mSamllRecy.setAdapter(adapter);
        holder.mBigCheck.setChecked(setBigCheck(i));
        holder.mBigCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callback != null) {
                    callback.bigCheckClick(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {
        private CheckBox mBigCheck;
        private TextView mBigTv;
        private RecyclerView mSamllRecy;

        public Holder(@NonNull View itemView) {
            super(itemView);
            mBigCheck = itemView.findViewById(R.id.Big_CheckBox);
            mBigTv = itemView.findViewById(R.id.Big_Text);
            mSamllRecy = itemView.findViewById(R.id.small_Recy);
            mSamllRecy.setLayoutManager(new LinearLayoutManager(mContext));
        }
    }

    //具体商品的适配器


    class SmallAdapter extends RecyclerView.Adapter<SmallAdapter.SamllHolder> {
        private List<MyData.ResultBean.ShoppingCartListBean> mList;
        private int bigIndex;

        public SmallAdapter(List<MyData.ResultBean.ShoppingCartListBean> list, int bigIndex) {
            this.mList = list;
            this.bigIndex = bigIndex;
        }

        @NonNull
        @Override
        public SamllHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = View.inflate(mContext, R.layout.recy_samll, null);
            SamllHolder holder = new SamllHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull SamllHolder samllHolder, final int i) {
            samllHolder.mPrice.setText(mList.get(i).getPrice() + "");
            Glide.with(mContext).load(mList.get(i).getPic()).into(samllHolder.mImage);
            samllHolder.mSmallName.setText(mList.get(i).getCommodityName() + "");
            samllHolder.myAddOrRemoveView.setNumber(mList.get(i).getCount());
            samllHolder.mSmallCheck.setChecked(mList.get(i).isStatus());
            samllHolder.mSmallCheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callback != null) {
                        callback.smallCheckClick(bigIndex, i);
                    }
                }
            });
            samllHolder.myAddOrRemoveView.setItemCount(new MyAddOrRemoveView.ItemCount() {
                @Override
                public void setItemCount(int number) {
                   if (callback!=null){
                       callback.smallCheckClickCount(bigIndex,i,number);
                   }
                }
            });
        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class SamllHolder extends RecyclerView.ViewHolder {
            private CheckBox mSmallCheck;
            private TextView mSmallName, mPrice;
            private ImageView mImage;
            private MyAddOrRemoveView myAddOrRemoveView;

            public SamllHolder(@NonNull View itemView) {
                super(itemView);
                mSmallCheck = itemView.findViewById(R.id.small_Check);
                mSmallName = itemView.findViewById(R.id.small_Name);
                mPrice = itemView.findViewById(R.id.small_Price);
                mImage = itemView.findViewById(R.id.samll_Image);
                myAddOrRemoveView = itemView.findViewById(R.id.AddOrRemove);
            }
        }
    }

    public interface ShoppingCallback {
        void bigCheckClick(int bigIndex);

        void smallCheckClick(int bigIndex, int smallIndex);

        void smallCheckClickCount(int bigIndex, int smallIndex, int number);
    }

    private ShoppingCallback callback;

    public void setCallback(ShoppingCallback callback) {
        this.callback = callback;
    }

    //接下来就是业务逻辑方法的时候了
    //判断商家是否要选中
    public boolean setBigCheck(int bigIndex) {
        boolean flag = true;
        MyData.ResultBean resultBean = mList.get(bigIndex);
        for (int i = 0; i < resultBean.getShoppingCartList().size(); i++) {
            if (!resultBean.getShoppingCartList().get(i).isStatus()) {
                flag = false;
                return flag;
            }
        }
        return flag;
    }

    //当我点击商家的复选框的时候
    public void setBigCheckStatus(int bigIndex, boolean isStatus) {
        List<MyData.ResultBean.ShoppingCartListBean> cartList = mList.get(bigIndex).getShoppingCartList();
        for (MyData.ResultBean.ShoppingCartListBean cardBean : cartList) {
            cardBean.setStatus(isStatus);
        }
    }

    //当我点击商品让他选中
    public void setSmallCheck(int bigIndex, int smallIndex, boolean isCheck) {
        mList.get(bigIndex).getShoppingCartList().get(smallIndex).setStatus(isCheck);
    }

    //当我点击Activity中的全选按钮式
    public boolean isAllChecked() {
        boolean isAllchecked = true;
        for (int i = 0; i < mList.size(); i++) {
            List<MyData.ResultBean.ShoppingCartListBean> shoppingCartList = mList.get(i).getShoppingCartList();
            for (int j = 0; j < shoppingCartList.size(); j++) {
                if (!shoppingCartList.get(j).isStatus()) {
                    isAllchecked = false;
                    return isAllchecked;
                }
            }
        }
        return isAllchecked;
    }

    public void setAllChecked(boolean isCheck) {
        for (int i = 0; i < mList.size(); i++) {
            List<MyData.ResultBean.ShoppingCartListBean> shoppingCartList = mList.get(i).getShoppingCartList();
            for (int j = 0; j < shoppingCartList.size(); j++) {
                shoppingCartList.get(j).setStatus(isCheck);
            }
        }
    }

    //计算总价格
    public int allPrice() {
        int allprice = 0;
        for (int i = 0; i < mList.size(); i++) {
            List<MyData.ResultBean.ShoppingCartListBean> shoppingCartList = mList.get(i).getShoppingCartList();
            for (int j = 0; j < shoppingCartList.size(); j++) {
                if (shoppingCartList.get(j).isStatus()) {
                    allprice += shoppingCartList.get(j).getPrice() * shoppingCartList.get(j).getCount();
                }
            }
        }
        return allprice;
    }

    //计算总数量
    public int allNumber() {
        int count = 0;
        for (int i = 0; i < mList.size(); i++) {
            List<MyData.ResultBean.ShoppingCartListBean> shoppingCartList = mList.get(i).getShoppingCartList();
            for (int j = 0; j < shoppingCartList.size(); j++) {
                if (shoppingCartList.get(j).isStatus()) {
                    count += shoppingCartList.get(j).getCount();
                }
            }
        }
        return count;
    }
}
