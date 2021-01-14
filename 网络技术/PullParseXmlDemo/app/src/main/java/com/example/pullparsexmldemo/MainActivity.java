package com.example.pullparsexmldemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;

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
                            .url("http://10.0.2.2/get_data.xml")//10.0.2.2对于模拟器来说就是本机的IP地址
                            .build();
                    //调用OkHttpClient的newCall()方法来创建一个Call对象，并调用execute()来发送请求获取服务器返回的数据
                    //Response就是返回的数据
                    //调用execute来发送请求
                    Response response = client.newCall(request).execute();
                    //得到返回的具体内容
                    String responseData = response.body().toString();
                    parseXMLWithPull(responseData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseXMLWithPull(String xmlData) {
        try {
            //获取XmlPullParserFactory的实例，并借助这个实例得到XmlPullParser对象
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            //调用setInput()将服务器返回的数据设置进去就开始解析
            xmlPullParser.setInput(new StringReader(xmlData));
            //获得当前解析事件类型
            int eventType = xmlPullParser.getEventType();
            String id = "";
            String name = "";
            String version = "";
            //如果解析事件不等于END_DOCUMENT，说明还没有解析完
            while (eventType != XmlPullParser.END_DOCUMENT){
                //获取当前节点名字
                String nodeName = xmlPullParser.getName();
                switch (eventType){
                    //开始解析某个节点并在之后一一打印出来
                    case XmlPullParser.START_TAG: {
                        //nextText可获取节点内具体内容
                        if ("id".equals(nodeName)) {
                            id = xmlPullParser.nextText();
                        } else if ("name".equals(nodeName)) {
                            name = xmlPullParser.nextText();
                        } else if ("version".equals(nodeName)) {
                            version = xmlPullParser.nextText();
                        }
                        break;
                    }
                    //完成解析某个节点
                    case XmlPullParser.END_TAG:{
                        if ("app".equals(nodeName)){
                            Log.d("MainActivity","id is" + id);
                            Log.d("MainActivity","name is" + name);
                            Log.d("MainActivity","version is" + version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                //调用next()获取下一个事件
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}