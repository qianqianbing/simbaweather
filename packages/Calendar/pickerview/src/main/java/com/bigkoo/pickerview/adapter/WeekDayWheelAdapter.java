package com.bigkoo.pickerview.adapter;


import com.contrarywind.adapter.WheelAdapter;

import java.util.Calendar;

/**
 * Numeric Wheel adapter.
 */
public class WeekDayWheelAdapter implements WheelAdapter {

    private final String[] weeks = new String[]{"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
    private int minValue;
    private int maxValue;
    private int year, month;
    private Calendar calendar;

    /**
     * Constructor
     *
     * @param minValue the wheel min value
     * @param maxValue the wheel max value
     */
    public WeekDayWheelAdapter(int year, int month, int minValue, int maxValue) {
        this.year = year;
        this.month = month - 1;
        this.minValue = minValue;
        this.maxValue = maxValue;
        calendar = Calendar.getInstance();
    }

    @Override
    public Object getItem(int index) {
        if (index >= 0 && index < getItemsCount()) {
            int value = minValue + index;
            calendar.set(year, month, value);
            return value + weeks[calendar.get(Calendar.DAY_OF_WEEK) - 1];
        }
        return 0;
    }

    @Override
    public int getItemsCount() {
        return maxValue - minValue + 1;
    }

    @Override
    public int indexOf(Object o) {
        try {
            return (int) o - minValue;
        } catch (Exception e) {
            return -1;
        }

    }
}
