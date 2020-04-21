package com.simba.themestore.launch;

import android.os.Handler;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.simba.themestore.R;
import com.simba.themestore.base.MyBaseActivity;
import com.simba.themestore.launch.adapter.PersonalWallPaperAdapter;
import com.simba.themestore.view.itemdecoration.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/21
 * @Desc :
 */
public class PersonalWallPaperActivity extends MyBaseActivity {
    private RecyclerView recyclerView;
    private List<String> mData;
    private PersonalWallPaperAdapter wallPaperAdapter;

    @Override
    protected int getLayoutId() {
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
            mData.add(i + "");
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
    }

    private void loadMore() {
        List<String> mData = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            mData.add(i + "");
        }
        wallPaperAdapter.addData(mData);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                wallPaperAdapter.getLoadMoreModule().loadMoreComplete();
            }
        }, 10000);


    }
}
