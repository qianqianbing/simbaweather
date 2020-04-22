package com.simba.simbaweather.ui.base;

import android.os.Bundle;

import androidx.annotation.CallSuper;
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
public abstract class BaseActivity<V,P extends BasePresenter<V>> extends com.simba.base.base.BaseActivity {
    public P mPresenter;
    private Unbinder bind;

    @Override
    @CallSuper
    protected void initView() {
        bind = ButterKnife.bind(this);
        mPresenter=oncreatePresenter();
        mPresenter.onAttech((V) this);
    }

    protected abstract P oncreatePresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
        mPresenter.onDeatch();
    }
}
