package com.example.smart_city.ui.person;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.ejlchina.okhttps.Mapper;
import com.ejlchina.okhttps.OkHttps;
import com.example.smart_city.MainActivity;
import com.example.smart_city.R;
import com.example.smart_city.util.OkHttpsConfig;
import com.google.android.material.button.MaterialButton;

public class Advice extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_advice;
    private MaterialButton btn_post;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advice);
        initView();
        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("意见反馈");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ed_advice = findViewById(R.id.ed_advice);
        btn_post = findViewById(R.id.btn_post);
        btn_post.setOnClickListener(v -> {
            OkHttps.async("/userinfo/feedback")
                    .bodyType(OkHttps.JSON)
                    .addHeader("Authorization",OkHttpsConfig.TOKEN)
                    .addBodyPara("content", ed_advice.getText())
                    .addBodyPara("userId", "18")
                    .setOnResMapper((Mapper mapper)->{
                        Log.i("11", "initView: "+mapper);
                        int code = mapper.getInt("code");
                        if (code == 200) {
                            ed_advice.setText("");
                            Toast.makeText(this, mapper.getString("msg"), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(this, "网络出错，请重试", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .post();
        });
    }

    private void initData() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }
}