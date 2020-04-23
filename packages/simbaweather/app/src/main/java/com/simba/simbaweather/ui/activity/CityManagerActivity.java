package com.simba.simbaweather.ui.activity;

import android.content.Context;
import android.content.Intent;
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

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lzy.okgo.model.Response;
import com.simba.base.base.BaseActivity;
import com.simba.base.dialog.DialogUtil;
import com.simba.base.network.ConstantDefine;
import com.simba.base.utils.LogUtil;
import com.simba.base.utils.SpStaticUtils;
import com.simba.simbaweather.BuildConfig;
import com.simba.simbaweather.CityManager;
import com.simba.simbaweather.ICityChangeView;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.bean.CitySearchBean;
import com.simba.simbaweather.di.cityidMvp.CityIdContract;
import com.simba.simbaweather.di.cityidMvp.CityIdPresnter;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class CityManagerActivity extends BaseActivity implements ICityChangeView {
    private static String TAG = "CityManagerActivity";

    private TextView mTvCompileoff, mTvCompileon;
    private RecyclerView rcv_cityweather;
    MyRecyclerViewAdapter myRecyclerViewAdapter;
    @Override
    protected void initData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_runcity;
    }

    @Override
    protected void initView() {
        CityManager.getInstance().registerCityChangeView(this);
        mTvCompileon = findViewById(R.id.tv_compileon);
        mTvCompileoff = findViewById(R.id.tv_compileoff);
        rcv_cityweather = findViewById(R.id.rcv_cityweather);
        rcv_cityweather.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false));
        myRecyclerViewAdapter = new MyRecyclerViewAdapter(this);

        myRecyclerViewAdapter.setData(CityManager.getInstance().getCityIdList());
        rcv_cityweather.setAdapter(myRecyclerViewAdapter);

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
            holder.ll_weather.setVisibility(View.VISIBLE);
            holder.bt_jump.setVisibility(View.GONE);
            if(position == 0){
                holder.mTvcityName.setText("0");

            }else if(position == getItemCount() -1) {
                holder.mTvcityName.setText("100");
                holder.ll_weather.setVisibility(View.GONE);
                holder.bt_jump.setVisibility(View.VISIBLE);
                holder.bt_jump.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(CityManagerActivity.this, AddCityActivity.class);
                        startActivity(intent);
                    }
                });
            }else {
                holder.mTvcityName.setText(""+cityIDList.get(position -1 ));
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
            TextView mTvcityName;
            LinearLayout ll_weather;
            Button bt_jump;
            private RecyclerHolder(View itemView) {
                super(itemView);
                mTvcityName = (TextView) itemView.findViewById(R.id.tv_citymangername);
                ll_weather = itemView.findViewById(R.id.ll_weather);
                bt_jump = itemView.findViewById(R.id.bt_jump);
            }
        }
    }

    @OnClick({R.id.tv_compileon, R.id.tv_compileoff})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_compileon:
                //打开
                mTvCompileoff.setVisibility(View.VISIBLE);
                mTvCompileon.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_compileoff:
                //关闭
                mTvCompileoff.setVisibility(View.INVISIBLE);
                mTvCompileon.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }

    @Override
    public void onCityChange(List<Integer> cityIdList) {
        myRecyclerViewAdapter.setData(CityManager.getInstance().getCityIdList());
    }


}
