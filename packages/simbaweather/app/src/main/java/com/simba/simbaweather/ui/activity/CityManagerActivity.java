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
import android.widget.LinearLayout;
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


public class CityManagerActivity extends BaseActivity implements ICityChangeView , View.OnClickListener{
    private static String TAG = "CityManagerActivity";

    private TextView mTvCompileoff, mTvCompileon;
    private RecyclerView rcv_cityweather;
    private Activity activityContext;
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
        rcv_cityweather.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(this);

        myRecyclerViewAdapter.setData(CityManager.getInstance().getCityIdList());
        rcv_cityweather.setAdapter(myRecyclerViewAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
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
        private List<Integer> cityIDList = new ArrayList<>();

        public MyRecyclerViewAdapter(Context mContext) {
            this.mContext = mContext;
        }

        public void setData(List<Integer> dataList) {
            if (null != dataList) {
                this.cityIDList.clear();
                this.cityIDList.addAll(dataList);
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
            if(position == 0){

                Location location = LocationUtil.getInstance(getApplicationContext()).getLocationInfo();
                double latitude;
                double longitude;
                if(location == null){
                    latitude = 32.298741;
                    longitude = 118.840485;
                }else {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                }
                HttpRequest.getIntance().requestWeatherDataByLocation(activityContext, "" + latitude, "" + longitude, new HttpRequest.WeatherHandler() {
                    @Override
                    public void handleWeatherResult(Response<WeaTher.DataBean> response) {
                        WeaTher.DataBean weatherDatabean = response.body();
                        try {
                            holder.mTvCityName.setText(weatherDatabean.getCity().getCity() + "·" + weatherDatabean.getCity().getDistrict() );
                            holder.mTvCityTemp.setText(weatherDatabean.getWeatherToday().getTemp());
                            holder.mTvCityTempRang.setText(weatherDatabean.getWeatherToday().getDate());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }else if(position == getItemCount() -1) {
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
            }else {
                int cityId = cityIDList.get(position -1);
                HttpRequest.getIntance().requestWeatherDataByCityId(activityContext, cityId , new HttpRequest.WeatherHandler() {
                    @Override
                    public void handleWeatherResult(Response<WeaTher.DataBean> response) {
                        WeaTher.DataBean weatherDatabean = response.body();
                        if(isEditState){
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
                            holder.mTvCityName.setText(weatherDatabean.getCity().getCity() + "·" + weatherDatabean.getCity().getDistrict() );
                            holder.mTvCityTemp.setText(weatherDatabean.getWeatherToday().getTemp());
                            holder.mTvCityTempRang.setText(weatherDatabean.getWeatherToday().getDate());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
            }

        }

        @Override
        public int getItemCount() {

            int size = 0;
            if(cityIDList == null || cityIDList.size() == 0){
                size = 2;
            }else {
                size = cityIDList.size() + 2;
            }
            Log.e(TAG, "size is " + size);
            return size;
        }

        class RecyclerHolder extends RecyclerView.ViewHolder {
            TextView mTvCityName,mTvCityTemp,mTvCityTempRang;
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
            }
        }
    }

    @Override
    public void onCityChange(List<Integer> cityIdList) {
        myRecyclerViewAdapter.setData(CityManager.getInstance().getCityIdList());
    }


}
