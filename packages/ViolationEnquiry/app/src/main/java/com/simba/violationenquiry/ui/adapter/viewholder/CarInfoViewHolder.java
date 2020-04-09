package com.simba.violationenquiry.ui.adapter.viewholder;

import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simba.violationenquiry.R;
import com.simba.violationenquiry.base.BaseHolder;
import com.simba.violationenquiry.net.model.detail.ViolateResDetail;
import com.simba.violationenquiry.utils.ResourceUtils;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/7
 * @Desc :
 */
public class CarInfoViewHolder extends BaseHolder<ViolateResDetail> {

    private RelativeLayout rlDetailEmpty;
    private TextView tvScore;
    private TextView tvMoney;
    private TextView tvTime;
    private TextView tvLocation;
    private TextView tvDetail;
    private TextView tvStatus;

    public CarInfoViewHolder(ViewGroup parent, int resId) {
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
