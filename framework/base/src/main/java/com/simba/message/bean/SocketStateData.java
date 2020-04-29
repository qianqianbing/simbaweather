package com.simba.message.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.simba.message.contant.MessageDef;

/**
 * @author chefengyun
 *
 * 与云端 Sokcet 的连接状态
 *
 */
public class SocketStateData implements Parcelable {

    boolean connect;

    public static final int CODE = MessageDef.Message.SOCKET_STATE;

    public SocketStateData(boolean connect) {
        this.connect = connect;
    }

    protected SocketStateData(Parcel in) {
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

    public static final Creator<SocketStateData> CREATOR = new Creator<SocketStateData>() {
        @Override
        public SocketStateData createFromParcel(Parcel in) {
            return new SocketStateData(in);
        }

        @Override
        public SocketStateData[] newArray(int size) {
            return new SocketStateData[size];
        }
    };

    public boolean isConnect() {
        return connect;
    }

    @Override
    public String toString() {
        return "SocketStateData[connect=" + connect + "]";
    }
}
