package com.sjh.leidian.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Entity {
    public int x,y;
    Bitmap bitmap;
    public Entity() {

    }
    public Entity(int x, int y, Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
    }
    void draw(Canvas canvas){


    }
}
