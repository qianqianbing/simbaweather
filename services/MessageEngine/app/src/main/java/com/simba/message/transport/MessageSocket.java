package com.simba.message.transport;

import android.os.SystemClock;
import android.util.Log;

import com.simba.message.manager.ProtocolFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * @author chefengyun
 */
public class MessageSocket implements IMessageTransport {

    private final String TAG = this.getClass().getSimpleName();

    private String SERVER_IP = ProtocolFactory.creat().getServerIP();
    private final int SERVER_PORT = ProtocolFactory.creat().getServerPort();
    private final int TIMEOUT = ProtocolFactory.creat().getServerTimeout();

    //private java.io.BufferedReader mIn;
    private InputStream mIn;
    private OutputStream mOut;
    private Socket mSocket;
    private boolean mConnected = false;

    private final byte buf[] = new byte[1024];

    public MessageSocket() {
    }

    @Override
    public String toString() {
        return "MessageSocket{" + "IP='" + SERVER_IP + '\'' + ", PORT=" + SERVER_PORT + '}';
    }

    public synchronized int transact(byte[] cmdBytes) {
        if (!connect()) {
            return -1;
        }

        if (!writeCmd(cmdBytes)) {
            // If tbox died and restarted in the background (unlikely but
            // possible) we'll fail on the next write (this one). Try to
            // reconnect and write the command one more time before giving up.
            Log.w(TAG, "MessageSocket write command failed? reconnect!");
            if (!connect() || !writeCmd(cmdBytes)) {
                return -1;
            }
        }

        return 0;

        /*
        final String s = readCmd();
        if (s != null) {
            return s.length();
        } else {
            Log.w(TAG, "MessageSocket recv fail");
            return -1;
        }
        */
    }

    @Override
    public boolean isConnected() {
        return mConnected;
    }

    @Override
    public boolean connect() {
        if (mSocket != null) {
            return true;
        }

        try {
            mSocket = new Socket();
            final SocketAddress address = new InetSocketAddress(SERVER_IP, SERVER_PORT);
            mSocket.connect(address, TIMEOUT);

            //final InputStream in = mSocket.getInputStream();
            //mIn = new java.io.BufferedReader(new java.io.InputStreamReader(in, CHARSET));
            mIn = mSocket.getInputStream();
            mOut = mSocket.getOutputStream();
        } catch (Exception e) {
            Log.e(TAG, e.getClass().getName() + ": " + e.getLocalizedMessage());
            disconnect();
            return false;
        }
        return true;
    }

    @Override
    public void disconnect() {
        mConnected = false;

        try {
            if (mIn != null) {
                mIn.close();
                mIn = null;
            }
            if (mOut != null) {
                mOut.close();
                mOut = null;
            }
            if (mSocket != null) {
                mSocket.close();
                mSocket = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 从 TBox 读取数据
     *
     * @return
     */
    @Override
    public byte[] readCmd() {
        byte[] cmd = null;
        try {
            //s = mIn.readLine();
            int read = mIn.read(buf);
            if (read > 0) {
                cmd = new byte[read];
                System.arraycopy(buf, 0, cmd, 0, read);
                ProtocolFactory.creat().log(cmd, true);
            }
        } catch (IOException e) {
            e.printStackTrace();
            disconnect();
        }

        // null 则服务器挂了
        if (cmd == null) {
            //disconnect();
            Log.i(TAG, "read none cmd, maybe disconnect.?");
        }
        return cmd;
    }

    /**
     * 回复数据到 TBox
     *
     * @param cmdBytes
     * @return
     */
    @Override
    public boolean writeCmd(byte[] cmdBytes) {
        try {
            if (cmdBytes == null) {
                throw new IllegalArgumentException("cmd cannot be null");
            }

            if (cmdBytes.length == 0) {
                throw new IllegalArgumentException("cmd cannot be none");
            }
            ProtocolFactory.creat().log(cmdBytes, false);

            mOut.write(cmdBytes, 0, cmdBytes.length);
//            mOut.write('\n');
            mOut.flush();
        } catch (IOException e) {
            e.printStackTrace();
            disconnect();
            return false;
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void waitForConnection() {
        for (; ; ) {
            if (transact(ProtocolFactory.creat().getDefaultCmd()) >= 0) {
                mConnected = true;
                Log.w(TAG, this + " connect success.");
                break;
            }
            SystemClock.sleep(TIMEOUT);
        }
        return;
    }

}
