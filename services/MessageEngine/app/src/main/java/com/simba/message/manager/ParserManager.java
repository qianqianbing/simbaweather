package com.simba.message.manager;

import android.util.Log;
import android.util.SparseArray;

import com.simba.message.protocol.BaseParser;

/**
 * @author chefengyun
 */
public class ParserManager {
    private static final String TAG = "ParserManager";

    private static ParserManager mInstance;

    private ParserManager() {
    }

    public static ParserManager get() {
        if (mInstance == null) {
            synchronized (ParserManager.class) {
                if (mInstance == null) {
                    mInstance = new ParserManager();
                }
            }
        }
        return mInstance;
    }

    private SparseArray<BaseParser> mParserList = new SparseArray<BaseParser>();

    public void addParser(int cmdType, BaseParser parser) {
        Log.i(TAG, "addParser cmdType=(" + cmdType + ")" + parser.toString());
        mParserList.put(cmdType, parser);
    }

    public void parseCmd(int cmdType, String jsonData) {
        BaseParser parser = mParserList.get(cmdType);
        if (parser != null) {
            parser.parse(cmdType, jsonData);
        } else {
            Log.w(TAG, "no such parse[" + cmdType + "]...");
        }
    }
}