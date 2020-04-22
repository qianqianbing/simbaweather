package com.simba.simbaweather.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
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
    private WeaTher weaTher;
    private WeaTher.DataBean data;
    private String edsearch;
    private String city;
    private String cityid;
    private List<CityplanningBean.DataBean> data1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_addpend;
    }

    @Override
    protected void initView() {
        super.initView();
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
    protected void initData() {


    }


    @Override
    protected CityplanningPresenter<CityplanningContract.ICityplanningShowView> oncreatePresenter() {
        return new CityplanningPresenter<>();
    }


    @Override
    public void CityplanningShowData(Response<List<CityplanningBean.DataBean>> response) {
        data1 = (List<CityplanningBean.DataBean>) response.body();

        intent1 = getIntent();
        city = intent1.getStringExtra("city");

        GridLayoutManager girdLayoutManager = new GridLayoutManager(this, 7);
        rcyCitytj.setLayoutManager(girdLayoutManager);
        CityplanningAdapter cityplanningAdapter = new CityplanningAdapter(R.layout.item_citymanager, data1);
        rcyCitytj.setAdapter(cityplanningAdapter);
        cityplanningAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                cityid = data1.get(position).getId();
                SharedPreferences sp = getSharedPreferences("mysp", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("cityid0", cityid);
                editor.commit();
                Toast.makeText(AddpendActivity.this, "" + cityid, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(AddpendActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void SearchShowData(Response<List<SearchBean.DataBean>> response) {

    }

}
