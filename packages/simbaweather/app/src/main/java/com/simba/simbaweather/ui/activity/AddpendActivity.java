package com.simba.simbaweather.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.model.Response;
import com.simba.base.network.model.GeneralResponse;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.bean.CityplanningBean;
import com.simba.simbaweather.data.bean.SearchBean;
import com.simba.simbaweather.data.bean.WeaTher;
import com.simba.simbaweather.di.cityplanningMvp.CityplanningContract;
import com.simba.simbaweather.di.cityplanningMvp.CityplanningPresenter;
import com.simba.simbaweather.ui.adapter.CityplanningAdapter;
import com.simba.simbaweather.ui.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddpendActivity extends BaseActivity<CityplanningContract.ICityplanningShowView, CityplanningPresenter<CityplanningContract.ICityplanningShowView>> implements CityplanningContract.ICityplanningShowView {

    @BindView(R.id.tv_ci)
    TextView tvCi;
    @BindView(R.id.rcy_citytj)
    RecyclerView rcyCitytj;
    @BindView(R.id.ed_search)
    EditText edSearch;
    private GeneralResponse<List<CityplanningBean.DataBean>> body;
    private CityplanningBean.DataBean body1;
    private Intent intent1;
    private String city1;
    private WeaTher weaTher;
    private WeaTher.DataBean data;
    private String edsearch;

    @Override
    protected void initData() {
        ImmersionBar.with(this)
                .fullScreen(true)
                .transparentNavigationBar()
                .init();
        //不自动弹出键盘
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        mPresenter.RequestCityPlnningData();

       edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //edSearch.setText(edSearch.getText().toString());
                edsearch = edSearch.getText().toString().trim();
                mPresenter.RequestSearchData(edsearch);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @Override
    protected CityplanningPresenter<CityplanningContract.ICityplanningShowView> oncreatePresenter() {
        return new CityplanningPresenter<>();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_addpend;
    }

    @Override
    public void CityplanningShowData(Response<List<CityplanningBean.DataBean>> response) {
        List<CityplanningBean.DataBean> data = (List<CityplanningBean.DataBean>) response.body();

        intent1 = getIntent();
        city1 = intent1.getStringExtra("city");
        TextView tv = findViewById(R.id.tv_dk);
        //  tv.setText(city1);

        GridLayoutManager girdLayoutManager = new GridLayoutManager(this, 7);
        rcyCitytj.setLayoutManager(girdLayoutManager);
        CityplanningAdapter cityplanningAdapter = new CityplanningAdapter(R.layout.item_citymanager, data);
        //rcyCitytj.addView(tv);
        rcyCitytj.setAdapter(cityplanningAdapter);


    }

    @Override
    public void SearchShowData(Response<List<SearchBean.DataBean>> response) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
