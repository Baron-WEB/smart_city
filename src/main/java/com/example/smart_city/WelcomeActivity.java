package com.example.smart_city;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;

import com.example.smart_city.guide.GuideActivity;
import com.example.smart_city.ui.login.LoginActivity;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_welcome);

        final SharedPreferences sharedPreferences = getSharedPreferences("config", Context.MODE_PRIVATE);

        //发送一个延迟的消息
        new Handler() {
            public void handleMessage(android.os.Message msg) {
                //接受到消息后的处理
                boolean b = sharedPreferences.getBoolean("is_first", true);

                if (b) {
                    //第一次进入应用，进入导航界面
                    sharedPreferences.edit().putBoolean("is_first", false).commit();
                    Intent intent = new Intent(WelcomeActivity.this, GuideActivity.class);
                    startActivity(intent);
                } else {
                    //不是第一次进入应用,直接跳转到主界面
                    Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            };
        }.sendEmptyMessageDelayed(0, 3000);
    }
}