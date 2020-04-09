package com.simba.violationenquiry.ui;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.simba.violationenquiry.net.model.CarInfo;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/3
 * @Desc :
 */
public class PageViewModel extends ViewModel {

    private MutableLiveData<CarInfo> mIndex = new MutableLiveData<>();
    private LiveData<CarInfo> mText = Transformations.map(mIndex, new Function<CarInfo, CarInfo>() {
        @Override
        public CarInfo apply(CarInfo input) {
            return input;
        }
    });

    public void setIndex(CarInfo index) {
        mIndex.setValue(index);
    }

    public LiveData<CarInfo> getText() {
        return mText;
    }
}