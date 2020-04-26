package com.simba.service.data;

import android.util.Log;
import android.util.SparseArray;

import com.simba.message.bean.CloudStateData;
import com.simba.message.bean.MemeberMsgData;
import com.simba.message.bean.MemeberInfoData;

public class DataTypeHelper {
    private static NoRepeatSparseArray sClassList = new NoRepeatSparseArray();

    static {
        // Message
        sClassList.put(CloudStateData.CODE, CloudStateData.class);
        sClassList.put(MemeberMsgData.CODE, MemeberMsgData.class);
        sClassList.put(MemeberInfoData.CODE, MemeberInfoData.class);
    }

    public static Class getDataClass(int dataCode) {
        return sClassList.get(dataCode);
    }

    public static class NoRepeatSparseArray extends SparseArray<Class> {
        @Override
        public void put(int key, Class value) {
            if (this.get(key) != null) {
                Log.e("DataWrapper", "Cannot add (" + key + ", " + value + ")");

                // 不同模块公用一个DataWrapper, key 有可能重复定义，则会覆盖class
                // 无法主动抛异常，这里写一个NullPointerException 以提醒
                String s = null;int e = s.length();
            }
            super.put(key, value);
        }
    }

}
