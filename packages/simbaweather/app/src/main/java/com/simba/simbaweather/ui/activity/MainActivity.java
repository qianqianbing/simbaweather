package com.simba.simbaweather.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.simba.base.base.BaseActivity;
import com.simba.simbaweather.CityManager;
import com.simba.simbaweather.ICityChangeView;
import com.simba.simbaweather.R;
import com.simba.simbaweather.ui.activity.frag.Cinema_Frag;
import com.simba.simbaweather.ui.activity.frag.Home_Frag;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity implements ICityChangeView {

    ViewPager showVp;
    @BindView(R.id.img_location)
    ImageView imgLocation;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_refreshtime)
    ImageView tvRefreshtime;
    @BindView(R.id.tv_runacity)
    TextView tvRunacity;
    @BindView(R.id.show_tab)
    TabLayout showTab;


    private Home_Frag home_frag;
    ArrayList<Fragment> fragmentList = new ArrayList<>();
    ArrayList<String> title = new ArrayList<>();
    private Runnable update;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        showVp = findViewById(R.id.show_vp);
        home_frag = new Home_Frag();
        fragmentList.add(home_frag);
        title.add("");
        CityManager.getInstance().registerCityChangeView(this);

        List<CityManager.CityManagerBean> cityIdList = CityManager.getInstance().getCityList();
        if (cityIdList != null && cityIdList.size() != 0) {
            for (CityManager.CityManagerBean city : cityIdList) {
                if (city.isLocationCity()) {
                    home_frag = new Home_Frag();
                    fragmentList.add(home_frag);
                } else {
                    fragmentList.add(new Cinema_Frag(city.getCityId()));
                }
            }
        }
        /**
         * 设置tab为可滚动模式
         */


        /**
         * viewpager加载适配器
         */
        showVp.setAdapter(myFragmentPagerAdapter);
        /**
         * tablayout关联viewpager
         */
        showTab.setupWithViewPager(showVp);

        showTab.getTabAt(0).setText("").setIcon(R.mipmap.stripswitch);
        showTab.getTabAt(1).setText("").setIcon(R.mipmap.circledrop);
//        showTab.getTabAt(2).setText("").setIcon(R.mipmap.circledrop);
//        showTab.getTabAt(3).setText("").setIcon(R.mipmap.circledrop);
//        showTab.getTabAt(4).setText("").setIcon(R.mipmap.circledrop);
        showVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    Log.i("tian", "onPageSelected: " + position);
                    showTab.getTabAt(0).setText("").setIcon(R.mipmap.stripswitch);
                    showTab.getTabAt(1).setText("").setIcon(R.mipmap.circledrop);
//                    showTab.getTabAt(2).setText("").setIcon(R.mipmap.circledrop);
//                    showTab.getTabAt(3).setText("").setIcon(R.mipmap.circledrop);
//                    showTab.getTabAt(4).setText("").setIcon(R.mipmap.circledrop);
                } else if (position == 1) {
                    Log.i("tian", "onPageSelected: " + position);
                    showTab.getTabAt(0).setText("").setIcon(R.mipmap.circledrop);
                    showTab.getTabAt(1).setText("").setIcon(R.mipmap.stripswitch);
//                    showTab.getTabAt(2).setText("").setIcon(R.mipmap.circledrop);
//                    showTab.getTabAt(3).setText("").setIcon(R.mipmap.circledrop);
//                    showTab.getTabAt(4).setText("").setIcon(R.mipmap.circledrop);
                } else if (position == 2) {
                    Log.i("tian", "onPageSelected: " + position);
                    showTab.getTabAt(0).setText("").setIcon(R.mipmap.circledrop);
                    showTab.getTabAt(1).setText("").setIcon(R.mipmap.circledrop);
//                    showTab.getTabAt(2).setText("").setIcon(R.mipmap.stripswitch);
//                    showTab.getTabAt(3).setText("").setIcon(R.mipmap.circledrop);
//                    showTab.getTabAt(4).setText("").setIcon(R.mipmap.circledrop);
                } else if (position == 3) {
                    Log.i("tian", "onPageSelected: " + position);
                    showTab.getTabAt(0).setText("").setIcon(R.mipmap.circledrop);
                    showTab.getTabAt(1).setText("").setIcon(R.mipmap.circledrop);
//                    showTab.getTabAt(2).setText("").setIcon(R.mipmap.circledrop);
//                    showTab.getTabAt(3).setText("").setIcon(R.mipmap.stripswitch);
//                    showTab.getTabAt(4).setText("").setIcon(R.mipmap.circledrop);
                } else if (position == 4) {
                    Log.i("tian", "onPageSelected: " + position);
                    showTab.getTabAt(0).setText("").setIcon(R.mipmap.circledrop);
                    showTab.getTabAt(1).setText("").setIcon(R.mipmap.circledrop);
//                    showTab.getTabAt(2).setText("").setIcon(R.mipmap.circledrop);
//                    showTab.getTabAt(3).setText("").setIcon(R.mipmap.circledrop);
//                    showTab.getTabAt(4).setText("").setIcon(R.mipmap.stripswitch);
                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    FragmentPagerAdapter myFragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
        @NonNull
        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "";
        }
    };

    @Override
    protected void initData() {
        home_frag.setItemOnClickInterface(new Home_Frag.onlisteneritem() {
            @Override
            public void onItemClick(String city, String district) {
                Log.i("11111111", "onItemClick: "+city+district);
                tvCity.setText(""+city+"·"+district);
            }
        });

    }

    @Override
    protected void initListener() {

    }


    @Override
    public void onCityChange(List<CityManager.CityManagerBean> cityList) {
        fragmentList = new ArrayList<>();
        fragmentList.add(home_frag);
        for (CityManager.CityManagerBean city : cityList) {
            fragmentList.add(new Cinema_Frag(city.getCityId()));
        }
        synchronized (this) {
            myFragmentPagerAdapter.notifyDataSetChanged();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        CityManager.getInstance().unRegisterCityChangeView(this);
    }


    @OnClick({R.id.tv_refreshtime, R.id.tv_runacity})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_refreshtime:
                Time t = new Time(); // or Time t=new Time("GMT+8"); 加上Time Zone资料。
                t.setToNow(); // 取得系统时间。
                int year = t.year;
                int month = t.month + 1;
                int day = t.monthDay;
                int hour = t.hour; // 0-23
                int minute = t.minute;
                int second = t.second;
//                2020-03-23 19:32
                tvTime.setText("中国天气  更新于：" + year + "-" + month + "-" + day + "  " + hour + ":" + minute);
                // home_frag.fPresenter.WeathershowRequestData("" + 32.298741, "" + 118.840485);
                Handler handler = new Handler();

                Runnable mRunnable = new Runnable() {
                    public void run() {
                        Message msg = new Message();
                        handler.sendMessage(msg);
                    }
                };
                /**
                 * 页面刷新点击一次
                 * */
                Handler h
                        = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {

                        Toast.makeText(MainActivity.this, "收到啦" + msg, Toast.LENGTH_LONG).show();
                        home_frag.fPresenter.WeathershowRequestData("" + 32.298741, "" + 118.840485);
                    }
                };

                break;
            case R.id.tv_runacity:
                Intent intent = new Intent(this, CityManagerActivity.class);
                startActivity(intent);
                break;
        }
    }

}
