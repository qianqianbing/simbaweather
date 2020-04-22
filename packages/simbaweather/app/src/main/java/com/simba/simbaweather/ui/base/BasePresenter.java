package com.simba.simbaweather.ui.base;

import java.lang.ref.WeakReference;

/*
 *@Auther:王自阳
 *@Description:
 * */
public class BasePresenter<V> {

    private WeakReference<V> weakReference;

    public void onAttech(V v) {
        weakReference = new WeakReference<>(v);
    }

    public V getView() {
        return weakReference.get();
    }

    public void onDeatch() {
        weakReference.clear();
    }
}
