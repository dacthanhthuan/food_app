package com.dtt.thanhthuan.bottomnavigation;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000); // Set thời gian 5 giây hiển thị màn hình SplashScreen
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashScreen.this, GetStartedActivity.class);
                    finish();
                    startActivity(intent);
                }
            }
        };
        timer.start();
    }
}

