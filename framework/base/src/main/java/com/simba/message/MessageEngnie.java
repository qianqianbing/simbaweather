package com.simba.message;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.simba.base.os.ServiceManager;
import com.simba.service.callbacks.OnInitListener;

/**
 * @author chefengyun
 */
class MessageEngnie {

    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private Handler mHandler;

    static final String SERVICE_NAME = "simba.message";
    static final String ACTION = "action.simba.service.message";
    static final String PKG = "com.simba.message";
    static final String CLZ = "com.simba.message.MessageService";

    private static MessageEngnie mHolder = null;
    private MessageEngnie(Context ctx) {
        mContext = ctx.getApplicationContext();

        initBinderIfNeed(false);
    }

    public static MessageEngnie getInstance(Context ctx) {
        if (mHolder == null) {
            synchronized (MessageEngnie.class) {
                if (mHolder == null) {
                    mHolder = new MessageEngnie(ctx);
                }
            }
        }
        return mHolder;
    }

    private OnInitListener mListener = null;

    public void setOnInitListener(OnInitListener l) {
        mListener = l;
    }

    private IMessage mService;
    private boolean mIntentBinder;
    private ServiceConnection mServiceConn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            initBinder(service, true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "msg disconnect.");

            if (mListener != null) {
                mListener.onServiceDisconnected();
            }
        }
    };

    private Runnable mBinderCheckRunnable = new Runnable() {
        @Override
        public void run() {
            initBinderIfNeed(true);
        }
    };

    private IBinder.DeathRecipient mDeathRecipient = new IBinder.DeathRecipient() {

        @Override
        public void binderDied() {
            Log.i(TAG, "msg binder died.");

            if (mService != null){
                mService.asBinder().unlinkToDeath(this, 0);
                mService = null;
            }

            // rebind
            rebindService(2000);
        }
    };

    private void initBinder(IBinder service, boolean intentBinder) {
        mIntentBinder = intentBinder;

        try {
            service.linkToDeath(mDeathRecipient, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        mService = IMessage.Stub.asInterface(service);

        Log.i(TAG, "msg bind ok");
        if (mListener != null) {
            mListener.onServiceConnected();
        }
    }

    private void initBinderIfNeed(boolean bind){
        IBinder service = ServiceManager.getService(SERVICE_NAME);
        if (service != null) {
            initBinder(service, false);
        } else {
            if(bind){
                bindService();
            }else{
                Log.w(TAG, "IBinder service is null.");
            }
        }
    }

    public void bindService() {
        if (isBinder()) {
            if (mListener != null) {
                mListener.onServiceConnected();
            }
        } else {
            boolean r = mContext.bindService(new Intent(ACTION).setClassName(PKG, CLZ), mServiceConn, Context.BIND_AUTO_CREATE);
            if(!r){
                Log.e(TAG, "bindService failed and rebind after 5s.");
                rebindService(5000);
            }
        }
    }

    private void rebindService(long delayMillis){
        if(mHandler == null){
            mHandler = new Handler(Looper.getMainLooper());
        }
        mHandler.removeCallbacks(mBinderCheckRunnable);
        mHandler.postDelayed(mBinderCheckRunnable, delayMillis);
    }

    public void unbindService() {
        mListener = null;

        if(mService != null){
            mService.asBinder().unlinkToDeath(mDeathRecipient, 0);
            if(mIntentBinder) mContext.unbindService(mServiceConn);
            mService = null;
        }
    }

    /**
     * Service 是否bind成功
     *
     * @return 成功/失败
     */
    public boolean isBinder() {
        return isBinder(false);
    }

    /**
     * Service 是否bind成功
     *
     * @param bind 非binder状态，传入TRUE 则要 bindService，反之亦然
     * @return 成功/失败
     */
    public boolean isBinder(boolean bind) {
        if (mService == null && bind) {
            bindService();
        }
        return mService != null;
    }

    public void registerCallback(IMessageCallback cb) {
        if (isBinder()) {
            try {
                mService.registerCallback(cb);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void unregisterCallback(IMessageCallback cb) {
        if (isBinder()) {
            try {
                mService.unregisterCallback(cb);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

}
