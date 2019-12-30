package com.bw.upload;

import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 上传图片的
 * 1：需要开权限
 * 2：日志拦截器加上
 */
public class UpLoadTestActivity extends AppCompatActivity implements View.OnClickListener {

    private Button UpLoad_Btn;
    private String url = "http://172.17.8.100/small/user/verify/v1/modifyHeadPic";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        UpLoad_Btn = (Button) findViewById(R.id.UpLoad_Btn);

        UpLoad_Btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.UpLoad_Btn:
                uploadImage();
                break;
        }
    }

    //上传图片
    private void uploadImage() {
        File file = new File(Environment.getExternalStorageDirectory() + "/Pictures/abc.jpg");
        OkHttpClient okHttpClient = new OkHttpClient();
        //上传图片 首先要声明类型
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), file);
        //要天添加到请求体里面
        RequestBody body = new MultipartBody.Builder().setType(MultipartBody.FORM)
                //参数1：就代表的是接口文档上的字段 参数2：代表你本地图片的名字，参数3：我们要上传的图片类型以及上传的文件
                .addFormDataPart("image", "abc.jpg", requestBody).build();

        Request request = new Request.Builder().url(url).method("POST", body)
                .addHeader("userId", "10931")
                .addHeader("sessionId", "157650409547810931").build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(UpLoadTestActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

}
