package com.example.playvideodemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button play,pause,replay;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.main_video_view);
        play = findViewById(R.id.main_btn_play);
        pause = findViewById(R.id.main_btn_pause);
        replay = findViewById(R.id.main_btn_replay);
        play.setOnClickListener(this);
        pause.setOnClickListener(this);
        replay.setOnClickListener(this);
        //判断用户是否授权
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }else {
            initVideoPath();
        }
    }

    private void initVideoPath() {
        //在SD卡的根目录放置一个名为movie.mp4的视频文件
        File file = new File(Environment.getExternalStorageDirectory(),"movie.mp4");
        //用file对象指定的路径作为参数获取路径
        videoView.setVideoPath(file.getPath());
    }

    //在请求授权结果时
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                //判断授权结果是否为同意
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    initVideoPath();
                }else{
                    Toast.makeText(MainActivity.this,"拒绝权限将无法使用程序",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_btn_play:
                if (!videoView.isPlaying()){
                    videoView.start();
                }
                break;
            case R.id.main_btn_pause:
                if (videoView.isPlaying()){
                    videoView.pause();
                }
                break;
            case R.id.main_btn_replay:
                if (videoView.isPlaying()){
                    videoView.resume(); //重新播放
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null){
            videoView.suspend();
        }
    }
}
//记得在Manifest中声明权限