package com.simba.simbaweather.ui.adapter;

import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.simba.base.dialog.DialogUtil;
import com.simba.base.utils.LogUtil;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.bean.CitySearchBean;
import com.simba.simbaweather.ui.activity.RuncityActivity;

import java.util.List;

/**
 * @author wzy
 * @description:
 * @date :2020/4/21 14:16
 */
public class AddAdapter extends BaseQuickAdapter<CitySearchBean.DataBean.WeatherTodayBean, BaseViewHolder> {
    public AddAdapter(int layoutResId, @Nullable List<CitySearchBean.DataBean.WeatherTodayBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CitySearchBean.DataBean.WeatherTodayBean item) {
        helper.setText(R.id.add_tv_temperature,item.getTemp()+"°");
        helper.setText(R.id.add_tv_maxmin,item.getTempDay()+"°/"+item.getTempNight()+"°");



    }
}
