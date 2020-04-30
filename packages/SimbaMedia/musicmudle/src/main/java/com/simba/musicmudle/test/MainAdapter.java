package com.simba.musicmudle.test;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.simba.musicmudle.R;

public class MainAdapter extends BaseAdapter {


    String[] strs = {
            "推荐歌单",
            "分类歌单",
            "排行榜",
            "歌手",
            "FM",
            "搜索歌曲",
            "搜索歌手",
            "每日推荐",
            "曲库分类热门标签",
            "相似歌曲",
            "验证码",
            "热搜词",
            "热门歌单",
            "最新歌单",
            "关键字",
            "歌曲详情",
            "歌单详情"
    };

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
        View contentView = View.inflate(viewGroup.getContext(), R.layout.music_adapter_mainitem, null);
        TextView textView = contentView.findViewById(R.id.tv);
        textView.setText(strs[i]);
        return contentView;
    }

}
