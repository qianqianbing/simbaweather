package com.simba.themestore.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.simba.themestore.R;
import com.simba.themestore.base.BaseMainFragment;
import com.simba.themestore.fragment.adapter.LockScreenAdapter;
import com.simba.themestore.launch.theme.ThemeDetailActivity;
import com.simba.themestore.model.LockScreenBean;
import com.simba.themestore.view.GridLoadMoreView;
import com.simba.themestore.view.itemdecoration.SpaceDecoration;
import com.simba.themestore.view.itemdecoration.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/20
 * @Desc :
 */
public class LockScreenFragment extends BaseMainFragment {
    private static final String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";
    private RecyclerView recyclerView;
    private List<LockScreenBean> mData;
    private LockScreenAdapter lockScreenAdapter;

    public static LockScreenFragment newInstance(int index) {
        LockScreenFragment fragment = new LockScreenFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_lockscreen;
    }

    @Override
    protected void initView() {
        recyclerView = getView(R.id.recyclerView);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        gridLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        Log.e(TAG, "initView");
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add(new LockScreenBean());
        }
        lockScreenAdapter = new LockScreenAdapter(R.layout.item_fragment_lockscreen, mData);
        recyclerView.setAdapter(lockScreenAdapter);
        SpaceDecoration commonDecoration = new SpaceDecoration(30);
        recyclerView.addItemDecoration(commonDecoration);

        lockScreenAdapter.getLoadMoreModule().setLoadMoreView(new GridLoadMoreView());
        lockScreenAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });


        lockScreenAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(ThemeDetailActivity.class);
            }
        });
    }

    private void loadMore() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<LockScreenBean> mData = new ArrayList<>();
                for (int i = 0; i < 6; i++) {
                    mData.add(new LockScreenBean());
                }
                lockScreenAdapter.addData(mData);
                lockScreenAdapter.getLoadMoreModule().loadMoreEnd();
            }
        }, 10000);
    }


}
