package com.simba.membercenter.DB;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.simba.membercenter.MyApplication;

import static com.simba.base.DeviceAccountManager.DeviceAccountManager.DEVICE_AUTHORITY;
import static com.simba.base.DeviceAccountManager.DeviceAccountManager.DEVICE_STATE_Str;
import static com.simba.base.DeviceAccountManager.DeviceAccountManager.DEVICE_STATE_TYPE;

public class AccountInfoProvider extends ContentProvider {
    private static String TAG = "AccountInfoProvider";
    private final static UriMatcher sUriMatcher;


    static {
        sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //把Uri和Uri_Code相关联
        sUriMatcher.addURI(DEVICE_AUTHORITY,  DEVICE_STATE_Str , DEVICE_STATE_TYPE);
    }

    @Override
    public boolean onCreate() {
        Log.e("timeTAG" , "AccountInfoProvider oncreate");

        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        int uriType = sUriMatcher.match(uri);
        Cursor cursor;
        switch (uriType) {
            case DEVICE_STATE_TYPE:
                Log.e(TAG, "query device state");
                 //   cursor = deviceStateBeanDao.query(DBOpenHelper.DATABASE_STUDENT_TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder,null);
                cursor = MyApplication.getMyApplication().getDaoSession().getDeviceStateBeanDao().queryBuilder().buildCursor().query();
                return cursor;

            default:
                throw new IllegalArgumentException("UnSupport Uri : " + uri);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }


}
