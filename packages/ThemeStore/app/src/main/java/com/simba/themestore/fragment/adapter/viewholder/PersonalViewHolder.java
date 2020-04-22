package com.simba.themestore.fragment.adapter.viewholder;

import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.simba.themestore.R;
import com.simba.themestore.base.BaseViewHolder;
import com.simba.themestore.model.personal.PersonalMenuBean;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/20
 * @Desc :
 */
public class PersonalViewHolder extends BaseViewHolder<PersonalMenuBean> {

    private RelativeLayout rlRoot;
    private ImageView ivIcon;
    private TextView tvTitle;

    /**
     * @param parent
     * @param resId
     * @see R.layout.item_fragment_personal
     */
    public PersonalViewHolder(ViewGroup parent, int resId) {
        super(parent, resId);
        rlRoot = getView(R.id.rl_root);
        ivIcon = getView(R.id.iv_icon);
        tvTitle = getView(R.id.tv_title);
    }

    @Override
    public void setData(PersonalMenuBean data) {
        rlRoot.setBackgroundResource(data.getBgRes());
        ivIcon.setImageResource(data.getIconRes());
        tvTitle.setText(data.getTitle());


    }
}
