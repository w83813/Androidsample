package com.miis.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;
import android.os.Bundle;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    Timer AI_timer;//宣告一個時間函示
    int CountDown_time=10;//設置初始秒數
    TextView t1;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        t1=(TextView)findViewById(R.id.t1);
        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AI_timer = new Timer();//時間函示初始化
                //這邊開始跑時間執行緒
                final TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CountDown_time--;//時間倒數
                                t1.setText(CountDown_time+"second");//讓TextView元件顯示時間倒數情況
                                //if判斷示裡面放置在時間結束後想要完成的事件
                                if (CountDown_time < 1) {
                                    AI_timer.cancel();
                                    CountDown_time = 10; //讓時間執行緒保持輪迴
                                }
                            }
                        });
                    }
                };
                AI_timer.schedule(task, 1000, 1000);//時間在幾毫秒過後開始以多少毫秒執行
            }
        });

    }
}