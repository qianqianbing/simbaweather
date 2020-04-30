package com.simba.themestore.launch;

import androidx.annotation.NonNull;
import androidx.viewpager2.widget.ViewPager2;

import com.blankj.utilcode.util.ResourceUtils;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.simba.themestore.R;
import com.simba.themestore.banner.NonPageTransformer;
import com.simba.themestore.base.MyBaseActivity;
import com.simba.themestore.launch.adapter.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/17
 * @Desc :
 */
public class MainActivity extends MyBaseActivity {
    private SectionsPagerAdapter sectionsPagerAdapter;
    private List<String> mData;
    private ViewPager2 viewPager;
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
        String[] menuValues = ResourceUtils.getStringArray(R.array.main_page_menu);
        for (String menu : menuValues) {
            mData.add(menu);
        }
        sectionsPagerAdapter = new SectionsPagerAdapter(this, mData);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setUserInputEnabled(false);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setPageTransformer(null);
        TabLayoutMediator tabLayoutMediator = new TabLayoutMediator(tabs, viewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                tab.setText(mData.get(position));
            }
        });
        tabLayoutMediator.attach();
    }


}
