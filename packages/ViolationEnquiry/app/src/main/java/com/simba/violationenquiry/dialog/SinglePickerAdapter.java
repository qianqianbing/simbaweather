package com.simba.violationenquiry.dialog;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.simba.violationenquiry.R;
import com.simba.violationenquiry.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc :
 */
public class SinglePickerAdapter<T extends KeyValue> extends BaseAdapter {


    private List<T> mData;

    private int resLayout;

    public SinglePickerAdapter(List<T> data, int markStyle) {
        resLayout = R.layout.item_single_choose;
        if (markStyle == SinglePickerManager.STYLE_RIGHT) {
            resLayout = R.layout.item_single_choose_right;
        }
        if (data == null) {
            data = new ArrayList<>();
        }

        mData = data;
    }

    public SinglePickerAdapter(List<T> data) {
        this(data, SinglePickerManager.STYLE_NORMAL);
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MySingleHolder singleHolder = null;
        if (convertView == null) {
            try {
                convertView = ResourceUtils.getViewFollowParent(resLayout, parent);
                singleHolder = new MySingleHolder();
                singleHolder.cbSingle = convertView.findViewById(R.id.cb_single);
                convertView.setTag(singleHolder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

            singleHolder = (MySingleHolder) convertView.getTag();
        }
        singleHolder.cbSingle.setText(mData.get(position).getValue());
        return convertView;
    }

    public void refresh(List<T> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        mData = data;
        notifyDataSetChanged();
    }


    static class MySingleHolder {

        CheckBox cbSingle;
    }
}
