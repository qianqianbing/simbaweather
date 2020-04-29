package com.simba.message.impl;

import android.content.Context;
import android.os.Binder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;

import com.simba.message.ICommand;
import com.simba.message.manager.ProtocolFactory;
import com.simba.message.protocol.BaseParser;
import com.simba.service.callbacks.IServiceDataCallback;
import com.simba.service.data.DataWrapper;

/**
 * @author chefengyun
 */
public class CommandImpl extends ICommand.Stub {

    private static final String TAG = "Msg";

    private Context mContext;

    public CommandImpl(Context ctx) {
        super();
        mContext = ctx;

        ProtocolFactory.creat().initialParsers(mParseCallback);
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

    private class PidDeathRecipient implements DeathRecipient {

        private int pid;
        private IServiceDataCallback callback;

        public PidDeathRecipient(int pid, IServiceDataCallback callback) {
            this.pid = pid;
            this.callback = callback;
        }

        @Override
        public void binderDied() {
            Log.i(TAG, "Prcess[" + pid + "] died, rm its callback.");

            callback.asBinder().unlinkToDeath(this, 0);
            mCallbacks.unregister(callback);
            mDeathRecipients.remove(pid);
            callback = null;
        }
    };

    /**
     * Client IServiceDataCallback RemoteCallbackList
     */
    private final RemoteCallbackList<IServiceDataCallback> mCallbacks = new RemoteCallbackList<IServiceDataCallback>();

    private final SparseArray<DeathRecipient> mDeathRecipients = new SparseArray<>();

    @Override
    public void registerCallback(IServiceDataCallback callback) {
        int pid = Binder.getCallingPid();
        mDeathRecipients.put(pid, new PidDeathRecipient(pid, callback));
        try {
            callback.asBinder().linkToDeath(mDeathRecipients.get(pid), 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        mCallbacks.register(callback);
        Log.i(TAG, "Prcess[" + pid + "] register ok.");
    }

    @Override
    public void unregisterCallback(IServiceDataCallback callback) {
        int pid = Binder.getCallingPid();
        callback.asBinder().unlinkToDeath(mDeathRecipients.get(pid), 0);
        mCallbacks.unregister(callback);
        mDeathRecipients.remove(pid);
        Log.i(TAG, "Prcess[" + pid + "] unregister ok.");
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
