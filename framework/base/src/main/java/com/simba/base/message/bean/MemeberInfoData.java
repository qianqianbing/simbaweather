package com.simba.base.message.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.simba.base.message.contant.MessageDef;

/**
 *
 * 会员登录信息
 *
 *@author chefengyun
 */
public class MemeberInfoData implements Parcelable {

    /**
     * 会员ID
     */
    String id;

    /**
     * 会员登录Token
     */
    String token;

    /**
     * 会员登录说明
     */
    String description;

    public static final int CODE = MessageDef.Message.MEMBER_INFO;

    public MemeberInfoData(String id, String token, String description) {
        this.id = id;
        this.token = token;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "MemeberInfo{" + "id='" + id + '\'' +
                ", token='" + token + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    protected MemeberInfoData(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        id = in.readString();
        token = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(token);
        dest.writeString(description);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MemeberInfoData> CREATOR = new Creator<MemeberInfoData>() {
        @Override
        public MemeberInfoData createFromParcel(Parcel in) {
            return new MemeberInfoData(in);
        }

        @Override
        public MemeberInfoData[] newArray(int size) {
            return new MemeberInfoData[size];
        }
    };

}
