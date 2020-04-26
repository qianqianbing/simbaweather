package com.simba.message.util;

import android.text.TextUtils;

import com.simba.message.protocol.MessageCmd;

import java.io.UnsupportedEncodingException;

/**
 * @author chefengyun
 */
public class DataUtils {

    private static final String TAG = "DataUtils";

    /**
     * 拼接指令长度的16进制ASCII码字符串
     * example：
     * MessageCmd.Num.TEN -> 10 -> 000A
     *
     * @param dataLen
     * @return
     */
    public static String getDataLengthStr(MessageCmd.Num dataLen) {
        return MessageCmd.Num.ZERO.toString() + dataLen.toString();
    }

    /**
     * 获取指令的第 index 个 DATA字符串
     * example:
     * 0D054640000116570D0A -> 16
     *
     * @param cmdStr
     * @param index
     * @return
     */
    public static String dataOf(String cmdStr, int index) {
        return dataOf(cmdStr, index, 1);
    }

    /**
     * 获取指令的第 index 个 DATA 的值
     * example:
     * 464C432E0000160014005D4BC37900000000000522010002050000020000011FF4 -> 011F -> 22
     *
     * @param cmdStr
     * @param index
     * @return
     */
    public static int dataValueOf(String cmdStr, int index) {
        return DataUtils.parseInt(dataOf(cmdStr, index, 1));
    }

    /**
     * 获取指令的第 index 个的 count 个DATA字符串
     * example:
     * 0D054640000116570D0A -> 16
     *
     * @param cmdStr
     * @param index
     * @param count
     * @return
     */
    public static String dataOf(String cmdStr, int index, int count) {
        if (index < 0) {
            throw new IllegalArgumentException("index must be [0, dataLen-1]");
        }
        if (count < 1) {
            throw new IllegalArgumentException("count must be [1, dataLen]");
        }

        return cmdStr.substring(index, index + count * 2);
    }

    /**
     * 获取指令的第 index 个的 count个 DATA 的值
     * example:
     * 0D054640000116570D0A -> 16 -> 22
     *
     * @param cmdStr
     * @param index
     * @return
     */
    public static int dataValueOf(String cmdStr, int index, int count) {
        return DataUtils.parseInt(dataOf(cmdStr, index, count));
    }

    /**
     * 异或校验
     *
     * @param value
     * @param datas
     * @return
     */
    public static byte bcc(byte value, byte[]... datas) {
        for (byte[] byteArr : datas) {
            for (byte b : byteArr) {
                value ^= b;
            }
        }
        return value;
    }

    /**
     * 异或校验
     *
     * @param value
     * @param bytes
     * @return
     */
    public static byte bcc(byte value, byte... bytes) {
        for (byte b : bytes) {
            value ^= b;
        }
        return value;
    }

    /**
     * 异或校验
     *
     * @param hexStrs
     * @return
     */
    public static String bcc(String... hexStrs) {
        byte ret = 0;
        for (String hexStr : hexStrs) {
            int len = hexStr.length() / 2;
            for (int i = 0; i < len; i++) {
                int pos = i * 2;
                ret ^= (hexCharToByte(hexStr.charAt(pos)) * 16 + hexCharToByte(hexStr.charAt(pos + 1)));
            }
        }
        return byte2HexString(ret);
    }

    public static String byte2HexString(byte b) {
        return int2HexString(b & 0xFF);
    }

    public static String int2HexString(int i) {
        String hex = Integer.toHexString(i);
        if (hex.length() == 1) {
            hex = "0" + hex;
        }
        return hex.toUpperCase();
    }

    /**
     * 字符串 转换为 对应的 16进制字符串
     *
     * @param str
     * @return
     */
    public static String str2ASCIIStr(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("str is empty");
        }

        final byte[] strs = str.getBytes();
        final StringBuffer hexStr = new StringBuffer();
        for (byte b : strs) {
            hexStr.append(byte2HexString(b));
        }

