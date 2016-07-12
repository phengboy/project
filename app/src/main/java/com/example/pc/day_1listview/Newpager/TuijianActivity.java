package com.example.pc.day_1listview.Newpager;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import com.example.pc.day_1listview.R;

/**
 * 作者：朴奈Created by pc on 2016/7/8.
 */
public class TuijianActivity extends FragmentActivity {
    WebView wvTest;
    Button weixin,friends,zone,qq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuijian);
        init();
        wvTest = (WebView) this.findViewById(R.id.tuijian_id);
        wvTest.setWebViewClient(new WebViewClient() {
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error){
                //handler.cancel(); // Android默认的处理方式
                handler.proceed();  // 接受所有网站的证书
                //handleMessage(Message msg); // 进行其他处理
            }
        });
        wvTest.getSettings().setJavaScriptEnabled(true);
        wvTest.getSettings().setDefaultTextEncodingName("gb2312");
        wvTest.loadUrl("http://m.e-traveltech.com/activity/index/recommend?device_id=aimei865290020503687&plat=aphone&os_version=4.4.4&version=3.2.1&dev_model=U3&net_type=WIFI&language=1&app=3&channel_id=1110101&invitation_code=&t=1468286780&user_id=145224&s=4e77db328ee97a93cf4c014eb0f0843a");

    }

    private void init() {
        weixin= (Button) findViewById(R.id.btn_weixin_id);
        friends= (Button) findViewById(R.id.btn_friends_id);
        zone= (Button) findViewById(R.id.btn_zone_id);
        qq= (Button) findViewById(R.id.btn_qq_id);
        weixin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        friends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        zone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        qq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
