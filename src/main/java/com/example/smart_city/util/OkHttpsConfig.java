package com.example.smart_city.util;

import android.os.Handler;
import android.os.Looper;

import com.ejlchina.okhttps.Config;
import com.ejlchina.okhttps.HTTP;

public class OkHttpsConfig implements Config {
    public static String IP = "http://dasai.sdvcst.edu.cn";
    public static  String PORT = "8080";
    public static String BASE_URL = IP+":"+PORT;
    public static String TOKEN;

    // 绑定到主线程的 Handler
    private Handler mainHandler = new Handler(Looper.getMainLooper());
    @Override
    public void with(HTTP.Builder builder) {
        builder.baseUrl(BASE_URL)
//                .callbackExecutor((Runnable run) -> {
////                    runOnUiThread(run);
//        });
        .callbackExecutor(
                run -> mainHandler.post(run)
        );
    }
}
