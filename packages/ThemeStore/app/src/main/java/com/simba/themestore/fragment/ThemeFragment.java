package com.simba.themestore.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.CollectionUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.simba.base.network.model.callback.ResultCallBack;
import com.simba.themestore.R;
import com.simba.themestore.banner.adapter.BannerImageAdapter;
import com.simba.themestore.base.BaseMainFragment;
import com.simba.themestore.fragment.adapter.ThemeAdapter;
import com.simba.themestore.launch.theme.ThemeDetailActivity;
import com.simba.themestore.launch.theme.ThemeDetailListActivity;
import com.simba.themestore.model.PageInfo;
import com.simba.themestore.model.theme.ThemeDetail;
import com.simba.themestore.model.theme.ThemeDetailBean;
import com.simba.themestore.model.theme.ThemeMainList;
import com.simba.themestore.model.theme.ThemeMainListBean;
import com.simba.themestore.net.HttpRequest;
import com.simba.themestore.view.ViewPagerIndicator;
import com.simba.themestore.view.itemdecoration.CommonDecoration;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/20
 * @Desc :
 */
public class ThemeFragment extends BaseMainFragment {

    private static final String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";
    private Banner banner;
    private RecyclerView recyclerView;
    private List<ThemeMainListBean> mData;
    private ThemeAdapter themeAdapter;
    private PageInfo pageInfo;
    private RelativeLayout rlLoading;
    private RelativeLayout rlError;
    private HorizontalScrollView scrollView;
    private Button btnRetry;
    private BannerImageAdapter bannerAdapter;
    private ViewPagerIndicator mIndicatorCircleLine;

    public static ThemeFragment newInstance(int index) {
        ThemeFragment fragment = new ThemeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart");
        banner.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e(TAG, "onStop");
        banner.stop();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_theme;
    }

    @Override
    protected void initView() {
        banner = getView(R.id.banner);
        rlLoading = getView(R.id.rl_loading);
        rlError = getView(R.id.rl_error);
        scrollView = getView(R.id.scrollView);
        btnRetry = getView(R.id.btn_retry);
        //设置适配器
        bannerAdapter = new BannerImageAdapter();
        banner.setAdapter(bannerAdapter);
        mIndicatorCircleLine = getView(R.id.indicator_circle_line);
        //圆角
        banner.setBannerRound(20);
        banner.setPageTransformer(new ZoomOutPageTransformer());
        banner.removeIndicator();

        banner.setUserInputEnabled(false);
        banner.setOnBannerListener(new OnBannerListener<ThemeDetailBean>() {
            @Override
            public void OnBannerClick(ThemeDetailBean data, int position) {
                startActivity(ThemeDetailActivity.class);
            }
        });


        recyclerView = getView(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        CommonDecoration commonDecoration = new CommonDecoration(30);
        recyclerView.addItemDecoration(commonDecoration);

    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        pageInfo = new PageInfo();

        themeAdapter = new ThemeAdapter(mData);
        recyclerView.setAdapter(themeAdapter);

        themeAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMoreData();
            }
        });

        themeAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()) {
                    case R.id.rl_title: {
                        ThemeMainListBean mainListBean = themeAdapter.getData().get(position);
                        Bundle bundle = new Bundle();
                        bundle.putString(ThemeDetailListActivity.THEME_TYPE_ID, mainListBean.getCategoryID());
                        bundle.putString(ThemeDetailListActivity.THEME_TYPE_NAME, mainListBean.getCategoryName());
                        startActivity(ThemeDetailListActivity.class, bundle);
                    }
                    break;
                    case R.id.rl_item:
                    case R.id.rl_item_bottom:

                        startActivity(ThemeDetailActivity.class);
                }

            }
        });
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

    @Override
    protected void loadData() {
        Observable<Object> themeListObservable = Observable.create(emitter -> HttpRequest.getMainThemeList(
                new ResultCallBack<ThemeMainList>() {
                    @Override
                    public void onLoaded(ThemeMainList wrapper) {
                        emitter.onNext(wrapper);
                        emitter.onComplete();
                    }

                    @Override
                    public void onDataLoadedFailure(Exception e) {
                        emitter.onError(new Exception(e.getMessage()));
                    }
                }, getContext(), "1", pageInfo.pageNum));

        Observable<Object> bannerObservable = Observable.create(emitter -> HttpRequest.getThemeBannerData(
                new ResultCallBack<ThemeDetail>() {
                    @Override
                    public void onLoaded(ThemeDetail wrapper) {
                        emitter.onNext(wrapper);
                        emitter.onComplete();
                    }

                    @Override
                    public void onDataLoadedFailure(Exception e) {
                        emitter.onError(new Exception(e.getMessage()));
                    }
                }, getContext(), "1"));

        Observable.merge(themeListObservable, bannerObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onNext(Object obj) {
                        if (obj instanceof ThemeMainList) {//列表
                            ThemeMainList themeMainList = (ThemeMainList) obj;
                            if (CollectionUtils.isNotEmpty(themeMainList.getData())) {
                                themeAdapter.setList(themeMainList.getData());

                            }
                            if (themeMainList.getData().size() < PageInfo.PAGE_SIZE) {
                                themeAdapter.getLoadMoreModule().loadMoreEnd();
                            }
                        } else if (obj instanceof ThemeDetail) {//轮播图
                            ThemeDetail themeDetail = (ThemeDetail) obj;
                            if (CollectionUtils.isNotEmpty(themeDetail.getData())) {
                                bannerAdapter.refresh(themeDetail.getData());
                                mIndicatorCircleLine.setViewPager(banner.getViewPager2(), true);
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        showError();
                        Log.e(TAG, "onError:" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e(TAG, "onComplete");
                        dismissLoading();
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable = d;
                        Log.e(TAG, "onSubscribe");
                        showLoading();

                    }
                });

    }

    private void loadMoreData() {
        Observable.create((ObservableOnSubscribe<List<ThemeMainListBean>>) emitter -> HttpRequest.getMainThemeList(
                new ResultCallBack<ThemeMainList>() {
                    @Override
                    public void onLoaded(ThemeMainList wrapper) {
                        emitter.onNext(wrapper.getData());
                    }

                    @Override
                    public void onDataLoadedFailure(Exception e) {
                        emitter.onError(new Exception(e.getMessage()));
                    }
                }, getContext(), "1", pageInfo.pageNum)).subscribeOn(Schedulers.io())
                .doOnSubscribe(disposable -> mDisposable = disposable).observeOn(AndroidSchedulers.mainThread())
                .subscribe(themeMainList -> {
                    if (CollectionUtils.isNotEmpty(themeMainList)) {
                        themeAdapter.addData(themeMainList);
                    }
                    if (themeMainList.size() < PageInfo.PAGE_SIZE) {
                        themeAdapter.getLoadMoreModule().loadMoreEnd();
                    }

                }, throwable -> {
                    themeAdapter.getLoadMoreModule().loadMoreFail();

                });
    }

    private void showLoading() {
        rlLoading.setVisibility(View.VISIBLE);
        scrollView.setVisibility(View.GONE);
        rlError.setVisibility(View.GONE);
    }

    private void dismissLoading() {
        rlLoading.setVisibility(View.GONE);
        scrollView.setVisibility(View.VISIBLE);
        rlError.setVisibility(View.GONE);
    }

    private void showError() {
        rlLoading.setVisibility(View.GONE);
        rlError.setVisibility(View.VISIBLE);
    }
}
