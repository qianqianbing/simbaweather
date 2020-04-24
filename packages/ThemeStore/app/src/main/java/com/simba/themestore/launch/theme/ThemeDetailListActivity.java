package com.simba.themestore.launch.theme;

import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.simba.themestore.R;
import com.simba.themestore.base.EditBaseActivity;
import com.simba.themestore.launch.WallPaperSettingActivity;
import com.simba.themestore.launch.adapter.PersonalWallPaperAdapter;
import com.simba.themestore.model.personal.PersonalWallPaperBean;
import com.simba.themestore.view.itemdecoration.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/23
 * @Desc :
 */
public class ThemeDetailListActivity extends EditBaseActivity {
    private RecyclerView recyclerView;
    private List<PersonalWallPaperBean> mData;
    private PersonalWallPaperAdapter wallPaperAdapter;

    @Override
    protected int getLayoutID() {
        return R.layout.activity_personalwallpaper;
    }

    @Override
    protected void initView() {
        setTitleName("类型二");
        hideEditButton();
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
        wallPaperAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(WallPaperSettingActivity.class);
            }
        });

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
}
