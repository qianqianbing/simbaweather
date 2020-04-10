package com.simba.violationenquiry.ui.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatCheckBox;

import com.simba.violationenquiry.R;
import com.simba.violationenquiry.utils.ResourceUtils;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc : 单选
 */
public class SingleCheckBox extends AppCompatCheckBox {

    public SingleCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);

        //左 上 右 下
        Drawable drawable = getCompoundDrawables()[0];
        boolean isRight = false;
        if (drawable == null) {
            drawable = getCompoundDrawables()[2];
            isRight = true;
        }
        //设置drawable 的位置属性     left top 代表drawable的位置（坐标）  距离左边和上方的距离
        //right  bottom 相当于宽和高

        int widthDimen = ResourceUtils.getDimen(R.dimen.dialog_margin);
        int heightDimen = ResourceUtils.getDimen(R.dimen.dialog_margin);

        drawable.setBounds(0, 0, widthDimen, heightDimen);
        if (isRight) {
            setCompoundDrawables(null, null, drawable, null);
        } else {
            setCompoundDrawables(drawable, null, null, null);
        }

    }
}
