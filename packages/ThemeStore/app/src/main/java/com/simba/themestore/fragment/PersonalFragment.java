package com.simba.themestore.fragment;

import android.os.Bundle;

import com.simba.themestore.R;
import com.simba.themestore.base.BaseLazyLoadFragment;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/20
 * @Desc :
 */
public class PersonalFragment extends BaseLazyLoadFragment {
    private static final String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";

    public static PersonalFragment newInstance(int index) {
        PersonalFragment fragment = new PersonalFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_personal;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyLoad() {

    }
}
