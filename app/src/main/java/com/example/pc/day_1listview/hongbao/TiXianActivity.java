package com.example.pc.day_1listview.hongbao;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pc.day_1listview.R;

/**
 * 作者：朴奈Created by pc on 2016/7/11.
 */
public class TiXianActivity extends Activity {
    private EditText weixin, number;
    ImageView iv;
    Button btn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tixian);
        weixin = (EditText) findViewById(R.id.et_weixin_id);
        number = (EditText) findViewById(R.id.et_shouji_id);
        btn = (Button) findViewById(R.id.btn_tixian_id);
        iv= (ImageView) findViewById(R.id.iv_txian_id);
        initView();
        queding();
    }

    private void queding() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(getApplicationContext(), "你输入的微信号或手机号不对，请重新输入",Toast.LENGTH_SHORT).show();
            }
        });


    }




    private void initView() {
iv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        new Thread() {
            public void run() {

                Instrumentation inst = new Instrumentation();
                inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
            }
        }.start();
    }
});
        SharedPreferences userInfo = getSharedPreferences("user_info", 0);
        String username = userInfo.getString("name", "");
        String pass = userInfo.getString("pass", "");
        weixin.setText(username);
        number.setText(pass);


    }
}
