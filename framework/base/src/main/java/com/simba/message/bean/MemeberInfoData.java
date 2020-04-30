package com.simba.message.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.simba.message.contant.MessageDef;

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
    String openid, userid;

    /**
     * 会员登录Token
     */
    String token;

    /**
     * 会员登录说明
     */
    String description;

    public static final int CODE = MessageDef.Message.MEMBER_INFO;

    public MemeberInfoData(String token, String openid, String userid, String description) {
        this.token = token;
        this.openid = openid;
        this.userid = userid;
        this.description = description;
    }

    public String getOpenid() {
        return openid;
    }

    public String getUserid() {
        return userid;
    }

    public String getToken() {
        return token;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "MemeberInfo{" +
                "token='" + token + '\'' +
                ", openid='" + openid + '\'' +
                ", userid='" + userid + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    protected MemeberInfoData(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        token = in.readString();
        openid = in.readString();
        userid = in.readString();
        description = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(token);
        dest.writeString(openid);
        dest.writeString(userid);
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
