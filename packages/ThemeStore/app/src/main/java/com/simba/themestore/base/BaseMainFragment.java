package com.simba.themestore.base;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;

import com.simba.base.utils.Toasty;

import io.reactivex.disposables.Disposable;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/13
 * @Desc :
 */
public abstract class BaseMainFragment extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();
    private View mRootView;
    protected Disposable mDisposable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayoutId(), container, false);
        initView();
        initData();
        loadData();
        return mRootView;
    }


    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();

        stopLoad();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    /**
     * 设置Fragment要显示的布局
     *
     * @return 布局的layoutId
     */
    protected abstract int getLayoutId();

    //初始化视图对象
    protected abstract void initView();

    //视图数据初始化
    protected abstract void initData();

    /**
     * 获取设置的布局
     *
     * @return
     */
    protected View getContentView() {
        return mRootView;
    }

    protected <T extends View> T getView(@IdRes int viewId) {
        return (T) (mRootView.findViewById(viewId));
    }


    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected void loadData() {
    }

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected void stopLoad() {

        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    protected void showToast(String msg) {
        Toasty.info(getContext(), msg).show();
    }

    protected void showToast(@StringRes int msg) {
        Toasty.info(getContext(), msg).show();
    }

    /**
     * @param cls
     * @param bundle
     */
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    /**
     * @param cls
     */
    protected void startActivity(Class<?> cls) {
        Intent intent = new Intent(getActivity(), cls);
        getActivity().startActivity(intent);
    }
}

