package com.simba.themestore.launch.adapter.personal;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.simba.themestore.base.IBaseOption;
import com.simba.themestore.model.AbstractChoose;

import java.util.List;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/22
 * @Desc :默认添加加载更多
 */
public abstract class BasePersonalAdapter<T extends AbstractChoose> extends BaseQuickAdapter<T, BaseViewHolder> implements LoadMoreModule, IBaseOption {
    private boolean isEdit = false;

    public BasePersonalAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    public BasePersonalAdapter(int layoutResId) {
        super(layoutResId);
    }

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        getLoadMoreModule().setEnableLoadMore(!edit);
        isEdit = edit;
        if (!edit) {
            selectOption(false);//清除选择的数据
        } else {
            notifyDataSetChanged();
        }
    }

    @Override
    public void selectOption(boolean isSelectAll) {
        List<T> mData = getData();
        for (AbstractChoose abstractChoose : mData) {
            abstractChoose.setChecked(isSelectAll);
        }
        notifyDataSetChanged();
    }


}
