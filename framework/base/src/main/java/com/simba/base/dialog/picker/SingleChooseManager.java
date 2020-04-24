package com.simba.base.dialog.picker;

import android.app.Activity;
import android.app.Dialog;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.StringRes;

import com.simba.base.R;
import com.simba.base.dialog.adapter.SingleChooseAdapter;
import com.simba.base.dialog.model.KeyValue;
import com.simba.base.dialog.model.KeyValueBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc : 单选dialog管理类
 */
public class SingleChooseManager {
    /**
     * icon在左边
     */
    public final static int STYLE_NORMAL = 0;
    /**
     * icon在右边
     */
    public final static int STYLE_RIGHT = 1;
    /**
     * 所属activity
     */
    private Activity mActivity;
    /**
     * 当前dialog
     */
    private Dialog mDialog;
    /**
     * 取消按钮
     */
    private TextView mTvCancel;

    /**
     * 单选列表
     */
    private ListView mLvSingle;

    /**
     * icon类型
     */
    private int markStyle;
    /**
     * 适配器
     */
    private SingleChooseAdapter adapter;

    private OnItemClickListener onItemClickListener;

    /**
     * @param activity
     * @param data
     * @param <T>
     */
    public <T extends KeyValue> SingleChooseManager(Activity activity, List<T> data) {
        this(activity, -1, STYLE_RIGHT, data);

    }

    public SingleChooseManager(Activity activity, String[] array) {
        List<KeyValueBean> keyValueBeanList = new ArrayList<>();
        for (String value : array) {
            keyValueBeanList.add(new KeyValueBean(value));
        }
        try {
            mActivity = activity;
            initDialog(-1, keyValueBeanList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * @param activity
     * @param style
     * @param data
     * @param <T>
     */
    public <T extends KeyValue> SingleChooseManager(Activity activity, int style, List<T> data) {
        this(activity, style, STYLE_RIGHT, data);

    }

    /**
     * @param activity
     * @param style
     * @param markStyle
     * @param data
     * @param <T>
     */
    public <T extends KeyValue> SingleChooseManager(Activity activity, int style, int markStyle, List<T> data) {
        try {
            mActivity = activity;
            this.markStyle = markStyle;

            initDialog(style, data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化
     *
     * @param style
     * @param data
     * @param <T>
     */
    private <T extends KeyValue> void initDialog(int style, List<T> data) {
        if (style == -1) {
            mDialog = new Dialog(mActivity);
        } else {
            mDialog = new Dialog(mActivity, style);
        }
        mDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        mDialog.setContentView(mActivity.getLayoutInflater().inflate(R.layout.dialog_single_choose, null));
        Display dd = mActivity.getWindowManager().getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        dd.getMetrics(dm);
        WindowManager.LayoutParams attributes = mDialog.getWindow().getAttributes();
        if (style == -1) {
            mDialog.getWindow().setGravity(Gravity.CENTER);
        } else {
            mDialog.getWindow().setGravity(Gravity.BOTTOM);
        }


        //  attributes.width = WindowManager.LayoutParams.WRAP_CONTENT;
        attributes.height = WindowManager.LayoutParams.WRAP_CONTENT;

        mTvCancel = mDialog.findViewById(R.id.tv_no);

        mLvSingle = mDialog.findViewById(R.id.lv_single);

        initData(data);
        setListener();


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
        adapter = new SingleChooseAdapter(data);
        mLvSingle.setAdapter(adapter);
    }

    /**
     * @param data
     * @param <T>
     */
    public <T extends KeyValue> void setData(List<T> data) {
        adapter.refresh(data);
    }

    /**
     * @param pos
     * @param <T>
     * @return
     */
    public <T extends KeyValue> T getItemData(int pos) {
        return (T) adapter.getItem(pos);
    }

    /**
     * 获取数量
     *
     * @return
     */
    public int getItemCount() {
        return adapter.getCount();
    }

    /**
     * 设置监听
     */
    private void setListener() {

        mLvSingle.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
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

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    /**
     *
     */
    public void dismiss() {

        if (mDialog != null) {
            mDialog.cancel();
        }
    }

    /**
     *
     */
    public void show() {

        if (mDialog != null) {
            mDialog.show();
        }
    }
}