        return hexStr.toString();
    }

    /**
     * int 转换为4个 16 进制的字符串
     * 31976026 -> {0x01, 0xe7, 0xea, 0x5a} -> 0x01E7EA5A -> 01E7EA5A
     *
     * @param data
     * @return
     */
    public static String int2ASCIIStr(int data) {
        return int2ASCIIStr(data, 4);
    }

    /**
     * int 转换为规定长度的 16 进制字符串
     *
     * @param data
     * @param count
     * @return
     */
    public static String int2ASCIIStr(int data, int count) {
        if (count < 1 || count > 4) {
            throw new IllegalArgumentException("count must be (1,4)");
        }

        final byte[] byteArr = int2ByteArray(data);
        final StringBuffer hexStr = new StringBuffer();
        for (int i = 0; i < byteArr.length; i++) {
            hexStr.append(byte2HexString(byteArr[i]));
        }

        return hexStr.toString().substring((4 - count) * 2);
    }

    /**
     * Convert char to byte
     *
     * @param c
     * @return byte
     */
    private static int hexCharToByte(char c) {
        String hex16 = "0123456789ABCDEF";
        int index = hex16.indexOf(c);
        if (index == -1) {
            throw new IllegalArgumentException(c + "is not in '" + hex16 + "'");
        }
        return index;
    }

    /**
     * 16进制字符按协议转换为int
     * 其中协议规定 int32(byte8 * 4) 排序如下
     * "byte3""byte2""byte1""byte0"
     * 故举例如下
     * 01020304 -> 0x01 * 256 * 256 * 256 + 0x02 * 256 * 256 + 0x03 * 256 + 0x04 -> 16909060
     *
     * @param hexStr
     * @return
     */
    public static int parseInt(String hexStr) {
        if (TextUtils.isEmpty(hexStr)) {
            throw new IllegalArgumentException("hexStr is empty");
        }

        int ret = 0;
        int count = 1;
        for (int i = hexStr.length() - 1; i >= 0; i--) {
            ret += hexCharToByte(hexStr.charAt(i)) * count;
            count *= 16;
        }

        return ret;
    }

    /**
     * 16进制字符按协议转换为 long
     * 算法同 parseInt(hexStr)
     *
     * @param hexStr
     * @return
     */
    public static long parseLong(String hexStr) {
        if (TextUtils.isEmpty(hexStr)) {
            throw new IllegalArgumentException("hexStr is empty");
        }

        long ret = 0;
        int count = 1;
        for (int i = hexStr.length() - 1; i >= 0; i--) {
            ret += hexCharToByte(hexStr.charAt(i)) * count;
            count *= 16;
        }

        return ret;
    }

    /**
     * 16进制字符的字节 转换为 ASCII 码字节
     * examle:
     * 31 -> '1'
     *
     * @param hexStr
     * @return
     */
    public static char parseChar(String hexStr) {
        if (TextUtils.isEmpty(hexStr)) {
            throw new IllegalArgumentException("hexStr is empty");
        }
        if (hexStr.length() != 2) {
            throw new IllegalArgumentException("hexStr length must be 2");
        }

        return (char) parseInt(hexStr);
    }

    /**
     * 16进制字符的字符串转换为 ASCII 码字符串
     * examle:
     * 3139322E3136382E313030313233 -> "192.168.100.123"
     *
     * @param hexStr
     * @return
     */
    public static String parseString(String hexStr) {
        if (TextUtils.isEmpty(hexStr)) {
            throw new IllegalArgumentException("hexStr is empty");
        }

        final StringBuffer ret = new StringBuffer();
        for (int i = 0; i < hexStr.length() / 2; i++) {
            final String hex = hexStr.substring(i * 2, (i + 1) * 2);
            if ("00".equals(hex)) {
                continue;
            }
            ret.append(parseChar(hex));
        }

        return ret.toString();
    }

    /**
     * 16进制字符的字符串转换为 UTF8 字符串
     * examle:
     * E6B58BE8AF95 -> "测试"
     *
     * @param hexStr
     * @return
     */
    public static String parseUTF8String(String hexStr) {
        if (TextUtils.isEmpty(hexStr)) {
            throw new IllegalArgumentException("hexStr is empty");
        }

        int count = 0;
        int size = hexStr.length() / 2;
        final byte[] data = new byte[size];
        for (int i = 0; i < size; i++) {
            final String hex = hexStr.substring(i * 2, (i + 1) * 2);
            if ("00".equals(hex)) {
                continue;
            }
            data[count++] = (byte) parseInt(hex);
        }

        String ret = "";
        try {
            ret = new String(data, 0, count, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return ret;
    }

    /**
     * 获取无符号 byte
     *
     * @param b
     * @return
     */
    public static int getUnsignedByte(byte b) {
        return b & 0xff;
    }

    /**
     * {0x00, 0x00, 0x01, 0x2c} -> 0x0000012c -> 300
     * 与 int2ByteArray 配套使用
     *
     * @param b
     * @return
     */
    public static int byteArray2Int(byte[] b) {
        return (b[3] & 0xFF) | (b[2] & 0xFF) << 8 | (b[1] & 0xFF) << 16 | (b[0] & 0xFF) << 24;
    }

    /**
     * 300 -> 0x0000012c -> {0x00, 0x00, 0x01, 0x2c}
     * 与 byteArrayToInt 配套使用
     *
     * @param a
     * @return
     */
    public static byte[] int2ByteArray(int a) {
        return new byte[]{(byte) ((a >> 24) & 0xFF), (byte) ((a >> 16) & 0xFF), (byte) ((a >> 8) & 0xFF), (byte) (a & 0xFF)};
    }

    /**
     * 获取byte数组的 16进制字符串
     *
     * @param buffer
     * @return
     */
    public static String bytes2HexString(byte[] buffer) {
        return bytes2HexString(buffer, false);
    }

    /**
     * 获取byte数组的 16进制字符串
     *
     * @param buffer
     * @return
     */
    public static String bytes2HexString(byte[] buffer, boolean showBinary) {
        if (buffer == null) {
            return " null";
        }
        if (buffer.length == 0) {
            return " none";
        }

        final StringBuffer ret = new StringBuffer("{");
        for (byte b : buffer) {
            String hex = byte2HexString(b);
            hex += showBinary ? "(" + Integer.toBinaryString((b & 0xFF) + 0x100).substring(1) + ") " : " ";
            ret.append(hex);
        }
        ret.setLength(ret.length() - 1);
        ret.append("}");
        return ret.toString();
    }

}
