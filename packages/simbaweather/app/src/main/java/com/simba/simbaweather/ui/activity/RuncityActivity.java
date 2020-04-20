package com.simba.simbaweather.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.simba.base.dialog.DialogUtil;
import com.simba.base.utils.LogUtil;
import com.simba.simbaweather.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RuncityActivity extends AppCompatActivity {

    @BindView(R.id.tv_citymangername)
    TextView tvCitymangername;
    @BindView(R.id.iv_citymangerimg)
    ImageView ivCitymangerimg;
    @BindView(R.id.tv_citymangerwendu)
    TextView tvCitymangerwendu;
    @BindView(R.id.tv_citymangermaxmin)
    TextView tvCitymangermaxmin;
    @BindView(R.id.tv_compileon)
    TextView tvCompileon;
    @BindView(R.id.tv_compileoff)
    TextView tvCompileoff;
    @BindView(R.id.tv_remove)
    ImageView tvRemove;
    @BindView(R.id.iv_add)
    ImageView ivAdd;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_Addpend)
    ImageView tvAddpend;
    @BindView(R.id.rl_remove)
    RelativeLayout rlRemove;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    private Intent intent;
    private String conditionId;
    private String city;
    private String district;
    private String temp;
    private String tempDay;
    private String tempNight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_runcity);
        ButterKnife.bind(this);
        ImmersionBar.with(this)
                .fullScreen(true)
                .transparentNavigationBar()
                .init();
        intent = getIntent();
        city = intent.getStringExtra("city");
        conditionId = intent.getStringExtra("conditionId");
        district = intent.getStringExtra("district");
        temp = intent.getStringExtra("temp");
        tempDay = intent.getStringExtra("tempDay");
        tempNight = intent.getStringExtra("tempNight");
        tvCitymangername.setText("" + city + "·" + district);
        tvCitymangermaxmin.setText("" + tempDay + "°/" + tempNight + "°");
        tvCitymangerwendu.setText("" + temp + "°");
        if (conditionId.equals("1")) {
            ivCitymangerimg.setBackgroundResource(R.drawable.qingtian);
        }
        tvCompileoff.setVisibility(View.INVISIBLE);
        tvRemove.setVisibility(View.INVISIBLE);
    }

    @OnClick({R.id.tv_compileon, R.id.tv_compileoff, R.id.tv_remove, R.id.tv_Addpend,R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_compileon:
                tvCompileoff.setVisibility(View.VISIBLE);
                tvCompileon.setVisibility(View.INVISIBLE);
                tvRemove.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_compileoff:
                //关闭
                tvCompileoff.setVisibility(View.INVISIBLE);
                tvCompileon.setVisibility(View.VISIBLE);
                tvRemove.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_remove:


                DialogUtil.build(this)
                        .content("确定删除该城市?")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new DialogUtil.SingleButtonCallback() {
                            @Override
                            public void onClick(DialogUtil dialogUtil, DialogUtil.DialogAction dialogAction) {
                                tvCompileoff.setVisibility(View.INVISIBLE);
                                tvCompileon.setVisibility(View.VISIBLE);
                                tvRemove.setVisibility(View.INVISIBLE);
                                LogUtil.e(dialogAction + "删除成功");
                                rlRemove.removeAllViews();
                                Toast.makeText(RuncityActivity.this, "删除成功", Toast.LENGTH_SHORT).show();

                            }
                        })
                        .onNegative(new DialogUtil.SingleButtonCallback() {
                            @Override
                            public void onClick(DialogUtil dialogUtil, DialogUtil.DialogAction dialogAction) {
                                tvCompileoff.setVisibility(View.VISIBLE);
                                tvCompileon.setVisibility(View.INVISIBLE);
                                LogUtil.e(dialogAction + "取消");
                            }
                        })
                        .show();

                break;
            case R.id.tv_Addpend:
                Intent intent = new Intent(RuncityActivity.this, AddpendActivity.class);
                intent.putExtra("city",city);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }


}
