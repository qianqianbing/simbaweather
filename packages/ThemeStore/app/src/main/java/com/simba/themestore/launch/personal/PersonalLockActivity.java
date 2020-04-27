package com.simba.themestore.launch.personal;

import android.os.Handler;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.simba.themestore.R;
import com.simba.themestore.base.EditBaseActivity;
import com.simba.themestore.launch.adapter.personal.PersonalLockAdapter;
import com.simba.themestore.model.personal.PersonalLockScreenBean;
import com.simba.themestore.view.itemdecoration.CommonDecoration;

import java.util.ArrayList;
import java.util.List;

public class PersonalLockActivity extends EditBaseActivity implements EditBaseActivity.OnOptionListener {
    private RecyclerView recyclerView;
    private List<PersonalLockScreenBean> mData;
    private PersonalLockAdapter lockAdapter;

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

        setTitleName(R.string.personal_lock);
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add(new PersonalLockScreenBean());
        }
        lockAdapter = new PersonalLockAdapter(mData);
        recyclerView.setAdapter(lockAdapter);
        CommonDecoration commonDecoration = new CommonDecoration(30);
        recyclerView.addItemDecoration(commonDecoration);
        lockAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
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
                lockAdapter.getLoadMoreModule().loadMoreEnd();
            }
        }, 10000);
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onEdit(boolean isOnEdit) {
        lockAdapter.setEdit(isOnEdit);
    }

    @Override
    public void onSelectAll(boolean isSelectAll) {
        lockAdapter.selectOption(isSelectAll);
    }


}