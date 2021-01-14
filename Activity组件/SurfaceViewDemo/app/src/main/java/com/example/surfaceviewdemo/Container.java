package com.example.surfaceviewdemo;


import android.graphics.Canvas;

import java.util.ArrayList;
import java.util.List;

public class Container {

    private List<Container> children = null;
    private float x = 0,y = 0;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public Container(){
        children = new ArrayList<>();
    }

    public void draw(Canvas canvas){
        canvas.save();
        canvas.translate(getX(),getY());
        childrenView(canvas);
        for (Container c:children){
            c.draw(canvas);
        }
        canvas.restore();
    }

    public void childrenView(Canvas canvas){

    }

    public void addChildrenView(Container child){
        children.add(child);
    }

    public void removeChildrenView(Container child){
        children.remove(child);
    }
}
