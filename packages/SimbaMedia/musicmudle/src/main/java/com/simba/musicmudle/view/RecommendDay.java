package com.simba.musicmudle.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.GridView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.simba.musicmudle.R;
import com.simba.musicmudle.utils.MusicContants;
import com.simba.musicmudle.view.adapter.RecommedDayAdapter;

import java.util.List;

import cn.kuwo.base.bean.Music;
import cn.kuwo.mod.quku.QukuRequestState;
import cn.kuwo.open.KwApi;
import cn.kuwo.open.OnDailyRecommendFetchListener;

@Route(path = MusicContants.PATH_RECCOMMEND_DAY)
public class RecommendDay extends AppCompatActivity {


    GridView  gridView;

    RecommedDayAdapter mRecommedDayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_activity_recommend_day);
        gridView = findViewById(R.id.gv);
        mRecommedDayAdapter = new RecommedDayAdapter();
        gridView.setAdapter(mRecommedDayAdapter);
        KwApi.fetchDailyRecommend(new OnDailyRecommendFetchListener(){

            @Override
            public void onFetch(QukuRequestState qukuRequestState, String s, List<Music> list) {
                mRecommedDayAdapter.doNotify(list);
            }
        });

    }
}
