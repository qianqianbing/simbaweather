package com.simba.themestore.launch.personal;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simba.themestore.R;
import com.simba.themestore.base.EditBaseActivity;
import com.simba.themestore.launch.adapter.personal.PersonalThemeAdapter;
import com.simba.themestore.model.personal.PersonalThemeBean;
import com.simba.themestore.view.itemdecoration.CommonDecoration;

import java.util.ArrayList;
import java.util.List;

public class PersonalThemeActivity extends EditBaseActivity implements EditBaseActivity.OnOptionListener {
    private RecyclerView recyclerView;
    private List<PersonalThemeBean> mData;
    private PersonalThemeAdapter themeAdapter;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_personal_theme;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void initData() {

        setTitleName(R.string.personal_theme);
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add(new PersonalThemeBean());
        }
        themeAdapter = new PersonalThemeAdapter(mData);
        recyclerView.setAdapter(themeAdapter);
        CommonDecoration commonDecoration = new CommonDecoration(30);
        recyclerView.addItemDecoration(commonDecoration);

        setOptionListener(this);
    }


    @Override
    public void onDelete() {

    }

    @Override
    public void onEdit(boolean isOnEdit) {
        themeAdapter.setEdit(isOnEdit);
    }

    @Override
    public void onSelectAll(boolean isSelectAll) {
        themeAdapter.selectOption(isSelectAll);
    }


}
