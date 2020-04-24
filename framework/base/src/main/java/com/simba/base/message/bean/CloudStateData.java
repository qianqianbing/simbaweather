package com.simba.base.message.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.simba.base.message.contant.MessageDef;

/**
 * @author chefengyun
 *
 * 与云端的连接状态
 *
 */
public class CloudStateData implements Parcelable {

    boolean connect;

    public static final int CODE = MessageDef.Message.DEFAULT;

    public CloudStateData(boolean connect) {
        this.connect = connect;
    }

    protected CloudStateData(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        connect = in.readInt() == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(connect ? 1 : 0);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CloudStateData> CREATOR = new Creator<CloudStateData>() {
        @Override
        public CloudStateData createFromParcel(Parcel in) {
            return new CloudStateData(in);
        }

        @Override
        public CloudStateData[] newArray(int size) {
            return new CloudStateData[size];
        }
    };

    public boolean isConnect() {
        return connect;
    }

    @Override
    public String toString() {
        return "CloudStateData[connect=" + connect + "]";
    }
}
