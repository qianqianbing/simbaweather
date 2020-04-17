package com.simba.simbaweather.ui.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 *@Auther:王自阳
 *@Date: 2019/9/4
 *@Time:10:40
 *@Package_name:com.bw.movie.ui.base
 *@Description:
 * */
public abstract class BaseActivity<V,P extends BasePresenter<V>> extends AppCompatActivity {
    public P mPresenter;
    private Unbinder bind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        bind = ButterKnife.bind(this);
        mPresenter=oncreatePresenter();
        mPresenter.onAttech((V) this);
        initData();
    }

    protected abstract void initData();

    protected abstract P oncreatePresenter();

    protected abstract int getLayout();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDeatch();
        bind.unbind();
    }
}
