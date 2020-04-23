package com.simba.simbaweather.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.simba.base.base.BaseActivity;
import com.simba.simbaweather.CityManager;
import com.simba.simbaweather.ICityChangeView;
import com.simba.simbaweather.R;
import com.simba.simbaweather.ui.activity.frag.Cinema_Frag;
import com.simba.simbaweather.ui.activity.frag.Home_Frag;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements ICityChangeView {

    ViewPager showVp;


    private Home_Frag home_frag;
    ArrayList<Fragment> fragmentList = new ArrayList<>();
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

        showVp = findViewById(R.id.show_vp);

        CityManager.getInstance().registerCityChangeView(this);
        List<CityManager.CityManagerBean> cityList = CityManager.getInstance().getCityList();
        if(cityList != null && cityList.size() != 0){
            for (CityManager.CityManagerBean city : cityList){
                if(city.isLocationCity()){
                    home_frag = new Home_Frag();
                    fragmentList.add(home_frag);
                }else {
                    fragmentList.add(new Cinema_Frag(city.getCityId()));
                }
            }
        }
        showVp.setAdapter(myFragmentPagerAdapter);
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

    }

    @Override
    protected void initListener() {

    }

    @Override
    public void onCityChange(List<CityManager.CityManagerBean> cityList) {
        fragmentList = new ArrayList<>();
        fragmentList.add(home_frag);
        for(CityManager.CityManagerBean city : cityList){
            fragmentList.add(new Cinema_Frag(city.getCityId()));
        }
        synchronized(this){
            myFragmentPagerAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        CityManager.getInstance().unRegisterCityChangeView(this);
    }
}
