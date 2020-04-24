package com.simba.base.os;

import android.os.IBinder;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ServiceManager {

    private static final String TAG = "ServiceManager";

    private static Class mClass;


    static{
        try {
            mClass = Class.forName("android.os.ServiceManager");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static IBinder getService(String name){
        try {
            Method getService = mClass.getMethod("getService", String.class);
            IBinder binder = (IBinder)getService.invoke(null, name);
            return binder;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void addService(String name, IBinder service){
        try {
            Method addService = mClass.getMethod("addService", String.class, IBinder.class);
            addService.invoke(null, name, service);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
