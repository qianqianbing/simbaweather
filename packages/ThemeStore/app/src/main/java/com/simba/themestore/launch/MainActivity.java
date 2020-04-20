package com.simba.themestore.launch;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.android.material.tabs.TabLayout;
import com.simba.base.UI.view.NoScrollViewPager;
import com.simba.themestore.R;
import com.simba.themestore.banner.ImageAdapter;
import com.simba.themestore.base.MyBaseActivity;
import com.simba.themestore.launch.adapter.SectionsPagerAdapter;
import com.simba.themestore.model.DataBean;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/17
 * @Desc :
 */
public class MainActivity extends MyBaseActivity implements OnPageChangeListener {
    private SectionsPagerAdapter sectionsPagerAdapter;
    private List<String> mData;
    private NoScrollViewPager viewPager;
    private ImageView ivAdd;
    private TabLayout tabs;
    private RelativeLayout rlEmpty;
    private LinearLayout optionLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
//        banner = findViewById(R.id.banner);
//
//        //设置适配器
//        ImageAdapter adapter = new ImageAdapter(DataBean.getTestData());
//        banner.setAdapter(adapter);
//        //设置指示器
//        banner.setIndicator(new CircleIndicator(this));
//        //设置点击事件
//        //添加切换监听
//        banner.addOnPageChangeListener(this);
//        //圆角
//        banner.setBannerRound(30);
//
//        banner.setBannerGalleryMZ(60);
        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);

    }

    @Override
    protected void initData() {
        mData=new ArrayList<>();
        mData.add("主题");
        mData.add("壁纸");
        mData.add("屏保");
        mData.add("我的");
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mData, this);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
