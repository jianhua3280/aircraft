package com.sjh.leidian.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;

import com.sjh.leidian.R;
import com.sjh.leidian.view.GameSurfaceView;

public class StartGame extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        GameSurfaceView gameSurfaceView=new GameSurfaceView(this);

        gameSurfaceView.mp= MediaPlayer.create(this, R.raw.gamemusic);
        gameSurfaceView.mpEnemyDown= MediaPlayer.create(this, R.raw.enemybomb);
        gameSurfaceView.mpEnemyDown.setVolume(0.5f,0.5f);
        gameSurfaceView.mpPlayerDown=MediaPlayer.create(this, R.raw.playbomb);
        setContentView(gameSurfaceView);

    }


}