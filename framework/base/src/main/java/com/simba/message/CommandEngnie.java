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
import com.simba.message.bean.MemeberMsgData;
import com.simba.message.bean.SocketStateData;
import com.simba.service.callbacks.IServiceDataCallback;
import com.simba.service.callbacks.OnInitListener;
import com.simba.service.data.DataWrapper;

/**
 * @author chefengyun
 */
public class CommandEngnie {
    private final String TAG = "CommandEngnie";
    private Context mContext;
    private Handler mHandler;

    static final String SERVICE_NAME = "simba.command";
    static final String ACTION = "action.simba.service.command";
    static final String PKG = "com.simba.message";
    static final String CLZ = "com.simba.message.CommandService";

    private static CommandEngnie mHolder = null;
    private CommandEngnie(Context ctx) {
        mContext = ctx.getApplicationContext();

        initBinderIfNeed(false);
    }

    public static CommandEngnie getInstance(Context ctx) {
        if (mHolder == null) {
            synchronized (CommandEngnie.class) {
                if (mHolder == null) {
                    mHolder = new CommandEngnie(ctx);
                }
            }
        }
        return mHolder;
    }

    private OnInitListener mListener = null;

    public void setOnInitListener(OnInitListener l) {
        mListener = l;
    }

    private ICommand mService;
    private boolean mIntentBinder;
    private ServiceConnection mServiceConn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            initBinder(service, true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "cmd disconnect.");

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
            Log.i(TAG, "cmd binder died.");

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

        mService = ICommand.Stub.asInterface(service);

        Log.i(TAG, "cmd bind ok");
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
     * TBoxService 是否bind成功
     *
     * @return 成功/失败
     */
    public boolean isBinder() {
        return isBinder(false);
    }

    /**
     * TBoxService 是否bind成功
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

    public void registerCallback(IServiceDataCallback cb) {
        if (isBinder()) {
            try {
                mService.registerCallback(cb);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void unregisterCallback(IServiceDataCallback cb) {
        if (isBinder()) {
            try {
                mService.unregisterCallback(cb);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取Socket连接状态
     * @return
     */
    public boolean getSocketState() {
        if (isBinder(true)) {
            try {
                DataWrapper dataWrapper = mService.getData(SocketStateData.CODE);
                if (dataWrapper != null) {
                    return ((SocketStateData) dataWrapper.getData()).isConnect();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取会员中心消息
     * @return
     */
    public MemeberMsgData getMemberMsg() {
        if (isBinder(true)) {
            try {
                DataWrapper dataWrapper = mService.getData(MemeberMsgData.CODE);
                if (dataWrapper != null) {
                    return (MemeberMsgData) dataWrapper.getData();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
