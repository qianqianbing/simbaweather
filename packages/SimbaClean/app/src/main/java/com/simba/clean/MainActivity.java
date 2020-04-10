package com.simba.clean;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.GridView;
import android.widget.ImageView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.simba.base.base.BaseActivity;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private ImageView iv_loading_memory;
    private ObjectAnimator animator;
    private PieChart mPieChart;
    private GridView gv_memory;

    public void startMemoryLoadingAnim() {
        animator = ObjectAnimator.ofFloat(iv_loading_memory, "rotation", 0f, 360f);//旋转360度
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);//无限循环
        animator.setDuration(2000);//设置持续时间
        animator.start();//动画开始
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        iv_loading_memory = findViewById(R.id.iv_loading_memory);
        mPieChart = findViewById(R.id.chart);
        mPieChart.getLegend().setEnabled(false);
        mPieChart.setDrawEntryLabels(false);
        mPieChart.setDrawCenterText(false);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setRotationEnabled(false);
        mPieChart.setHighlightPerTapEnabled(false);

        gv_memory = findViewById(R.id.gv_memory);
        gv_memory.setAdapter(new MemoryAdapter());

    }

    @Override
    protected void initData() {
        startMemoryLoadingAnim();
        setData(7, 99);
    }


    private void setData(int count, float range) {
        ArrayList<PieEntry> entries = new ArrayList<>();

        PieDataSet dataSet = new PieDataSet(entries, "");
        for (int i = 0; i < count; i++) {
            entries.add(new PieEntry((float) ((Math.random() * range) + range / 5)));
        }

        dataSet.setDrawIcons(false);
        dataSet.setSliceSpace(0f);   //NEED1 设置边距
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(getResources().getColor(R.color.colorModule1));
        colors.add(getResources().getColor(R.color.colorModule2));
        colors.add(getResources().getColor(R.color.colorModule3));
        colors.add(getResources().getColor(R.color.colorModule4));
        colors.add(getResources().getColor(R.color.colorModule5));
        colors.add(getResources().getColor(R.color.colorModule6));
        colors.add(getResources().getColor(R.color.colorModule7));

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter(mPieChart));
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        data.setDrawValues(false);
        mPieChart.setData(data);
        mPieChart.highlightValues(null);
        mPieChart.invalidate();
    }


}
