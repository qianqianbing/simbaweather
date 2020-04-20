package com.simba.clean;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import java.text.DecimalFormat;

public class WaveProgressView extends View {

    private int radius = dp2px(55);
    private int textColor;
    private int textSize;
    private int progressColor;
    private int radiusColor;
    private int radiusColor2;

    private Paint circlePaint;
    private Paint pathPaint;
    private Bitmap bitmap;
    private Canvas bitmapCanvas;
    private int width, height;
    private int minPadding;
    private float currentProgress;
    private int changeProgress;
    private float maxProgress;
    private Path path = new Path();
    private DecimalFormat df = new DecimalFormat("0.0");

    private String TAG = "WaveProgressView";

    public WaveProgressView(Context context) {
        this(context, null);
    }

    public WaveProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaveProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.WaveProgressView, defStyleAttr, R.style.WaveProgressViewDefault);
        radius = (int) a.getDimension(R.styleable.WaveProgressView_radius, radius);
        textColor = a.getColor(R.styleable.WaveProgressView_progress_text_color, 0);
        textSize = a.getDimensionPixelSize(R.styleable.WaveProgressView_progress_text_size, 0);
        progressColor = a.getColor(R.styleable.WaveProgressView_progress_color, 0);
        radiusColor = a.getColor(R.styleable.WaveProgressView_radius_color, 0);
        radiusColor2 = a.getColor(R.styleable.WaveProgressView_radius_color, 0);

        currentProgress = a.getFloat(R.styleable.WaveProgressView_progress, 0);
        Log.i(TAG,"currentProgress---------"+currentProgress);
        maxProgress = a.getFloat(R.styleable.WaveProgressView_maxProgress, 100);
        a.recycle();


        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(radiusColor);
        circlePaint.setDither(true);

        pathPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        pathPaint.setColor(progressColor);
        pathPaint.setDither(true);
        pathPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        bitmap = Bitmap.createBitmap(350, 350, Bitmap.Config.ARGB_8888);
        bitmapCanvas = new Canvas(bitmap);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //计算宽和高
        int exceptW = getPaddingLeft() + getPaddingRight() + 2 * radius;
        int exceptH = getPaddingTop() + getPaddingBottom() + 2 * radius;
        int width = resolveSize(exceptW, widthMeasureSpec);
        int height = resolveSize(exceptH, heightMeasureSpec);
        int min = Math.min(width, height);

        this.width = this.height = min;

        //计算半径,减去padding的最小值
        int minLR = Math.min(getPaddingLeft(), getPaddingRight());
        int minTB = Math.min(getPaddingTop(), getPaddingBottom());
        minPadding = Math.min(minLR, minTB);
        radius = (min - minPadding * 2) / 2;

        setMeasuredDimension(min, min);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        bitmap.eraseColor(Color.parseColor("#00000000"));


        bitmapCanvas.save();
        //移动坐标系
        bitmapCanvas.translate(minPadding, minPadding);

        bitmapCanvas.drawCircle(radius, radius, radius, circlePaint);




        //绘制PATH
        //重置绘制路线
        path.reset();
        float percent =   changeProgress * 1.0f / maxProgress;
        float y = (1 - percent) * radius * 2;
        //移动到右上边
        path.moveTo(radius * 2, y);
        //移动到最右下方
        path.lineTo(radius * 2, radius * 2);
        //移动到最左下边
        path.lineTo(0, radius * 2);
        //移动到左上边
        // path.lineTo(0, y);
        //实现左右波动,根据progress来平移
        path.lineTo(-(1 -percent) * radius*2, y);
        path.close();
        bitmapCanvas.drawPath(path, pathPaint);

        bitmapCanvas.restore();

        canvas.drawBitmap(bitmap, 0, 0, null);
    }

    public float getProgress() {
        return currentProgress;
    }

    public void setCurentProgress(float progress){
        currentProgress =  progress;
    }

    public void setProgress(float progress) {
//        this.currentProgress = Float.valueOf(df.format(progress));
        float  per = progress/100;
        changeProgress = (int) (currentProgress *  per);
        Log.i(TAG,"per--------"+per+"|"+"changeProgress---------"+changeProgress+"|"+"currentProgress-----------"+currentProgress);
        invalidate();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }

    public void setChangeProgress(int progress){
        changeProgress = progress;
        invalidate();
    }

    private final static class SavedState extends BaseSavedState {
        float progress;

        public SavedState(Parcel source) {
            super(source);
        }

        public SavedState(Parcelable superState) {
            super(superState);
        }

        public static final Creator<SavedState> CREATOR
                = new Creator<SavedState>() {
            public SavedState createFromParcel(Parcel in) {
                return new SavedState(in);
            }

            public SavedState[] newArray(int size) {
                return new SavedState[size];
            }
        };
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState ss = new SavedState(superState);
        ss.progress = currentProgress;
        return ss;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        SavedState ss = (SavedState) state;
        super.onRestoreInstanceState(ss.getSuperState());
        setChangeProgress((int) ss.progress);
    }
}
