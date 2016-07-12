package com.example.pc.day_1listview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pc.day_1listview.Myadapter.MyAd;
import com.example.pc.day_1listview.Newpager.LvxingActivity;
import com.example.pc.day_1listview.Newpager.LvxingMoneyActivity;
import com.example.pc.day_1listview.Newpager.MyDingdanActivity;
import com.example.pc.day_1listview.Newpager.MyHongbaoActivity;
import com.example.pc.day_1listview.Newpager.MyJifengActivity;
import com.example.pc.day_1listview.Newpager.MyMsegActivity;
import com.example.pc.day_1listview.Newpager.MyShoucangActivity;
import com.example.pc.day_1listview.Newpager.MyYJActivity;
import com.example.pc.day_1listview.Newpager.TuijianActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {

private ImageView user_logo;
    TextView usernikname;
    private ListView listView;
    // 申请的id
    public String mAppid = "222222";
    private ArrayAdapter<String> adapter;
    int[] imagview = {R.mipmap.youji_icon, R.mipmap.gowith_icon, R.mipmap.score_icon, R.mipmap.my_hongbao_ic, R.mipmap.order_icon, R.mipmap.msg_icon, R.mipmap.collect_ic, R.mipmap.lyjj_icon, R.mipmap.award_icon};
    String[] title = {"我的游记", "结伴旅行", "我的积分", "我的红包", "我的订单", "我的消息", "我的收藏", "旅行基金", "推荐有奖"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mfragment);

        init();
        List<Map<String, Object>> list = getData();
        listView.setAdapter(new MyAd(this, list));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                setTitle("点击第"+position+"个项目");
                Intent intent = null;
                switch (position)
                {
                    case 0:intent = new Intent(MainActivity.this, MyYJActivity.class);break;
                    case 1:intent = new Intent(parent.getContext(), LvxingActivity.class);break;
                    case 2:intent=new Intent(parent.getContext(),MyJifengActivity.class);break;
                    case 3:intent=new Intent(parent.getContext(),MyHongbaoActivity.class);break;
                    case 4:intent=new Intent(parent.getContext(),MyDingdanActivity.class);break;
                    case 5:intent=new Intent(parent.getContext(),MyMsegActivity.class);break;
                    case 6:intent=new Intent(parent.getContext(),MyShoucangActivity.class);break;
                    case 7:intent=new Intent(parent.getContext(),LvxingMoneyActivity.class);break;
                    case 8:intent=new Intent(parent.getContext(),TuijianActivity.class);break;
                }
               startActivity(intent);

            }
        });
    }

    private void init() {
        user_logo= (ImageView) findViewById(R.id.user_logo);
        listView = (ListView) findViewById(R.id.list);
        usernikname= (TextView) findViewById(R.id.user_nickname);

    }


    public List<Map<String, Object>> getData() {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < imagview.length; i++) {
            ImageView iv = new ImageView(this);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("image", imagview[i]);
            map.put("title", title[i]);
            map.put("info", R.mipmap.set_item_right_icon);
            list.add(map);
        }
        return list;

    }


}
