package com.example.smart_city.ui.home;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.ejlchina.okhttps.Array;
import com.ejlchina.okhttps.Mapper;
import com.ejlchina.okhttps.OkHttps;
import com.example.smart_city.R;
import com.example.smart_city.util.OkHttpsConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Fragment1 extends Fragment implements AdapterView.OnItemClickListener {

    private ListView list_news;
    private ArrayList<Map<String,Object>> mArrayList;
    private int dictCode;

    public Fragment1(int dictCode) {
        // Required empty public constructor
        this.dictCode = dictCode;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_1, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        list_news = view.findViewById(R.id.list_news);
        list_news.setOnItemClickListener(this);
    }

    private void initData() {
        mArrayList = new ArrayList<>();
        OkHttps.async("/press/press/list?pageNum=1&pageSize=10&pressCategory="+dictCode)
                .setOnResMapper((Mapper mapper)-> {
                    Array rows = mapper.getArray("rows");
                    for (int i = 0; i < rows.size(); i++) {
                        String title = rows.getMapper(i).getString("title");
                        String content = rows.getMapper(i).getString("content");
                        String createTime = rows.getMapper(i).getString("createTime");
                        String viewsNumber = rows.getMapper(i).getString("viewsNumber");
                        String imgUrl = rows.getMapper(i).getString("imgUrl");
                        Map<String, Object> map = new HashMap<>();
                        map.put("title", title);
                        map.put("content", content);
                        map.put("createTime", createTime);
                        map.put("viewsNumber", viewsNumber+"评论");
                        mArrayList.add(map);
                    }
                    SimpleAdapter simpleAdapter = new SimpleAdapter(
                            getActivity(),
                            mArrayList,
                            R.layout.list_news,
                            new String[]{"title", "content", "createTime","viewsNumber"},
                            new int[]{R.id.tv_title, R.id.tv_content, R.id.tv_time,R.id.tv_comment}
                    );
                    list_news.setAdapter(simpleAdapter);
                })
                .get();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}