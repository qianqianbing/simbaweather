package com.simba.violationenquiry.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.simba.violationenquiry.R;
import com.simba.violationenquiry.utils.ResourceUtils;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc :
 */
public class CommonDialog extends Dialog {

    private TextView mTvHint;
    private TextView mTvYes;
    private TextView mTvNo;
    private String mHintMsg;
    private View line;
    private String noMsg;
    private int yesVisuble = View.VISIBLE;

    public CommonDialog(@NonNull Context context, String hintMsg) {

        super(context, R.style.MyBaseDialog);
        mHintMsg = hintMsg;
    }

    public CommonDialog(@NonNull Context context, String hintMsg, String noMsg, int yesVisible) {

        super(context, R.style.MyBaseDialog);
        mHintMsg = hintMsg;
        this.noMsg = noMsg;
        this.yesVisuble = yesVisible;
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
        setYesVisbile(yesVisuble);
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

    public void setNoVisbile(int visbile) {
        mTvNo.setVisibility(visbile);
        line.setVisibility(visbile);
    }

    public void setYesVisbile(int visbile) {
        mTvYes.setVisibility(visbile);
        line.setVisibility(visbile);
        if (visbile == View.GONE) {
            mTvNo.setTextColor(ResourceUtils.getColor(R.color.colorPrimary));
        }
    }

    public void setHintMessage(String hintMsg) {

        mTvHint.setText(hintMsg);
    }

    public void setYesMessage(String yesMsg) {

        mTvYes.setText(yesMsg);
    }

    public void setNoMsg(String noMsg) {

        mTvNo.setText(noMsg);
    }

    public void setHintMessage(int hintMsg) {

        mTvHint.setText(hintMsg);
    }

    public void setYesMessage(int yesMsg) {

        mTvYes.setText(yesMsg);
    }

    public void setNoMsg(int noMsg) {

        mTvNo.setText(noMsg);
    }

    public void setOnConfirmListener(final OnConfirmListener yesListener) {

        mTvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                yesListener.setOnConfirmClick(CommonDialog.this);
            }
        });
    }

    public void setOnCancelListener(final OnCancelListener cancelListener) {
        mTvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                cancelListener.setOnCancelClick(CommonDialog.this);
            }
        });
    }

    public interface OnConfirmListener {

        void setOnConfirmClick(CommonDialog instance);
    }

    public interface OnCancelListener {

        void setOnCancelClick(CommonDialog instance);
    }
}
