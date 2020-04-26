package com.simba.simbamedia.test;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;

import com.simba.simbamedia.MainAdapter;
import com.simba.simbamedia.R;

import java.util.ArrayList;
import java.util.List;

import cn.kuwo.base.bean.Music;
import cn.kuwo.base.bean.SongListMoreInfo;
import cn.kuwo.base.bean.online.BaseOnlineSection;
import cn.kuwo.base.bean.online.OnlineRootInfo;
import cn.kuwo.base.bean.quku.BaseQukuItem;
import cn.kuwo.mod.quku.QukuRequestState;
import cn.kuwo.open.ImageSize;
import cn.kuwo.open.KwApi;
import cn.kuwo.open.OnCategoriesHotTagListener;
import cn.kuwo.open.OnDailyRecommendFetchListener;
import cn.kuwo.open.OnHotKeywordsFetchListener;
import cn.kuwo.open.OnImageFetchListener;
import cn.kuwo.open.OnMusicsFetchListener;
import cn.kuwo.open.OnSearchTipsSearchListener;
import cn.kuwo.open.OnSongListInfoFetchListener;
import cn.kuwo.open.base.PhoneCodeResult;
import cn.kuwo.open.base.SearchType;

public class KwinterfaceActivity extends Activity {
    private String TAG = "KwinterfaceActivity";
    List<String> names;
    private MainAdapter mainAdapter;
    private GridView mGridView;
    private EditText et_music;
    private EditText et_singer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kwinterface);
        et_music = findViewById(R.id.et_music);
        et_singer = findViewById(R.id.et_singer);

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
                        testSearchSong();
                        break;

                    case 6:
                        testSearchSinger();
                        break;

                    case 7:
                        testRecommendDay();
                        break;

                    case 8:
                        requestCategoriesHotTag();
                        break;

                    case 9:
                        fetchSimilarSong(music);
                        break;

                    case 10:
                        sendPhoneCode();
                        break;

                    case 11:
                        fetchSearchHotKeywords();
                        break;

                    case 12:
                        fetchHotSongList();
                        break;

                    case 13:
                        fetchNewSongList();
                        break;

                    case  14:
                        fetchSearchTips("权");
                        break;

                    case 15:
                        fetchMusics();
                        break;

                    case 16:
                        fetchSongListMoreInfo();
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
                        Log.i(TAG, "id:" +  mBaseOnlineSection.getMid());

                        Log.i(TAG, "--------------------------------------------------------------------");
                        for (BaseQukuItem mBaseQukuItem : mBaseOnlineSection.getOnlineInfos()) {
                            Log.i(TAG, "BaseQukuItemId:" + mBaseQukuItem.getId());
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


    public void testSinger() {
        //获取全部歌手，按热度排序
        KwApi.fetchAllArtist(true, 0, 30, new KwApi.OnFetchListener() {
            @Override
            public void onFetched(QukuRequestState state, String message, OnlineRootInfo info) {
                if (state == QukuRequestState.SUCCESS) {

                    for (BaseOnlineSection mBaseOnlineSection : info.getOnlineSections()) {
                        Log.i(TAG, "label:" + mBaseOnlineSection.getLabel());
                        Log.i(TAG, "name:" + mBaseOnlineSection.getName());
                        Log.i(TAG, "mData:" + mBaseOnlineSection.getMdata());
                        Log.i(TAG, "mdigest:" + mBaseOnlineSection.getMdigest());
                        Log.i(TAG, "type:" + mBaseOnlineSection.getType());
                        Log.i(TAG, "appDesc:" + mBaseOnlineSection.getAppDesc());
                        Log.i(TAG, "appUrl:" + mBaseOnlineSection.getAppUrl());
                        Log.i(TAG, "adText:" + mBaseOnlineSection.getAdText());
                        Log.i(TAG, "androidUrl:" + mBaseOnlineSection.getAndroidUrl());
                        Log.i(TAG, "img:" + mBaseOnlineSection.getImg());
                        Log.i(TAG, "action:" + mBaseOnlineSection.getAction());
                        Log.i(TAG, "adType:" + mBaseOnlineSection.getAdType());
                        Log.i(TAG, "moreType:" + mBaseOnlineSection.getMoreType());


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

    public void testFM() {
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

    public void testSearchSong() {

        String musicContent = et_music.getText().toString().trim();
        Log.i(TAG, "musicContent   :   " + musicContent);
        //搜索歌曲
        KwApi.search(musicContent, SearchType.MUSIC, 0, 30, new KwApi.OnFetchListener() {
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
    }


    public void testSearchSinger() {
        String singerContent = et_singer.getText().toString().trim();
        Log.i(TAG, "singerContent   :   " + singerContent);
        //搜索歌手
        KwApi.search(singerContent, SearchType.ARTIST, 0, 30, new KwApi.OnFetchListener() {
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


    Music music;

    public void testRecommendDay() {
        KwApi.fetchDailyRecommend(new OnDailyRecommendFetchListener() {
            @Override
            public void onFetch(QukuRequestState state, String message, List<Music> musics) {
                if (state == QukuRequestState.SUCCESS) {
                    for (Music mMusic : musics) {
                        Log.i(TAG, "musics:" + mMusic.name + "  :   " + mMusic.rid);
                        if ("一路向北".equals(mMusic.name)) {
                            music = mMusic;
                        }

                        Log.i(TAG, "-----------------------------------------------");
                    }

                    KwApi.fetchImage(musics.get(0), new OnImageFetchListener() {
                        @Override
                        public void onFetched(QukuRequestState state, String message, String imageUrl) {
                            Log.i(TAG, "imageUrl----------------" + imageUrl);

                        }
                    }, ImageSize.SIZE_500);

                    KwApi.fetchLyric(musics.get(1), new KwApi.OnLyricFetchListener() {
                        @Override
                        public void onFetched(QukuRequestState state, String message, String lyrics) {
                            Log.i(TAG, lyrics);

                        }
                    });

                }
            }
        });
    }

    /**
     * 获取曲库分类热门标签
     */
    public void requestCategoriesHotTag() {
        KwApi.requestCategoriesHotTag(new OnCategoriesHotTagListener() {
            @Override
            public void onFetch(QukuRequestState state, String s, List<BaseQukuItem> list) {
                if (state == QukuRequestState.SUCCESS) {
                    for (BaseQukuItem mBaseQukuItem : list) {
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
        });
    }

    public void fetchSimilarSong(Music music) {
        Log.i(TAG, "推荐" + music.name + "相似歌曲");
        KwApi.fetchSimilarSong(music, 3, new KwApi.OnFetchListener() {
            @Override
            public void onFetched(QukuRequestState state, String s, OnlineRootInfo info) {
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


    public static void go2Activty(Context context) {
        Intent intent = new Intent(context, KwinterfaceActivity.class);
        context.startActivity(intent);

    }

    public void sendPhoneCode() {
        Log.i(TAG, "sendPhoneCode..");
        KwApi.sendPhoneCode("17301592900", new KwApi.OnFetchPhoneCodeListener() {
            @Override
            public void onFetched(PhoneCodeResult phoneCodeResult) {

                Log.i(TAG, "phoneCodeResult :" + phoneCodeResult.getMessage());

            }
        });
    }

    public void fetchSearchHotKeywords() {
        KwApi.fetchSearchHotKeywords(new OnHotKeywordsFetchListener() {
            @Override
            public void onFetch(QukuRequestState state, String s, List<String> list) {
                if (state == QukuRequestState.SUCCESS) {
                    for (String content : list) {
                        Log.i(TAG, "content  :" + content);
                    }
                }

            }
        });
    }

    public void fetchHotSongList() {
        KwApi.fetchHotSongList(1, 10, new KwApi.OnFetchListener() {
            @Override
            public void onFetched(QukuRequestState state, String s, OnlineRootInfo onlineRootInfo) {
                if (state == QukuRequestState.SUCCESS) {
                    if (state == QukuRequestState.SUCCESS) {
                        for (BaseOnlineSection mBaseOnlineSection : onlineRootInfo.getOnlineSections()) {
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
                                Log.i(TAG, "-----------------------------------------------------------------");
                            }
                        }
                    }
                }

            }
        });
    }


    public void fetchNewSongList(){
        KwApi.fetchNewSongList(1,10, new KwApi.OnFetchListener() {
            @Override
            public void onFetched(QukuRequestState qukuRequestState, String s, OnlineRootInfo onlineRootInfo) {

                if (qukuRequestState == QukuRequestState.SUCCESS) {
                    if (qukuRequestState == QukuRequestState.SUCCESS) {
                        for (BaseOnlineSection mBaseOnlineSection : onlineRootInfo.getOnlineSections()) {
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
                                Log.i(TAG, "-----------------------------------------------------------------");
                            }
                        }
                    }
                }
            }
        });
    }


    public void fetchSearchTips(String keyWord){
        KwApi.fetchSearchTips(keyWord, new OnSearchTipsSearchListener() {
            @Override
            public void onFetch(QukuRequestState qukuRequestState, String s, List<String> list) {
                if (qukuRequestState == QukuRequestState.SUCCESS) {

                    for(String contnt : list){
                        Log.i(TAG,"contnt---------------"+contnt);
                    }
                }
            }
        });
    }

    public void fetchMusics(){
        List<Long> ids =  new ArrayList<>();
        ids.add((long) 3229127);
        ids.add((long) 82393306);
        KwApi.fetchMusics(ids, new OnMusicsFetchListener() {
            @Override
            public void onFetch(QukuRequestState qukuRequestState, String s, List<Music> list) {
                if (qukuRequestState == QukuRequestState.SUCCESS) {
                    for (Music mMusic : list) {
                        Log.i(TAG, "musics:" + mMusic.name + "  :   " + mMusic.rid);
                        Log.i(TAG, "artist:" + mMusic.artist);
                        Log.i(TAG, "album:" + mMusic.album);
                        Log.i(TAG, "tag:" + mMusic.tag);
                        Log.i(TAG, "mvQuality:" + mMusic.mvQuality);
                        Log.i(TAG, "mvIconUrl:" + mMusic.mvIconUrl);
                        Log.i(TAG, "audioId:" + mMusic.audioId);
                        Log.i(TAG, "floatAdId:" + mMusic.floatAdId);
                        Log.i(TAG, "source:" + mMusic.source);
                        Log.i(TAG, "getPolicy:" +   mMusic.getPolicy());

                        Log.i(TAG, "----------------------------------------------------------");
                    }

                }
            }
        });
    }

    public void fetchSongListMoreInfo(){
        KwApi.fetchSongListMoreInfo(1091,new OnSongListInfoFetchListener(){

            @Override
            public void onFetch(QukuRequestState qukuRequestState, String s, SongListMoreInfo songListMoreInfo) {
                if (qukuRequestState == QukuRequestState.SUCCESS) {
                    Log.i(TAG,"songListMoreInfo.pic:"+songListMoreInfo.getPic());
                    Log.i(TAG,"songListMoreInfo.desc:"+songListMoreInfo.getDesc());
                    Log.i(TAG,"songListMoreInfo.total:"+songListMoreInfo.getTotal());
                    Log.i(TAG,"songListMoreInfo.uname:"+songListMoreInfo.getUname());
                    Log.i(TAG,"songListMoreInfo.play_num:"+songListMoreInfo.getPlay_num());
                    Log.i(TAG,"songListMoreInfo.share_num:"+songListMoreInfo.getShare_num());
                    Log.i(TAG,"songListMoreInfo.ctime:"+songListMoreInfo.getCtime());
                    Log.i(TAG,"songListMoreInfo.tag:"+songListMoreInfo.getTag());
                    Log.i(TAG,"songListMoreInfo.upic:"+songListMoreInfo.getUpic());

                }
            }
        });

    }


}
