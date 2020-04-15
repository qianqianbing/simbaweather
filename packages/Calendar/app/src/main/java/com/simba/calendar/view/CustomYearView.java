/*
 * Copyright (C) 2016 huanghaibin_dev <huanghaibin_dev@163.com>
 * WebSite https://github.com/MiracleTimes-Dev
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.simba.calendar.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.YearView;
import com.simba.calendar.MainActivity;
import com.simba.calendar.R;

/**
 * 自定义年视图
 * Created by huanghaibin on 2018/10/9.
 */
@SuppressWarnings("unused")
public class CustomYearView extends YearView {

    String[] monthArray;
    Bitmap bitmapPaint;

    public CustomYearView(Context context) {
        super(context);
        monthArray = getContext()
                .getResources()
                .getStringArray(com.haibin.calendarview.R.array.month_string_array);
        bitmapPaint = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_main_current_month_bg);
    }

    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void onDrawMonth(Canvas canvas, int year, int month, int x, int y, int width, int height) {

        if (MainActivity.CUR_YEAR == year) {
            if (MainActivity.CUR_MONTH == month)
                canvas.drawBitmap(bitmapPaint, (getWidth() - bitmapPaint.getWidth()) / 2f, (getHeight() - bitmapPaint.getHeight()) / 2f, new
                        Paint(Paint.ANTI_ALIAS_FLAG));
        } else {
            if (1 == month)
                canvas.drawBitmap(bitmapPaint, (getWidth() - bitmapPaint.getWidth()) / 2f, (getHeight() - bitmapPaint.getHeight()) / 2f, new
                        Paint(Paint.ANTI_ALIAS_FLAG));
        }

        float x1 = (getWidth() - mMonthTextPaint.measureText(monthArray[month - 1])) / 2;

        //文字的y轴坐标
        Paint.FontMetrics fontMetrics = mMonthTextPaint.getFontMetrics();
        float y2 = getHeight() / 2 + (Math.abs(fontMetrics.ascent) - fontMetrics.descent) / 2;

        canvas.drawText(monthArray[month - 1],
                x1,
                y2,
                mMonthTextPaint);

    }

    /**
     * 是否是闰年
     *
     * @param year year
     * @return 是否是闰年
     */
    private static boolean isLeapYear(int year) {
        return ((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0);
    }


    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void onDrawWeek(Canvas canvas, int week, int x, int y, int width, int height) {
//        String text = getContext().getResources().getStringArray(com.haibin.calendarview.R.array.year_view_week_string_array)[week];
//        canvas.drawText(text,
//                x + width / 2,
//                y + mWeekTextBaseLine,
//                mWeekTextPaint);
    }


    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
//        int cx = x + mItemWidth / 2;
//        int cy = y + mItemHeight / 2;
//        int radius = Math.min(mItemWidth, mItemHeight) / 8 * 5;
//        canvas.drawCircle(cx, cy, radius, mSelectedPaint);
        return true;
    }

    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {

    }

    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
//        float baselineY = mTextBaseLine + y;
//        int cx = x + mItemWidth / 2;
//
//        if (isSelected) {
//            canvas.drawText(String.valueOf(calendar.getDay()),
//                    cx,
//                    baselineY,
//                    hasScheme ? mSchemeTextPaint : mSelectTextPaint);
//        } else if (hasScheme) {
//            canvas.drawText(String.valueOf(calendar.getDay()),
//                    cx,
//                    baselineY,
//                    calendar.isCurrentDay() ? mCurDayTextPaint : mSchemeTextPaint);
//
//        } else {
//            canvas.drawText(String.valueOf(calendar.getDay()), cx, baselineY,
//                    calendar.isCurrentDay() ? mCurDayTextPaint : mCurMonthTextPaint);
//        }
    }

    /**
     * dp转px
     *
     * @param context context
     * @param dpValue dp
     * @return px
     */
    private static int dipToPx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
