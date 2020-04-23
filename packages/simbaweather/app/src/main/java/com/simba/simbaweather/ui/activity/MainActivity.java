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

import butterknife.BindView;
import butterknife.ButterKnife;


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
        home_frag = new Home_Frag();
        fragmentList.add(home_frag);
        CityManager.getInstance().registerCityChangeView(this);
        List<Integer> cityIdList = CityManager.getInstance().getCityIdList();
        if(cityIdList != null && cityIdList.size() != 0){
            for (Integer cityId : cityIdList){
                fragmentList.add(new Cinema_Frag(cityId));
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
    public void onCityChange(List<Integer> cityIdList) {
        fragmentList = new ArrayList<>();
        fragmentList.add(home_frag);
        for(Integer cityId : cityIdList){
            fragmentList.add(new Cinema_Frag(cityId));
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
