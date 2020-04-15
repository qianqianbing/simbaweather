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
 * @Desc : 键盘适配器
 */
public class ProvicesAdapter extends RecyclerView.Adapter<KeyViewViewHolder> {
    /**
     * 数据源
     */
    private List<String> mData;
    /**
     * 单个点击监听
     */
    private OnItemClickListener onItemClickListener;
    /**
     * 选中的位置
     */
    private int checkPos = -1;

    /**
     *
     * @param mData
     * @param onItemClickListener
     */
    public ProvicesAdapter(List<String> mData, OnItemClickListener onItemClickListener) {
        this.mData = mData;
        this.onItemClickListener = onItemClickListener;
    }

    public ProvicesAdapter(List<String> mData, OnItemClickListener onItemClickListener, int checkPos) {
        this.mData = mData;
        this.onItemClickListener = onItemClickListener;
        this.checkPos = checkPos;
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

    public int getCheckPos() {
        return checkPos;
    }

    public void setCheckPos(int checkPos) {
        this.checkPos = checkPos;
    }
}