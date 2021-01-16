package com.example.smart_city.ui.person;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ejlchina.okhttps.Mapper;
import com.ejlchina.okhttps.OkHttps;
import com.example.smart_city.MainActivity;
import com.example.smart_city.R;
import com.example.smart_city.util.OkHttpsConfig;
import com.google.android.material.button.MaterialButton;

public class Message extends AppCompatActivity implements View.OnClickListener {

    private EditText ed_avatar;
    private EditText ed_nickname;
    private EditText ed_sex;
    private EditText ed_phone;
    private MaterialButton btn_update;
    private MaterialButton btn_quit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initView();
        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("个人信息");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ed_avatar = findViewById(R.id.ed_avatar);
        ed_nickname = findViewById(R.id.ed_nickname);
        ed_sex = findViewById(R.id.ed_sex);
        ed_phone = findViewById(R.id.ed_phone);
        btn_update = findViewById(R.id.btn_update);
        btn_quit = findViewById(R.id.btn_quit);
        btn_update.setOnClickListener(this);
        btn_quit.setOnClickListener(this);

    }

    private void initData() {
        OkHttps.async("/system/user/1")
                .addHeader("Authorization",OkHttpsConfig.TOKEN)
                .setOnResMapper((Mapper mapper)->{
                    int code = mapper.getInt("code");
                    Log.i("...", "onCreate: "+OkHttpsConfig.TOKEN);
                    if (code == 200) {
                         Mapper data = mapper.getMapper("data");
                        Log.i("", "initData: "+data);
                         ed_avatar.setText(OkHttpsConfig.BASE_URL+data.getString("avatar"));
                         ed_nickname.setText(data.getString("nickName"));
                         ed_sex.setText((data.getString("sex") == "1")?"男": "女");
                         ed_phone.setText(data.getString("phonenumber"));
                    }else {
                        Toast.makeText(this, "网络错误请重试", Toast.LENGTH_SHORT).show();
                    }
                })
                .get();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_update:
                updateMsg();
                break;
            case R.id.btn_quit:
                finish();
                break;
            default:
                break;
        }
    }

    private void updateMsg() {

    }
}