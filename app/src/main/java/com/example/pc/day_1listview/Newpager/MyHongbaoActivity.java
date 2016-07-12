package com.example.pc.day_1listview.Newpager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.pc.day_1listview.R;
import com.example.pc.day_1listview.hongbao.TiXianActivity;

/**
 * 作者：朴奈Created by pc on 2016/7/8.
 */
public class MyHongbaoActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hongbao);
        Button btn= (Button) findViewById(R.id.btn_id);
        btn.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setClass(MyHongbaoActivity.this,TiXianActivity.class);
                startActivity(intent);


            }

        });

    }


}
