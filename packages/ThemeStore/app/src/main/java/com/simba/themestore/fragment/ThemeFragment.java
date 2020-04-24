package com.simba.themestore.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.simba.themestore.R;
import com.simba.themestore.banner.ImageAdapter;
import com.simba.themestore.base.BaseLazyLoadFragment;
import com.simba.themestore.fragment.adapter.ThemeAdapter;
import com.simba.themestore.launch.theme.ThemeDetailListActivity;
import com.simba.themestore.model.DataBean;
import com.simba.themestore.model.ThemeBean;
import com.simba.themestore.view.ViewPagerIndicator;
import com.simba.themestore.view.itemdecoration.CommonDecoration;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.transformer.ZoomOutPageTransformer;
import com.youth.banner.util.BannerUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/20
 * @Desc :
 */
public class ThemeFragment extends BaseLazyLoadFragment implements OnPageChangeListener {

    private static final String ARG_SECTION_NUMBER = "ARG_SECTION_NUMBER";
    private Banner banner;
    private RecyclerView recyclerView;
    private List<ThemeBean> mData;
    private ThemeAdapter themeAdapter;

    public static ThemeFragment newInstance(int index) {
        ThemeFragment fragment = new ThemeFragment();
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
        //设置适配器
        ImageAdapter adapter = new ImageAdapter(DataBean.getTestData());
        banner.setAdapter(adapter);

        //添加切换监听
        banner.addOnPageChangeListener(this);
        //圆角
        banner.setBannerRound(BannerUtils.dp2px(5));
        //banner.setBannerGalleryMZ(60);
        banner.setBannerGalleryEffect(60, 0, 1f);
        banner.setPageTransformer(new ZoomOutPageTransformer());
        banner.removeIndicator();
        banner.setUserInputEnabled(false);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(Object data, int position) {
                showToast("" + position);
            }
        });
        ViewPagerIndicator mIndicatorCircleLine = findViewById(R.id.indicator_circle_line);
        mIndicatorCircleLine.setViewPager(banner.getViewPager2(), true);


        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);


    }

    @Override
    protected void initData() {
        mData = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mData.add(new ThemeBean());
        }
        themeAdapter = new ThemeAdapter(R.layout.item_fragment_theme, mData);
        recyclerView.setAdapter(themeAdapter);
        CommonDecoration commonDecoration = new CommonDecoration(30);
        recyclerView.addItemDecoration(commonDecoration);
        themeAdapter.getLoadMoreModule().setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
        themeAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                startActivity(ThemeDetailListActivity.class);
            }
        });
    }

    private void loadMore() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                themeAdapter.getLoadMoreModule().loadMoreFail();
            }
        }, 10000);
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
