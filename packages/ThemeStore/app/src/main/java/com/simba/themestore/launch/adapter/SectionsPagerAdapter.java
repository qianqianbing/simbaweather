package com.simba.themestore.launch.adapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.simba.themestore.fragment.LockScreenFragment;
import com.simba.themestore.fragment.PersonalFragment;
import com.simba.themestore.fragment.ThemeFragment;
import com.simba.themestore.fragment.WallpaperFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/3
 * @Desc :
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {


    private List<String> mData;

    public SectionsPagerAdapter(AppCompatActivity activity, List<String> mData) {
        super(activity);
        if (mData == null) {
            mData = new ArrayList<>();
        }
        this.mData = mData;

    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return ThemeFragment.newInstance(position);
            case 1:
                return WallpaperFragment.newInstance(position);
            case 2:
                return LockScreenFragment.newInstance(position);
            case 3:
                return PersonalFragment.newInstance(position);

        }
        return ThemeFragment.newInstance(position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}