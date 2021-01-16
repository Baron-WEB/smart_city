package com.example.smart_city.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.ejlchina.okhttps.HttpResult;
import com.ejlchina.okhttps.Mapper;
import com.ejlchina.okhttps.OkHttps;
import com.example.smart_city.MainActivity;
import com.example.smart_city.R;
import com.example.smart_city.util.OkHttpsConfig;
import com.google.android.material.button.MaterialButton;

public class LoginActivity extends AppCompatActivity{

    private EditText ed_username;
    private EditText ed_psd;
    private MaterialButton btn_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("登录");
        ed_username = findViewById(R.id.ed_username);
        ed_psd = findViewById(R.id.ed_psd);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(v -> {
                    OkHttps.async("/login")
                .bodyType(OkHttps.JSON)
                .addBodyPara("username", ed_username.getText())
                .addBodyPara("password", ed_psd.getText())
                .setOnResMapper((Mapper mapper)->{
                    Log.i("11", "initView: "+mapper);
                    OkHttpsConfig.TOKEN = mapper.getString("token");
                    int code = mapper.getInt("code");
                    Log.i("...", "onCreate: "+OkHttpsConfig.TOKEN);
                    if (code == 200) {
                        startActivity(new Intent(this, MainActivity.class));
                    }else {
                        Toast.makeText(this, "请输入正确的用户名/密码", Toast.LENGTH_SHORT).show();
                    }
                })
                .post();
        });
    }

    private void initData() {


    }

}