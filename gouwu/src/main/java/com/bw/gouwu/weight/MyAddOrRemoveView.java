package com.bw.gouwu.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.gouwu.R;

public class MyAddOrRemoveView extends LinearLayout implements View.OnClickListener {
    private TextView mAdd, mNumber, mRemove;

    public MyAddOrRemoveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.add_remove_layout, this);
        initViews();
    }

    public void setNumber(int number) {
        mNumber.setText(number + "");
    }

    private void initViews() {
        mAdd = findViewById(R.id.Add);
        mNumber = findViewById(R.id.Number);
        mRemove = findViewById(R.id.Remove);
        mAdd.setOnClickListener(this);
        mRemove.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int count = Integer.parseInt(mNumber.getText().toString());
        switch (v.getId()) {
            case R.id.Add:
                count++;
                mNumber.setText(count + "");
                if (itemCount != null) {
                    itemCount.setItemCount(count);
                }
                break;
            case R.id.Remove:
                if (count > 1) {
                    count--;
                    mNumber.setText(count + "");
                    if (itemCount != null) {
                        itemCount.setItemCount(count);
                    }
                } else {
                    Toast.makeText(getContext(), "数量最少为1", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public interface ItemCount {
        void setItemCount(int number);
    }

    private ItemCount itemCount;

    public void setItemCount(ItemCount itemCount) {
        this.itemCount = itemCount;
    }
}
