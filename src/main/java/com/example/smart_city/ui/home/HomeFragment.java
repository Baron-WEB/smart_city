package com.example.smart_city.ui.home;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.ejlchina.okhttps.Array;
import com.ejlchina.okhttps.HttpResult;
import com.ejlchina.okhttps.Mapper;
import com.ejlchina.okhttps.OkHttps;
import com.example.smart_city.MainActivity;
import com.example.smart_city.R;
import com.example.smart_city.util.MyImageLoader;
import com.example.smart_city.util.OkHttpsConfig;
import com.google.android.material.tabs.TabLayout;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private SearchView searchView;
    private Banner banner;
    private static final String TAG = "HomeFragment";
    private TabLayout mTabLayout;
    private FrameLayout mFrameLayout;
    private int[] dictCode = new int[6];

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initView(View view) {
        banner= (Banner)view.findViewById(R.id.banner);
        searchView = view.findViewById(R.id.searchView);
        //自动展开
        searchView.setIconified(false);
        //当展开无输入内容时，隐藏关闭按钮
        searchView.onActionViewExpanded();
        searchView.clearFocus();
        searchView.setSubmitButtonEnabled(true);//显示提交按钮
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //提交按钮的点击事件
                searchView.clearFocus();
                Toast.makeText(getActivity(), query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //当输入框内容改变的时候回调
                return true;
            }
        });
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        mFrameLayout = (FrameLayout) view.findViewById(R.id.frameLayout);

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment=null;
                switch (tab.getPosition()){
                    case 0:
                        fragment=new Fragment1(dictCode[0]);
                        break;
                    case 1:
                        fragment=new Fragment1(dictCode[1]);
                        break;
                    case 2:
                        fragment=new Fragment1(dictCode[2]);
                        break;
                    case 3:
                        fragment=new Fragment1(dictCode[3]);
                        break;
                    case 4:
                        fragment=new Fragment1(dictCode[4]);
                        break;
                    case 5:
                        fragment=new Fragment1(dictCode[5]);
                        break;
                    default:
                        break;
                }
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.frameLayout,fragment);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                fragmentTransaction.commit();
            }
        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

            switch (tab.getPosition()) {

            }
        }
        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    });
    }

    private void initData() {
        //初始化banner图片初始化
//        List<Integer> banner_pic = new ArrayList<>();
//        banner_pic.add(R.drawable.banner1);
//        banner_pic.add(R.drawable.banner2);
//        banner_pic.add(R.drawable.banner3);
//        banner_pic.add(R.drawable.banner4);

        List<String> img_list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("pageNum",1);
        map.put("pageSize",10);
        map.put("type",45);
        OkHttps.async("/userinfo/rotation/list")
                .addUrlPara(map)
                .setOnResMapper((Mapper mapper)-> {
                    Log.i(TAG, "onCreate: "+mapper.getArray("rows"));
                    Array rows = mapper.getArray("rows");
                    Log.i(TAG, "onCreate: "+rows.getMapper(0).getString("imgUrl"));
                    for (int i = 0; i < rows.size(); i++) {
                        String img_url = rows.getMapper(i).getString("imgUrl");
                        img_list.add(OkHttpsConfig.BASE_URL+img_url);
                    }
                    Log.i(TAG, "onClick: "+img_list);
                    banner.setImages(img_list).setImageLoader(new ImageLoader() {
                        @Override
                        public void displayImage(Context context, Object path, ImageView imageView) {
                            Glide.with(context).load(path).into(imageView);
                        }
                    }).start();
                    
                })
                .get();
        //增加监听事件
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
            }
        });

        OkHttps.async("/system/dict/data/type/press_category")
                .setOnResMapper((Mapper mapper)-> {
                    Array data = mapper.getArray("data");
                    for (int i = 0; i < data.size(); i++) {
                        String dictLabel = data.getMapper(i).getString("dictLabel");
                        dictCode[i] = data.getMapper(i).getInt("dictCode");
                        mTabLayout.addTab(mTabLayout.newTab().setText(dictLabel));
                    }
//                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Fragment1(dictCode[0])).commit();
                })
                .get();

    }




}