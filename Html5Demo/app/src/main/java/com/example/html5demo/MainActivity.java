package com.example.html5demo;

import  androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.Toast;


/**
 * 步骤
 * 1.设置允许执行JavaScript脚本
 *webView.getSettings().setJavaScriptEnabled(true);
 *2.添加通信接口
 * webView.addJavascriptInterface(appInterface,"interfaceName");
 * 3.JavaScript调用android
 * interfaceName.MethodName
 * 4.android调用javaScript
 * webView.loadUrl("javascript:functionName()");
 * */
public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private Button button;

    private WebAppInterface appInterface;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用WebAppInterface中方法
                appInterface.showName("wilson");
            }
        });

        webView.loadUrl("file:///android_asset/index.html");
        //设置可以调用JavaScrip脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //添加一个接口
        appInterface = new WebAppInterface(this);
        webView.addJavascriptInterface(appInterface,"app");

    }

    //创建一个类，这个类是App的一个接口
    class WebAppInterface{

        private Context context;

        public WebAppInterface(Context context){
            this.context = context;
        }
        //javascript调用android的方法
        @JavascriptInterface
        public void sayHello(String name){
            Toast.makeText(context, "name" + name, Toast.LENGTH_SHORT).show();
        }

        //android调用javascript的方法
        public void showName(String name){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    webView.loadUrl("javascript:showName('"+name+"')");
                }
            });
        }
    }
}