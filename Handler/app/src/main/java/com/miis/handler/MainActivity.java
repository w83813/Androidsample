package com.miis.handler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import android.os.Handler;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    Handler AIhandler=new Handler();
    int xxx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Runnable runnable=new Runnable() {
            @Override
            public void run() {
                xxx++;
                AIhandler.postDelayed(this, 2000);
                System.out.println("tesx : " + xxx);
            }
        };

        AIhandler.postDelayed(runnable, 2000);//每两秒执行一次runnable.

        //handler.removeCallbacks(runnable);  //停止執行

    }
}
