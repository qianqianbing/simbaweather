package com.simba.base.message;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;

import com.simba.base.message.bean.MemeberMsgData;
import com.simba.base.os.ServiceManager;
import com.simba.base.service.callbacks.IServiceDataCallback;
import com.simba.base.service.callbacks.OnInitListener;
import com.simba.base.service.data.DataWrapper;

/**
 * @author chefengyun
 */
public class MessageManager {
    private final String TAG = "MessageManager";
    private Context mContext;
    private Handler mHandler;

    static final String SERVICE_NAME = "simba.message";
    static final String ACTION = "action.simba.service.message";

    private static MessageManager mHolder = null;
    private MessageManager(Context ctx) {
        mContext = ctx.getApplicationContext();

        initBinder(false);
    }

    public static MessageManager getInstance(Context ctx) {
        if (mHolder == null) {
            synchronized (MessageManager.class) {
                if (mHolder == null) {
                    mHolder = new MessageManager(ctx);
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
    private ServiceConnection mServiceConn = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            init(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "msg disconnect.");

            mService = null;
            if (mListener != null) {
                mListener.onServiceDisconnected();
            }
        }
    };

    private void init(IBinder service) {
        mService = IMessage.Stub.asInterface(service);
        Log.i(TAG, "msg bind ok");
        if (mListener != null) {
            mListener.onServiceConnected();
        }
    }

    public void bindService() {
        if (isBinder()) {
            if (mListener != null) {
                mListener.onServiceConnected();
            }
        } else {
            boolean r = mContext.bindService(new Intent(ACTION), mServiceConn, Context.BIND_AUTO_CREATE);
            if(!r){
                if(mHandler == null){
                    mHandler = new Handler(Looper.getMainLooper());
                }
                mHandler.removeCallbacks(mBinderCheckRunnable);
                mHandler.postDelayed(mBinderCheckRunnable, 5000);
            }
        }
    }

    private Runnable mBinderCheckRunnable = new Runnable() {
        @Override
        public void run() {
            initBinder(true);
        }
    };

    private void initBinder(boolean bind){
        IBinder service = ServiceManager.getService(SERVICE_NAME);
        if (service != null) {
            init(service);
        } else {
            if(bind){
                bindService();
            }else{
                Log.w(TAG, "IBinder service is null.");
            }
        }
    }

    @Deprecated
    public void unbindService() {
        mListener = null;
        mService = null;
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
