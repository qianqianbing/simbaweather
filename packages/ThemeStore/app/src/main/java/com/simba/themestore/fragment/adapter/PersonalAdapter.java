package com.simba.themestore.fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.simba.themestore.R;
import com.simba.themestore.fragment.adapter.viewholder.PersonalViewHolder;
import com.simba.themestore.launch.personal.PersonalAboutActivity;
import com.simba.themestore.launch.personal.PersonalLockActivity;
import com.simba.themestore.launch.personal.PersonalThemeActivity;
import com.simba.themestore.launch.personal.PersonalWallPaperActivity;
import com.simba.themestore.model.personal.PersonalMenuBean;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/7
 * @Desc :
 */
public class PersonalAdapter extends RecyclerView.Adapter<PersonalViewHolder> {

    private List<PersonalMenuBean> mData;

    public PersonalAdapter(List<PersonalMenuBean> mData) {
        this.mData = mData;
    }

    @NonNull
    @Override
    public PersonalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        PersonalViewHolder viewHolder = new PersonalViewHolder(parent, R.layout.item_fragment_personal);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PersonalViewHolder holder, int position) {
        holder.setData(mData.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpActivity(holder.itemView.getContext(), position);
            }
        });

    }

    private void jumpActivity(Context cxt, int pos) {
        switch (pos) {
            case 0:
                startActivity(cxt, PersonalThemeActivity.class);
                break;
            case 1:
                startActivity(cxt, PersonalWallPaperActivity.class);
                break;
            case 2:
                startActivity(cxt, PersonalLockActivity.class);
                break;
            case 3:
                startActivity(cxt, PersonalAboutActivity.class);
                break;
        }
    }

    private void startActivity(Context cxt, Class<?> cls) {
        Intent intent = new Intent(cxt, cls);
        cxt.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void refresh(List<PersonalMenuBean> mData) {
        this.mData = mData;
        notifyDataSetChanged();
    }

}
