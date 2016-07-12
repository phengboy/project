package com.example.pc.day_1listview.Newpager;

import android.app.Instrumentation;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.pc.day_1listview.Myadapter.FragmentAdapter;
import com.example.pc.day_1listview.R;
import com.example.pc.day_1listview.myyouji.LocalFragment;
import com.example.pc.day_1listview.myyouji.YunFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：朴奈Created by pc on 2016/7/7.
 */
public class MyYJActivity extends FragmentActivity {


    private List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private FragmentAdapter mFragmentAdapter;
    private int bottomLineWidth;
    private ViewPager mPageVp;
    /**
     * Tab显示内容TextView
     */
    private TextView local, yun;
    /**
     * Tab的那个引导线
     */
    private ImageView mTabline;
    private ImageView imageView;
    /**
     * 图片add的实例
     */
    private ImageView addiv;

    /**
     * Fragment
     */
    private LocalFragment mLocalFg;
    private YunFragment mYunFg;
    /**
     * ViewPager的当前选中页
     */
    private int currentIndex;
    /**
     * 屏幕的宽度
     */
    private int screenWidth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myyj);
        findById();
        init();
        initTabLineWidth();

    }

    private void findById() {
        imageView=(ImageView) findViewById(R.id.iv_my_id);
        local = (TextView) findViewById(R.id.local_my_id);
        yun = (TextView) findViewById(R.id.yun_my_id);
        mPageVp = (ViewPager) findViewById(R.id.id_page_vp);
        mTabline = (ImageView) findViewById(R.id.id_tab_line_iv);
        //addiv = (ImageView) findViewById(R.id.iv_my_add_id);
        local.setOnClickListener(new MyOnClickListener(0));
        yun.setOnClickListener(new MyOnClickListener(1));
        imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
// TODO Auto-generated method stub
                new Thread() {
                    public void run() {

                            Instrumentation inst = new Instrumentation();
                            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        }
                }.start();
            }
        });
    }

    public class MyOnClickListener implements View.OnClickListener {
        private int index = 0;

        public MyOnClickListener(int i) {
            index = i;i++;
        }

        @Override
        public void onClick(View v) {
            mPageVp.setCurrentItem(index);
        }
    }
    /**
     * 设置滑动条的宽度为屏幕的1/2(根据Tab的个数而定)
     */
    private void initTabLineWidth() {
        bottomLineWidth = mTabline.getLayoutParams().width;
//        Log.d(TAG, "cursor imageview width=" + bottomLineWidth);
//        DisplayMetrics dm = new DisplayMetrics();
//        getWindowManager().getDefaultDisplay().getMetrics(dm);
//        int screenW = dm.widthPixels;
//        offset = (int) ((screenW / 2.0 - bottomLineWidth) / 2);
//        Log.i("MainActivity", "offset=" + offset);
//        position_one = (int) (screenW / 4.0);
//        position_two = position_one * 2;
//        position_three = position_one * 3;
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabline
                .getLayoutParams();

        lp.width = (screenWidth / 2-bottomLineWidth)/2;

        mTabline.setLayoutParams(lp);
    }

    /**
     * 初始化fragment
     */
    private void init() {
        mLocalFg = new LocalFragment();
        mYunFg = new YunFragment();
        mFragmentList.add(mLocalFg);
        mFragmentList.add(mYunFg);
        mFragmentAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFragmentList);
        //设置适配器
        mPageVp.setAdapter(mFragmentAdapter);
        //当前的条目
        mPageVp.setCurrentItem(0);
        mPageVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {

            }

            /**
             * position：当前的页面，及你点击滑动的页面
             * positionOffset：当前页面偏移的百分比
             * positionOffsetPixels：当前页面偏移的乡俗位置
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                LinearLayout.LayoutParams Ip = (LinearLayout.LayoutParams) mTabline.getLayoutParams();
                Log.e("positionOffset:", positionOffset + "");
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及positionOffset来
                 * 设置mTabLine的左边距 滑动场景
                 * 记2个页面,
                 * 从左到右分别为0,1,2
                 * 0->1; 1->0
                 */
                if (currentIndex == 0 && position == 0)//0-->1
                {
                    Ip.leftMargin = (int) (positionOffset * (screenWidth * 1.0 / 2) + currentIndex
                            * (screenWidth / 2));
                } else if (currentIndex == 1 && position == 0)//1-->0
                {
                    Ip.leftMargin = (int) (-(1 - positionOffset) * (screenWidth * 1.0 / 2) + currentIndex * (screenWidth / 2));
                }
                mTabline.setLayoutParams(Ip);

            }

            /**
             *position：当前的页面，及你点击滑动的页面
             */
            @Override
            public void onPageSelected(int position) {

                resetTextView();
                switch (position) {
                    case 0:
                        local.setTextColor(Color.RED);
                        //yun.setEnabled(false);
                        break;
                    case 1:
                        yun.setTextColor(Color.RED);
                       // yun.setEnabled(true);
                        break;
                }
                currentIndex = position;
            }

        });
    }

    /**
     * 重置颜色
     */
    private void resetTextView() {
        local.setTextColor(Color.BLACK);
        yun.setTextColor(Color.BLACK);

    }


}
