package com.simba.violationenquiry.add.presenter;

import com.simba.base.mvp.presenter.BasePresenter;
import com.simba.base.network.model.SimpleResponse;
import com.simba.violationenquiry.R;
import com.simba.violationenquiry.add.contract.AddCarInfoContract;
import com.simba.violationenquiry.add.model.AddCarModel;
import com.simba.violationenquiry.net.HttpRequest;
import com.simba.violationenquiry.net.callback.ResultCallBack;
import com.simba.violationenquiry.net.model.CarInfo;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/14
 * @Desc :
 */
public class AddCarPresenter extends BasePresenter<AddCarModel, AddCarInfoContract.AddView> implements AddCarInfoContract.AddPresenter {
    private Disposable mDisposable;

    @Override
    protected AddCarModel createModule() {
        return new AddCarModel();
    }

    @Override
    public void start() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            if (!mDisposable.isDisposed()) {
                mDisposable.dispose();
            }
        }
    }

    @Override
    public void onAdd(final CarInfo carInfo) {
        Observable.create(new ObservableOnSubscribe<SimpleResponse>() {
            @Override
            public void subscribe(final ObservableEmitter<SimpleResponse> emitter) throws Exception {

                HttpRequest.add(new ResultCallBack<SimpleResponse>() {
                    @Override
                    public void onLoaded(SimpleResponse wrapper) {
                        emitter.onNext(wrapper);
                    }

                    @Override
                    public void onDataLoadedFailure(Exception e) {
                        emitter.onError(e);
                    }
                }, getContext(), carInfo);
            }
        }).subscribeOn(Schedulers.io())
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mDisposable = disposable;
                        showLoading();
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<SimpleResponse>() {
                    @Override
                    public void accept(SimpleResponse response) throws Exception {
                        if (isViewAttached()) {
                            dismissLoading();
                            if (response.success) {
                                showToast(R.string.binding_success);
                                getView().onAddSuccess();
                            } else {
                                getView().onAddFail(response.message);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (isViewAttached()) {
                            dismissLoading();
                            getView().onAddFail(throwable.getMessage());
                        }
                    }
                });


    }
}
