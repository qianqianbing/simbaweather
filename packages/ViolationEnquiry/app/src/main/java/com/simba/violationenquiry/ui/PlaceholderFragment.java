package com.simba.violationenquiry.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simba.base.utils.Toasty;
import com.simba.violationenquiry.AddNewCarActivity;
import com.simba.violationenquiry.R;
import com.simba.violationenquiry.dialog.CommonDialog;
import com.simba.violationenquiry.net.HttpRequest;
import com.simba.violationenquiry.net.callback.ResultCallBack;
import com.simba.violationenquiry.net.model.CarInfo;
import com.simba.violationenquiry.net.model.detail.ViolateResData;
import com.simba.violationenquiry.net.model.detail.ViolateResDetail;
import com.simba.violationenquiry.ui.adapter.DetailAdapter;
import com.simba.violationenquiry.ui.itemdecoration.CommonDecoration;
import com.simba.violationenquiry.utils.AppUtils;

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
public class PlaceholderFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private static final String CAR_INFO = "CAR_INFO";
    //  private PageViewModel pageViewModel;
    private View root;
    private ImageView ivRefresh;
    private ImageView ivProgress;
    private RecyclerView recyclerView;
    private ImageView ivLoading;
    private RelativeLayout rlLoading;
    private LinearLayout llItemLoading;
    private Disposable mDisposable;
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

    public static PlaceholderFragment newInstance(int index, CarInfo carInfo) {
        PlaceholderFragment fragment = new PlaceholderFragment();
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
            carInfo = (CarInfo) getArguments().getSerializable(CAR_INFO);
        }
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_main, container, false);
        initView();
        initData();

        return root;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    private void initView() {
        ivRefresh = root.findViewById(R.id.iv_refresh);
        ivProgress = root.findViewById(R.id.iv_progress);
        recyclerView = root.findViewById(R.id.recyclerView);
        ivLoading = root.findViewById(R.id.iv_loading);
        rlLoading = root.findViewById(R.id.rl_loading);
        llItemLoading = root.findViewById(R.id.ll_loading);
        rlItemError = root.findViewById(R.id.rl_error_car);
        rlItemNormal = root.findViewById(R.id.rl_card);

        tvPlateNo = root.findViewById(R.id.tv_plate_no);
        tvBeProcessed = root.findViewById(R.id.tv_be_processed);
        tvScore = root.findViewById(R.id.tv_total_score);
        tvTotalMoney = root.findViewById(R.id.tv_total_money);
        tvUpdateTime = root.findViewById(R.id.tv_update_time);

        //异常item
        btnModify = root.findViewById(R.id.btn_modify);
        tvErrorPlateNo = root.findViewById(R.id.tv_error_plate);
        ivErrorRefresh = root.findViewById(R.id.iv_error_refresh);
        llErrorLoading = root.findViewById(R.id.ll_error_loading);
        ivErrorProgress = root.findViewById(R.id.iv_error_progress);

        ivRefresh.setOnClickListener(this);
        ivErrorRefresh.setOnClickListener(this);

        btnModify.setOnClickListener(this);

    }

    private void initData() {
        detailAdapter = new DetailAdapter(mData);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(detailAdapter);
        recyclerView.addItemDecoration(new CommonDecoration(getContext(), R.drawable.drawable_item_decoration));

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


        tvPlateNo.setText(detailData.getCph());
        tvBeProcessed.setText(detailData.getViolatesum());
        tvScore.setText(detailData.getScoresum());
        tvTotalMoney.setText(detailData.getAmountsum());
        tvUpdateTime.setText("更新于：" + detailData.getUpdatetime());

        tvErrorPlateNo.setText(detailData.getCph());

    }

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

                        if (isFirst) {
                            showLoadingView(false);
                        } else {
                            showItemLoading(false, isError);
                        }
                        showItemError(false);
                        detailData = violateResData;
                        initPageData();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showToast("查询失败,请稍后重试");
                        showItemError(true);
                        if (isFirst) {
                            showLoadingView(false);//进度对话框
                            showErrorDialog();
                        } else {
                            showItemLoading(false, isError);
                        }

                    }
                });
    }


    private void showLoadingView(boolean show) {
        rlLoading.setVisibility(show ? View.VISIBLE : View.GONE);
        if (show) {
            AppUtils.startLoadingAnimation(ivLoading);
        } else {
            AppUtils.endLoadingAnimation(ivLoading);
        }

    }

    private void showItemError(boolean show) {
        rlItemError.setVisibility(show ? View.VISIBLE : View.GONE);
        rlItemNormal.setVisibility(show ? View.GONE : View.VISIBLE);
    }

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

    private void showItemErrorLoading(boolean show) {
        llErrorLoading.setVisibility(show ? View.VISIBLE : View.GONE);
        ivErrorRefresh.setVisibility(show ? View.GONE : View.VISIBLE);
        if (show) {
            AppUtils.startLoadingAnimation(ivErrorProgress);
        } else {
            AppUtils.endLoadingAnimation(ivErrorProgress);
        }

    }

    private void showErrorDialog() {
        CommonDialog commonDialog = new CommonDialog(getContext(), "当前车辆信息无法查询到结果，请确认车辆信息是否正确。");
        commonDialog.show();
        commonDialog.setCanceledOnTouchOutside(false);
        commonDialog.setOnConfirmListener(new CommonDialog.OnConfirmListener() {
            @Override
            public void setOnConfirmClick(CommonDialog instance) {
                modifyCarInfo();
            }
        });

    }

    private void refresh(boolean isErrorItem) {
        loadData(false, isErrorItem);
    }

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

    private void showToast(String msg) {
        Toasty.info(getContext(), msg);
    }
}