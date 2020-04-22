package com.simba.simbaweather.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/*
 *@Auther:王自阳
 *@Date: 2020/04/17
 *@Description:
 * */
public abstract class BaseFragment<V, P extends BasePresenter<V>> extends Fragment {
    public P fPresenter;
    private View inflate;
    private Unbinder bind;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = LayoutInflater.from(getContext()).inflate(getLayout(), container, false);
        bind = ButterKnife.bind(this, inflate);
        return inflate;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fPresenter = oncreatePresenter();
        fPresenter.onAttech((V) this);
        initData();
    }

    public abstract void initData();

    protected abstract P oncreatePresenter();

    protected abstract int getLayout();

    @Override
    public void onDestroy() {
        super.onDestroy();
        fPresenter.onDeatch();
        bind.unbind();
    }
}
