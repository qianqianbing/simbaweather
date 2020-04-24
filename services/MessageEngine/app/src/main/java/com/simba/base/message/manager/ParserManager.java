package com.simba.base.message.manager;

import android.util.Log;
import android.util.SparseArray;

import com.simba.base.message.protocol.BaseParser;

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

    public void parseCmd(byte[] cmdBytes) {
        BaseParser parser = mParserList.get(ProtocolFactory.creat().getCmdType(cmdBytes));
        if (parser != null) {
            if (parser.check(cmdBytes)) {
                parser.parse(cmdBytes);
            } else {
                Log.w(TAG, "cannot parse use " + parser);
            }
        } else {
            Log.w(TAG, "no such parse...");
        }
    }
}