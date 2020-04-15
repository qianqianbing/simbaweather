package com.simba.calendar.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.simba.calendar.R;

import java.util.Calendar;
import java.util.Date;

/**
 * ================================================
 * 作    者：谢广胜
 * 版    本：1.0
 * 创建日期：2020/4/15 15:44
 * 描    述：日期选择器构建类
 * 修订历史：
 * ================================================
 */
public class TimePickerBuilderHelper {

    public interface OnTimeSelectListener {
        void onTimeSelect(Calendar date, View v);
    }

    TimePickerView pvTime;

    public TimePickerBuilderHelper(Context context, final OnTimeSelectListener onTimeSelectListener) {
        java.util.Calendar startDate = java.util.Calendar.getInstance();
        startDate.set(1990, 1, 1);
        java.util.Calendar endDate = java.util.Calendar.getInstance();
        endDate.set(2099, 12, 30);
        //时间选择器 ，自定义布局
        pvTime = new TimePickerBuilder(context,
                new com.bigkoo.pickerview.listener.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        java.util.Calendar selectData = java.util.Calendar.getInstance();
                        selectData.setTime(date);
                        onTimeSelectListener.onTimeSelect(selectData, v);
                    }
                })
                .setLayoutRes(R.layout.time_picker_dialog, new CustomListener() {
                    @Override
                    public void customLayout(final View v) {
                        TextView tvSubmit = (TextView) v.findViewById(R.id.tv_positive);
                        TextView tvCancel = (TextView) v.findViewById(R.id.tv_negative);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.returnData();
                                pvTime.dismiss();
                            }
                        });
                        tvCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvTime.dismiss();
                            }
                        });
//                            //公农历切换
//                            CheckBox cb_lunar = (CheckBox) v.findViewById(R.id.cb_lunar);
//                            cb_lunar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                                @Override
//                                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                                    pvTime.setLunarCalendar(!pvTime.isLunarCalendar());
//                                    //自适应宽
//                                    setTimePickerChildWeight(v, isChecked ? 0.8f : 1f, isChecked ? 1f : 1.1f);
//                                }
//                            });
                    }

                    /**
                     * 公农历切换后调整宽
                     * @param v
                     * @param yearWeight
                     * @param weight
                     */
                    private void setTimePickerChildWeight(View v, float yearWeight, float weight) {
                        ViewGroup timePicker = (ViewGroup) v.findViewById(R.id.timepicker);
                        View year = timePicker.getChildAt(0);
                        LinearLayout.LayoutParams lp = ((LinearLayout.LayoutParams) year.getLayoutParams());
                        lp.weight = yearWeight;
                        year.setLayoutParams(lp);
                        for (int i = 1; i < timePicker.getChildCount(); i++) {
                            View childAt = timePicker.getChildAt(i);
                            LinearLayout.LayoutParams childLp = ((LinearLayout.LayoutParams) childAt.getLayoutParams());
                            childLp.weight = weight;
                            childAt.setLayoutParams(childLp);
                        }
                    }
                })
                .setDate(java.util.Calendar.getInstance())
                .setRangDate(startDate, endDate)
                .isDialog(true)
                .setContentTextSize(34)
                .setItemVisibleCount(3) //若设置偶数，实际值会加1（比如设置6，则最大可见条目为7）
                .setLineSpacingMultiplier(3.5f)
                .setBgColor(Color.TRANSPARENT)
                .setTextColorCenter(Color.WHITE)
                .setTextColorOut(Color.parseColor("#494A51"))
                .isAlphaGradient(true)
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .setDividerColor(Color.parseColor("#303238"))
                .build();

        Dialog mDialog = pvTime.getDialog();
        if (mDialog != null) {
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    dipToPx(context, 700),
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    Gravity.CENTER);

            params.leftMargin = 0;
            params.rightMargin = 0;
            pvTime.getDialogContainerLayout().setLayoutParams(params);

            Window dialogWindow = mDialog.getWindow();
            if (dialogWindow != null) {
                dialogWindow.setWindowAnimations(com.bigkoo.pickerview.R.style.picker_view_scale_anim);//修改动画样式
                dialogWindow.setGravity(Gravity.CENTER);//改成Bottom,底部显示
                dialogWindow.setDimAmount(0.5f);
            }
        }
    }

    public TimePickerView getTimePickerView() {
        return pvTime;
    }

    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
