package com.simba.themestore.base;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.simba.base.dialog.DialogUtil;
import com.simba.themestore.R;

import io.reactivex.disposables.Disposable;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/22
 * @Desc :
 */
public abstract class EditBaseActivity extends AppCompatActivity {
    protected final String mActivityName = this.getClass().getSimpleName();
    protected Context mContext;
    protected TextView mTVTitle;
    protected ImageView mIvLeftBack;
    protected LinearLayout mLlRoot;
    protected Button btnEdit;
    protected Button btnDelete;
    protected Button btnSelect;
    protected boolean isSelectAll = false;
    protected boolean isEdit = false;
    protected Disposable mDisposable;
    private OnOptionListener optionListener;
    protected OnRetryListener onRetryListener;
    /**
     * 进度dialog
     */
    private DialogUtil publicDialog;

    private RelativeLayout rlLoading;
    private RelativeLayout rlError;
    private Button btnRetry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        try {

            mLlRoot = findViewById(R.id.ll_root);
            rlLoading = findViewById(R.id.rl_loading);
            rlError = findViewById(R.id.rl_error);
            View vgContent = getLayoutInflater().inflate(getLayoutID(), null);
            mLlRoot.addView(vgContent, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            ImmersionBar.with(this).init();
            initCommon();
            initView();
            initData();
            initClick();
            loadData();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void initCommon() {

        //初始化环境
        mContext = this;
        //初始化控件
        mTVTitle = findViewById(R.id.tv_title);
        mIvLeftBack = findViewById(R.id.iv_back);
        btnEdit = findViewById(R.id.btn_edit);
        btnDelete = findViewById(R.id.btn_delete);
        btnSelect = findViewById(R.id.btn_select_all);
        btnRetry = findViewById(R.id.btn_retry);

        //返回键监听
        mIvLeftBack.setOnClickListener(clickListener);
        btnEdit.setOnClickListener(clickListener);
        btnDelete.setOnClickListener(clickListener);
        btnSelect.setOnClickListener(clickListener);
        btnRetry.setOnClickListener(clickListener);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_back:
                    finish();
                    break;
                case R.id.btn_edit: {//编辑
                    setEdit();
                }

                break;
                case R.id.btn_delete: {
                    if (optionListener != null) {
                        optionListener.onDelete();
                    }
                }
                break;
                case R.id.btn_select_all: {
                    btnSelect.setText(isSelectAll ? "全选" : "全不选");
                    if (optionListener != null) {
                        optionListener.onSelectAll(!isSelectAll);
                    }
                    isSelectAll = !isSelectAll;
                }
                break;
                case R.id.btn_retry: {
                    if (onRetryListener != null) {
                        onRetryListener.onRetry();
                    }
                }
                break;
                default:
                    break;
            }
        }
    };

    private void setEdit() {
        if (isEdit) {//已经是编辑模式了 做取消的
            mTVTitle.setVisibility(View.VISIBLE);
            mIvLeftBack.setVisibility(View.VISIBLE);
            btnEdit.setText("编辑");
            btnDelete.setVisibility(View.GONE);
            btnSelect.setVisibility(View.GONE);
            //初始化全选按钮状态
            isSelectAll = false;
            btnSelect.setText("全选");
        } else {
            mTVTitle.setVisibility(View.GONE);
            mIvLeftBack.setVisibility(View.GONE);

            btnEdit.setText("取消");
            btnDelete.setVisibility(View.VISIBLE);
            btnSelect.setVisibility(View.VISIBLE);
        }

        if (optionListener != null) {
            optionListener.onEdit(!isEdit);
        }
        isEdit = !isEdit;
    }

    /**
     * 获取子类要绑定视图
     *
     * @return
     */
    protected abstract int getLayoutID();

    /**
     * 获取子类要绑定视图
     *
     * @return
     */
    protected abstract void initView();

    protected abstract void initData();

    protected void initClick() {
    }
    protected void loadData() {
    }
    public OnRetryListener getOnRetryListener() {
        return onRetryListener;
    }

    public void setOnRetryListener(OnRetryListener onRetryListener) {
        this.onRetryListener = onRetryListener;
    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setTitleName(String title) {
        mTVTitle.setText(title);
    }

    /**
     * 设置标题
     *
     * @param title
     */
    protected void setTitleName(int title) {
        mTVTitle.setText(title);
    }

    protected void hideEditButton() {
        findViewById(R.id.btn_edit).setVisibility(View.GONE);
    }

    /**
     * 隐藏返回键
     */
    protected void hideLeftImage() {
        findViewById(R.id.iv_back).setVisibility(View.GONE);
    }

    public OnOptionListener getOptionListener() {
        return optionListener;
    }

    public void setOptionListener(OnOptionListener optionListener) {
        this.optionListener = optionListener;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            if (!mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        }
    }

    /**
     * 显示dialog
     */
    protected void showProgressDialog() {
        if (publicDialog == null) {
            publicDialog = DialogUtil.buildProgress(this, com.simba.base.R.string.base_is_loading);
        }
        publicDialog.show();
    }

    protected void dismissProgressDialog() {
        if (publicDialog != null && publicDialog.isShowing()) {
            publicDialog.dismiss();
        }
    }

    protected void showLoading() {
        rlLoading.setVisibility(View.VISIBLE);
        rlError.setVisibility(View.GONE);
    }

    protected void dismissLoading() {
        rlLoading.setVisibility(View.GONE);
        rlError.setVisibility(View.GONE);
    }

    protected void showError() {
        rlLoading.setVisibility(View.GONE);
        rlError.setVisibility(View.VISIBLE);
    }

    protected void startActivity(Class aClass) {
        startActivity(aClass, null);
    }

    protected void startActivity(Class aClass, Bundle bundle) {
        Intent intent = new Intent(this, aClass);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public interface OnOptionListener {
        void onDelete();

        void onEdit(boolean isOnEdit);

        void onSelectAll(boolean isSelectAll);

    }

    public interface OnRetryListener {
        void onRetry();
    }
}

