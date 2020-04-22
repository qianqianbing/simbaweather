package com.simba.simbaweather.ui.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.bean.CitySearchBean;

import java.util.List;

/**
 * @author wzy
 * @description:
 * @date :2020/4/20 15:13
 */
public class CityIdAdapter extends BaseQuickAdapter<CitySearchBean.DataBean.WeatherListBean, BaseViewHolder> {
    public CityIdAdapter(int layoutResId, @Nullable List<CitySearchBean.DataBean.WeatherListBean> data) {
        super(layoutResId, data);
    }

    private String conditionId;

    @Override
    protected void convert(BaseViewHolder helper, CitySearchBean.DataBean.WeatherListBean item) {
        //日期
        helper.setText(R.id.tv_tomomorro, item.getDayStr());
        //日历
        helper.setText(R.id.tv_date, item.getDate());
        //天气特征
        helper.setText(R.id.tv_weathersituation, item.getCondition());
        //温度
        helper.setText(R.id.tv_tirtmp, item.getTempDay() + "°/" + item.getTempNight() + "°");
        //天气图标
        ImageView mivIMG = helper.getView(R.id.miv_img);
        conditionId = item.getConditionId();
        //进行天气的判断是晴天还是其他的一些天气
        if (conditionId.equals("1")) {
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.drawable.duoyun));
        } else if (conditionId.equals("0")) {
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.drawable.qingtian));
        }
    }
}
