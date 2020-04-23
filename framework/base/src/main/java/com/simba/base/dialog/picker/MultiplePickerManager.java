package com.simba.base.dialog.picker;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.simba.base.R;
import com.simba.base.dialog.adapter.SinglePickerAdapter;
import com.simba.base.dialog.model.KeyValue;
import com.simba.base.utils.ResourceUtils;

import java.util.List;


/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc : 多选dialog管理类
 */
public class MultiplePickerManager {

    private Activity mActivity;
    private Dialog mDialog;
    private TextView mTvCancel;
    private TextView mTvConfirm;
    private TextView mTvTitle;
    private ListView mLvMultiple;
    private onConfirmClickListener mConfirmListener;

    private SinglePickerAdapter adapter;

    public <T extends KeyValue> MultiplePickerManager(Activity activity, List<T> data) {
        mActivity = activity;
        initDialog(data);
    }

    private <T extends KeyValue> void initDialog(List<T> data) {

        mDialog = new Dialog(mActivity);
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDialog.setContentView(mActivity.getLayoutInflater().inflate(R.layout.dialog_multiple_picker, null));
        Display dd = mActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        dd.getMetrics(dm);
        WindowManager.LayoutParams attributes = mDialog.getWindow().getAttributes();
        mDialog.getWindow().setGravity(Gravity.CENTER);

        attributes.height = (int) (dm.heightPixels * 0.8);

        mTvCancel = mDialog.findViewById(R.id.tv_no);
        mTvConfirm = mDialog.findViewById(R.id.tv_yes);
        mTvTitle = mDialog.findViewById(R.id.tvTitle);
        mLvMultiple = mDialog.findViewById(R.id.lv_multiple);
        mLvMultiple.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (mLvMultiple.getCheckedItemCount() > 0) {
                    mTvConfirm.setTextColor(ResourceUtils.getColor(R.color.base_white));
                } else {
                    mTvConfirm.setTextColor(ResourceUtils.getColor(R.color.base_white_trans_4d));
                }
            }
        });

        initData(data);
        setListener();

    }

    public void setConfirmText(@StringRes int text) {
        mTvConfirm.setText(text);
    }

    public void setConfirmText(String text) {
        mTvConfirm.setText(text);
    }

    public void setCancelText(@StringRes int text) {
        mTvCancel.setText(text);
    }

    public void setCancelText(String text) {
        mTvCancel.setText(text);
    }

    /**
     * @param data
     * @param <T>
     */
    private <T extends KeyValue> void initData(List<T> data) {
        adapter = new SinglePickerAdapter(data);
        mLvMultiple.setAdapter(adapter);
    }

    private void setListener() {

        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mConfirmListener != null && mLvMultiple.getCheckedItemCount() > 0) {
                    SparseBooleanArray checkedItemPositions = mLvMultiple.getCheckedItemPositions();
                    int count = mLvMultiple.getAdapter().getCount();
                    mConfirmListener.onClick(checkedItemPositions, count);
                }
            }
        });

        mTvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dismiss();
            }
        });
    }

    public void setOnConfirmListener(onConfirmClickListener listener) {

        mConfirmListener = listener;
    }

    public interface OnCancelClickListener {

        void onClick();
    }

    public interface onConfirmClickListener {

        void onClick(SparseBooleanArray checkedItemPositions, int count);
    }

    public void dismiss() {

        if (mDialog != null) {
            mDialog.cancel();
        }
    }

    public void setTitle(@StringRes int text) {
        mTvTitle.setText(text);
    }

    public void setTitle(String text) {
        mTvTitle.setText(text);
    }

    public void show() {

        if (mDialog != null) {
            mDialog.show();
        }
    }
}
