package com.simba.simbamedia;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MainAdapter extends BaseAdapter {


    String[] strs = {"推荐歌单","分类歌单","排行榜","歌手","FM","搜索","每日推荐"};

    public MainAdapter() {

    }

    @Override
    public int getCount() {
        return strs.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View contentView = View.inflate(viewGroup.getContext(), R.layout.adapter_mainitem, null);
        TextView textView = contentView.findViewById(R.id.tv);
        textView.setText(strs[i]);
        return contentView;
    }

}
