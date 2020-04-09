package com.simba.violationenquiry.ui.view.adapter;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simba.violationenquiry.R;
import com.simba.violationenquiry.ui.view.adapter.viewholder.KeyViewViewHolder;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/8
 * @Desc :
 */
public class ProvicesAdapter extends RecyclerView.Adapter<KeyViewViewHolder> {

    private List<String> mData;
    private OnItemClickListener onItemClickListener;
    private int checkPos = -1;

    public ProvicesAdapter(List<String> mData, OnItemClickListener onItemClickListener) {
        this.mData = mData;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public KeyViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new KeyViewViewHolder(parent, R.layout.item_keyboard);
    }

    @Override
    public void onBindViewHolder(@NonNull final KeyViewViewHolder holder, final int position) {
        holder.setData(mData.get(position));
        holder.setBackground(checkPos == position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPos = position;

                onItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

}