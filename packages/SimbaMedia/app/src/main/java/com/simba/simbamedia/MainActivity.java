package com.simba.simbamedia;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import cn.kuwo.base.bean.Music;
import cn.kuwo.base.bean.online.BaseOnlineSection;
import cn.kuwo.base.bean.online.OnlineRootInfo;
import cn.kuwo.base.bean.quku.BaseQukuItem;
import cn.kuwo.mod.quku.QukuRequestState;
import cn.kuwo.open.ImageSize;
import cn.kuwo.open.KwApi;
import cn.kuwo.open.KwLogMgr;
import cn.kuwo.open.OnDailyRecommendFetchListener;
import cn.kuwo.open.OnImageFetchListener;
import cn.kuwo.open.base.SearchType;

public class MainActivity extends AppCompatActivity {

    private String TAG = "MainActivity";
    List<String> names;
    private MainAdapter mainAdapter;
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        KwLogMgr.sendAppStart();//酷我程序启动时调用
        mainAdapter = new MainAdapter();
        names = new ArrayList<>();
        mGridView = findViewById(R.id.gv);
        mGridView.setAdapter(mainAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i) {
                    case 0:
                        testRecommendSong();
                        break;

                    case 1:
                        testCategorySongSheet();
                        break;

                    case 2:
                        testRankingList();
                        break;

                    case 3:
                        testSinger();
                        break;

                    case 4:
                        testFM();
                        break;

                    case 5:
                        testSearch();
                        break;

                    case 6:
                        testRecommendDay();
                        break;
                }

            }
        });

    }


    /**
     * 分类歌单
     */

    public void testCategorySongSheet() {
        KwApi.fetchMusicCategories(new KwApi.OnFetchListener() {
            @Override
            public void onFetched(QukuRequestState state, String message, OnlineRootInfo info) {
                if (state == QukuRequestState.SUCCESS) {
                    for (BaseOnlineSection mBaseOnlineSection : info.getOnlineSections()) {
                        Log.i(TAG, "label:" + mBaseOnlineSection.getLabel());
                        Log.i(TAG, "mdigest:" + mBaseOnlineSection.getMdigest());
                        Log.i(TAG, "type:" + mBaseOnlineSection.getType());
                        Log.i(TAG, "appDesc:" + mBaseOnlineSection.getAppDesc());
                        Log.i(TAG, "appUrl:" + mBaseOnlineSection.getAppUrl());
                        Log.i(TAG, "adText:" + mBaseOnlineSection.getAdText());
                        Log.i(TAG, "androidUrl:" + mBaseOnlineSection.getAndroidUrl());
                        Log.i(TAG, "action:" + mBaseOnlineSection.getAction());
                        Log.i(TAG, "img:" + mBaseOnlineSection.getImg());
                        Log.i(TAG, "arUrl:" + mBaseOnlineSection.getArUrl());
                        Log.i(TAG, "adType:" + mBaseOnlineSection.getAdType());
                        Log.i(TAG, "hasClassfy:" + mBaseOnlineSection.getHasClassfy());
                        Log.i(TAG, "moreType:" + mBaseOnlineSection.getMoreType());
                        Log.i(TAG, "name:" + mBaseOnlineSection.getName());
                        Log.i(TAG, "mData:" + mBaseOnlineSection.getMdata());
                        Log.i(TAG, "--------------------------------------------------------------------");
                        for (BaseQukuItem mBaseQukuItem : mBaseOnlineSection.getOnlineInfos()) {
                            Log.i(TAG, "qukuItemType:" + mBaseQukuItem.getQukuItemType());
                            Log.i(TAG, "name:" + mBaseQukuItem.getName());
                            Log.i(TAG, "imageUrl:" + mBaseQukuItem.getImageUrl());
                            Log.i(TAG, "smallImageUrl:" + mBaseQukuItem.getSmallImageUrl());
                            Log.i(TAG, "extend:" + mBaseQukuItem.getExtend());
                            Log.i(TAG, "description:" + mBaseQukuItem.getDescription());
                            Log.i(TAG, "url:" + mBaseQukuItem.getUrl());
                            Log.i(TAG, "info:" + mBaseQukuItem.getInfo());
                            Log.i(TAG, "publish:" + mBaseQukuItem.getPublish());
                            Log.i(TAG, "isNew:" + mBaseQukuItem.getIsNew());
                            Log.i(TAG, "updateTime:" + mBaseQukuItem.getUpdateTime());
                            Log.i(TAG, "***********************************************************");
                        }
                    }
                }
            }
        });
    }


    /**
     * 推荐歌单
     */
    public void testRecommendSong() {

        KwApi.fetchRecommendSongList(new KwApi.OnFetchListener() {
            @Override
            public void onFetched(QukuRequestState state, String message, OnlineRootInfo info) {
                if (state == QukuRequestState.SUCCESS) {
                    for (BaseOnlineSection mBaseOnlineSection : info.getOnlineSections()) {
                        Log.i(TAG, "label:" + mBaseOnlineSection.getLabel());
                        Log.i(TAG, "--------------------------------------------------------------------");
                        for (BaseQukuItem mBaseQukuItem : mBaseOnlineSection.getOnlineInfos()) {
                            Log.i(TAG, "qukuItemType:" + mBaseQukuItem.getQukuItemType());
                            Log.i(TAG, "name:" + mBaseQukuItem.getName());
                            Log.i(TAG, "imageUrl:" + mBaseQukuItem.getImageUrl());
                            Log.i(TAG, "smallImageUrl:" + mBaseQukuItem.getSmallImageUrl());
                            Log.i(TAG, "extend:" + mBaseQukuItem.getExtend());
                            Log.i(TAG, "description:" + mBaseQukuItem.getDescription());
                            Log.i(TAG, "url:" + mBaseQukuItem.getUrl());
                            Log.i(TAG, "info:" + mBaseQukuItem.getInfo());
                            Log.i(TAG, "publish:" + mBaseQukuItem.getPublish());
                            Log.i(TAG, "isNew:" + mBaseQukuItem.getIsNew());
                            Log.i(TAG, "updateTime:" + mBaseQukuItem.getUpdateTime());
                            Log.i(TAG, "***********************************************************");
                        }
                    }

                }
            }
        });
    }

    /**
     * 排行榜
     */
    public void testRankingList() {
        KwApi.fetchBillBroad(new KwApi.OnFetchListener() {
            @Override
            public void onFetched(QukuRequestState state, String message, OnlineRootInfo info) {
                if (state == QukuRequestState.SUCCESS) {

                    for (BaseOnlineSection mBaseOnlineSection : info.getOnlineSections()) {
                        Log.i(TAG, "label:" + mBaseOnlineSection.getLabel());
                        Log.i(TAG, "--------------------------------------------------------------------");
                        for (BaseQukuItem mBaseQukuItem : mBaseOnlineSection.getOnlineInfos()) {
                            Log.i(TAG, "qukuItemType:" + mBaseQukuItem.getQukuItemType());
                            Log.i(TAG, "name:" + mBaseQukuItem.getName());
                            Log.i(TAG, "imageUrl:" + mBaseQukuItem.getImageUrl());
                            Log.i(TAG, "smallImageUrl:" + mBaseQukuItem.getSmallImageUrl());
                            Log.i(TAG, "extend:" + mBaseQukuItem.getExtend());
                            Log.i(TAG, "description:" + mBaseQukuItem.getDescription());
                            Log.i(TAG, "url:" + mBaseQukuItem.getUrl());
                            Log.i(TAG, "info:" + mBaseQukuItem.getInfo());
                            Log.i(TAG, "publish:" + mBaseQukuItem.getPublish());
                            Log.i(TAG, "isNew:" + mBaseQukuItem.getIsNew());
                            Log.i(TAG, "updateTime:" + mBaseQukuItem.getUpdateTime());
                            Log.i(TAG, "***********************************************************");
                        }
                    }
                }
            }
        });
    }


    public void testSinger(){
        //获取全部歌手，按热度排序
        KwApi.fetchAllArtist(true, 0, 30, new KwApi.OnFetchListener() {
            @Override
            public void onFetched(QukuRequestState state, String message, OnlineRootInfo info) {
                if (state == QukuRequestState.SUCCESS) {

                    for (BaseOnlineSection mBaseOnlineSection : info.getOnlineSections()) {
                        Log.i(TAG, "label:" + mBaseOnlineSection.getLabel());
                        Log.i(TAG, "--------------------------------------------------------------------");
                        for (BaseQukuItem mBaseQukuItem : mBaseOnlineSection.getOnlineInfos()) {
                            Log.i(TAG, "name:" + mBaseQukuItem.getName());
                            Log.i(TAG, "***********************************************************");
                        }
                    }
                }
            }
        });
        //获取华语男
        KwApi.fetchAllArtist(true, 0, 30, new KwApi.OnFetchListener() {
            @Override
            public void onFetched(QukuRequestState state, String message, OnlineRootInfo info) {
                if (state == QukuRequestState.SUCCESS) {

                    for (BaseOnlineSection mBaseOnlineSection : info.getOnlineSections()) {
                        Log.i(TAG, "label:" + mBaseOnlineSection.getLabel());
                        Log.i(TAG, "--------------------------------------------------------------------");
                        for (BaseQukuItem mBaseQukuItem : mBaseOnlineSection.getOnlineInfos()) {
                            Log.i(TAG, "name:" + mBaseQukuItem.getName());
                            Log.i(TAG, "=======================================================");
                        }
                    }
                }
            }
        });
    }

    public void testFM(){
        //获取电台分类
        KwApi.fetchRadio(new KwApi.OnFetchListener() {
            @Override
            public void onFetched(QukuRequestState state, String message, OnlineRootInfo info) {
                if (state == QukuRequestState.SUCCESS) {

                    for (BaseOnlineSection mBaseOnlineSection : info.getOnlineSections()) {
                        Log.i(TAG, "label:" + mBaseOnlineSection.getLabel());
                        Log.i(TAG, "--------------------------------------------------------------------");
                        for (BaseQukuItem mBaseQukuItem : mBaseOnlineSection.getOnlineInfos()) {
                            Log.i(TAG, "name:" + mBaseQukuItem.getName());
                            Log.i(TAG, "=======================================================");
                        }
                    }
                }
            }
        });

    }

    public void testSearch(){
        //搜索歌曲
        KwApi.search("夜曲", SearchType.MUSIC, 0, 30, new KwApi.OnFetchListener() {
            @Override
            public void onFetched(QukuRequestState state, String message, OnlineRootInfo info) {
                if (state == QukuRequestState.SUCCESS) {
                    for (BaseOnlineSection mBaseOnlineSection : info.getOnlineSections()) {
                        for (BaseQukuItem mBaseQukuItem : mBaseOnlineSection.getOnlineInfos()) {
                            Log.i(TAG, "name:" + mBaseQukuItem.getName());
                            Log.i(TAG, "***********************************************************");
                        }
                    }
                }
            }
        });
        //搜索歌手
        KwApi.search("周杰伦", SearchType.ARTIST, 0, 30, new KwApi.OnFetchListener() {
            @Override
            public void onFetched(QukuRequestState state, String message, OnlineRootInfo info) {
                if (state == QukuRequestState.SUCCESS) {
                    for (BaseOnlineSection mBaseOnlineSection : info.getOnlineSections()) {
                        for (BaseQukuItem mBaseQukuItem : mBaseOnlineSection.getOnlineInfos()) {
                            Log.i(TAG, "qukuItemType:" + mBaseQukuItem.getQukuItemType());
                            Log.i(TAG, "name:" + mBaseQukuItem.getName());
                            Log.i(TAG, "imageUrl:" + mBaseQukuItem.getImageUrl());
                            Log.i(TAG, "smallImageUrl:" + mBaseQukuItem.getSmallImageUrl());
                            Log.i(TAG, "extend:" + mBaseQukuItem.getExtend());
                            Log.i(TAG, "description:" + mBaseQukuItem.getDescription());
                            Log.i(TAG, "url:" + mBaseQukuItem.getUrl());
                            Log.i(TAG, "info:" + mBaseQukuItem.getInfo());
                            Log.i(TAG, "publish:" + mBaseQukuItem.getPublish());
                            Log.i(TAG, "isNew:" + mBaseQukuItem.getIsNew());
                            Log.i(TAG, "updateTime:" + mBaseQukuItem.getUpdateTime());
                            Log.i(TAG, "--------------------------------------");
                        }
                    }
                }

            }
        });
    }


    public void testRecommendDay(){
        KwApi.fetchDailyRecommend(new OnDailyRecommendFetchListener() {
            @Override
            public void onFetch(QukuRequestState state, String message, List<Music> musics) {
                if (state == QukuRequestState.SUCCESS) {
                    for (Music mMusic : musics) {
                        Log.i(TAG, "musics:" + mMusic.name);

                        Log.i(TAG,"-----------------------------------------------");
                    }

                    KwApi.fetchImage(musics.get(0), new OnImageFetchListener() {
                        @Override
                        public void onFetched(QukuRequestState state, String message, String imageUrl) {
                            Log.i(TAG,"imageUrl----------------"+imageUrl);

                        }
                    }, ImageSize.SIZE_500);

                    KwApi.fetchLyric(musics.get(1), new KwApi.OnLyricFetchListener() {
                        @Override
                        public void onFetched(QukuRequestState state, String message, String lyrics) {
                            Log.i(TAG,lyrics);

                        }
                    });

                }
            }
        });
    }


    public void musicDetial(){

    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        KwLogMgr.sendAppStop();//酷我程序退出时调用

    }
}
