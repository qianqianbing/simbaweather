package com.simba.violationenquiry;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.simba.violationenquiry.base.BaseActivity;
import com.simba.violationenquiry.ui.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private SectionsPagerAdapter sectionsPagerAdapter;
    private List<String> mData;
    private ViewPager viewPager;
    private ImageView ivAdd;
    private TabLayout tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected int initLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.view_pager);
        tabs = findViewById(R.id.tabs);
        ivAdd = findViewById(R.id.iv_add_car_info);
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddNewCarActivity.class);
            }
        });
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), mData, this);
        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
    }

    public void add(View view) {
        mData.add("苏A12345");
        sectionsPagerAdapter.notifyDataSetChanged();


    }

    public void delete(View view) {
        mData.remove(sectionsPagerAdapter.getCount() - 1);
        sectionsPagerAdapter.notifyDataSetChanged();
    }


}