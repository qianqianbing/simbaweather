package com.simba.themestore.banner.indicator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.youth.banner.indicator.BaseIndicator;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/20
 * @Desc :
 */
public class RectIndicator extends BaseIndicator {

    private float mDistance=10;//间隔距离
    private float mRadius=5;//半径
    private float mOffset=0;

    public RectIndicator(Context context) {
        super(context);
    }

    public RectIndicator(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RectIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


        int count = config.getIndicatorSize();
        if (count <= 1) return;
        //间距*（总数-1）+默认宽度*总数
        int width = (int) ((count - 1) * config.getIndicatorSpace() + config.getNormalWidth() * count);
        //setMeasuredDimension(width, config.getHeight());
    }

    @Override
    public void onPageChanged(int count, int currentPosition) {
//        if (currentPosition == count - 1 && !isLeft) {//第一个 右滑
//            mOffset = percent * mDistance;
//        }
//        if (mPosition == mNum - 1 && isLeft) {//最后一个 左滑
//            mOffset = percent * mDistance;
//        } else {//中间
//            mOffset = percent * mDistance;
//        }
        super.onPageChanged(count, currentPosition);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int mNum = config.getIndicatorSize();
        int mPosition=config.getCurrentPosition();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(3);

        if (mPosition == mNum - 1) {//最后一个 右滑
            //第一个 线 选中 消失
            float leftClose = -(mNum) * 0.5f * mDistance - mRadius;
            float rightClose = leftClose + 2 * mRadius + mOffset;
            float topClose = -mRadius;
            float bottomClose = mRadius;
            RectF rectClose = new RectF(leftClose, topClose, rightClose, bottomClose);// 设置个新的长方形
            canvas.drawRoundRect(rectClose, mRadius, mRadius, mPaint);
            //最后一个 线  显示
            float rightOpen = -(mNum) * 0.5f * mDistance + mNum * mDistance + mRadius;
            float leftOpen = rightOpen - 2 * mRadius - mDistance + mOffset;
            float topOpen = -mRadius;
            float bottomOpen = mRadius;
            RectF rectOpen = new RectF(leftOpen, topOpen, rightOpen, bottomOpen);// 设置个新的长方形
            canvas.drawRoundRect(rectOpen, mRadius, mRadius, mPaint);
            //圆
            for (int i = 1; i < mNum; i++) {
                canvas.drawCircle(rightClose - mRadius + i * mDistance, 0, mRadius, mPaint);
            }

        } else {
            //第一个 线 选中 消失
            float leftClose = -(mNum) * 0.5f * mDistance + mPosition * mDistance - mRadius;
            float rightClose = leftClose + 2 * mRadius + mDistance - mOffset;
            float topClose = -mRadius;
            float bottomClose = mRadius;
            RectF rectClose = new RectF(leftClose, topClose, rightClose, bottomClose);// 设置个新的长方形
            canvas.drawRoundRect(rectClose, mRadius, mRadius, mPaint);
            //第二个 线  显示
            if (mPosition < mNum - 1) {
                float rightOpen = -(mNum) * 0.5f * mDistance + (mPosition + 2) * mDistance + mRadius;
                float leftOpen = rightOpen - 2 * mRadius - mOffset;
                float topOpen = -mRadius;
                float bottomOpen = mRadius;
                RectF rectOpen = new RectF(leftOpen, topOpen, rightOpen, bottomOpen);// 设置个新的长方形
                canvas.drawRoundRect(rectOpen, mRadius, mRadius, mPaint);
            }
            //圆
            for (int i = mPosition + 3; i <= mNum; i++) {
                canvas.drawCircle(-(mNum) * 0.5f * mDistance + i * mDistance, 0, mRadius, mPaint);
            }
            for (int i = mPosition - 1; i >= 0; i--) {
                canvas.drawCircle(-(mNum) * 0.5f * mDistance + i * mDistance, 0, mRadius, mPaint);
            }
        }
    }
}


