package com.simba.clean;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

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
    private PieChart mPieChart;
    private GridView gv_memory;
    private WaveProgressView mWaveProgressView;
    private View view_cpunting;
    private View view_cpuchart;
    private ImageView iv_loading_cpu;
    private Button bt_clean;
    private ObjectAnimator animator;
    private ObjectAnimator animator2;
    private View view_memcounting;
    private View view_memchart;
    private LinearLayout ll_memcountnum;
    private LinearLayout ll_remainmemcount;
    private TextView tv_remainmem;
    private MemoryAdapter mMemoryAdapter;
    private TextView tv_cpu;
    private LinearLayout ll_cpuper;
    private TextView tv_cleanok;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message message) {
            return false;
        }
    });

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

        view_cpunting = findViewById(R.id.view_cpucounting);
        view_cpuchart = findViewById(R.id.view_cpuchart);
        iv_loading_cpu = findViewById(R.id.iv_loading_cpu);
        bt_clean = findViewById(R.id.bt_clean);
        view_memcounting = findViewById(R.id.view_memcounting);
        view_memchart = findViewById(R.id.view_memchart);
        mWaveProgressView = findViewById(R.id.waveProgressView);
        iv_loading_memory = findViewById(R.id.iv_loading_memory);

        ll_memcountnum = findViewById(R.id.ll_memcountnum);
        ll_remainmemcount = findViewById(R.id.ll_remainmemcount);
        tv_remainmem = findViewById(R.id.tv_remainmem);
        gv_memory = findViewById(R.id.gv_memory);
        tv_cpu = findViewById(R.id.tv_cpu);
        ll_cpuper = findViewById(R.id.ll_cpuper);
        tv_cleanok = findViewById(R.id.tv_cleanok);

        mMemoryAdapter = new MemoryAdapter();
        gv_memory.setAdapter(mMemoryAdapter);

        bt_clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bt_clean.setText(R.string.tv_cleaning);
                bt_clean.setBackgroundResource(R.drawable.ic_clean_unable);
                bt_clean.setEnabled(false);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mWaveProgressView.setCurentProgress(50);
                        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mWaveProgressView, "progress", 0f, 100f);
                        objectAnimator.setDuration(1500);
                        objectAnimator.setInterpolator(new LinearInterpolator());

                        objectAnimator.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {
                                tv_cpu.setText("112M");
                                tv_cleanok.setVisibility(View.VISIBLE);
                                ll_cpuper.setVisibility(View.INVISIBLE);
                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                bt_clean.setBackgroundResource(R.drawable.selector_clean);
                                bt_clean.setEnabled(true);
                                tv_cpu.setText("50%");
                                tv_cleanok.setVisibility(View.INVISIBLE);
                                ll_cpuper.setVisibility(View.VISIBLE);

                                bt_clean.setBackgroundResource(R.drawable.selector_clean);
                                bt_clean.setEnabled(true);
                                bt_clean.setText(R.string.tv_clean);

                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });

                        objectAnimator.start();
                    }
                },3000);
            }
        });

        initPieChart();

    }

    public void initPieChart() {
        mPieChart = findViewById(R.id.chart);
        mPieChart.getLegend().setEnabled(false);
        mPieChart.setDrawEntryLabels(false);
        mPieChart.setDrawCenterText(false);
        mPieChart.getDescription().setEnabled(false);
        mPieChart.setRotationEnabled(false);
        mPieChart.setHighlightPerTapEnabled(false);
    }


    @Override
    protected void initData() {
        showCPUCountingView();
        showMemView();
        setData(7, 99);
    }


    public void showMemView() {

        view_memcounting.setVisibility(View.VISIBLE);
        view_memchart.setVisibility(View.GONE);
        iv_loading_memory.setVisibility(View.VISIBLE);
        ll_memcountnum.setVisibility(View.VISIBLE);
        ll_remainmemcount.setVisibility(View.GONE);


        animator2 = ObjectAnimator.ofFloat(iv_loading_memory, "rotation", 0f, 360f);//旋转360度
        animator2.setInterpolator(new LinearInterpolator());
        animator2.setRepeatCount(3);//无限循环
        animator2.setDuration(1000);//设置持续时间

        animator2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                view_memcounting.setVisibility(View.GONE);
                view_memchart.setVisibility(View.VISIBLE);
                iv_loading_memory.setVisibility(View.GONE);
                ll_memcountnum.setVisibility(View.GONE);
                ll_remainmemcount.setVisibility(View.VISIBLE);
                mPieChart.animateX(1400);
                tv_remainmem.setText("3GB");
                mMemoryAdapter.updateData();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animator2.start();//动画开始

    }

    public void showCPUCountingView() {

        startCPULoadingAnim();

        view_cpunting.setVisibility(View.VISIBLE);
        view_cpuchart.setVisibility(View.GONE);
        iv_loading_cpu.setVisibility(View.VISIBLE);
        bt_clean.setBackgroundResource(R.drawable.ic_clean_unable);
        bt_clean.setEnabled(false);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (animator != null) animator.cancel();

                view_cpunting.setVisibility(View.GONE);
                view_cpuchart.setVisibility(View.VISIBLE);
                iv_loading_cpu.setVisibility(View.GONE);

                ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mWaveProgressView, "progress", 0f, 100f);
                objectAnimator.setDuration(1500);
                objectAnimator.setInterpolator(new LinearInterpolator());

                objectAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        bt_clean.setBackgroundResource(R.drawable.selector_clean);
                        bt_clean.setEnabled(true);

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {

                    }
                });

                objectAnimator.start();

            }
        }, 5000);

    }


    public void startCPULoadingAnim() {
        animator = ObjectAnimator.ofFloat(iv_loading_cpu, "rotation", 0f, 360f);//旋转360度
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(-1);//无限循环
        animator.setDuration(2000);//设置持续时间
        animator.start();//动画开始
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
