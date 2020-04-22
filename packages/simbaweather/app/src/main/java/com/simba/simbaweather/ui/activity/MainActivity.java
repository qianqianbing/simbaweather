package com.simba.simbaweather.ui.activity;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.simba.base.base.BaseActivity;
import com.simba.simbaweather.R;
import com.simba.simbaweather.ui.activity.frag.Cinema_Frag;
import com.simba.simbaweather.ui.activity.frag.Home_Frag;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

    @BindView(R.id.show_vp)
    ViewPager showVp;
    @BindView(R.id.show_tab)
    TabLayout showTab;
    private Cinema_Frag cinema_frag;
    private Home_Frag home_frag;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        ButterKnife.bind(this);
        home_frag = new Home_Frag();
        cinema_frag = new Cinema_Frag();
        ArrayList<String> title = new ArrayList<>();
        ArrayList<Fragment> list = new ArrayList<>();
        list.add(home_frag);
        list.add(cinema_frag);
        title.add("");
        title.add("");
        showTab.setTabMode(TabLayout.MODE_SCROLLABLE);
        showTab.setTabGravity(0);

        showVp.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return title.get(position);
            }
        });

        showTab.setupWithViewPager(showVp);
        showTab.setTabMode(TabLayout.MODE_FIXED);
        showTab.getTabAt(0).setText("").setIcon(R.mipmap.qiehuanone);
        showTab.getTabAt(1).setText("").setIcon(R.mipmap.qiehuantwo);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        showTab.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                showVp.setCurrentItem(position);
                if (position == 0) {
                    showTab.getTabAt(0).setText("").setIcon(R.mipmap.qiehuanone);
                    showTab.getTabAt(1).setText("").setIcon(R.mipmap.qiehuantwo);
                } else if (position == 1) {
                    showTab.getTabAt(1).setText("").setIcon(R.mipmap.qiehuanone);
                    showTab.getTabAt(0).setText("").setIcon(R.mipmap.qiehuantwo);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
