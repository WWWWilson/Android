package com.example.servicedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MyService.DownloadBinder downloadBinder;
    //重写ServiceConnection的匿名类，并重写两个方法
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            downloadBinder = (MyService.DownloadBinder) service;//得到downloadBinder的实例
            downloadBinder.startDownload();
            downloadBinder.getProgress();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button start = findViewById(R.id.main_btn_start);
        Button stop = findViewById(R.id.main_btn_stop);
        Button bind = findViewById(R.id.main_btn_bind_service);
        Button unbind = findViewById(R.id.main_btn_unbind_service);
        Button startIntentService = findViewById(R.id.main_btn_start_intent_service);
        start.setOnClickListener(this);
        stop.setOnClickListener(this);
        bind.setOnClickListener(this);
        unbind.setOnClickListener(this);
        startIntentService.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_btn_start:
                Intent startIntent = new Intent(this,MyService.class);
                startService(startIntent);//启动服务
                break;
            case R.id.main_btn_stop:
                Intent stopIntent = new Intent(this,MyService.class);
                stopService(stopIntent);
                break;
            case R.id.main_btn_bind_service:
                Intent bindIntent = new Intent(this,MyService.class);
                bindService(bindIntent,connection,BIND_AUTO_CREATE);//绑定服务
                break;
            case R.id.main_btn_unbind_service:
                unbindService(connection);
                break;
            case R.id.main_btn_start_intent_service:
                Log.d("MainActivity","Thread id is" + Thread.currentThread().getId());
                Intent intentService = new Intent(this,MyIntentServiceDemo.class);
                startService(intentService);
                break;
            default:
                break;
        }
    }
}