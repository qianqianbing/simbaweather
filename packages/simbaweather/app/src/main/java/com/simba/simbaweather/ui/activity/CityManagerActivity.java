package com.simba.simbaweather.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.okgo.model.Response;
import com.simba.base.base.BaseActivity;
import com.simba.base.dialog.DialogUtil;
import com.simba.base.utils.LocationUtil;
import com.simba.base.utils.LogUtil;
import com.simba.simbaweather.CityManager;
import com.simba.simbaweather.HttpRequest;
import com.simba.simbaweather.ICityChangeView;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.bean.WeaTher;


import java.util.ArrayList;
import java.util.List;


public class CityManagerActivity extends BaseActivity implements ICityChangeView, View.OnClickListener {
    private static String TAG = "CityManagerActivity";

    private TextView mTvCompileoff, mTvCompileon;
    private RecyclerView rcv_cityweather;
    private Activity activityContext;
    private  ImageView mivIMG;
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    private ImageView iv_back;
    //是否处在编辑状态
    private boolean isEditState = false;

    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_runcity;
    }

    @Override
    protected void initView() {
        activityContext = this;
        CityManager.getInstance().registerCityChangeView(this);
        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        mTvCompileon = findViewById(R.id.tv_compileon);
        mTvCompileon.setOnClickListener(this);
        mTvCompileoff = findViewById(R.id.tv_compileoff);
        mTvCompileoff.setOnClickListener(this);
        rcv_cityweather = findViewById(R.id.rcv_cityweather);
        rcv_cityweather.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(this);

        myRecyclerViewAdapter.setData(CityManager.getInstance().getCityList());
        rcv_cityweather.setAdapter(myRecyclerViewAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_compileon:
                //打开
                isEditState = true;
                mTvCompileoff.setVisibility(View.VISIBLE);
                mTvCompileon.setVisibility(View.INVISIBLE);
                myRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case R.id.tv_compileoff:
                //关闭
               isEditState = false;
                mTvCompileoff.setVisibility(View.INVISIBLE);
                mTvCompileon.setVisibility(View.VISIBLE);
                myRecyclerViewAdapter.notifyDataSetChanged();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.RecyclerHolder> {

        private Context mContext;
        private List<CityManager.CityManagerBean> cityList = new ArrayList<>();

        public MyRecyclerViewAdapter(Context mContext) {
            this.mContext = mContext;
        }

        public void setData(List<CityManager.CityManagerBean> dataList) {
            if (null != dataList) {
                this.cityList.clear();
                this.cityList.addAll(dataList);
                notifyDataSetChanged();
            }
        }

        @Override
        public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_cityweather, parent, false);
            return new RecyclerHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerHolder holder, int position) {
            holder.rl_weather.setVisibility(View.VISIBLE);
            holder.rl_jump.setVisibility(View.GONE);
            holder.mIvCityDelete.setVisibility(View.GONE);
            if (position == 0) {

                Location location = LocationUtil.getInstance(getApplicationContext()).getLocationInfo();
                double latitude;
                double longitude;
                if (location == null) {
                    latitude = 32.298741;
                    longitude = 118.840485;
                } else {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
                HttpRequest.getIntance().requestWeatherDataByLocation(activityContext, "" + latitude, "" + longitude, new HttpRequest.WeatherHandler() {

                    private String conditionId;

                    @Override
                    public void handleWeatherResult(Response<WeaTher.DataBean> response) {
                        WeaTher.DataBean weatherDatabean = response.body();
                        try {
                            holder.mTvCityName.setText(weatherDatabean.getCity().getCity() + "·" + weatherDatabean.getCity().getDistrict());
                            holder.mTvCityTemp.setText(weatherDatabean.getWeatherToday().getTemp());
                            holder.mTvCityTempRang.setText(weatherDatabean.getWeatherToday().getDate());
                            conditionId = weatherDatabean.getWeatherToday().getConditionId();

                            if (conditionId.equals("1")) {
                                //晴天
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.sunny));
                            } else if (conditionId.equals("3")) {
                                //大部晴朗
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.mostlysunny));
                            } else if (conditionId.equals("4")) {
                                //多云
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.cloudy));
                            } else if (conditionId.equals("5")) {
                                //少云
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lesscludy));
                            } else if (conditionId.equals("6")) {
                                //阴天
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.overcast));
                            } else if (conditionId.equals("7")) {
                                //阵雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.shower));
                            } else if (conditionId.equals("8")) {
                                //局部阵雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.scatteredshower));
                            } else if (conditionId.equals("9")) {
                                //小阵雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lightshower));
                            } else if (conditionId.equals("10")) {
                                //强阵雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavyshower));
                            } else if (conditionId.equals("11")) {
                                //阵雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.snowshower));
                            } else if (conditionId.equals("12")) {
                                //小阵雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lightsnowshower));
                            } else if (conditionId.equals("13")) {
                                //雾
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.fog));
                            } else if (conditionId.equals("14")) {
                                //冻雾
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.freezingfog));
                            } else if (conditionId.equals("15")) {
                                //沙尘暴
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.sandstorm));
                            } else if (conditionId.equals("16")) {
                                //浮尘
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.floatingdust));
                            } else if (conditionId.equals("17")) {
                                //尘卷风
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.dustdevil));
                            } else if (conditionId.equals("18")) {
                                //扬沙
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.sanddust));
                            } else if (conditionId.equals("19")) {
                                //强沙尘暴
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.strongsandstrong));
                            } else if (conditionId.equals("20")) {
                                //霾
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.smog));
                            } else if (conditionId.equals("21")) {
                                //雷阵雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.thundershower));
                            } else if (conditionId.equals("22")) {
                                //雷电
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.thunder));
                            } else if (conditionId.equals("23")) {
                                //雷暴
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.thunderstorm));
                            } else if (conditionId.equals("24")) {
                                //雷阵雨伴有冰雹
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.thundershowerwithhail));
                            } else if (conditionId.equals("25")) {
                                //冰雹
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.hail));
                            } else if (conditionId.equals("26")) {
                                //冰针
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.iceneedle));
                            } else if (conditionId.equals("27")) {
                                //冰粒
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ice));
                            } else if (conditionId.equals("28")) {
                                //雨夹雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.sleet));
                            } else if (conditionId.equals("29")) {
                                //小雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lightrain));
                            } else if (conditionId.equals("30")) {
                                //中雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.moderaterain));
                            } else if (conditionId.equals("31")) {
                                //大雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavyrain));
                            } else if (conditionId.equals("32")) {
                                //暴雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.rainstorm));
                            } else if (conditionId.equals("33")) {
                                //特大暴雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavy));
                            } else if (conditionId.equals("34")) {
                                //小雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lightsnow));
                            } else if (conditionId.equals("35")) {
                                //中雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.modeatesnow));
                            } else if (conditionId.equals("36")) {
                                //大雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavysnow));
                            } else if (conditionId.equals("37")) {
                                //暴雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.snowstorm));
                            } else if (conditionId.equals("38")) {
                                //冻雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.freezingrain));
                            } else if (conditionId.equals("39")) {
                                //大暴雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavyrainstorm));
                            } else if (conditionId.equals("40")) {
                                //雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.snow));
                            } else if (conditionId.equals("41")) {
                                //雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.rain));
                            } else if (conditionId.equals("42")) {
                                //小到中雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lighttomoderaterain));
                            } else if (conditionId.equals("43")) {
                                //中到大雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.moderatetoheavyrain));
                            } else if (conditionId.equals("44")) {
                                //大到暴雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavytostormrain));
                            } else if (conditionId.equals("45")) {
                                //小到中雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lighttomoderatesnow));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else if (position == getItemCount() - 1) {
                holder.mTvCityName.setText("100");
                holder.rl_weather.setVisibility(View.GONE);
                holder.rl_jump.setVisibility(View.VISIBLE);
                holder.bt_jump.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CityManagerActivity.this, AddCityActivity.class);
                        startActivity(intent);
                    }
                });
            } else {
                int cityId = cityList.get(position).getCityId();
                HttpRequest.getIntance().requestWeatherDataByCityId(activityContext, cityId, new HttpRequest.WeatherHandler() {

                    private String conditionId;

                    @Override
                    public void handleWeatherResult(Response<WeaTher.DataBean> response) {
                        WeaTher.DataBean weatherDatabean = response.body();
                        if (isEditState) {
                            holder.mIvCityDelete.setVisibility(View.VISIBLE);
                            holder.mIvCityDelete.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    DialogUtil.build(CityManagerActivity.this)
                                            .content("确定删除该城市?")
                                            .positiveText("确定")
                                            .negativeText("取消")
                                            .onPositive(new DialogUtil.SingleButtonCallback() {
                                                @Override
                                                public void onClick(DialogUtil dialogUtil, DialogUtil.DialogAction dialogAction) {
                                                    mTvCompileoff.setVisibility(View.INVISIBLE);
                                                    mTvCompileon.setVisibility(View.VISIBLE);
                                                    holder.mIvCityDelete.setVisibility(View.INVISIBLE);
                                                    LogUtil.e(dialogAction + "删除成功");
                                                    Toast.makeText(CityManagerActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                                    CityManager.getInstance().updateCityState(false, cityId);
                                                    myRecyclerViewAdapter.notifyDataSetChanged();

                                                }
                                            })
                                            .onNegative(new DialogUtil.SingleButtonCallback() {
                                                @Override
                                                public void onClick(DialogUtil dialogUtil, DialogUtil.DialogAction dialogAction) {
                                                    mTvCompileoff.setVisibility(View.VISIBLE);
                                                    mTvCompileon.setVisibility(View.INVISIBLE);
                                                    LogUtil.e(dialogAction + "取消");
                                                }
                                            })
                                            .show();
                                }
                            });
                        }
                        try {
                            holder.mTvCityName.setText(weatherDatabean.getCity().getCity() + "·" + weatherDatabean.getCity().getDistrict());
                            holder.mTvCityTemp.setText(weatherDatabean.getWeatherToday().getTemp());
                            holder.mTvCityTempRang.setText(weatherDatabean.getWeatherToday().getDate());
                            conditionId = weatherDatabean.getWeatherToday().getConditionId();
                            if (conditionId.equals("1")) {
                                //晴天
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.sunny));
                            } else if (conditionId.equals("3")) {
                                //大部晴朗
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.mostlysunny));
                            } else if (conditionId.equals("4")) {
                                //多云
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.cloudy));
                            } else if (conditionId.equals("5")) {
                                //少云
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lesscludy));
                            } else if (conditionId.equals("6")) {
                                //阴天
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.overcast));
                            } else if (conditionId.equals("7")) {
                                //阵雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.shower));
                            } else if (conditionId.equals("8")) {
                                //局部阵雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.scatteredshower));
                            } else if (conditionId.equals("9")) {
                                //小阵雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lightshower));
                            } else if (conditionId.equals("10")) {
                                //强阵雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavyshower));
                            } else if (conditionId.equals("11")) {
                                //阵雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.snowshower));
                            } else if (conditionId.equals("12")) {
                                //小阵雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lightsnowshower));
                            } else if (conditionId.equals("13")) {
                                //雾
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.fog));
                            } else if (conditionId.equals("14")) {
                                //冻雾
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.freezingfog));
                            } else if (conditionId.equals("15")) {
                                //沙尘暴
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.sandstorm));
                            } else if (conditionId.equals("16")) {
                                //浮尘
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.floatingdust));
                            } else if (conditionId.equals("17")) {
                                //尘卷风
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.dustdevil));
                            } else if (conditionId.equals("18")) {
                                //扬沙
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.sanddust));
                            } else if (conditionId.equals("19")) {
                                //强沙尘暴
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.strongsandstrong));
                            } else if (conditionId.equals("20")) {
                                //霾
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.smog));
                            } else if (conditionId.equals("21")) {
                                //雷阵雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.thundershower));
                            } else if (conditionId.equals("22")) {
                                //雷电
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.thunder));
                            } else if (conditionId.equals("23")) {
                                //雷暴
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.thunderstorm));
                            } else if (conditionId.equals("24")) {
                                //雷阵雨伴有冰雹
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.thundershowerwithhail));
                            } else if (conditionId.equals("25")) {
                                //冰雹
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.hail));
                            } else if (conditionId.equals("26")) {
                                //冰针
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.iceneedle));
                            } else if (conditionId.equals("27")) {
                                //冰粒
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ice));
                            } else if (conditionId.equals("28")) {
                                //雨夹雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.sleet));
                            } else if (conditionId.equals("29")) {
                                //小雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lightrain));
                            } else if (conditionId.equals("30")) {
                                //中雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.moderaterain));
                            } else if (conditionId.equals("31")) {
                                //大雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavyrain));
                            } else if (conditionId.equals("32")) {
                                //暴雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.rainstorm));
                            } else if (conditionId.equals("33")) {
                                //特大暴雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavy));
                            } else if (conditionId.equals("34")) {
                                //小雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lightsnow));
                            } else if (conditionId.equals("35")) {
                                //中雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.modeatesnow));
                            } else if (conditionId.equals("36")) {
                                //大雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavysnow));
                            } else if (conditionId.equals("37")) {
                                //暴雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.snowstorm));
                            } else if (conditionId.equals("38")) {
                                //冻雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.freezingrain));
                            } else if (conditionId.equals("39")) {
                                //大暴雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavyrainstorm));
                            } else if (conditionId.equals("40")) {
                                //雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.snow));
                            } else if (conditionId.equals("41")) {
                                //雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.rain));
                            } else if (conditionId.equals("42")) {
                                //小到中雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lighttomoderaterain));
                            } else if (conditionId.equals("43")) {
                                //中到大雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.moderatetoheavyrain));
                            } else if (conditionId.equals("44")) {
                                //大到暴雨
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavytostormrain));
                            } else if (conditionId.equals("45")) {
                                //小到中雪
                                mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lighttomoderatesnow));
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }

        }

        @Override
        public int getItemCount() {

            int size = 0;
            size = cityList.size() + 1;
            Log.e(TAG, "size is " + size);
            return size;
        }

        class RecyclerHolder extends RecyclerView.ViewHolder {
            TextView mTvCityName, mTvCityTemp, mTvCityTempRang;
            private ImageView mIvCityDelete;
            RelativeLayout rl_weather;
            RelativeLayout rl_jump;
            Button bt_jump;


            private RecyclerHolder(View itemView) {
                super(itemView);
                mTvCityName = (TextView) itemView.findViewById(R.id.tv_citymangername);
                mTvCityTemp = itemView.findViewById(R.id.tv_cityTemp);
                mTvCityTempRang = itemView.findViewById(R.id.tv_cityTempRang);
                rl_weather = itemView.findViewById(R.id.rl_weather);
                mIvCityDelete = itemView.findViewById(R.id.iv_citydelete);
                rl_jump = itemView.findViewById(R.id.rl_jump);
                bt_jump = itemView.findViewById(R.id.bt_jump);
                mivIMG = itemView.findViewById(R.id.iv_citymangerimg1);
            }
        }
    }

    @Override
    public void onCityChange(List<CityManager.CityManagerBean> cityIdList) {
        myRecyclerViewAdapter.setData(CityManager.getInstance().getCityList());
    }


}
