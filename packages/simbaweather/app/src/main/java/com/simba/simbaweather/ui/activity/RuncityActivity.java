package com.simba.simbaweather.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.okgo.model.Response;
import com.simba.base.dialog.DialogUtil;
import com.simba.base.utils.LogUtil;
import com.simba.base.utils.SpStaticUtils;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.bean.CitySearchBean;
import com.simba.simbaweather.di.cityidMvp.CityIdContract;
import com.simba.simbaweather.di.cityidMvp.CityIdPresnter;
import com.simba.simbaweather.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class RuncityActivity extends BaseActivity<CityIdContract.ICityIdView, CityIdPresnter<CityIdContract.ICityIdView>> implements CityIdContract.ICityIdView {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_compileon)
    TextView tvCompileon;
    @BindView(R.id.tv_compileoff)
    TextView tvCompileoff;
    @BindView(R.id.tv_citymangername1)
    TextView tvCitymangername1;
    @BindView(R.id.iv_citymangerimg1)
    ImageView ivCitymangerimg1;
    @BindView(R.id.tv_citymangerwendu1)
    TextView tvCitymangerwendu1;
    @BindView(R.id.tv_citymangermaxmin1)
    TextView tvCitymangermaxmin1;
    @BindView(R.id.tv_remove)
    ImageView tvRemove;
    @BindView(R.id.add_tv_gps)
    TextView addTvGps;
    @BindView(R.id.add_iv_img)
    ImageView addIvImg;
    @BindView(R.id.add_tv_temperature)
    TextView addTvTemperature;
    @BindView(R.id.add_tv_maxmin)
    TextView addTvMaxmin;
    @BindView(R.id.rl_remove)
    RelativeLayout rlRemove;
    @BindView(R.id.tv_remove_a)
    ImageView tvRemoveA;
    @BindView(R.id.add_tv_gps_a)
    TextView addTvGpsA;
    @BindView(R.id.add_iv_img_a)
    ImageView addIvImgA;
    @BindView(R.id.add_tv_temperature_a)
    TextView addTvTemperatureA;
    @BindView(R.id.add_tv_maxmin_a)
    TextView addTvMaxminA;
    @BindView(R.id.rl_remove_a)
    RelativeLayout rlRemoveA;
    @BindView(R.id.tv_Addpend)
    ImageView tvAddpend;

    private Intent intent;
    private String conditionId;
    private String city;
    private String district;
    private String temp;
    private String tempDay;
    private String tempNight;
    private String cityid0;
    private CitySearchBean.DataBean.WeatherTodayBean weatherToday;

    @Override
    protected void initData() {
        cityid0 = SpStaticUtils.getString("cityid0", "");
        mPresenter.RequestCityData("2");
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_runcity;
    }

    @Override
    protected void initView() {
        super.initView();

        intent = getIntent();
        city = intent.getStringExtra("city");
        conditionId = intent.getStringExtra("conditionId");
        district = intent.getStringExtra("district");
        temp = intent.getStringExtra("temp");
        tempDay = intent.getStringExtra("tempDay");
        tempNight = intent.getStringExtra("tempNight");

        tvCitymangername1 = findViewById(R.id.tv_citymangername1);
        tvCitymangername1.setText(city);
        tvCitymangermaxmin1.setText("" + tempDay + "°/" + tempNight + "°");
        tvCitymangerwendu1.setText("" + temp + "°");
        if (conditionId.equals("1")) {
            ivCitymangerimg1.setBackgroundResource(R.drawable.qingtian);
        }

        tvCompileoff.setVisibility(View.INVISIBLE);
        tvRemove.setVisibility(View.INVISIBLE);
        tvRemoveA.setVisibility(View.INVISIBLE);
    }

    @Override
    protected CityIdPresnter<CityIdContract.ICityIdView> oncreatePresenter() {
        return new CityIdPresnter<>();
    }

    @Override
    public void CityIdData(Response<CitySearchBean.DataBean> response) {
        weatherToday = response.body().getWeatherToday();
        addTvGps.setText(response.body().getCity().getProvince() + "." + response.body().getCity().getDistrict());
        addTvTemperature.setText(response.body().getWeatherToday().getTempDay());
        addTvMaxmin.setText(response.body().getWeatherToday().getTempNight() + "" + response.body().getWeatherToday().getTempDay());

        addTvGpsA.setText(response.body().getCity().getProvince() + "." + response.body().getCity().getDistrict());
        addTvTemperatureA.setText(response.body().getWeatherToday().getTempDay());
        addTvMaxminA.setText(response.body().getWeatherToday().getTempNight() + "" + response.body().getWeatherToday().getTempDay());

    }

    @OnClick({R.id.tv_compileon, R.id.tv_compileoff, R.id.tv_remove, R.id.tv_Addpend, R.id.iv_back, R.id.tv_remove_a})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_compileon:
                //打开
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
                DialogUtil.build(RuncityActivity.this)
                        .content("确定删除该城市?")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new DialogUtil.SingleButtonCallback() {
                            @Override
                            public void onClick(DialogUtil dialogUtil, DialogUtil.DialogAction dialogAction) {
                                tvCompileoff.setVisibility(View.INVISIBLE);
                                tvCompileon.setVisibility(View.VISIBLE);
                                tvRemove.setVisibility(View.INVISIBLE);
                                rlRemove.removeAllViews();
                                LogUtil.e(dialogAction + "删除成功");
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
                Intent intent = new Intent(RuncityActivity.this, AddCityActivity.class);
                intent.putExtra("city", city);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_remove_a:
                DialogUtil.build(RuncityActivity.this)
                        .content("确定删除该城市?")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new DialogUtil.SingleButtonCallback() {
                            @Override
                            public void onClick(DialogUtil dialogUtil, DialogUtil.DialogAction dialogAction) {
                                tvCompileoff.setVisibility(View.INVISIBLE);
                                tvCompileon.setVisibility(View.VISIBLE);
                                tvRemoveA.setVisibility(View.INVISIBLE);
                                rlRemoveA.removeAllViews();
                                LogUtil.e(dialogAction + "删除成功");
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
        }
    }

}
