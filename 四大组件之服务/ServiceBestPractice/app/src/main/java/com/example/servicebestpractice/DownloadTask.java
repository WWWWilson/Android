package com.example.servicebestpractice;

import android.os.AsyncTask;
import android.os.Environment;
import android.webkit.DownloadListener;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//第一个String泛型参数表示在执行AsyncTask时需传入一个字符串参数个给后台任务
//第二个Integer表示使用整形数据来作为进度显示单位
//第三个Integer表示使用整形数据来反馈结果
public class DownloadTask extends AsyncTask<String,Integer,Integer> {

    //定义4个整形变量来表示下载的状态
    public static final int TYPE_SUCCESS = 0;
    public static final int TYPE_FAILED = 1;
    public static final int TYPE_PAUSED = 2;
    public static final int TYPE_CANCELED = 3;

    private DownloadListener listener;
    private boolean isCanceled = false;
    private boolean isPaused = false;
    private int lastProgress;

    public interface DownloadListener{
        void onProgress(int progress);
        void onSuccess();
        void onFailed();
        void onPaused();
        void onCanceled();
    }

    //创建构造方法，传入定义的DownloadListener参数，将会通过这个参数进行回调
    public DownloadTask(DownloadListener listener) {
        this.listener = listener;
    }

    //用于在后台执行具体的下载逻辑，此方法自动在子线程中
    @Override
    protected Integer doInBackground(String... params) {
        InputStream is = null;
        RandomAccessFile savedFile = null;
        File file = null;

        try {
            long downloadLength = 0; //记录已下载文件的长度
            //从参数中获取到了下载的URL地址
            String downloadUrl = params[0];
            //根据URl地址解析出了下载的文件名
            String fileName = downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            //指定将文件下载到Environment.DIRECTORY_DOWNLOADS目录下，也就是SD卡的Download目录
            String directory = Environment.getExternalStoragePublicDirectory
                    (Environment.DIRECTORY_DOWNLOADS).getPath();
            file = new File(directory + fileName);
            //判断是否存在要下载的文件
            if (file.exists()){
                //如果存在则读取已下载的字节数，这样可在后面启用断点续传的功能
                downloadLength = file.length();
            }
            //调用getContentLength()来获取待下载文件的总长度
            long contentLength = getContentLength(downloadUrl);
            //如果文件长度==0，说明文件有问题，返回TYPE_FAILED
            if (contentLength == 0){
                return TYPE_FAILED;
            }else if (contentLength == downloadLength){
                //已下载字节和文件总字节相等，说明已经下载完成
                return TYPE_SUCCESS;
            }
            //接着使用OkHttp来发送一条网络请求
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    //addHeader()断点下载，用于告诉服务器我们想要从哪个字节开始下载，因为下载过的部分就不用下载了
                    .addHeader("RANGE","bytes=" + downloadLength + "-")
                    .url(downloadUrl)
                    .build();
            Response response = client.newCall(request).execute();
            if (response != null){
                is = response.body().byteStream();
                savedFile = new RandomAccessFile(file,"rw");
                savedFile.seek(downloadLength); //跳过已下载的字节
                byte[] b = new byte[1024];
                int total = 0;
                int len;
                while ((len = is.read(b)) != -1){
                    if (isCanceled){
                        return TYPE_CANCELED;
                    }else if (isPaused){
                        return TYPE_PAUSED;
                    }else {
                        total += len;
                        savedFile.write(b,0,len);
                        //计算已下载的百分比
                        int progress = (int) ((total + downloadLength) * 100 / contentLength);
                        //调用publishProgress()在UI线程上发布更新
                        publishProgress(progress);
                    }
                }
                response.body().close();
                return TYPE_SUCCESS;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null){
                    is.close();
                }
                if (savedFile != null){
                    savedFile.close();
                }
                if (isCanceled && file != null){
                    file.delete();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return TYPE_FAILED;
    }

    //在界面上更新当前的下载进度
    @Override
    protected void onProgressUpdate(Integer... values) {
        int progress = values[0];
        if (progress > lastProgress){
            listener.onProgress(progress);
            lastProgress = progress;
        }
    }

    //用于通知最终的下载结果
    @Override
    protected void onPostExecute(Integer status) {
        switch (status){
            case TYPE_SUCCESS:
                listener.onSuccess();
                break;
            case TYPE_FAILED:
                listener.onFailed();
                break;
            case TYPE_PAUSED:
                listener.onPaused();
                break;
            case TYPE_CANCELED:
                listener.onCanceled();
                break;
            default:
                break;
        }
    }
    public void pauseDownload(){
        isPaused = true;
    }

    public void cancelDownload(){
        isCanceled = true;
    }

    private long getContentLength(String downloadUrl) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(downloadUrl)
                .build();
        Response response = client.newCall(request).execute();
        if (response != null && response.isSuccessful()){
            long contentLength = response.body().contentLength();
            return contentLength;
        }
        return 0;
    }
}
