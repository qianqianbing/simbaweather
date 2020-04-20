package com.simba.simbaweather.ui.activity.frag;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.text.format.Time;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.model.Response;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.bean.LocationUtils;
import com.simba.simbaweather.data.bean.WeaTher;
import com.simba.simbaweather.di.weatherShowMvp.WeatherShowContract;
import com.simba.simbaweather.di.weatherShowMvp.WeatherShowPresenter;
import com.simba.simbaweather.ui.activity.RuncityActivity;
import com.simba.simbaweather.ui.activity.view.RecyclerViewDivider;
import com.simba.simbaweather.ui.adapter.WeatherAdapter;
import com.simba.simbaweather.ui.base.BaseFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/*
 *@Auther:王自阳
 *@Date: 2019/9/4
 *@Time:21:49
 *@Package_name:com.bw.movie.data.frag
 *@Description:
 * */
public class Cinema_Frag extends BaseFragment<WeatherShowContract.IWeatherShowView, WeatherShowPresenter<WeatherShowContract.IWeatherShowView>> implements WeatherShowContract.IWeatherShowView  {


    @Override
    public void WeatherShowData(Response<WeaTher.DataBean> response) {
    }



    @Override
    public void initData() {


    }

    @Override
    protected WeatherShowPresenter<WeatherShowContract.IWeatherShowView> oncreatePresenter() {
        return new WeatherShowPresenter<>();
    }

    @Override
    protected int getLayout() {
        return R.layout.item_cinema;
    }

}
