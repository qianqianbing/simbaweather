package com.simba.themestore.launch;

import com.google.android.material.tabs.TabLayout;
import com.simba.base.UI.view.NoScrollViewPager;
import com.simba.themestore.R;
import com.simba.themestore.base.MyBaseActivity;
import com.simba.themestore.launch.adapter.SectionsPagerAdapter;
import com.youth.banner.listener.OnPageChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/17
 * @Desc :
 */
public class MainActivity extends MyBaseActivity  {
    private SectionsPagerAdapter sectionsPagerAdapter;
    private List<String> mData;
    private NoScrollViewPager viewPager;
    private TabLayout tabs;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);

    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        mData.add("主题");
        mData.add("壁纸");
        mData.add("屏保");
        mData.add("我的");
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mData, this);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }


}
