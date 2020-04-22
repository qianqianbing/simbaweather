package com.simba.themestore.launch.personal;

import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.simba.themestore.R;
import com.simba.themestore.base.EditBaseActivity;
import com.simba.themestore.launch.adapter.PersonalThemeAdapter;
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
        themeAdapter = new PersonalThemeAdapter(R.layout.item_personal_theme, mData);
        recyclerView.setAdapter(themeAdapter);
        CommonDecoration commonDecoration = new CommonDecoration(30);
        recyclerView.addItemDecoration(commonDecoration);
        themeAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });

        setOptionListener(this);
    }

    private void loadMore() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                themeAdapter.getLoadMoreModule().loadMoreEnd();
            }
        }, 10000);
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onEdit() {

        themeAdapter.setEdit(true);
        themeAdapter.notifyDataSetChanged();
    }

    @Override
    public void cancelEdit() {
        themeAdapter.setEdit(false);
        themeAdapter.notifyDataSetChanged();
    }


    @Override
    public void onSelectAll() {

    }

    @Override
    public void onReset() {

    }
}
