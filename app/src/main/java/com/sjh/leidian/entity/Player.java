package com.sjh.leidian.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Player extends Entity {
    public int life = 3;
    public boolean bigMode = false;

    public Player(int x, int y, Bitmap bitmap) {
        super(x, y, bitmap);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (!bigMode) {
            Rect rect1 = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            Rect rect2 = new Rect(x - 100, y - 100, x + 100, y + 100);
            canvas.drawBitmap(bitmap, rect1, rect2, null);
        } else {
            Rect rect1 = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());

            Rect rect2 = new Rect(x - 150, y - 150, x + 150, y + 150);
            canvas.drawBitmap(bitmap, rect1, rect2, null);
            bigMode = false;

        }


    }
}
