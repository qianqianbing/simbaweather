package com.simba.violationenquiry.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simba.violationenquiry.AddNewCarActivity;
import com.simba.violationenquiry.R;
import com.simba.violationenquiry.base.BaseLazyLoadFragment;
import com.simba.violationenquiry.dialog.CommonDialog;
import com.simba.violationenquiry.net.HttpRequest;
import com.simba.violationenquiry.net.callback.ResultCallBack;
import com.simba.violationenquiry.net.model.CarInfo;
import com.simba.violationenquiry.net.model.detail.ViolateResData;
import com.simba.violationenquiry.net.model.detail.ViolateResDetail;
import com.simba.violationenquiry.ui.adapter.DetailAdapter;
import com.simba.violationenquiry.ui.itemdecoration.CommonDecoration;
import com.simba.violationenquiry.utils.AppUtils;
import com.simba.violationenquiry.utils.ResourceUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @Author : chenjianbo
 * @Date : 2020/4/3
 * @Desc :
 */
public class CarInfoDetailFragment extends BaseLazyLoadFragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String CAR_INFO = "CAR_INFO";

    private ImageView ivRefresh;
    private ImageView ivProgress;
    private RecyclerView recyclerView;
    private ImageView ivLoading;
    private RelativeLayout rlLoading;
    private LinearLayout llItemLoading;

    private CarInfo carInfo;
    private ViolateResData detailData;
    private RelativeLayout rlItemError;
    private RelativeLayout rlItemNormal;

    private TextView tvPlateNo;
    private TextView tvBeProcessed;
    private TextView tvScore;
    private TextView tvTotalMoney;
    private TextView tvUpdateTime;

    private Button btnModify;
    private TextView tvErrorPlateNo;
    private ImageView ivErrorRefresh;
    private LinearLayout llErrorLoading;
    private ImageView ivErrorProgress;


    private DetailAdapter detailAdapter;
    private List<ViolateResDetail> mData;
    private CommonDialog commonDialog;

    private int index = 0;

    public static CarInfoDetailFragment newInstance(int index, CarInfo carInfo) {
        CarInfoDetailFragment fragment = new CarInfoDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        bundle.putSerializable(CAR_INFO, carInfo);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
            carInfo = (CarInfo) getArguments().getSerializable(CAR_INFO);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_main;
    }
    /**
     * 初始化界面
     */
    @Override
    protected void initView() {
        ivRefresh = getView(R.id.iv_refresh);
        ivProgress = getView(R.id.iv_progress);
        recyclerView = getView(R.id.recyclerView);
        ivLoading = getView(R.id.iv_loading);
        rlLoading = getView(R.id.rl_loading);
        llItemLoading = getView(R.id.ll_loading);
        rlItemError = getView(R.id.rl_error_car);
        rlItemNormal = getView(R.id.rl_card);

        tvPlateNo = getView(R.id.tv_plate_no);
        tvBeProcessed = getView(R.id.tv_be_processed);
        tvScore = getView(R.id.tv_total_score);
        tvTotalMoney = getView(R.id.tv_total_money);
        tvUpdateTime = getView(R.id.tv_update_time);

        //异常item
        btnModify = getView(R.id.btn_modify);
        tvErrorPlateNo = getView(R.id.tv_error_plate);
        ivErrorRefresh = getView(R.id.iv_error_refresh);
        llErrorLoading = getView(R.id.ll_error_loading);
        ivErrorProgress = getView(R.id.iv_error_progress);

        ivRefresh.setOnClickListener(this);
        ivErrorRefresh.setOnClickListener(this);

        btnModify.setOnClickListener(this);

    }

    /**
     * 初始化数据
     */
    @Override
    protected void initData() {
        detailAdapter = new DetailAdapter(mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(detailAdapter);
        recyclerView.addItemDecoration(new CommonDecoration(getContext(), R.drawable.drawable_item_decoration));

        tvErrorPlateNo.setText(carInfo.getPlateno());

    }

    @Override
    protected void lazyLoad() {
        loadData(true, false);
    }


    /**
     * 正确返回的页面
     */
    private void initPageData() {
        if (detailData == null || carInfo == null) {
            return;
        }
        mData = detailData.getViolateResDataList();
        detailAdapter.refresh(mData, true);


        tvPlateNo.setText(carInfo.getPlateno());
        tvBeProcessed.setText(detailData.getViolatesum());
        tvScore.setText(detailData.getScoresum());
        tvTotalMoney.setText(detailData.getAmountsum());
        tvUpdateTime.setText(String.format(ResourceUtils.getString(R.string.update_time), detailData.getUpdatetime()));


    }

    /**
     * @param isFirst 是否第一次加载
     * @param isError 是否左边item异常
     */
    private void loadData(final boolean isFirst, final boolean isError) {

        Observable.create(new ObservableOnSubscribe<ViolateResData>() {
            @Override
            public void subscribe(final ObservableEmitter<ViolateResData> emitter) throws Exception {
                HttpRequest.getDetail(new ResultCallBack<ViolateResData>() {
                    @Override
                    public void onLoaded(ViolateResData wrapper) {
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
                        if (isFirst) {
                            showLoadingView(true);
                        } else {
                            showItemLoading(true, isError);
                        }
                    }
                }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ViolateResData>() {
                    @Override
                    public void accept(ViolateResData violateResData) throws Exception {
                        if (getUserVisibleHint()) {
                            if (isFirst) {
                                showLoadingView(false);
                            } else {
                                showItemLoading(false, isError);
                            }
                            showItemError(false);
                            detailData = violateResData;
                            initPageData();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (getUserVisibleHint()) {
                            showToast(R.string.query_fail);
                            showItemError(true);
                            if (isFirst) {
                                showLoadingView(false);//进度对话框
                                showErrorDialog();
                            } else {
                                showItemLoading(false, isError);
                            }
                        }
                    }
                });
    }

    /**
     * 显示/隐藏加载中View
     *
     * @param show
     */
    private void showLoadingView(boolean show) {
        rlLoading.setVisibility(show ? View.VISIBLE : View.GONE);
        if (show) {
            AppUtils.startLoadingAnimation(ivLoading);
        } else {
            AppUtils.endLoadingAnimation(ivLoading);
        }

    }

    /**
     * 显示左边item error的View
     *
     * @param show
     */
    private void showItemError(boolean show) {
        rlItemError.setVisibility(show ? View.VISIBLE : View.GONE);
        rlItemNormal.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    /**
     * 显示左边item加载
     *
     * @param show
     * @param isError 是否是error状态的item
     */
    private void showItemLoading(boolean show, boolean isError) {
        if (isError) {
            showItemErrorLoading(show);
            return;
        }
        llItemLoading.setVisibility(show ? View.VISIBLE : View.GONE);
        ivRefresh.setVisibility(show ? View.GONE : View.VISIBLE);
        if (show) {
            AppUtils.startLoadingAnimation(ivProgress);
        } else {
            AppUtils.endLoadingAnimation(ivProgress);
        }

    }

    /**
     * 显示左边error item的加载
     *
     * @param show
     */
    private void showItemErrorLoading(boolean show) {
        llErrorLoading.setVisibility(show ? View.VISIBLE : View.GONE);
        ivErrorRefresh.setVisibility(show ? View.GONE : View.VISIBLE);
        if (show) {
            AppUtils.startLoadingAnimation(ivErrorProgress);
        } else {
            AppUtils.endLoadingAnimation(ivErrorProgress);
        }

    }

    /**
     * 显示加载失败的dialog
     */
    private void showErrorDialog() {
        if (commonDialog != null && commonDialog.isShowing()) {
            commonDialog.dismiss();
        }
        commonDialog = new CommonDialog(getContext(), ResourceUtils.getString(R.string.query_fail_please_make_confirm));
        commonDialog.show();
        commonDialog.setCanceledOnTouchOutside(false);
        commonDialog.setOnConfirmListener(new CommonDialog.OnConfirmListener() {
            @Override
            public void setOnConfirmClick(CommonDialog instance) {
                modifyCarInfo();
            }
        });

    }

    /**
     * 刷新
     *
     * @param isErrorItem 是否是error
     */
    private void refresh(boolean isErrorItem) {
        loadData(false, isErrorItem);
    }

    /**
     * 修改车辆信息
     */
    private void modifyCarInfo() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(AddNewCarActivity.CAR_INFO, carInfo);
        Intent intent = new Intent(getActivity(), AddNewCarActivity.class);
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_refresh: {
                refresh(false);
            }
            break;
            case R.id.iv_error_refresh: {
                refresh(true);
            }
            break;
            case R.id.btn_modify: {
                modifyCarInfo();
            }
        }
    }


}