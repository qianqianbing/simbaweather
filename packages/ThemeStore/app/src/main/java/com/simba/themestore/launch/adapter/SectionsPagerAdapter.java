package com.simba.themestore.launch.adapter;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.simba.themestore.fragment.PersonalFragment;
import com.simba.themestore.fragment.ThemeFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/3
 * @Desc :
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {


    private List<String> mData;
    private final Context mContext;

    public SectionsPagerAdapter(@NonNull FragmentManager fm, List<String> mData, Context mContext) {
        super(fm);
        if (mData == null) {
            mData = new ArrayList<>();
        }
        this.mData = mData;
        this.mContext = mContext;
    }

//    public SectionsPagerAdapter(Context context, FragmentManager fm) {
//        super(fm);
//        mContext = context;
//    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
//            case 0:
//            case 1:
//            case 2:
//                return ThemeFragment.newInstance(position);
            case 3:
                return PersonalFragment.newInstance(position);
            default:
                return ThemeFragment.newInstance(position);
        }

    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position);
    }

    @Override
    public int getCount() {
        return mData.size();
    }


    @Override
    public int getItemPosition(@NonNull Object object) {
        return PagerAdapter.POSITION_NONE;
    }


}