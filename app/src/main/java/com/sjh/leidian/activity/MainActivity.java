package com.sjh.leidian.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sjh.leidian.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //点击按钮事件，预留别的按钮
    public void clickButton(View view){
       switch (view.getId()){
           case  R.id.id_button_start:
               Intent intent=new Intent(this,StartGame.class);
               startActivity(intent);
               break;



       }

    }
}