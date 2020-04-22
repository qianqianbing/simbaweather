package com.simba.themestore.launch.personal;

import android.os.Handler;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.simba.themestore.R;
import com.simba.themestore.base.EditBaseActivity;
import com.simba.themestore.launch.adapter.PersonalWallPaperAdapter;
import com.simba.themestore.model.personal.PersonalWallPaperBean;
import com.simba.themestore.view.itemdecoration.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/21
 * @Desc :
 */
public class PersonalWallPaperActivity extends EditBaseActivity implements EditBaseActivity.OnOptionListener {
    private RecyclerView recyclerView;
    private List<PersonalWallPaperBean> mData;
    private PersonalWallPaperAdapter wallPaperAdapter;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_personalwallpaper;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.recyclerView);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, 2);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(gridLayoutManager);
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add(new PersonalWallPaperBean());
        }
        wallPaperAdapter = new PersonalWallPaperAdapter(R.layout.item_personalwallpaper, mData);
        recyclerView.setAdapter(wallPaperAdapter);
        SpaceItemDecoration commonDecoration = new SpaceItemDecoration(30);
        recyclerView.addItemDecoration(commonDecoration);
        wallPaperAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });

        setOptionListener(this);
    }

    private void loadMore() {
        List<PersonalWallPaperBean> mData = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mData.add(new PersonalWallPaperBean());
        }
        wallPaperAdapter.addData(mData);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                wallPaperAdapter.getLoadMoreModule().loadMoreComplete();
            }
        }, 10000);
    }

    @Override
    public void onDelete() {

    }

    @Override
    public void onEdit() {

        wallPaperAdapter.setEdit(true);
        wallPaperAdapter.notifyDataSetChanged();
    }

    @Override
    public void cancelEdit() {
        wallPaperAdapter.setEdit(false);
        wallPaperAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSelectAll() {

    }

    @Override
    public void onReset() {

    }
}
