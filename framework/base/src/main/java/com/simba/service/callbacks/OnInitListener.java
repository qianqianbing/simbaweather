package com.simba.service.callbacks;

/**
 * This interface is asynchronous to listen the service connection result.
 */
public interface OnInitListener {
    /**
     * Connect service successfully
     */
    void onServiceConnected();

    /**
     * Connection with service is broken
     */
    void onServiceDisconnected();
}
