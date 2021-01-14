package com.example.surfaceviewdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

import java.util.Timer;
import java.util.TimerTask;

public class MyView extends SurfaceView implements SurfaceHolder.Callback {

    private Container container;
    private Rect rect;
    private Circle circle;

    private Paint paint = null;
    public MyView(Context context) {
        super(context);

        container = new Container();
        rect = new Rect();
        circle = new Circle();

        rect.addChildrenView(circle);
        container.addChildrenView(rect);

        paint = new Paint();
        paint.setColor(Color.RED);
        getHolder().addCallback(this);
    }
    public void draw(){
        Canvas canvas = getHolder().lockCanvas();
        canvas.drawColor(0xFFFFFFFF);
        container.draw(canvas);

        getHolder().unlockCanvasAndPost(canvas);
    }

    private Timer timer = null;
    private TimerTask task = null;

    public void startTimer(){
        timer = new Timer();
        task = new TimerTask() {
            @Override
            public void run() {
                draw();
            }
        };
        timer.schedule(task,100,100);
    }

    public void stopTimer(){
        if (timer != null){
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        draw();
        startTimer();
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        stopTimer();
    }
}
