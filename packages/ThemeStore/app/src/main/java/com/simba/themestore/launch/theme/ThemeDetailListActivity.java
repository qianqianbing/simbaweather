package com.simba.themestore.launch.theme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.CollectionUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.simba.base.network.model.callback.ResultCallBack;
import com.simba.themestore.R;
import com.simba.themestore.base.EditBaseActivity;
import com.simba.themestore.launch.adapter.theme.ThemeTypeListAdapter;
import com.simba.themestore.model.PageInfo;
import com.simba.themestore.model.theme.ThemeDetailBean;
import com.simba.themestore.net.HttpRequest;
import com.simba.themestore.view.itemdecoration.SpaceItemDecoration;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/23
 * @Desc :主题列表
 */
public class ThemeDetailListActivity extends EditBaseActivity implements EditBaseActivity.OnRetryListener {
    public static final String THEME_TYPE_ID = "THEME_TYPE_ID";
    public static final String THEME_TYPE_NAME = "THEME_TYPE_NAME";

    private RecyclerView recyclerView;
    private List<ThemeDetailBean> mData;
    private ThemeTypeListAdapter themeTypeListAdapter;
    private String typeID = "";
    private PageInfo pageInfo;

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
        Intent intent = this.getIntent();
        String typeName = "";
        if (intent.getExtras() != null) {
            Bundle bundle = intent.getExtras();
            typeID = bundle.getString(THEME_TYPE_ID);
            typeName = bundle.getString(THEME_TYPE_NAME);
        }
        setTitleName(typeName);
        hideEditButton();
        pageInfo = new PageInfo();

        themeTypeListAdapter = new ThemeTypeListAdapter();
        recyclerView.setAdapter(themeTypeListAdapter);
        SpaceItemDecoration commonDecoration = new SpaceItemDecoration(30);
        recyclerView.addItemDecoration(commonDecoration);
        themeTypeListAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadData();
            }
        });
        themeTypeListAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(ThemeDetailActivity.class);
            }
        });

        setOnRetryListener(this);

    }

    @Override
    protected void loadData() {
        Observable.create((ObservableOnSubscribe<List<ThemeDetailBean>>) emitter -> HttpRequest.getThemeTypeList(
                new ResultCallBack<List<ThemeDetailBean>>() {
                    @Override
                    public void onLoaded(List<ThemeDetailBean> wrapper) {
                        emitter.onNext(wrapper);
                    }
                    @Override
                    public void onDataLoadedFailure(Exception e) {
                        emitter.onError(new Exception(e.getMessage()));
                    }
                }, mContext, typeID, pageInfo.pageNum)).subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> {
                    mDisposable = disposable;
                    if (pageInfo.isFirstPage()) {
                        showLoading();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(detailBeanList -> {
                    dismissLoading();
                    if (CollectionUtils.isNotEmpty(detailBeanList)) {
                        if (pageInfo.isFirstPage()) {
                            themeTypeListAdapter.setList(detailBeanList);
                        } else {
                            themeTypeListAdapter.addData(detailBeanList);
                        }
                        pageInfo.nextPage();
                    }

                    if (detailBeanList.size() < PageInfo.PAGE_SIZE) {
                        themeTypeListAdapter.getLoadMoreModule().loadMoreEnd();
                    }


                }, throwable -> {
                    dismissLoading();
                    if (pageInfo.isFirstPage()) {
                        showError();
                    } else {
                        themeTypeListAdapter.getLoadMoreModule().loadMoreFail();
                    }


                });
    }

    @Override
    public void onRetry() {
        pageInfo.reset();
        loadData();
    }
}
