package com.simba.violationenquiry.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ResourceUtils;
import com.simba.violationenquiry.R;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc :通用提示类
 */
public class CommonDialog extends Dialog {
    /**
     * 内容
     */
    private TextView mTvHint;
    /**
     * 确定（在右边）
     */
    private TextView mTvYes;
    /**
     * 取消
     */
    private TextView mTvNo;
    /**
     * 提示内容
     */
    private String mHintMsg;
    /**
     * 按钮间的分割线 只显示一个的时候不用显示分割线
     */
    private View line;
    /**
     * 取消按钮
     */
    private String noMsg;
    /**
     * 是否显示确定按钮
     */
    private int yesVisible = View.VISIBLE;

    public CommonDialog(@NonNull Context context, String hintMsg) {

        super(context, R.style.MyBaseDialog);
        mHintMsg = hintMsg;
    }

    public CommonDialog(@NonNull Context context, String hintMsg, String noMsg, int yesVisible) {

        super(context, R.style.MyBaseDialog);
        mHintMsg = hintMsg;
        this.noMsg = noMsg;
        this.yesVisible = yesVisible;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_common_hint);

        mTvHint = findViewById(R.id.tv_hint);
        mTvYes = findViewById(R.id.tv_yes);
        mTvNo = findViewById(R.id.tv_no);
        line = findViewById(R.id.line);
        mTvHint.setText(mHintMsg);
        setYesVisible(yesVisible);
        if (!TextUtils.isEmpty(noMsg)) {
            setNoMsg(noMsg);
        }
        mTvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    /**
     * 取消按钮显示
     *
     * @param visible
     */
    public void setNoVisible(int visible) {
        mTvNo.setVisibility(visible);
        line.setVisibility(visible);
    }

    /**
     * 确定按钮显示
     *
     * @param visible
     */
    public void setYesVisible(int visible) {
        mTvYes.setVisibility(visible);
        line.setVisibility(visible);
        if (visible == View.GONE) {
            mTvNo.setTextColor(ResourceUtils.getColor(R.color.colorPrimary));
        }
    }

    /**
     * 提示信息
     *
     * @param hintMsg
     */
    public void setHintMessage(String hintMsg) {

        mTvHint.setText(hintMsg);
    }

    /**
     * 确定按钮显示
     *
     * @param yesMsg
     */
    public void setYesMessage(String yesMsg) {

        mTvYes.setText(yesMsg);
    }

    /**
     * 取消按钮
     *
     * @param noMsg
     */
    public void setNoMsg(String noMsg) {

        mTvNo.setText(noMsg);
    }

    /**
     * @param hintMsg
     */
    public void setHintMessage(int hintMsg) {

        mTvHint.setText(hintMsg);
    }

    public void setYesMessage(int yesMsg) {

        mTvYes.setText(yesMsg);
    }

    public void setNoMsg(int noMsg) {

        mTvNo.setText(noMsg);
    }

    /**
     * 确定监听事件
     *
     * @param yesListener
     */
    public void setOnConfirmListener(final OnConfirmListener yesListener) {

        mTvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                yesListener.setOnConfirmClick(CommonDialog.this);
            }
        });
    }

    /**
     * 取消监听事件
     *
     * @param cancelListener
     */
    public void setOnCancelListener(final OnCancelListener cancelListener) {
        mTvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                cancelListener.setOnCancelClick(CommonDialog.this);
            }
        });
    }

    /**
     * 确认listener
     */
    public interface OnConfirmListener {

        void setOnConfirmClick(CommonDialog instance);
    }

    /**
     * 取消listener
     */
    public interface OnCancelListener {

        void setOnCancelClick(CommonDialog instance);
    }
}
