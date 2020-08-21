package com.sjh.leidian.entity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Duang {
    public int x;
    public int y;
    public Bitmap bitmap;
    public int xdiv, ydiv, qidian;
    public int num = 0;//索引变量

    public Duang() {

    }

    public Duang(int x, int y, Bitmap bitmap) {
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        xdiv = bitmap.getWidth() / 8;
        ydiv = bitmap.getHeight() ;
        qidian = 0;

    }

    public boolean draw(Canvas canvas) {
        if (qidian >= bitmap.getWidth()) {
            return false;
        } else {
            Rect rect = new Rect(x - 70, y - 70, x + 70, y + 70);
            Rect rect1 = new Rect(qidian, 0, qidian + xdiv, ydiv);
            canvas.drawBitmap(bitmap, rect1, rect, null);
            if (num % 2 == 0) {
                qidian += xdiv;
            }

            num++;
            return true;
        }

    }
}
