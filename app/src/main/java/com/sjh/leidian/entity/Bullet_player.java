package com.sjh.leidian.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Bullet_player extends Entity {
    public Bullet_player(int x, int y, Bitmap bitmap) {
        super(x, y, bitmap);
    }


    public boolean draw(Canvas canvas, int speed) {
        if (y <= 0) {
            return false;
        }
        Rect rect1 = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        Rect rect2 = new Rect(x - 60, y - 50, x + 60, y + 50);
        y = y - speed;

        canvas.drawBitmap(bitmap, rect1, rect2, null);
        return true;

    }
}
