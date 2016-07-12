package com.example.pc.day_1listview.remen;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 作者：朴奈Created by pc on 2016/7/11.
 */
public class MyApp  extends Application{
    private static MyApp app;
    private RequestQueue requestQueue;

    @Override
    public void onCreate() {
        super.onCreate();
        this.app=this;
        //实例化Volley
        initVolley();
    }

    private void initVolley() {
        requestQueue = Volley.newRequestQueue(this);

    }

    public static MyApp getApp() {
        return app;
    }

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
}