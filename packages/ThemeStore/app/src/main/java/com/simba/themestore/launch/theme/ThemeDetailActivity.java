package com.simba.themestore.launch.theme;

import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simba.themestore.R;
import com.simba.themestore.base.EditBaseActivity;
import com.simba.themestore.launch.adapter.theme.ThemeDetailAdapter;
import com.simba.themestore.model.personal.PersonalThemeBean;
import com.simba.themestore.view.itemdecoration.CommonDecoration;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/23
 * @Desc :
 */
public class ThemeDetailActivity extends EditBaseActivity {
    public static final String THEME_ID = "THEME_ID";
    public static final String THEME_NAME = "THEME_NAME";
    private RecyclerView recyclerView;
    private List<PersonalThemeBean> mData;
    private ThemeDetailAdapter themeAdapter;
    private String themeID;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_themedetail;
    }

    @Override
    protected void initView() {

        hideEditButton();
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {
        Intent intent = this.getIntent();
        String typeName = "";
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            themeID = bundle.getString(THEME_ID);
            typeName = bundle.getString(THEME_NAME);
        }
        setTitleName(typeName);

        themeAdapter = new ThemeDetailAdapter(mData);
        recyclerView.setAdapter(themeAdapter);
        CommonDecoration commonDecoration = new CommonDecoration(30);
        recyclerView.addItemDecoration(commonDecoration);

    }
}
