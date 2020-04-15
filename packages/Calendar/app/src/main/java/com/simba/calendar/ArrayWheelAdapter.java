package com.simba.calendar;

import com.contrarywind.adapter.WheelAdapter;

import java.util.List;

/**
 * ================================================
 * 作    者：谢广胜
 * 版    本：1.0
 * 创建日期：2020/4/15 10:21
 * 描    述：
 * 修订历史：
 * ================================================
 */
class ArrayWheelAdapter implements WheelAdapter {
    List<String> mOptionsItems;

    public ArrayWheelAdapter(List<String> mOptionsItems) {
        this.mOptionsItems = mOptionsItems;
    }

    @Override
    public int getItemsCount() {
        return mOptionsItems.size();
    }

    @Override
    public Object getItem(int index) {
        return mOptionsItems.get(index);
    }

    @Override
    public int indexOf(Object o) {
        return mOptionsItems.indexOf(o);
    }
}
