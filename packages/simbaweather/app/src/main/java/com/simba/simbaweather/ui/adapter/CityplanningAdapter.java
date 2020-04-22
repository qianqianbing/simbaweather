package com.simba.simbaweather.ui.adapter;

import android.graphics.Color;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.bean.CityplanningBean;

/**
 * @author wzy
 * @description:
 * @date :2020/4/14 16:19
 */
public class CityplanningAdapter extends BaseQuickAdapter<CityplanningBean.DataBean, BaseViewHolder> {

    int localColor, otherColor;

    public CityplanningAdapter(int layoutResId) {
        super(layoutResId);
        localColor = Color.parseColor("#FF6CF8FC");
        otherColor = Color.WHITE;
    }

    @Override
    protected void convert(BaseViewHolder helper, CityplanningBean.DataBean item) {

        helper.setText(R.id.tv_di, item.getDistrict());
        if (item.getId() == "-1") {
            //本地定位城市
            helper.setTextColor(R.id.tv_di, localColor);
            helper.setBackgroundRes(R.id.ll_bg, R.mipmap.add_city_local_city_bg);
            helper.setGone(R.id.iv_add_city_local, true);
        } else {
            //推荐城市
            helper.setTextColor(R.id.tv_di, otherColor);
            helper.setBackgroundRes(R.id.ll_bg, R.mipmap.add_city_recommend_bg);
            helper.setGone(R.id.iv_add_city_local, false);
        }
    }
}
