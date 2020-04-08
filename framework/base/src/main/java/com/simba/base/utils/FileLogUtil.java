package com.simba.base.utils;

import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * ================================================
 * 作    者：谢广胜
 * 版    本：1.0
 * 创建日期：2020/4/08
 * 描    述：打印日志到文件
 * 修订历史：
 * ================================================
 */
public class FileLogUtil {

    public static String LOG_PATH = null;
    private static boolean BUFF_KEY = true;// 是否启用缓存  true 启用，false 关闭;
    private static boolean LOG_DEBUG = true;// 日志输出开关，true 打开，false 关闭;
    private static final int DAY = 7;//保留最近几天的数据
    private static final int BUFFER_SIZE = 2048;//0:关闭缓存，1000

    private static StringBuilder SB = new StringBuilder();
    private static String tempStr = "";


    private final static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss:SSS", Locale.CHINA);
    private final static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy_MM_dd", Locale.CHINA);

    // 获取格式化时间字符串
    private static String getNowDateTime() {
        return sdf.format(new Date());
    }

    // 获取格式化时间字符串
    private static String getLogName(String fileName) {
        return "log_" + sdf2.format(new Date()) + "_" + fileName + ".txt";
    }


    /**
     * Created by Administrator on 2016/5/5.
     * FolderName 文件夹名称
     * LOG_DEBUG 是否启用文件日志
     * BUFF_KEY 是否启用缓存  使用缓存 程序退出 需要 手动 调用commit（）
     */
    public static void initLog(String folderName, boolean LOG_DEBUG, boolean BUFF_KEY) {
        LOG_PATH = folderName;
        FileLogUtil.LOG_DEBUG = LOG_DEBUG;
        FileLogUtil.BUFF_KEY = BUFF_KEY;
    }


    private static File createFolders(String filePath) {
        File logFile = new File(filePath);
        if (logFile.exists())
            return logFile;
        if (logFile.isFile())
            logFile.delete();
        if (logFile.mkdirs())
            return logFile;
        return Environment.getExternalStorageDirectory();
    }

    private static File getLogFile(String fileName) {

        if (LOG_PATH == null)
            return null;

        File folder = createFolders(LOG_PATH);
        if (folder != null && folder.exists()) {
            return new File(folder, fileName);
        }
        return null;
    }


    //删除过期的日志!
    private static void deleteLogFile() {

        if (LOG_PATH == null)
            return;

        File logDir = new File(LOG_PATH);
        if (logDir.exists()) {
            File[] logs = logDir.listFiles();
            if (logs != null) {
                for (File log : logs) {

                    if (log.getName().startsWith("log_")) {
                        try {
                            String date = log.getName().substring(4, 14);

                            Date now = new Date();
                            Date logTime = sdf2.parse(date);

                            long diff = now.getTime() - logTime.getTime();

                            LogUtil.d("相差" + (diff / (24 * 60 * 60 * 1000)));

                            int theDay = (int) (diff / (24 * 60 * 60 * 1000));

                            if (theDay >= DAY) {
                                log.delete();
                                LogUtil.e("FileLogUtil", " delete Log:" + log.getName());
                                tempStr = tempStr + " \r\ndelete Log:" + log.getName();
                            }
                        } catch (Exception e) {
                            LogUtil.e(e);
                        }
                    }
                }
            }
        }
    }


    public static void info(String tagName, String s, boolean br) {
        LogUtil.e(tagName, s);
        if (LOG_DEBUG) append("INFO - " + tagName, s, br);
    }

    public static void info(String tagName, String s) {
        LogUtil.e(tagName, s);
        if (LOG_DEBUG) append("INFO - " + tagName, s, false);
    }

    public static void error(String tagName, String s) {
        LogUtil.e(tagName, s);
        if (LOG_DEBUG) append("ERROR - " + tagName, s, false);
    }

    public static void error(String tagName, Exception e) {
        String s = Log.getStackTraceString(e);
        LogUtil.e(tagName, s);
        if (LOG_DEBUG)
            append("ERROR - " + tagName, s, false);
    }

    //提交缓存里的数据
    public static void commit() {

        if (SB.length() > 0) {
            write("", SB.toString());
            SB.setLength(0);
        }

    }

    /**
     * @Description: 写入到本地的日志
     * @Author xgs
     * @Date 2016-5-4
     * br 换行标记
     */


    private static void append(String tagName, String s, boolean br) {

        try {
            String cotext = getNowDateTime() + " " + tagName + " - " + s + "\r\n";

            //先换行
            if (br)
                cotext = "\r\n" + cotext;

            if (BUFF_KEY && BUFFER_SIZE != 0) {
                //使用缓存
                try {
                    SB.append(cotext);
                } catch (Exception e) {
                    //sb出错了
                    write("", "TAG:append-start\r\n" + SB.toString() + cotext + "TAG:append-end\r\n");
                    SB.setLength(0);
                }

                if (SB.length() > BUFFER_SIZE) {
                    write("", SB.toString());
                    SB.setLength(0);
                }
            } else {
                //直接输出
                write("", cotext);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void write(String fileName, String str) {
        if (LOG_DEBUG) {
            try {

                File file = getLogFile(getLogName(fileName));

                if (file == null) {
                    LogUtil.e("FileLogUtil", "file==null");
                    return;
                }

                if (!file.exists()) {
                    //创建新文件前， 删除过期的日志!
                    deleteLogFile();
                }

                //添加临时缓存的数据
                if (!TextUtils.isEmpty(tempStr)) {
                    //使用缓存
                    str = getNowDateTime() + " TAG:DELETE_LOG - " + tempStr + "\r\n" + str;
                    tempStr = "";
                }


                FileOutputStream fot = new FileOutputStream(file, true);
                fot.write(str.getBytes("utf-8"));
                fot.flush();
                fot.close();

            } catch (Exception e) {
                e.printStackTrace();
                LogUtil.e("FileLogUtil", e);
            }
        }
    }

}
