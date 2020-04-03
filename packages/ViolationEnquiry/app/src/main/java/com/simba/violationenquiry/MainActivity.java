package com.simba.violationenquiry;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;
import com.simba.violationenquiry.ui.SectionsPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private SectionsPagerAdapter sectionsPagerAdapter;
    private List<String> mData;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme);
        setContentView(R.layout.activity_main);

        mData = new ArrayList<>();
        sectionsPagerAdapter = new SectionsPagerAdapter( getSupportFragmentManager(), mData, this);

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);


    }

    public void add(View view) {
//        mData.add("苏A12345");
//        sectionsPagerAdapter.notifyDataSetChanged();

        QMUITipDialog tipDialog = new QMUITipDialog.Builder(getApplicationContext())
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_SUCCESS)
                .setTipWord("发送成功")
                .create();
        tipDialog.show();
    }

    public void delete(View view) {
        mData.remove(sectionsPagerAdapter.getCount() - 1);
        sectionsPagerAdapter.notifyDataSetChanged();
    }
}