package com.simba.themestore.fragment;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ResourceUtils;
import com.simba.themestore.R;
import com.simba.themestore.base.BaseLazyLoadFragment;
import com.simba.themestore.fragment.adapter.PersonalAdapter;
import com.simba.themestore.model.personal.PersonalMenuBean;
import com.simba.themestore.view.itemdecoration.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/20
 * @Desc :
 */
public class PersonalFragment extends BaseLazyLoadFragment {
    private static final String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";
    private List<PersonalMenuBean> mData;
    private RecyclerView recyclerView;

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
        recyclerView = findViewById(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        String[] titleArray = ResourceUtils.getStringArray(R.array.personal_title);
        int[] bgArray = ResourceUtils.getIntegerArray(R.array.personal_bg);
        int[] iconArray = ResourceUtils.getIntegerArray(R.array.personal_icon);
        for (int i = 0, j = titleArray.length; i < j; i++) {
            mData.add(new PersonalMenuBean(titleArray[i], bgArray[i], iconArray[i]));
        }
        PersonalAdapter personalAdapter = new PersonalAdapter(mData);
        recyclerView.setAdapter(personalAdapter);
        SpaceItemDecoration commonDecoration = new SpaceItemDecoration(39);
        recyclerView.addItemDecoration(commonDecoration);

    }

    @Override
    protected void lazyLoad() {

    }
}
