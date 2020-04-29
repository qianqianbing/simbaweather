package com.simba.message.protocol.cloud;

import android.util.Log;

import com.simba.message.util.DataUtils;

import java.util.ArrayList;

/**
 * @author chefengyun
 */
public class SimbaCloudUtils {

    private static final String TAG = "HikvisionUtils";

    public static int getCmdType(byte[] cmdBytes){
        return DataUtils.getUnsignedByte(cmdBytes[3]) * 256 + DataUtils.getUnsignedByte(cmdBytes[4]);
    }

    public static int getCmdLength(byte[] cmdBytes){
        return DataUtils.getUnsignedByte(cmdBytes[5]) * 256 + DataUtils.getUnsignedByte(cmdBytes[6]);
    }

    public static int getCmdStart(){
        return 7;
    }

    /**
     * 检查指令长度是否符合
     *
     * @param cmdBytes
     * @return
     */
    private static byte[] checkCmdLen(byte[] cmdBytes) {
        if (cmdBytes.length < SimbaCloudCmd.MIN_LEN) {
            Log.w(TAG, "Illegal cmd length of less than " + SimbaCloudCmd.MIN_LEN);
            return null;
        }

        int checkLen = SimbaCloudCmd.MIN_LEN + getCmdLength(cmdBytes);
        if (checkLen == cmdBytes.length) {
            // TODO bcc check
            return cmdBytes;
        } else {
            Log.w(TAG, "Illegal cmd length, need " + checkLen + ", but found " + cmdBytes.length);
            return null;
        }
    }

    /**
     * 检查指令是否合法
     *
     * @param rCmd
     * @return
     */
    public static ArrayList<byte[]> checkCmd(byte[] rCmd) {
        ArrayList<byte[]> ret = new ArrayList<>();
        if (rCmd[0] == SimbaCloudCmd.HEADER && rCmd[1] == SimbaCloudCmd.HEADEREX) {
            int index = 0;
            for (int i = SimbaCloudCmd.MIN_LEN; i < rCmd.length; ) {
                if (i < rCmd.length - 1 && rCmd[i] == SimbaCloudCmd.HEADER && rCmd[i + 1] == SimbaCloudCmd.HEADEREX) {
                    // 中途发现第二条 cmd, 添加之前的 cmd
                    final byte[] cmd = new byte[i - index];
                    System.arraycopy(rCmd, index, cmd, 0, i - index);
                    SimbaCloudUtils.checkCmd(ret, cmd);
                    index = i;
                }

                i++;
                // 多条 cmd 的最后一条也要添加
                if (index > 0 && i == rCmd.length) {
                    final byte[] cmd = new byte[i - index];
                    System.arraycopy(rCmd, index, cmd, 0, i - index);
                    SimbaCloudUtils.checkCmd(ret, cmd);
                }
            }

            // 只有一条 cmd
            if (index == 0) {
                SimbaCloudUtils.checkCmd(ret, rCmd);
            }
        } else {
            Log.w(TAG, "Illegal cmd header.");
        }

        return ret;
    }

    public static void checkCmd(ArrayList<byte[]> ret, byte[] rCmd) {
        if (rCmd[2] == SimbaCloudCmd.UpDownLink.Cloud2App) {
            byte[] check = checkCmdLen(rCmd);
            if (check != null && check.length > 0) {
                ret.add(check);
            }
        } else {
            Log.w(TAG, "Illegal cmd type of not Cloud2App.");
        }
    }

}
