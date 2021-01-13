package com.example.asynctaskdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import static com.example.asynctaskdemo.MainActivity.progressDialog;

public class MainActivity extends AppCompatActivity {
    private static Context context;
    static ProgressDialog progressDialog = new ProgressDialog(context);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}

class DownloadTask extends AsyncTask<Void,Integer,Boolean> {
    private Context context;

    @Override
    protected void onPreExecute() {
        progressDialog.show(); //显示进度对话框
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            while (true){
                int downloadPercent = doDownload();
                publishProgress(downloadPercent);
                if (downloadPercent >= 100){
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        //在这里更新下载速度
        progressDialog.setMessage("Downloaded" + values[0] + "%");
    }

    @Override
    protected void onPostExecute(Boolean result) {
        progressDialog.dismiss(); //关闭对话框
        //在这里显示下载结果
        if (result){
            Toast.makeText(context,"Download succeeded",Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context,"Download failed",Toast.LENGTH_SHORT).show();
        }
    }

    private int doDownload() {
        return 0;
    }
}
