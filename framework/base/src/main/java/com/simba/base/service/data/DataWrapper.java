package com.simba.base.service.data;

import android.os.Parcel;
import android.os.Parcelable;

public class DataWrapper<T extends Parcelable> implements Parcelable {

    private int mDataCode;
    private T mData;


    public DataWrapper(T data, int typeCode) {
        this.mData = data;
        this.mDataCode = typeCode;
    }

    protected DataWrapper(Parcel in) {
        mDataCode = in.readInt();
        mData = in.readParcelable(DataTypeHelper.getDataClass(mDataCode).getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mDataCode);
        dest.writeParcelable(mData, flags);
    }

    public void readFromParcel(Parcel in) {
        mDataCode = in.readInt();
        mData = in.readParcelable(DataTypeHelper.getDataClass(mDataCode).getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<DataWrapper> CREATOR = new Creator<DataWrapper>() {
        @Override
        public DataWrapper createFromParcel(Parcel in) {
            return new DataWrapper(in);
        }

        @Override
        public DataWrapper[] newArray(int size) {
            return new DataWrapper[size];
        }
    };

    public T getData() {
        return mData;
    }

    public int getDataCode() {
        return mDataCode;
    }

    @Override
    public String toString() {
        return "DataWrapper{" +
                "mDataCode=" + mDataCode +
                ", mData=" + mData +
                '}';
    }
}
