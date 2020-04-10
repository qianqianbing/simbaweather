package com.simba.violationenquiry.ui.adapter.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simba.violationenquiry.R;
import com.simba.violationenquiry.base.BaseViewHolder;
import com.simba.violationenquiry.net.model.detail.ViolateResDetail;
import com.simba.violationenquiry.utils.ResourceUtils;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/7
 * @Desc : 违章详情ViewHolder
 */
public class CarInfoViewViewHolder extends BaseViewHolder<ViolateResDetail> {
    /**
     * 没有查到数据的空布局
     */
    private RelativeLayout rlDetailEmpty;
    /**
     * 扣分
     */
    private TextView tvScore;
    /**
     * 钱
     */
    private TextView tvMoney;
    /**
     * 违章时间
     */
    private TextView tvTime;
    /**
     * 违章地点
     */
    private TextView tvLocation;
    /**
     * 违章详情
     */
    private TextView tvDetail;
    /**
     * 处理状态
     */
    private TextView tvStatus;

    /**
     * @param parent
     * @param resId
     */
    public CarInfoViewViewHolder(ViewGroup parent, int resId) {
        super(parent, resId);
        rlDetailEmpty = getView(R.id.rl_detail_empty);
        tvScore = getView(R.id.tv_score);
        tvMoney = getView(R.id.tv_money);
        tvTime = getView(R.id.tv_time);
        tvLocation = getView(R.id.tv_location);
        tvDetail = getView(R.id.tv_detail);
        tvStatus = getView(R.id.tv_status);
    }

    @Override
    public void setData(ViolateResDetail data) {
        rlDetailEmpty.setVisibility(View.GONE);
        tvScore.setText(data.getViolatescore());
        tvMoney.setText(data.getViolateamount());
        tvTime.setText(data.getViolatetime());
        tvLocation.setText(data.getViolateaddress());
        tvDetail.setText(data.getViolatemotion());
        tvStatus.setText(data.getHandletagval());
        tvStatus.setTextColor(setStatusColor(data.getHandletag()));
    }

    /**
     * 获取处理状态的文字颜色
     *
     * @param sTag
     * @return
     */
    private int setStatusColor(String sTag) {
        int res = ResourceUtils.getColor(R.color.yellow_fdc53e);
        int tag = Integer.parseInt(sTag);
        switch (tag) {
            case 1:
                res = ResourceUtils.getColor(R.color.yellow_fdc53e);
                break;
            case 2:
                res = ResourceUtils.getColor(R.color.red_fdc53e);
                break;
            case 3:
                res = ResourceUtils.getColor(R.color.green_78b202);
                break;
        }
        return res;
    }

    @Override
    public void setEmptyView() {
        rlDetailEmpty.setVisibility(View.VISIBLE);
    }
}
