package com.simba.simbaweather.ui.activity;

import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.simba.base.base.BaseActivity;
import com.simba.base.network.JsonCallback;
import com.simba.base.network.SimbaUrl;
import com.simba.base.utils.SpStaticUtils;
import com.simba.simbaweather.CityManager;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.bean.CityInfo;
import com.simba.simbaweather.ui.activity.view.DrawableEditText;
import com.simba.simbaweather.ui.adapter.CityplanningAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.BindView;

/**
 * 添加城市
 */
public class AddCityActivity extends BaseActivity {

    @BindView(R.id.tv_ci)
    TextView tvCi;
    @BindView(R.id.tv_recommend_title)
    TextView tvRecommendTitle;
    @BindView(R.id.rcy_citytj)
    RecyclerView rcyCitytj;
    @BindView(R.id.ed_search)
    DrawableEditText edSearch;
    @BindView(R.id.ll_add_city_recommend_group)
    LinearLayout llCityRecommendGroup;
    @BindView(R.id.iv_add_city_loading)
    ImageView ivAddCityLoading;

    CityInfo localCity;
    CityplanningAdapter cityplanningAdapter;
    Drawable editLeftDrawable, editRightDrawable;
    Animation animation;
    List<CityInfo> recommendCity;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_city;
    }

    @Override
    protected void initView() {
        rcyCitytj.setLayoutManager(new GridLayoutManager(this, 7));
        editLeftDrawable = getResources().getDrawable(R.mipmap.add_city_search_icon);
        editRightDrawable = getResources().getDrawable(R.mipmap.ic_add_city_search_closed);
        animation = AnimationUtils.loadAnimation(this, R.anim.anim_network_load_rotate);
    }

    @Override
    protected void initData() {
        String cityName = getIntent().getStringExtra("city");
        localCity = new CityInfo();
        localCity.setId("-1");
        localCity.setCity(cityName);
        localCity.setDistrict(cityName);
        localCity.setProvince(cityName);

        cityplanningAdapter = new CityplanningAdapter(R.layout.item_citymanager);

        //获取推荐城市列表
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
                String searchKey = s.toString().trim();
                if (searchKey.length() > 0) {
                    edSearch.setCompoundDrawablesWithIntrinsicBounds(editLeftDrawable, null, editRightDrawable, null);
                    tvRecommendTitle.setVisibility(View.GONE);
                    requestSearchData(searchKey);
                } else {
                    edSearch.setCompoundDrawablesWithIntrinsicBounds(editLeftDrawable, null, null, null);
                    tvRecommendTitle.setVisibility(View.VISIBLE);
                    cityplanningAdapter.setNewData(recommendCity);
                }
            }
        });
        cityplanningAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String cityId = ((CityInfo) adapter.getItem(position)).getId();
 				CityManager.getInstance().updateCityState(true, Integer.valueOf(cityId).intValue());
                SpStaticUtils.put("cityid0", cityId);//本地保存
                finish();
            }
        });
        edSearch.setOnDrawableRightListener(new DrawableEditText.OnDrawableRightListener() {
            @Override
            public void onDrawableRightClick(View view) {
                edSearch.setText("");
            }
        });
    }

    /**
     * 推荐城市列表
     */
    private void recommendCityList() {
        OkGo.<List<CityInfo>>post(SimbaUrl.WEATHER_GET_WEATHER_RECOMMENDCITYLIST)
                .tag(this)
                .execute(new JsonCallback<List<CityInfo>>() {
                    @Override
                    public void onSuccess(Response<List<CityInfo>> response) {
                        if (isCode200()) {
                            recommendCity = response.body();
                            recommendCity.add(0, localCity);
                            cityplanningAdapter.setNewData(recommendCity);
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

        OkGo.<List<CityInfo>>post(SimbaUrl.WEATHER_GET_WEATHER_MATCHINGCITY)
                .tag(this)
                .upJson(jsonObject)
                .execute(new JsonCallback<List<CityInfo>>() {
                    @Override
                    public void onStart(Request<List<CityInfo>, ? extends Request> request) {
                        super.onStart(request);
                        ivAddCityLoading.startAnimation(animation);
                    }

                    @Override
                    public void onSuccess(Response<List<CityInfo>> response) {
                        if (isCode200())
                            cityplanningAdapter.setNewData(response.body());
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        ivAddCityLoading.clearAnimation();
                        ivAddCityLoading.setVisibility(View.INVISIBLE);
                    }
                });
    }

}
