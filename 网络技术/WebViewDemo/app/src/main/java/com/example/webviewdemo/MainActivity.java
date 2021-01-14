package com.example.webviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        WebView webView = findViewById(R.id.main_wv);
        //调用getSettings()设置属性，这里只设置了支持JavaScript
        webView.getSettings().setJavaScriptEnabled(true);
        //需要从一个网页跳转到另一个网页时，网页仍在WebView中显示，而不是打开系统浏览器
        webView.setWebViewClient(new WebViewClient());
        //传入网址
        webView.loadUrl("http://www.baidu.com");
    }
}
//网络功能需要声明权限