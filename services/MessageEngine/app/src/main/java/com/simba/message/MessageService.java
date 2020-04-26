package com.simba.message;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.simba.message.manager.ParserManager;
import com.simba.message.transport.MessageSocket;
import com.simba.message.util.DataUtils;
import com.simba.base.os.ServiceManager;
import com.simba.message.manager.ProtocolFactory;

import java.util.ArrayList;

/**
 * @author chefengyun
 */
public class MessageService extends Service {

    private final String TAG = "MessageService";

    private Context mContext;
    private MessageImpl mTBox;
    private MessageSocket mSocket;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.d(TAG, "onCreate().");
        mContext = this.getApplicationContext();

        ProtocolFactory.creat().init(mContext);

        mTBox = new MessageImpl(this, mHandler);
        ServiceManager.addService(MessageManager.SERVICE_NAME, mTBox.asBinder());

        mSocket = new MessageSocket();

        new Thread(new DispatchThread()).start();
        new Thread(new SendThread()).start();
        new Thread(new ConnectThread()).start();
    }

    @Override
    public IBinder onBind(Intent arg0) {
        Log.d(TAG, "onBind ok");
        return mTBox.asBinder();
    }

    // 命令处理队列
    private ArrayList<byte[]> mReadList = new ArrayList<byte[]>();
    // 命令发送队列
    private ArrayList<byte[]> mWriteList = new ArrayList<byte[]>();

    private Handler mHandler = new MessageHandler();
    private class MessageHandler extends Handler {

        public static final int MSG_RCMD = 100;

        @Override
        public void handleMessage(Message msg) {
            if (!mSocket.isConnected()) {
                Log.w(TAG, "socket is not connected, ignore msg of" + msg.what);
                return;
            }

            if(msg.what == MSG_RCMD){
                ParserManager.get().parseCmd((byte[]) msg.obj);
            }else{
                byte[] send = ProtocolFactory.creat().getSendCmd(msg);
                if(send != null){
                    synchronized (mWriteList) {
                        mWriteList.add(send);
                    }
                }
            }
        }
    };

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind ok");
        return super.onUnbind(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand startId=" + startId);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

    public class ConnectThread implements Runnable {

        @Override
        public void run() {
            for (; ; ) {
                try {
                    if (!mSocket.isConnected()) {
                        mTBox.setSocketConnect(false);
                        mSocket.waitForConnection();

                        // connect succes to query
                        synchronized (mWriteList) {
                            ProtocolFactory.creat().queryIfNeed(mWriteList);
                        }

                        continue;
                    }

                    mTBox.setSocketConnect(true);
                    final byte[] rCmd = mSocket.readCmd();
                    if (rCmd != null && rCmd.length > 0) {

                        ArrayList<byte[]> cmds = ProtocolFactory.creat().checkCmd(rCmd);
                        if (cmds == null || cmds.size() == 0) {
                            Log.w(TAG, "Illegal cmd: " + DataUtils.bytes2HexString(rCmd));
                            continue;
                        }

                        synchronized (mReadList) {
                            mReadList.addAll(cmds);
                        }
                    }

                    // 100ms 刷新一次
                    SystemClock.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * cmd 读取后的分发线程
     */
    public class DispatchThread implements Runnable {
        @Override
        public void run() {
            try {
                final ArrayList<byte[]> list = new ArrayList<byte[]>();
                for (; ; ) {
                    list.clear();
                    synchronized (mReadList) {
                        list.addAll(mReadList);
                        mReadList.clear();
                    }

                    for (byte[] cmd : list) {
//                        ParserManager.get().parseCmd(cmd);
                        // 抛到消息队列处理
                        mHandler.sendMessage(mHandler.obtainMessage(MessageHandler.MSG_RCMD, cmd));
                    }

                    SystemClock.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private long mAckFlag;

    /**
     * AP cmd 数据发送线程
     */
    public class SendThread implements Runnable {
        @Override
        public void run() {
            try {
                final ArrayList<byte[]> list = new ArrayList<byte[]>();
                for (; ; ) {
                    if (mSocket.isConnected()) {
                        list.clear();
                        synchronized (mWriteList) {
                            list.addAll(mWriteList);
                            mWriteList.clear();
                        }

                        if (list.size() == 0) {
                            // 心跳
                            if ((++mAckFlag) % ProtocolFactory.creat().getAckInterval() == 0) {
                                mSocket.writeCmd(ProtocolFactory.creat().getAckCmd());
                            }
                        } else {
                            // 发送AP cmd
                            for (byte[] cmd : list) {
                                mSocket.writeCmd(cmd);

                                // 防止写入太快，丢包
                                SystemClock.sleep(100);
                            }
                        }
                    }

                    // 100ms 刷新一次
                    SystemClock.sleep(100);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}