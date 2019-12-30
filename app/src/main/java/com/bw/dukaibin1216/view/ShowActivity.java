package com.bw.dukaibin1216.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bw.dukaibin1216.R;
import com.bw.dukaibin1216.bean.MyBean;
import com.bw.dukaibin1216.tools.NetUtils;
import com.bw.dukaibin1216.url.MyUrls;

import java.util.HashMap;
import java.util.Map;

public class ShowActivity extends AppCompatActivity {

    private EditText edit_name;
    private EditText edit_mima;
    private Button btn_a;
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);
        initView();
        sp = getSharedPreferences("lsx", MODE_PRIVATE);
        btn_a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = edit_name.getText().toString().trim();
                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(ShowActivity.this, "请输入手机号", Toast.LENGTH_SHORT).show();
                    return;
                }
                String mima = edit_mima.getText().toString().trim();
                if (TextUtils.isEmpty(mima)) {
                    Toast.makeText(ShowActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                Map<String, Object> map = new HashMap<>();
                map.put("phone", name);
                map.put("pwd", mima);
                NetUtils.getInstance().postInfo(MyUrls.LOGIN, map, MyBean.class, new NetUtils.NetCallback() {
                    @Override
                    public void onSuccess(Object o) {
                        if (o instanceof MyBean && ((MyBean) o).getStatus().equals("0000")) {
                            sp.edit().putString("userId", ((MyBean) o).getResult().getUserId() + "")
                                    .putString("sessionId", ((MyBean) o).getResult().getSessionId() + "").commit();
                            Intent intent = new Intent(ShowActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onError(String url) {
                    }
                });
            }
        });
    }

    private void initView() {
        edit_name = (EditText) findViewById(R.id.phone);
        edit_mima = (EditText) findViewById(R.id.pwd);
        btn_a = (Button) findViewById(R.id.registered);
    }
}
