package com.simba.clean;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MemoryAdapter extends BaseAdapter {

    private int colors[] = {
            R.color.colorModule1,
            R.color.colorModule2,
            R.color.colorModule3,
            R.color.colorModule4,
            R.color.colorModule5,
            R.color.colorModule6,
    };

    private int names[]={
            R.string.tv_module1,
            R.string.tv_module2,
            R.string.tv_module3,
            R.string.tv_module4,
            R.string.tv_module5,
            R.string.tv_module6,

    };
    List<MemoryItemVo> memoryItemVoList;

    public MemoryAdapter(){
        memoryItemVoList = new ArrayList<>();
        for(int i=0;i<6;i++){
            MemoryItemVo memoryItemVo = new MemoryItemVo();
            memoryItemVo.color = colors[i];
            memoryItemVo.name  = names[i];
            memoryItemVoList.add(memoryItemVo);
        }

    }


    @Override
    public int getCount() {
        return memoryItemVoList==null?0:memoryItemVoList.size();
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
        Context context = viewGroup.getContext();
        View contentView = View.inflate(viewGroup.getContext(),R.layout.adapter_memory,null);
        ImageView ic_iconlogo =  contentView.findViewById(R.id.ic_iconlogo);
        TextView  tv_modulename =  contentView.findViewById(R.id.tv_modulename);

        GradientDrawable drawable = (GradientDrawable) ic_iconlogo.getBackground();
        drawable.setColor(context.getResources().getColor(colors[i]));
        tv_modulename.setText(names[i]);
        return contentView;
    }




}
