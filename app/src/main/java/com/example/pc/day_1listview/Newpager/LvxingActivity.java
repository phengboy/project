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
import com.example.pc.day_1listview.jiebanlvxing.FabuFragment;
import com.example.pc.day_1listview.jiebanlvxing.GuanzhuFragment;
import com.example.pc.day_1listview.jiebanlvxing.RemenFragment;

import java.util.ArrayList;
import java.util.List;

public class LvxingActivity extends FragmentActivity {

    private List<Fragment> mFraList = new ArrayList<Fragment>();
    private FragmentAdapter mAdapter;

    private ViewPager mPaVp;
    /**
     * Tab显示内容TextView
     */
    private TextView mremen, mguanzhu, mfabu;
    /**
     * Tab的那个引导线
     * 返回、编辑
     */
    private ImageView mTabLineIv,back,write;

    /**
     * Fragment
     */
    private RemenFragment mRemenFg;
    private GuanzhuFragment mGuanzhuFg;
    private FabuFragment mFabuFg;
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
        setContentView(R.layout.jieban);
        findById();
        init();
        initTabLineWidth();

    }

    private void findById() {
        mremen = (TextView) findViewById(R.id.tv_remen_id);
        mguanzhu = (TextView) findViewById(R.id.tv_guanzhu_id);
        mfabu = (TextView) findViewById(R.id.tv_fa_id);
        mTabLineIv = (ImageView) findViewById(R.id.id_tab_line_iv_jieban);
        mPaVp = (ViewPager) findViewById(R.id.id__jieban_vp);
        back= (ImageView) findViewById(R.id.iv_jban_id);
        write= (ImageView) findViewById(R.id.iv_write_id);
        mremen.setOnClickListener(new MyOnClickListener(0));
        mguanzhu.setOnClickListener(new MyOnClickListener(1));
        mfabu.setOnClickListener(new MyOnClickListener(2));
        back.setOnClickListener(new View.OnClickListener() {

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
            mPaVp.setCurrentItem(index);
        }
    }


    private void init() {

        mRemenFg = new RemenFragment();
        mGuanzhuFg = new GuanzhuFragment();
        mFabuFg = new FabuFragment();
        mFraList.add(mRemenFg);
        mFraList.add(mGuanzhuFg);
        mFraList.add(mFabuFg);
        mAdapter = new FragmentAdapter(this.getSupportFragmentManager(), mFraList);
        mPaVp.setAdapter(mAdapter);
        mPaVp.setCurrentItem(0);
        mPaVp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            /**
             * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
             */
            @Override
            public void onPageScrollStateChanged(int state) {

            }

            /**
             * position :当前页面，及你点击滑动的页面 offset:当前页面偏移的百分比
             * offsetPixels:当前页面偏移的像素位置
             */
            @Override
            public void onPageScrolled(int position, float offset,
                                       int offsetPixels) {
                LinearLayout.LayoutParams gp = (LinearLayout.LayoutParams) mTabLineIv
                        .getLayoutParams();

                //Log.e("offset:", offset + "");
                /**
                 * 利用currentIndex(当前所在页面)和position(下一个页面)以及offset来
                 * 设置mTabLineIv的左边距 滑动场景：
                 * 记3个页面,
                 * 从左到右分别为0,1,2
                 * 0->1; 1->2; 2->1; 1->0
                 */
                if (currentIndex == 0 && position == 0)// 0->1
                {
                    gp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 0) // 1->0
                {
                    gp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));

                } else if (currentIndex == 1 && position == 1) // 1->2
                {
                    gp.leftMargin = (int) (offset * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                } else if (currentIndex == 2 && position == 1) // 2->1
                {
                    gp.leftMargin = (int) (-(1 - offset)
                            * (screenWidth * 1.0 / 3) + currentIndex
                            * (screenWidth / 3));
                }
                mTabLineIv.setLayoutParams(gp);
            }

            @Override
            public void onPageSelected(int position) {
                resetTextView();
                switch (position) {
                    case 0:
                        mremen.setTextColor(Color.BLUE);
                        break;
                    case 1:
                        mguanzhu.setTextColor(Color.BLUE);
                        break;
                    case 2:
                        mfabu.setTextColor(Color.BLUE);
                        break;
                }
                currentIndex = position;
            }


        });
    }

    private void initTabLineWidth() {
        DisplayMetrics dpMetrics = new DisplayMetrics();
        getWindow().getWindowManager().getDefaultDisplay()
                .getMetrics(dpMetrics);
        screenWidth = dpMetrics.widthPixels;
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mTabLineIv
                .getLayoutParams();
        lp.width = screenWidth / 3;
        mTabLineIv.setLayoutParams(lp);

    }

    private void resetTextView() {
        mremen.setTextColor(Color.BLACK);

        mguanzhu.setTextColor(Color.BLACK);
        mfabu.setTextColor(Color.BLACK);
    }

}