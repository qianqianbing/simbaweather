package com.simba.violationenquiry.add.contract;

import android.content.Context;

import com.simba.base.mvp.model.BaseModel;
import com.simba.base.mvp.view.BaseView;
import com.simba.base.network.model.SimpleResponse;
import com.simba.base.network.model.callback.ResultCallBack;
import com.simba.violationenquiry.net.model.CarInfo;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/14
 * @Desc :
 */
public interface AddCarInfoContract {
    interface AddModel extends BaseModel {
        void onAdd(ResultCallBack<SimpleResponse> callBack, Context cxt, CarInfo carInfo);
    }

    interface AddView extends BaseView {
        void onAddSuccess();

        void onAddFail(String value);
    }

    interface AddPresenter {
        void onAdd(CarInfo carInfo);
    }
}
