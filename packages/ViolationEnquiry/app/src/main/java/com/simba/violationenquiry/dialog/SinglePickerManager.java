package com.simba.violationenquiry.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.TextView;

import com.simba.violationenquiry.R;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc :
 */
public class SinglePickerManager {
    public final static int STYLE_NORMAL = 0;
    public final static int STYLE_RIGHT = 1;
    private Activity mActivity;
    private Dialog mDialog;
    private TextView mTvCancel;
    private TextView mTvConfirm;
    private TextView mTvTitle;
    private ListView mLvSingle;
    private onConfirmClickListener mConfirmListener;
    private int markStyle;

    private SinglePickerAdapter adapter;

    public <T extends KeyValue> SinglePickerManager(Activity activity, List<T> data) {
        this(activity, -1, STYLE_RIGHT, data);

    }

    public <T extends KeyValue> SinglePickerManager(Activity activity, int style, List<T> data) {
        this(activity, style, STYLE_RIGHT, data);

    }

    public <T extends KeyValue> SinglePickerManager(Activity activity, int style, int markStyle, List<T> data) {
        try {
            mActivity = activity;
            this.markStyle = markStyle;

            initDialog(style, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private <T extends KeyValue> void initDialog(int style, List<T> data) {
        if (style == -1) {
            mDialog = new Dialog(mActivity);
        } else {
            mDialog = new Dialog(mActivity, style);
        }
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDialog.setContentView(mActivity.getLayoutInflater().inflate(R.layout.dialog_single_picker, null));
        Display dd = mActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        dd.getMetrics(dm);
        WindowManager.LayoutParams attributes = mDialog.getWindow().getAttributes();
        if (style == -1) {
            mDialog.getWindow().setGravity(Gravity.CENTER);
        } else {
            mDialog.getWindow().setGravity(Gravity.BOTTOM);
        }

        attributes.height = (int) (dm.heightPixels * 0.8);
//        attributes.width = (int) (dm.widthPixels * 0.5);

        mTvCancel = mDialog.findViewById(R.id.tv_no);
        mTvConfirm = mDialog.findViewById(R.id.tv_yes);
        mTvTitle = mDialog.findViewById(R.id.tvTitle);
        mLvSingle = mDialog.findViewById(R.id.lv_single);

        initData(data);
        setListener();


    }

    public void setTitle(int text) {
        mTvTitle.setText(text);
    }

    public void setTitle(String text) {
        mTvTitle.setText(text);
    }

    private <T extends KeyValue> void initData(List<T> data) {
        adapter = new SinglePickerAdapter(data, markStyle);
        mLvSingle.setAdapter(adapter);
    }

    public <T extends KeyValue> void setData(List<T> data) {
        adapter.refresh(data);
    }

    public <T extends KeyValue> T getItemData(int pos) {
        return (T) adapter.getItem(pos);
    }

    public int getItemCount() {
        return adapter.getCount();
    }

    private void setListener() {

        mTvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int checkedItemPosition = mLvSingle.getCheckedItemPosition();
                if (mConfirmListener != null && checkedItemPosition != ListView.INVALID_POSITION) {
                    dismiss();
                    mConfirmListener.onClick(checkedItemPosition);
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

        void onClick(int checkedItemPosition);
    }

    public void dismiss() {

        if (mDialog != null) {
            mDialog.cancel();
        }
    }

    public void show() {

        if (mDialog != null) {
            mDialog.show();
        }
    }
}
