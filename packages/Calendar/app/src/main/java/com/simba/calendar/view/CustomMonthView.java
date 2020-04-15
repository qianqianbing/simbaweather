package com.simba.calendar.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.MonthView;
import com.simba.calendar.R;

/**
 * 高仿魅族日历布局
 * Created by huanghaibin on 2017/11/15.
 */

public class CustomMonthView extends MonthView {

    Bitmap currentBitmap;
    Bitmap selectBitmap;
    Bitmap restBitmap;
    Bitmap workBitmap;

    Paint bitmapPaint;

    /**
     * 自定义魅族标记的圆形背景
     */
    private Paint mSchemeBasicPaint = new Paint();

    public CustomMonthView(Context context) {
        super(context);

        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        currentBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_main_current_day_bg);
        selectBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_main_select_day_bg);
        restBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_main_rest_day_bg);
        workBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_main_work_day_bg);

        mSchemeBasicPaint.setAntiAlias(true);
        mSchemeBasicPaint.setStyle(Paint.Style.FILL);
        mSchemeBasicPaint.setTextAlign(Paint.Align.CENTER);
        mSchemeBasicPaint.setFakeBoldText(true);

        //兼容硬件加速无效的代码
        setLayerType(View.LAYER_TYPE_SOFTWARE, mSchemeBasicPaint);
//        //4.0以上硬件加速会导致无效
//        mSelectedPaint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.SOLID));

    }

    /**
     * 绘制选中的日子
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param y         日历Card y起点坐标
     * @param hasScheme hasScheme 非标记的日期
     * @return true 则绘制onDrawScheme，因为这里背景色不是是互斥的
     */
    @Override
    protected boolean onDrawSelected(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme) {
//        canvas.drawRect(x, y, x + mItemWidth, y + mItemHeight, mSelectedPaint);

        canvas.drawBitmap(selectBitmap, x + (mItemWidth - selectBitmap.getWidth()) / 2f, y + (mItemHeight - selectBitmap.getHeight()) / 2f, bitmapPaint);

        return true;
    }

    /**
     * 绘制标记的事件日子
     *
     * @param canvas   canvas
     * @param calendar 日历calendar
     * @param x        日历Card x起点坐标
     * @param y        日历Card y起点坐标
     */
    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void onDrawScheme(Canvas canvas, Calendar calendar, int x, int y) {


        String scheme = calendar.getScheme();
        if ("rest".equals(scheme)) {
            canvas.drawBitmap(restBitmap, x + (mItemWidth - selectBitmap.getWidth()) / 2f, y + (mItemHeight - selectBitmap.getHeight()) / 2f, bitmapPaint);
        } else if ("work".equals(scheme)) {
            canvas.drawBitmap(workBitmap, x + (mItemWidth - selectBitmap.getWidth()) / 2f, y + (mItemHeight - selectBitmap.getHeight()) / 2f, bitmapPaint);
        }

//        mSchemeBasicPaint.setColor(calendar.getSchemeColor());
//        List<Calendar.Scheme> schemes = calendar.getSchemes();
//        if (schemes == null || schemes.size() == 0) {
//            return;
//        }
//        int space = dipToPx(getContext(), 2);
//        int indexY = y + mItemHeight - 2 * space;
//        int sw = dipToPx(getContext(), mItemWidth / 6);
//        int sh = dipToPx(getContext(), 4);
//        for (Calendar.Scheme scheme : schemes) {
//
//            mSchemePaint.setColor(scheme.getShcemeColor());
//
//            canvas.drawRect(x + mItemWidth - sw - 2 * space,
//
//                    indexY - sh, x + mItemWidth - 2 * space, indexY, mSchemePaint);
//            indexY = indexY - space - sh;
//        }
    }

    /**
     * 绘制文本
     *
     * @param canvas     canvas
     * @param calendar   日历calendar
     * @param x          日历Card x起点坐标
     * @param y          日历Card y起点坐标
     * @param hasScheme  是否是标记的日期
     * @param isSelected 是否选中
     */
    @SuppressWarnings("IntegerDivisionInFloatingPointContext")
    @Override
    protected void onDrawText(Canvas canvas, Calendar calendar, int x, int y, boolean hasScheme, boolean isSelected) {
        int cx = x + mItemWidth / 2;
        int top = y - mItemHeight / 6;
        boolean isInRange = isInRange(calendar);
        if (calendar.isCurrentDay()) {
            canvas.drawBitmap(currentBitmap, x + (mItemWidth - selectBitmap.getWidth()) / 2f, y + (mItemHeight - selectBitmap.getHeight()) / 2f, bitmapPaint);
        }

        canvas.drawText(formatStr(calendar.getDay()), cx, mTextBaseLine + top,
                calendar.isCurrentDay() ? mCurDayTextPaint :
                        calendar.isCurrentMonth() && isInRange ? mCurMonthTextPaint : mOtherMonthTextPaint);
        canvas.drawText(calendar.getLunar(), cx, mTextBaseLine + y + mItemHeight / 6,
                calendar.isCurrentDay() && isInRange ? mCurDayLunarTextPaint :
                        calendar.isCurrentMonth() ? mCurMonthLunarTextPaint : mOtherMonthLunarTextPaint);
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

    private String formatStr(int number) {
        return number < 10 ? "0" + number : "" + number;
    }

}
