package com.simba.musicmudle.view.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.simba.musicmudle.R;

import java.util.List;

import cn.kuwo.base.bean.Music;

public class RecommedDayAdapter extends BaseAdapter {

    List<Music> mMusics;

    @Override
    public int getCount() {
        return mMusics == null?0:mMusics.size();
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
    public View getView(int i, View containerView, ViewGroup viewGroup) {
        ViewHolder  viewHolder;

        Music music = mMusics.get(i);
        if(containerView == null){
            containerView = View.inflate(viewGroup.getContext(), R.layout.adapter_recommendday,null);
            viewHolder = new ViewHolder();
            viewHolder.iv_music = containerView.findViewById(R.id.iv_music);
            viewHolder.tv_recommenddayname = containerView.findViewById(R.id.tv_recommenddayname);
            viewHolder.tv_recommenddaysinger = containerView.findViewById(R.id.tv_recommenddaysinger);
            containerView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) containerView.getTag();
        }

        viewHolder.tv_recommenddayname.setText(music.name);
        viewHolder.tv_recommenddaysinger.setText(music.artist);
        return containerView;
    }


    class ViewHolder{
        ImageView  iv_music;
        TextView   tv_recommenddayname;
        TextView   tv_recommenddaysinger;
    }



    public void doNotify(List<Music>  music){
        mMusics = music;
        notifyDataSetChanged();
    }



}
