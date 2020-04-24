package com.simba.base.message;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.util.Log;
import android.util.SparseArray;

import com.simba.base.message.bean.CloudStateData;
import com.simba.base.message.protocol.MessageCmd;
import com.simba.base.service.callbacks.IServiceDataCallback;
import com.simba.base.service.data.DataWrapper;
import com.simba.base.message.protocol.BaseParser;
import com.simba.base.message.manager.ProtocolFactory;

/**
 * @author chefengyun
 */
public class MessageImpl extends IMessage.Stub {

    private static final String TAG = "TBox";

    private Context mContext;
    private Handler mHandler;

    public MessageImpl(Context ctx, Handler handler) {
        super();
        mContext = ctx;
        mHandler = handler;

        ProtocolFactory.creat().initialParsers(mParseCallback);
        onCloudStateDataCallback();
    }

    private void onCloudStateDataCallback(){
        DataWrapper dataWrapper = new DataWrapper(new CloudStateData(mSocketConnect), CloudStateData.CODE);
        Log.i(TAG, "handleCallback " + dataWrapper);
        mParseCallback.cache(dataWrapper);

        onDataCallback(dataWrapper);
    }


    private boolean mSocketConnect = false;
    public void setSocketConnect(boolean connect) {
        if (mSocketConnect != connect) {
            mSocketConnect = connect;
            if (!connect) {
                mDataArray.clear();
            }
            onCloudStateDataCallback();
        }
    }

    private BaseParser.ParseCallback mParseCallback = new BaseParser.ParseCallback() {
        @Override
        public void handleCallback(DataWrapper dataWrapper) {
            Log.i(TAG, "handleCallback " + dataWrapper);
            onDataCallback(dataWrapper);
        }

        @Override
        public void cache(DataWrapper dataWrapper) {
            mDataArray.put(dataWrapper.getDataCode(), dataWrapper);
        }

        @Override
        public void statusAck(int msgType) {
            Message msg = mHandler.obtainMessage(MessageCmd.MSG_STATUS_ACK);
            msg.arg1 = msgType;
            mHandler.sendMessage(msg);
        }

    };

    public synchronized void onDataCallback(DataWrapper dataWrapper) {
        final int length = mCallbacks.beginBroadcast();
        for (int i = 0; i < length; i++) {
            try {
                IServiceDataCallback cb = mCallbacks.getBroadcastItem(i);
                if (cb.accept(dataWrapper.getDataCode())) {
//                    Log.i(TAG, "onChange " + dataWrapper);
                    cb.onChange(dataWrapper);
                }
            } catch (Exception e) {
            }
        }
        mCallbacks.finishBroadcast();
    }

    /**
     * Client IServiceDataCallback RemoteCallbackList
     */
    private final RemoteCallbackList<IServiceDataCallback> mCallbacks = new RemoteCallbackList<IServiceDataCallback>();

    @Override
    public void registerCallback(IServiceDataCallback iServiceDataCallback) {
        mCallbacks.register(iServiceDataCallback);
    }

    @Override
    public void unregisterCallback(IServiceDataCallback iServiceDataCallback) {
        mCallbacks.unregister(iServiceDataCallback);
    }

    protected SparseArray<DataWrapper> mDataArray = new SparseArray<DataWrapper>();

    @Override
    public DataWrapper getData(int dataCode) {
        DataWrapper dataWrapper = mDataArray.get(dataCode);
        Log.i(TAG, "getData(" + dataCode + ")=" + dataWrapper);
        return dataWrapper;
    }

    @Override
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags) {
        try {
            return super.onTransact(code, data, reply, flags);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return false;
    }

}
