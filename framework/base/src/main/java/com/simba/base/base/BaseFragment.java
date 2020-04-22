package com.simba.base.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.CallSuper;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.lzy.okgo.OkGo;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * ================================================
 * 作    者：谢广胜
 * 版    本：1.0
 * 创建日期：2020/4/09
 * 描    述：Fragment 基类
 * 修订历史：
 * ================================================
 */
public abstract class BaseFragment extends Fragment {
    public View mRootView;
    public LayoutInflater mInflater;
    public Context mContext;
    public Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        mContext = context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //缓存的rootView需要判断是否已经被加过parent，如果有parent需要从parent删除，要不然会发生这个rootview已经有parent的错误。
        if (mRootView != null) {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null)
                parent.removeView(mRootView);
        } else {
            mRootView = inflater.inflate(getLayoutId(), container, false);
            mInflater = inflater;
            unbinder = ButterKnife.bind(this, mRootView);
            initView();
            initData();
        }
        return mRootView;
    }

    @Override
    @CallSuper
    public void onDestroyView() {
        if (unbinder != null)
            unbinder.unbind();
        OkGo.getInstance().cancelTag(this);//取消未处理的请求
        super.onDestroyView();
    }

    @Override
    public void onDetach() {
        mContext = null;
        super.onDetach();
    }

    public abstract int getLayoutId();

    public abstract void initView();

    public abstract void initData();

    /**
     * 获取View
     *
     * @param viewId
     * @param <T>
     * @return
     */
    protected <T extends View> T getView(@IdRes int viewId) {
        return (T) (mRootView.findViewById(viewId));
    }

}
