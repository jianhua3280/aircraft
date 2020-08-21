package com.sjh.leidian.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.sjh.leidian.R;
import com.sjh.leidian.activity.MainActivity;
import com.sjh.leidian.activity.StartGame;
import com.sjh.leidian.entity.Bullet_player;
import com.sjh.leidian.entity.Duang;
import com.sjh.leidian.entity.Enemy;
import com.sjh.leidian.entity.Player;

import java.util.ArrayList;
import java.util.Random;

public class GameSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private Bitmap background, playerBit, bullet_player_bit1, enemy_bit1,
            enemy_bit2, enemy_bit3, enemy_bit4, enemy_bit5, enemy_bit6, enemy_bit7,
            enemy_bit8, ebullet_bit, duang_bit, life_bit;
    private int height1, height2, screen_width, screen_height;
    private int backSpeed;
    private SurfaceHolder sh;
    private int enemySpeed, bullPlayerFre, enemyFre;
    private Player player;
    private ArrayList<Bullet_player> bul_list_player;
    private ArrayList<Enemy> enemies;
    public MediaPlayer mp, mpEnemyDown, mpPlayerDown;
    boolean bo = true;//玩家飞机是否会爆炸
    private Duang duang, playerDuang;//爆炸动画
    private int time = 0;//秒数


    public GameSurfaceView(Context context) {
        super(context);
        background = BitmapFactory.
                decodeResource(getResources(), R.drawable.background);
        playerBit = BitmapFactory.
                decodeResource(getResources(), R.drawable.player);
        bullet_player_bit1 = BitmapFactory.
                decodeResource(getResources(), R.drawable.bullet1_player);
        bul_list_player = new ArrayList<Bullet_player>();
        enemy_bit1 = BitmapFactory.
                decodeResource(getResources(), R.drawable.enemy1);
        enemy_bit2 = BitmapFactory.
                decodeResource(getResources(), R.drawable.enemy2);
        enemy_bit3 = BitmapFactory.
                decodeResource(getResources(), R.drawable.enemy3);
        enemy_bit4 = BitmapFactory.
                decodeResource(getResources(), R.drawable.enemy4);
        enemy_bit5 = BitmapFactory.
                decodeResource(getResources(), R.drawable.enemy5);
        enemy_bit6 = BitmapFactory.
                decodeResource(getResources(), R.drawable.enemy6);
        enemy_bit7 = BitmapFactory.
                decodeResource(getResources(), R.drawable.enemy7);
        enemy_bit8 = BitmapFactory.
                decodeResource(getResources(), R.drawable.enemy8);
        ebullet_bit = BitmapFactory.
                decodeResource(getResources(), R.drawable.ebullet1);
        duang_bit = BitmapFactory.
                decodeResource(getResources(), R.drawable.duang);
        life_bit = BitmapFactory.
                decodeResource(getResources(), R.drawable.life);
        enemies = new ArrayList<Enemy>();
        backSpeed = 20;
        enemySpeed = 25;
        enemyFre = 4000;//敌人产生间隔（毫秒）
        bullPlayerFre = 500;//玩家子弹发射间隔（毫秒）


        sh = this.getHolder();
        sh.addCallback(this);


    }


    //计算线程
    private Thread comThread = new Thread(new Runnable() {
        @Override
        public void run() {


            long cycleNum = 0;//循环次数
            Random random = new Random();


            while (true) {
                if ((cycleNum != 0) && (cycleNum % 50 == 0)) {
                    time++;//计时器每秒加1
                }
                //每20秒敌机生成变快，速度增加
                if ((cycleNum != 0) && (cycleNum % 1000 == 0)) {
                    if (enemyFre > 500) {
                        enemySpeed += 5;
                        enemyFre -= 500;
                    } else if (enemyFre > 100) {
                        enemyFre -= 100;

                    }


                }
                //产生玩家子弹
                if (player.life > 0) {
                    if (cycleNum % (bullPlayerFre / 20) == 0) {
                        Bullet_player bullet_player = new Bullet_player(player.x, player.y - 120, bullet_player_bit1);
                        bul_list_player.add(bullet_player);
                    }
                }


                //产生敌人
                if (cycleNum % (enemyFre / 20) == 0) {
                    //产生敌机随机X坐标
                    int rx = random.nextInt(screen_width - 200) + 100;
                    //产生随机样子的敌人
                    switch (random.nextInt(8)) {
                        case 0:
                            Enemy enemy1 = new Enemy(rx, 0, enemy_bit1, ebullet_bit);
                            enemies.add(enemy1);
                            break;
                        case 1:
                            Enemy enemy2 = new Enemy(rx, 0, enemy_bit2, ebullet_bit);
                            enemies.add(enemy2);
                            break;
                        case 2:
                            Enemy enemy3 = new Enemy(rx, 0, enemy_bit3, ebullet_bit);
                            enemies.add(enemy3);
                            break;
                        case 3:
                            Enemy enemy4 = new Enemy(rx, 0, enemy_bit4, ebullet_bit);
                            enemies.add(enemy4);
                            break;
                        case 4:
                            Enemy enemy5 = new Enemy(rx, 0, enemy_bit5, ebullet_bit);
                            enemies.add(enemy5);
                            break;
                        case 5:
                            Enemy enemy6 = new Enemy(rx, 0, enemy_bit6, ebullet_bit);
                            enemies.add(enemy6);
                            break;
                        case 6:
                            Enemy enemy7 = new Enemy(rx, 0, enemy_bit7, ebullet_bit);
                            enemies.add(enemy7);
                            break;
                        case 7:
                            Enemy enemy8 = new Enemy(rx, 0, enemy_bit8, ebullet_bit);
                            enemies.add(enemy8);
                            break;
                    }

                }
                //计算玩家子弹打中敌机
                if (player.life > 0) {
                    int dx;
                    int dy;
                    for (int i = 0; i < bul_list_player.size(); i++) {
                        for (int k = 0; k < enemies.size(); k++) {
                            dx = bul_list_player.get(i).x - enemies.get(k).x;
                            dy = bul_list_player.get(i).y - enemies.get(k).y;
                            if ((dx > -100 && dx < 100) && (dy > -100 && dy < 100)) {

                                bul_list_player.remove(i);
                                i--;
                                enemies.get(k).beShoot = true;
                                duang = new Duang(enemies.get(k).x, enemies.get(k).y, duang_bit);
                                mpEnemyDown.start();

                            }


                        }


                    }
                }
                //判断敌机是否撞上玩家
                for(int i=0;i<enemies.size();i++) {
                    int ex = enemies.get(i).x;
                    int ey = enemies.get(i).y;
                    if ((((ex - player.x) > (-128)) && ((ex - player.x) < 128)) && (((ey - player.y) > (-128)) && (ey - player.y) < 128)) {
                        enemies.get(i).beShoot = true;
                        duang = new Duang(enemies.get(i).x, enemies.get(i).y, duang_bit);
                        mpEnemyDown.start();
                        enemies.get(i).deLife+=1;
                    }

                }
                cycleNum++;

                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    });

    //所有画图操作线程
    private Thread drawThread = new Thread(new Runnable() {
        @Override
        public void run() {
            height1 = 0;
            height2 = 0 - screen_height;
            boolean bullet_player_seen = true;//有关玩家子弹是否可见的变量
            boolean enemy_seen = true;//有关敌机是否可见的变量
            Paint paint = new Paint();
            paint.setColor(Color.YELLOW);
            paint.setStrokeWidth(3);
            paint.setTextSize(50);

            while (true) {
                Canvas canvas = sh.lockCanvas();
                canvas.drawText(Integer.toString(time), 1950, 150, paint);
                drawBackground(canvas);//画背景
                if (duang != null) {
                    boolean dua = duang.draw(canvas);//判断是否爆炸完毕
                    if (!dua) {
                        duang = null;
                    }
                }
                if (player.life > 0) {
                    player.draw(canvas);//画玩家飞机
                    //画玩家子弹
                    for (int i = 0; i < bul_list_player.size(); i++) {
                        bullet_player_seen = bul_list_player.get(i).draw(canvas, 30);
                        if (!bullet_player_seen) {
                            bul_list_player.remove(i);
                            i--;
                        }
                    }
                } else {


                    if (bo) {
                        mpPlayerDown.start();
                        bo = false;
                        playerDuang = new Duang(player.x, player.y, duang_bit);
                    }
                    if (playerDuang != null) {
                        boolean pd = playerDuang.draw(canvas);
                        if (!pd) {
                            playerDuang = null;
                        }

                    }
                    player.x = -1000;//把玩家飞机移出屏幕
                }
                //画敌机
                for (int i = 0; i < enemies.size(); i++) {


                        enemy_seen = enemies.get(i).draw(canvas, enemySpeed, screen_height, player.x, player.y);


                    //玩家被打中减去生命值
                    player.life -= enemies.get(i).deLife;
                    enemies.get(i).deLife = 0;

                    if (!enemy_seen) {
                        enemies.remove(i);
                        i--;
                    }
                }
                //显示秒数
                canvas.drawText(Integer.toString(time), 980, 70, paint);
               //画生命图标
                int begin=0;
                for(int i=0;i<player.life;i++){
                   drawLife(canvas,begin);
                   begin+=60;
               }



                sh.unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }


        }
    });
    //画生命图标
    void drawLife(Canvas canvas,int begin){
        Rect rect1=new Rect(0,0,life_bit.getWidth(),life_bit.getHeight());
        Rect rect2=new Rect(50+begin,1960,100+begin,2010);
        canvas.drawBitmap(life_bit,rect1,rect2,null);


    }

    //单次画背景
    void drawBackground(Canvas canvas) {
        Rect rect1 = new Rect(0, 0, background.getWidth(), background.getHeight());
        Rect rect2 = new Rect(0, height1, screen_width, height1 + screen_height);
        Rect rect3 = new Rect(0, height2, screen_width, height2 + screen_height);
        canvas.drawBitmap(background, rect1, rect2, null);
        canvas.drawBitmap(background, rect1, rect3, null);

        height1 += backSpeed;
        height2 += backSpeed;
        if (height1 >= screen_height) {
            height1 = 0 - screen_height;
        }
        if (height2 >= screen_height) {
            height2 = 0 - screen_height;
        }

    }

    // 触摸屏幕事件监听
    public boolean onTouchEvent(MotionEvent event) {

        //先确定触摸点为玩家飞机
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int actX = (int) event.getX();
            int actY = (int) event.getY();
            if (((actX - player.x) > -130) && ((actX - player.x) < 130)
                    && (((actY - player.y) > -130) && ((actY - player.y) < 130))
            ) {
                player.x = (int) event.getX();
                player.y = (int) event.getY();
                player.bigMode = true;

            }
        }
        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        screen_width = getWidth();
        screen_height = getHeight();
        player = new Player(screen_width / 2, screen_height - 200, playerBit);

        comThread.start();
        drawThread.start();
        mp.setLooping(true);
        mp.start();


    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
