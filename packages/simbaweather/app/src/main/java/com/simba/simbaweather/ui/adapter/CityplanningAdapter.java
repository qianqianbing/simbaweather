package com.simba.simbaweather.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.bean.CityplanningBean;
import com.simba.simbaweather.ui.activity.MainActivity;

import java.util.List;

/**
 * @author wzy
 * @description:
 * @date :2020/4/14 16:19
 */
public class CityplanningAdapter extends BaseQuickAdapter<CityplanningBean.DataBean, BaseViewHolder> {
    private String cityid;

    public CityplanningAdapter(int layoutResId, @Nullable List<CityplanningBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityplanningBean.DataBean item) {
        cityid = item.getId();
        helper.setText(R.id.tv_di, item.getDistrict());

    }


}
