package com.simba.message.impl;

import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;

import com.simba.message.IMessage;
import com.simba.message.IMessageCallback;
import com.simba.message.manager.ProtocolFactory;

/**
 * @author chefengyun
 */
public class MessageImpl extends IMessage.Stub {

    private static final String TAG = "Msg";

    public MessageImpl() {
        super();

        onSocketStateChange(mSocketConnect);
    }

    private boolean mSocketConnect = false;

    public void setSocketConnect(boolean connect) {
        if (mSocketConnect != connect) {
            mSocketConnect = connect;

            onSocketStateChange(mSocketConnect);
        }
    }

    public void parse(byte[] cmd) {

        int cmdType = ProtocolFactory.creat().getCmdType(cmd);
        int cmdLength = ProtocolFactory.creat().getCmdLength(cmd);
        int cmdStart = ProtocolFactory.creat().getCmdStart();
        String jsonData = new String(cmd, cmdStart, cmdLength);

        onDataCallback(cmdType, jsonData);
    }


    public synchronized void onSocketStateChange(boolean state) {
        final int length = mCallbacks.beginBroadcast();
        for (int i = 0; i < length; i++) {
            try {
                IMessageCallback cb = mCallbacks.getBroadcastItem(i);
                cb.onSocketStateChange(state);
            } catch (Exception e) {
            }
        }
        mCallbacks.finishBroadcast();
    }

    public synchronized void onDataCallback(int msgType, String jsonData) {
        final int length = mCallbacks.beginBroadcast();
        for (int i = 0; i < length; i++) {
            try {
                IMessageCallback cb = mCallbacks.getBroadcastItem(i);
                cb.onChange(msgType, jsonData);
            } catch (Exception e) {
            }
        }
        mCallbacks.finishBroadcast();
    }

    private class PidDeathRecipient implements IBinder.DeathRecipient {

        private int pid;
        private IMessageCallback callback;

        public PidDeathRecipient(int pid, IMessageCallback callback) {
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
    private final RemoteCallbackList<IMessageCallback> mCallbacks = new RemoteCallbackList<IMessageCallback>();

    private final SparseArray<DeathRecipient> mDeathRecipients = new SparseArray<>();

    @Override
    public void registerCallback(IMessageCallback callback) {
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
    public void unregisterCallback(IMessageCallback callback) {
        int pid = Binder.getCallingPid();
        callback.asBinder().unlinkToDeath(mDeathRecipients.get(pid), 0);
        mCallbacks.unregister(callback);
        mDeathRecipients.remove(pid);
        Log.i(TAG, "Prcess[" + pid + "] unregister ok.");
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
