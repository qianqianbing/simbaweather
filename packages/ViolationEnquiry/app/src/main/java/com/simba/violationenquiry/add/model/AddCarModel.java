package com.simba.violationenquiry.add.model;

import android.content.Context;

import com.simba.base.network.model.SimpleResponse;
import com.simba.violationenquiry.add.contract.AddCarInfoContract;
import com.simba.violationenquiry.net.HttpRequest;
import com.simba.violationenquiry.net.callback.ResultCallBack;
import com.simba.violationenquiry.net.model.CarInfo;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/14
 * @Desc :
 */
public class AddCarModel implements AddCarInfoContract.AddModel {

    @Override
    public void onAdd(ResultCallBack<SimpleResponse> callBack, Context cxt, CarInfo carInfo) {
        HttpRequest.add(callBack, cxt, carInfo);
    }
}
