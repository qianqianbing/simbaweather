package com.simba.themestore.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.HorizontalScrollView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.simba.themestore.R;
import com.simba.themestore.banner.adapter.BannerImageAdapter;
import com.simba.themestore.base.BaseLazyLoadFragment;
import com.simba.themestore.fragment.adapter.WallPaperAdapter;
import com.simba.themestore.launch.wallpaper.WallPaperListActivity;
import com.simba.themestore.launch.wallpaper.WallPaperSettingActivity;
import com.simba.themestore.model.DataBean;
import com.simba.themestore.model.WallPaperBean;
import com.simba.themestore.view.ViewPagerIndicator;
import com.simba.themestore.view.itemdecoration.CommonDecoration;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.transformer.ZoomOutPageTransformer;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/20
 * @Desc :
 */
public class WallpaperFragment extends BaseLazyLoadFragment {

    private static final String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";
    private Banner banner;
    private RecyclerView recyclerView;
    private List<WallPaperBean> mData;
    private WallPaperAdapter wallPaperAdapter;
    private HorizontalScrollView scrollView;
    private ViewPagerIndicator mIndicatorCircleLine;

    public static WallpaperFragment newInstance(int index) {
        WallpaperFragment fragment = new WallpaperFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_theme;
    }

    @Override
    protected void initView() {
        banner = findViewById(R.id.banner);
        scrollView = findViewById(R.id.scrollView);
        //设置适配器
        BannerImageAdapter adapter = new BannerImageAdapter(DataBean.getTestData());
        banner.setAdapter(adapter);

        banner.setBannerRound(BannerUtils.dp2px(5));
    //    banner.setBannerGalleryEffect(60, 0, 1f);
        banner.setPageTransformer(new ZoomOutPageTransformer());
        banner.removeIndicator();
        banner.setUserInputEnabled(false);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                startActivity(WallPaperSettingActivity.class);
            }
        });
        mIndicatorCircleLine = findViewById(R.id.indicator_circle_line);
        mIndicatorCircleLine.setViewPager(banner.getViewPager2(), true);


        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        CommonDecoration commonDecoration = new CommonDecoration(30);
        recyclerView.addItemDecoration(commonDecoration);
    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add(new WallPaperBean());
        }
        wallPaperAdapter = new WallPaperAdapter(R.layout.item_fragment_theme, mData);
        recyclerView.setAdapter(wallPaperAdapter);

        wallPaperAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        wallPaperAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                switch (view.getId()) {
                    case R.id.rl_title:
                        startActivity(WallPaperListActivity.class);
                        break;
                    case R.id.rl_item:
                    case R.id.rl_item_bottom:
                        startActivity(WallPaperSettingActivity.class);
                }

            }
        });

    }

    private void loadMore() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                wallPaperAdapter.getLoadMoreModule().loadMoreFail();
            }
        }, 10000);
    }

    @Override
    protected void lazyLoad() {
        scrollView.scrollTo(0, 0);
    }


}
