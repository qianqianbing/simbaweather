package com.simba.violationenquiry.ui.adapter;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simba.violationenquiry.R;
import com.simba.violationenquiry.net.model.detail.ViolateResDetail;
import com.simba.violationenquiry.ui.adapter.viewholder.CarInfoViewViewHolder;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/7
 * @Desc :
 */
public class DetailAdapter extends RecyclerView.Adapter<CarInfoViewViewHolder> {

    private List<ViolateResDetail> mData;
    private boolean isEmpty = false;
    private boolean needEmpty = false;

    public DetailAdapter(List<ViolateResDetail> mData) {
        this.mData = mData;

        if (mData == null || mData.size() == 0) {
            isEmpty = true;
        }
    }

    @NonNull
    @Override
    public CarInfoViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        CarInfoViewViewHolder viewHolder = new CarInfoViewViewHolder(parent, R.layout.item_car_detail);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarInfoViewViewHolder holder, int position) {
        if (isEmpty) {
            holder.setEmptyView();
        } else {
            holder.setData(mData.get(position));
        }

    }


    @Override
    public int getItemCount() {
        int size = 0;
        if (mData != null) {
            size = mData.size();
        }
        return isEmpty ? needEmpty ? 1 : 0 : size;
    }

    public void refresh(List<ViolateResDetail> mData, boolean needEmpty) {
        isEmpty = mData == null || mData.size() == 0;
        this.mData = mData;
        this.needEmpty = needEmpty;
        notifyDataSetChanged();
    }

}
