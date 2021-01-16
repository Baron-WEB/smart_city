package com.example.smart_city.guide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smart_city.ui.login.LoginActivity;
import com.google.android.material.dialog.*;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.smart_city.MainActivity;
import com.example.smart_city.R;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private ArrayList<View> views;
    private ViewPager pager;
    private Button btn_start;
    private Button btn_setting;
    private View viewa;
    private View viewb;
    private RadioGroup radioGroup1;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    //存放图片的数组
    private int guidePic[] = {
            R.drawable.guide1,
            R.drawable.guide2,
            R.drawable.guide3,
            R.drawable.guide4,
            R.drawable.guide5,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
    }

    private void initView() {
        //获取控件
        pager = findViewById(R.id.pager);
        pager.setOnPageChangeListener(this);
        pager.setOnClickListener(this);
        //获取空间
        radioGroup1 = (RadioGroup) findViewById(R.id.radioGroup1);
        viewa = (View) View.inflate(this,R.layout.guide1,null);
        viewb = (View) View.inflate(this,R.layout.guide2,null);
        btn_start =  (Button) viewb.findViewById(R.id.btn_start);
        btn_setting = (Button) viewb.findViewById(R.id.btn_setting);
        btn_start.setOnClickListener(this);
        btn_setting.setOnClickListener(this);
    }

    private void initData() {
        //创建视图类型的集合
        views = new ArrayList<View>();
        for (int i = 0; i < guidePic.length - 1; i++) {
            //给视图上面设置图片
            viewa = (View) View.inflate(this,R.layout.guide1,null);
            viewa.setBackgroundResource(guidePic[i]);
            //将整理好的视图添加到集合里面
            views.add(viewa);
        }
        //为最后一个试图设置背景
        viewb.setBackgroundResource(guidePic[guidePic.length-1]);
        //添加到集合中
        views.add(viewb);
        //给ViewPager设置适配器
        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return views.size();//集合数量
            }

            @Override
            public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
                return view == o;
            }

            @NonNull
            @Override
            public Object instantiateItem(@NonNull ViewGroup container, int position) {
                View view1 = views.get(position);
                container.addView(view1);
                return view1;
            }

            @Override
            public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
                container.removeView((View) object);
            }
        });
        RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8,8,8,8);
        for (int i = 0; i < views.size(); i++) {
            RadioButton radioButton = new RadioButton(GuideActivity.this);
            radioButton.setWidth(20);
            radioButton.setHeight(20);
            radioButton.setBackground(getResources().getDrawable(R.drawable.tab_selector));
            radioButton.setLayoutParams(layoutParams);
            radioButton.setButtonDrawable(android.R.color.transparent);
            radioGroup1.addView(radioButton);
            int finalI = i;
            radioButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    pager.setCurrentItem(radioGroup1.indexOfChild(v));
                    pager.setCurrentItem(finalI);
                }
            });
        }
        //默认第一个按钮选中
        radioGroup1.check(radioGroup1.getChildAt(0).getId());
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start:
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
                break;
            case R.id.btn_setting:
                netWorkDialog();
                break;
        }
    }


    public void netWorkDialog() {
        //对话框
        View dialog = View.inflate(GuideActivity.this, R.layout.dialog, null);
        EditText ed_ip = dialog.findViewById(R.id.e1);
        EditText ed_port = dialog.findViewById(R.id.e2);
        ed_ip.setInputType(InputType.TYPE_TEXT_VARIATION_URI);
        ed_port.setInputType(InputType.TYPE_CLASS_NUMBER);
        resumeSetting(ed_ip, ed_port);
        new MaterialAlertDialogBuilder(GuideActivity.this)
                .setView(dialog)
                .setNegativeButton("取消", null)
                .setPositiveButton("保存", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String ip = ed_ip.getText().toString().trim();//获取IP地址
                        String port = ed_port.getText().toString().trim();//获取端口号
                        if (ip.equals("") && port.equals("")) {
                            Toast.makeText(GuideActivity.this, "请输入ip与端口号", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        saveSetting(ip,port);
                        Toast.makeText(GuideActivity.this, "保存成功！\nip:" + ip + "\n" + "port:" + port, Toast.LENGTH_SHORT).show();
                    }
                }).show();
    }

    private void saveSetting(String ip,String port) {
        editor = sharedPreferences.edit();
        editor.putString("ip",ip);
        editor.putString("port",port);
        editor.putBoolean("first",true);
        editor.apply();
    }

    private void resumeSetting(EditText ed_ip,EditText ed_port) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        ed_ip.setText(sharedPreferences.getString("ip",""));
        ed_port.setText(sharedPreferences.getString("port",""));
    }


    //页面改变按钮也跟着改变
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        radioGroup1.check(radioGroup1.getChildAt(position).getId());//页面改变则按钮改变
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}