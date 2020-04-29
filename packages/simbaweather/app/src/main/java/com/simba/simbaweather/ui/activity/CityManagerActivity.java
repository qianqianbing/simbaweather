package com.simba.simbaweather.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
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

import com.simba.base.base.BaseActivity;
import com.simba.base.dialog.DialogUtil;
import com.simba.base.utils.LogUtil;
import com.simba.simbaweather.CityInfoManager;
import com.simba.simbaweather.ICityChangeView;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.MyApplication;
import com.simba.simbaweather.data.WeatherIconUtil;
import com.simba.simbaweather.data.bean.WeatherBean;


import java.util.List;
import java.util.Map;


public class CityManagerActivity extends BaseActivity implements ICityChangeView, View.OnClickListener {
    private static String TAG = "CityManagerActivity";

    private TextView mTvCompileoff, mTvCompileon;
    private RecyclerView rcv_cityweather;
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

        iv_back = findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);
        mTvCompileon = findViewById(R.id.tv_compileon);
        mTvCompileon.setOnClickListener(this);
        mTvCompileoff = findViewById(R.id.tv_compileoff);
        mTvCompileoff.setOnClickListener(this);
        rcv_cityweather = findViewById(R.id.rcv_cityweather);
        rcv_cityweather.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(this);

        myRecyclerViewAdapter.setData(CityInfoManager.getInstance().getCityList(), null);

        rcv_cityweather.setAdapter(myRecyclerViewAdapter);

        CityInfoManager.getInstance().registerCityChangeView(this, this);
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
        private List<CityInfoManager.CityManagerBean> cityManagerBeanList;
        private Map<Integer, WeatherBean> weatherBeanMap;
        private WeatherBean weatherBean;
        private int cityId;

        public MyRecyclerViewAdapter(Context mContext) {
            this.mContext = mContext;
        }

        public void setData(List<CityInfoManager.CityManagerBean> cityManagerBeanList, Map<Integer, WeatherBean> weatherBeanMap) {
            this.cityManagerBeanList = cityManagerBeanList;
            this.weatherBeanMap = weatherBeanMap;
            notifyDataSetChanged();
        }

        @Override
        public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_cityweather, parent, false);
            return new RecyclerHolder(view);
        }

        public String getCityNameByPosition(int position) {
            if (weatherBeanMap != null) {
                int cityId = cityManagerBeanList.get(position).getCityId();
                if (position == 0) {
                    return weatherBeanMap.get(cityId).getCity().getCity() + "·" + weatherBeanMap.get(cityId).getCity().getDistrict();
                } else {
                    return weatherBeanMap.get(cityId).getCity().getDistrict();
                }
            }
            return "";
        }

        @Override
        public void onBindViewHolder(RecyclerHolder holder, int position) {
            holder.rl_weather.setVisibility(View.VISIBLE);
            holder.rl_jump.setVisibility(View.GONE);
            holder.mIvCityDelete.setVisibility(View.GONE);
//          只显示当前定位的location
            if (position == 0) {
                holder.Location.setVisibility(View.VISIBLE);
            }
            //从管理城市页面一个点击跳转到首页
//            holder.itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int cityId = cityManagerBeanList.get(position).getCityId();
//                    //  Toast.makeText(mContext, "跳转成功"+weatherBeanMap.get(position).getWeatherToday().getAqi(), Toast.LENGTH_SHORT).show();
//                    CityInfoManager.getInstance().updateCityState(true, cityId);
//                    Toast.makeText(mContext, "" + cityId, Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(CityManagerActivity.this, MainActivity.class);
//                    startActivity(intent);
//                }
//            });
// 最后一个 跳转的item
            if (position == getItemCount() - 1) {
                holder.rl_weather.setVisibility(View.GONE);
                holder.rl_jump.setVisibility(View.VISIBLE);

                holder.bt_jump.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (getItemCount() >= 6) {

                            Toast toast = Toast.makeText(getApplicationContext(),
                                    "本功能只能添加五个页面", Toast.LENGTH_LONG);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        } else {
                            Intent intent = new Intent(CityManagerActivity.this, AddCityActivity.class);
                            intent.putExtra("cityname", weatherBeanMap.get(cityId).getCity().getDistrict());
                            intent.putExtra("cityid", weatherBean.getCity().getCityid());
                            startActivity(intent);
                        }
                    }
                });
            } else {
                int cityId = cityManagerBeanList.get(position).getCityId();
                if (weatherBeanMap != null) {
                    weatherBean = weatherBeanMap.get(cityId);

                    if (isEditState) {
                        holder.mIvCityDelete.setVisibility(View.VISIBLE);
                        //本地定位不可被删除
                        if (position == 0) {
                            //当position等于cityid则隐藏删除按钮
                            holder.mIvCityDelete.setVisibility(View.GONE);
                        }

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
                                                mTvCompileoff.setVisibility(View.VISIBLE);
                                                mTvCompileon.setVisibility(View.INVISIBLE);
                                                LogUtil.e(dialogAction + "删除成功");
                                                // Toast.makeText(CityManagerActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                                                CityInfoManager.getInstance().updateCityState(false, cityId);
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
                        holder.mTvCityName.setText(getCityNameByPosition(position));
                        holder.mTvCityTemp.setText(weatherBean.getWeatherToday().getTemp() + "°");
                        holder.mTvCityTempRang.setText(weatherBean.getWeatherToday().getTempDay() + "°/" + weatherBean.getWeatherToday().getTempNight() + "°");
                        holder.mIvCityImg.setImageDrawable(WeatherIconUtil.getWeatherIconByType(MyApplication.getMyApplication(), weatherBean.getWeatherToday().getConditionId()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }

        @Override
        public int getItemCount() {

            int size = 0;
            size = cityManagerBeanList.size() + 1;
            Log.e(TAG, "size is " + size);
            return size;
        }

        class RecyclerHolder extends RecyclerView.ViewHolder {
            TextView mTvCityName, mTvCityTemp, mTvCityTempRang, Location;
            private ImageView mIvCityDelete, mIvCityImg;
            RelativeLayout rl_weather;
            RelativeLayout rl_jump;
            Button bt_jump;

            private RecyclerHolder(View itemView) {
                super(itemView);
                mTvCityName = (TextView) itemView.findViewById(R.id.tv_citymangername);
                Location = itemView.findViewById(R.id.location);
                mTvCityTemp = itemView.findViewById(R.id.tv_cityTemp);
                mIvCityImg = itemView.findViewById(R.id.iv_cityimg);
                mTvCityTempRang = itemView.findViewById(R.id.tv_cityTempRang);
                rl_weather = itemView.findViewById(R.id.rl_weather);
                mIvCityDelete = itemView.findViewById(R.id.iv_citydelete);
                rl_jump = itemView.findViewById(R.id.rl_jump);
                bt_jump = itemView.findViewById(R.id.bt_jump);
            }
        }
    }

    @Override
    public void onCityChange(List<CityInfoManager.CityManagerBean> cityManagerBeanList, Map<Integer, WeatherBean> weatherBeanList) {
        Log.e(TAG, "onCityChange");
        myRecyclerViewAdapter.setData(cityManagerBeanList, weatherBeanList);
    }
}
