package com.example.smart_city.ui.person;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.ejlchina.okhttps.Array;
import com.ejlchina.okhttps.Mapper;
import com.ejlchina.okhttps.OkHttps;
import com.example.smart_city.R;
import com.example.smart_city.util.OkHttpsConfig;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderList extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    private ListView order_list;
    private ArrayList<Map<String,Object>> mArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        initView();
        initData();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("订单列表");
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        order_list = findViewById(R.id.order_list);
        order_list.setOnItemClickListener(this);


    }

    private void initData() {
        mArrayList = new ArrayList<>();
        OkHttps.async("/userinfo/orders/list?pageNum=1&pageSize=10")
                .addHeader("Authorization",OkHttpsConfig.TOKEN)
                .setOnResMapper((Mapper mapper)-> {
                    Array data = mapper.getArray("data");
                    for (int i = 0; i < data.size(); i++) {
                        String orderNum = data.getMapper(i).getString("orderNum");
                        String path = data.getMapper(i).getString("path");
                        String date = data.getMapper(i).getString("createTime");
                        Map<String, Object> map = new HashMap<>();
                        map.put("orderNum", "订单号码:"+orderNum);
                        map.put("path", "订单类型:地铁"+path);
                        map.put("date", "订单日期:"+date);
                        mArrayList.add(map);
                    }
                    SimpleAdapter simpleAdapter = new SimpleAdapter(
                            this,
                            mArrayList,
                            R.layout.list_order,
                            new String[]{"orderNum", "path", "date"},
                            new int[]{R.id.tv_order_number, R.id.tv_order_type, R.id.tv_order_date}
                    );
                    order_list.setAdapter(simpleAdapter);
                })
                .get();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String,Object> map=mArrayList.get(position);
        Intent intent = new Intent(this,OrderDetail.class);
        intent.putExtra("orderNum",(String) map.get("orderNum"));
        startActivity(intent);
    }
}