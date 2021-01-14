package com.example.networkdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpDemoActivity extends AppCompatActivity {
    private TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_demo);

        responseText = findViewById(R.id.main_tv_response_text);
        Button sendRequest = findViewById(R.id.main_btn_send);
        sendRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendRequestWithOkHttp();
            }
        });
    }

    private void sendRequestWithOkHttp() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //创建OkHttpClient实例
                    OkHttpClient client = new OkHttpClient();
                    //创建request对象，并通过url()来设置网络地址
                    Request request = new Request.Builder()
                            .url("http://www.baidu.com")
                            .build();
                    //调用OkHttpClient的newCall()方法来创建一个Call对象，并调用execute()来发送请求获取服务器返回的数据
                    //Response就是返回的数据
                    //调用execute来发送请求
                    Response response = client.newCall(request).execute();
                    //得到返回的具体内容
                    String responseData = response.body().toString();
                    showResponse(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        /*
        * 如果是发起POST
        * 先构建RequestBody对象来存放待提交的参数
        * RequestBody requestBody = new FormBody.Builder().add("username","admin")
        * .add("password","123456").build();
        * 然后在Request.Builder中调用post()方法,并将RequestBody对象传入
        * Request request = new Request.Builder()
        * .url("http://www.baidu.com")
        * .post(requestBody)
        * .build();
        *
        * */
    }

    private void showResponse(String responseData) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(responseData);
            }
        });
    }
}