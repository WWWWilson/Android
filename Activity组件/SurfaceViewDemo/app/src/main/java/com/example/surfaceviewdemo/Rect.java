package com.example.surfaceviewdemo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Rect extends Container{
    private Paint paint = null;
    public Rect(){
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    public void childrenView(Canvas canvas){
        super.childrenView(canvas);
        canvas.drawRect(200,200,0,0,paint);
        this.setY(this.getY()+1);
    }
}
