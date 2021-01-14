package com.example.gsonparsejsondemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private TextView responseText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                    //设置网络地址为电脑本机
                    Request request = new Request.Builder()
                            .url("http://10.0.2.2/get_data.json")//10.0.2.2对于模拟器来说就是本机的IP地址
                            .build();
                    //调用OkHttpClient的newCall()方法来创建一个Call对象，并调用execute()来发送请求获取服务器返回的数据
                    //Response就是返回的数据
                    Response response = client.newCall(request).execute();
                    //得到返回的具体内容
                    String responseData = response.body().string();
                    parseJSONWithGSON(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJSONWithGSON(String jsonData) {
        //定义一个App类并创建gson对象
        Gson gson = new Gson();
        //调用TypeToken将期望解析成的数据类型传入到fromJson中
        List<App> appList = gson.fromJson(jsonData,new TypeToken<List<App>>(){}.getType());
        for (App app : appList){
            Log.d("MainActivity","id is" + app.getId());
            Log.d("MainActivity","name is" + app.getName());
            Log.d("MainActivity","version is" + app.getVersion());
        }
    }
}