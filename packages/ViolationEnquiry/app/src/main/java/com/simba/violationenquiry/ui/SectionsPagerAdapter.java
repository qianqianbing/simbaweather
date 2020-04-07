package com.simba.violationenquiry.ui;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.simba.violationenquiry.net.model.CarInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/3
 * @Desc :
 */
public class SectionsPagerAdapter extends FragmentStatePagerAdapter {


    private List<CarInfo> mData;
    private final Context mContext;

    public SectionsPagerAdapter(@NonNull FragmentManager fm, List<CarInfo> mData, Context mContext) {
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
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return PlaceholderFragment.newInstance(position + 1);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mData.get(position).getPlateno();
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