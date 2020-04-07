package com.simba.violationenquiry.ui.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simba.violationenquiry.R;
import com.simba.violationenquiry.ui.adapter.viewholder.CarInfoViewHolder;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/7
 * @Desc :
 */
public class DetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CarInfoViewHolder viewHolder = new CarInfoViewHolder(parent, R.layout.item_car_detail);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class DetailViewHolder extends RecyclerView.ViewHolder {
        public DetailViewHolder(@NonNull View itemView) {
            super(itemView);
        }


    }


}
