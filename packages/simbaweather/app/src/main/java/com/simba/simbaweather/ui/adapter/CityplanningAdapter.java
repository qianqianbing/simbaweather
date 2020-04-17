package com.simba.simbaweather.ui.adapter;

import android.content.Context;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.bean.CityplanningBean;

import java.util.List;

/**
 * @author wzy
 * @description:
 * @date :2020/4/14 16:19
 */
public class CityplanningAdapter extends BaseQuickAdapter<CityplanningBean.DataBean, BaseViewHolder> {
private Context context;
    public CityplanningAdapter(int layoutResId, @Nullable List<CityplanningBean.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CityplanningBean.DataBean item) {

        helper.setText(R.id.tv_di, item.getDistrict());

    }


}
