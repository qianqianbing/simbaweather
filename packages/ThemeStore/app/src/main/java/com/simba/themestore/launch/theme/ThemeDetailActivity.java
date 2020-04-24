package com.simba.themestore.launch.theme;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simba.themestore.R;
import com.simba.themestore.base.EditBaseActivity;
import com.simba.themestore.launch.adapter.theme.ThemeDetailAdapter;
import com.simba.themestore.model.personal.PersonalThemeBean;
import com.simba.themestore.view.itemdecoration.CommonDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/23
 * @Desc :
 */
public class ThemeDetailActivity extends EditBaseActivity {
    private RecyclerView recyclerView;
    private List<PersonalThemeBean> mData;
    private ThemeDetailAdapter themeAdapter;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_themedetail;
    }

    @Override
    protected void initView() {
        setTitleName("破晓主题");
        hideEditButton();
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add(new PersonalThemeBean());
        }
        themeAdapter = new ThemeDetailAdapter(R.layout.item_theme_detail, mData);
        recyclerView.setAdapter(themeAdapter);
        CommonDecoration commonDecoration = new CommonDecoration(30);
        recyclerView.addItemDecoration(commonDecoration);

    }
}
