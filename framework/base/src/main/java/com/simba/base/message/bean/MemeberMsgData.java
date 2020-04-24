package com.simba.base.message.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.simba.base.message.contant.MessageDef;

/**
 * @author chefengyun
 *
 * 会员中心消息
 *
 */
public class MemeberMsgData implements Parcelable {

    /**
     * 会员ID
     */
    String id;

    /**
     * 消息标题
     */
    String title;

    /**
     * 消息内容
     */
    String message;

    /**
     * 消息网址
     */
    String url;

    public static final int CODE = MessageDef.Message.MEMBER_MSG;

    public MemeberMsgData(String id, String title, String message, String url) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getMessage() {
        return message;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "MemeberMsgData{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", message='" + message + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    protected MemeberMsgData(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        id = in.readString();
        title = in.readString();
        message = in.readString();
        url = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(message);
        dest.writeString(url);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MemeberMsgData> CREATOR = new Creator<MemeberMsgData>() {
        @Override
        public MemeberMsgData createFromParcel(Parcel in) {
            return new MemeberMsgData(in);
        }

        @Override
        public MemeberMsgData[] newArray(int size) {
            return new MemeberMsgData[size];
        }
    };

}
