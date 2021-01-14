package com.example.counttime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText inputtime;
    private Button getTime,startTime,stopTime;
    private TextView time;
    private int i = 0;
    private Timer timer = null;
    private TimerTask task = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    public void initView(){
        inputtime = findViewById(R.id.inputtime);
        getTime = findViewById(R.id.gettime);
        startTime = findViewById(R.id.starttime);
        stopTime = findViewById(R.id.stoptime);
        time = findViewById(R.id.time);

        getTime.setOnClickListener(this);
        startTime.setOnClickListener(this);
        stopTime.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.gettime:
                time.setText(inputtime.getText().toString());
                i = Integer.parseInt(inputtime.getText().toString());
                break;
            case R.id.starttime:
                startTime();
                break;
            case R.id.stoptime:
                stopTime();
                break;
        }
    }

    private Handler mHandler = new Handler(){
        public void hanldeMessage(Message msg){
            time.setText(msg.arg1+"");
            startTime();
        };
    };

    public void startTime(){
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                i--;
                Message message = mHandler.obtainMessage();
                message.arg1 = i;
                mHandler.sendMessage(message);
            }
        };
        timer.schedule(task,1000);
    }
    public void stopTime(){
        timer.cancel();
    }
}