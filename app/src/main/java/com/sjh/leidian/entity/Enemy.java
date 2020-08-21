package com.sjh.leidian.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.ArrayList;

public class Enemy extends Entity {
    public Bitmap bulletMap;
    public ArrayList<Ebullet> ebullets = new ArrayList<Ebullet>();
    int m = 0;//敌人子弹产生频率相关变量
    public boolean beShoot = false;//敌人是否被打中
    public int deLife=0;//子弹打中玩家1次，此变量加1


    public Enemy(int x, int y, Bitmap bitmap, Bitmap bulletMap) {
        super();
        this.x = x;
        this.y = y;
        this.bitmap = bitmap;
        this.bulletMap = bulletMap;

    }

    public boolean draw(Canvas canvas, int enemySpeed, int screen_height, int playerX, int playerY) {
        if (y > screen_height) {
            return false;
        }

        if (!beShoot) {
            Rect rect1 = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            Rect rect2 = new Rect(x - 70, y - 70, x + 70, y + 70);
            canvas.drawBitmap(bitmap, rect1, rect2, null);
            //敌机自动追踪
            if (x < (playerX - 3)) {
                x += 5;
            } else if (x > (playerX + 3)) {
                x -= 5;
            }
            y += enemySpeed;
            int mm=2*enemySpeed/5;//子弹间隔相关变量
            //生成每个敌机的子弹
            if (m % (35-mm) == 0) {
                ebullets.add(new Ebullet(x, y,enemySpeed));
            }
            m++;
               //画敌机子弹并判断是否打中玩家
            for (int i = 0; i < ebullets.size(); i++) {
                boolean shoot = false;//子弹是否打中玩家相关变量
                shoot = ebullets.get(i).draw(canvas, playerX, playerY);
                if (shoot) {
                    ebullets.remove(i);
                    i--;
                    deLife+=1;
                }


            }
        } else {
            y += enemySpeed;//假设敌机中弹后隐形继续前进
            x=-500;//敌机横向移出屏幕
            //继续画敌机子弹，但不再增加数量
            for (int i = 0; i < ebullets.size(); i++) {
                boolean shoot = false;//子弹是否打中玩家相关变量
                shoot = ebullets.get(i).draw(canvas, playerX, playerY);
                if (shoot) {
                    ebullets.remove(i);
                    i--;
                    deLife+=1;
                }
            }

        }

        return true;
    }

    class Ebullet {
        int x, y;
        Bitmap ebulletMap = bulletMap;
        int enemySpeed;

        public Ebullet(int x, int y,int enemySpeed) {
            this.x = x;
            this.y = y + 100;
            this.enemySpeed=enemySpeed;
        }

       public  boolean draw(Canvas canvas, int playerX, int playerY) {

            Rect rect1 = new Rect(0, 0, ebulletMap.getWidth(), ebulletMap.getHeight());
            Rect rect2 = new Rect(x - 40, y - 30, x + 40, y + 30);
            canvas.drawBitmap(ebulletMap, rect1, rect2, null);
            y = y + enemySpeed+enemySpeed/5;//子弹速度
            //计算子弹是否打中玩家
            if ((((x - playerX) > (-128)) && ((x - playerX) < 128)) && (((y - playerY) > (-128)) && (y - playerY) < 128)) {
                return true;
            } else {
                return false;
            }


        }
    }
}
