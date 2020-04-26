package com.simba.message.protocol;

import android.content.Context;

import com.simba.service.data.DataWrapper;

/**
 * @author chefengyun
 */
public abstract class BaseParser {

    protected final String TAG = this.getClass().getSimpleName();

    protected ParseCallback mCallBack = null;

    protected Context context;

    public BaseParser(Context context) {
        this.context = context;
    }

    public void registerCallback(ParseCallback callback) {
        mCallBack = callback;
    }

    public interface ParseCallback {
        void handleCallback(DataWrapper dataWrapper);

        void cache(DataWrapper dataWrapper);

        void statusAck(int msgType);
    }

    public abstract void parse(byte[] cmdBytes);

    public abstract boolean check(byte[] cmdBytes);

}
