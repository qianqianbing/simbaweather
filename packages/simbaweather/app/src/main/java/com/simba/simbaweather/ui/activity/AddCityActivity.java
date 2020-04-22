package com.simba.simbaweather.ui.activity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.simba.base.base.BaseActivity;
import com.simba.base.network.JsonCallback;
import com.simba.base.network.SimbaUrl;
import com.simba.base.utils.SpStaticUtils;
import com.simba.base.utils.Toasty;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.bean.CityplanningBean;
import com.simba.simbaweather.data.bean.SearchBean;
import com.simba.simbaweather.ui.adapter.CityplanningAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 添加城市
 */
public class AddCityActivity extends BaseActivity {

    @BindView(R.id.tv_ci)
    TextView tvCi;
    @BindView(R.id.rcy_citytj)
    RecyclerView rcyCitytj;
    @BindView(R.id.ed_search)
    EditText edSearch;
    @BindView(R.id.ll_add_city_recommend_group)
    EditText llCityRecommendGroup;
    private String edsearch;
    CityplanningBean.DataBean localCity;
    CityplanningAdapter cityplanningAdapter;
    private Unbinder bind;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_city;
    }

    @Override
    protected void initView() {
        bind = ButterKnife.bind(this);
        rcyCitytj.setLayoutManager(new GridLayoutManager(this, 7));
    }

    @Override
    protected void initData() {
        String cityName = getIntent().getStringExtra("city");
        localCity = new CityplanningBean.DataBean();
        localCity.setId("-1");
        localCity.setCity(cityName);
        localCity.setDistrict(cityName);
        localCity.setProvince(cityName);

        cityplanningAdapter = new CityplanningAdapter(R.layout.item_citymanager);

        recommendCityList();
    }

    @Override
    protected void initListener() {
        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                edsearch = s.toString().trim();
                llCityRecommendGroup.setVisibility(View.GONE);
                requestSearchData(edsearch);
            }
        });
        cityplanningAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String cityId = ((CityplanningBean.DataBean) adapter.getItem(position)).getId();
                SpStaticUtils.put("cityid0", cityId);//本地保存
                Toasty.error(mContext, cityId).show();
                finish();
            }
        });
    }

    public void searchShowData(Response<List<SearchBean.DataBean>> response) {

    }

    /**
     * 推荐城市列表
     */
    private void recommendCityList() {
        OkGo.<List<CityplanningBean.DataBean>>post(SimbaUrl.WEATHER_GET_WEATHER_RECOMMENDCITYLIST)
                .tag(this)
                .execute(new JsonCallback<List<CityplanningBean.DataBean>>() {
                    @Override
                    public void onSuccess(Response<List<CityplanningBean.DataBean>> response) {
                        if (isCode200()) {
                            List<CityplanningBean.DataBean> data = response.body();
                            data.add(0, localCity);
                            cityplanningAdapter.setNewData(data);
                            rcyCitytj.setAdapter(cityplanningAdapter);
                        }
                    }
                });
    }

    /**
     * 根据输入模糊查询结果
     *
     * @param searchValue
     */
    public void requestSearchData(String searchValue) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("searchValue", searchValue);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        OkGo.<List<SearchBean.DataBean>>post(SimbaUrl.WEATHER_GET_WEATHER_MATCHINGCITY)
                .tag(this)
                .upJson(jsonObject)
                .execute(new JsonCallback<List<SearchBean.DataBean>>() {
                    @Override
                    public void onSuccess(Response<List<SearchBean.DataBean>> response) {
                        if (isCode200())
                            searchShowData(response);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
