package com.simba.base.dialog.adapter;


import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.simba.base.R;
import com.simba.base.dialog.model.KeyValue;
import com.simba.base.dialog.picker.SinglePickerManager;
import com.simba.base.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc :单选dialog 列表适配器
 */
public class SingleChooseAdapter<T extends KeyValue> extends BaseAdapter {

    /**
     * 数据源
     */
    private List<T> mData;
    /**
     * 不同的style
     */
    private int resLayout;

    /**
     * @param data
     * @param
     */
    public SingleChooseAdapter(List<T> data ) {

        resLayout = R.layout.item_single;

        if (data == null) {
            data = new ArrayList<>();
        }

        mData = data;
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

    /**
     * 刷新
     *
     * @param data
     */
    public void refresh(List<T> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        mData = data;
        notifyDataSetChanged();
    }


    static class MySingleHolder {

        TextView cbSingle;
    }
}
