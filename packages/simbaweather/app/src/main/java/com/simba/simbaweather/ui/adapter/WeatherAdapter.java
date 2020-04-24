package com.simba.simbaweather.ui.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.simba.simbaweather.R;
import com.simba.simbaweather.data.bean.WeaTher;

import java.util.List;

/**
 * @author wzy
 * @description:
 * @date :2020/4/10 14:45
 */
public class WeatherAdapter extends BaseQuickAdapter<WeaTher.DataBean.WeatherListBean, BaseViewHolder> {

    private String conditionId;

    public WeatherAdapter(int layoutResId, @Nullable List<WeaTher.DataBean.WeatherListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WeaTher.DataBean.WeatherListBean item) {
        //日期
        helper.setText(R.id.tv_tomomorro, item.getDayStr());
        //日历
        helper.setText(R.id.tv_date, item.getDate());
        //天气特征
        helper.setText(R.id.tv_weathersituation, item.getCondition());
        //温度
        helper.setText(R.id.tv_tirtmp, item.getTempDay() + "°/" + item.getTempNight() + "°");
        //天气图标
        ImageView mivIMG = helper.getView(R.id.miv_img);
        conditionId = item.getConditionId();
        //进行天气的判断是晴天还是其他的一些天气
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
        }
        else if (conditionId.equals("18")) {
            //扬沙
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.sanddust));
        }
        else if (conditionId.equals("19")) {
            //强沙尘暴
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.strongsandstrong));
        }
        else if (conditionId.equals("20")) {
            //霾
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.smog));
        }
        else if (conditionId.equals("21")) {
            //雷阵雨
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.thundershower));
        }
        else if (conditionId.equals("22")) {
            //雷电
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.thunder));
        }
        else if (conditionId.equals("23")) {
            //雷暴
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.thunderstorm));
        }
        else if (conditionId.equals("24")) {
            //雷阵雨伴有冰雹
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.thundershowerwithhail));
        }
        else if (conditionId.equals("25")) {
            //冰雹
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.hail));
        }
        else if (conditionId.equals("26")) {
            //冰针
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.iceneedle));
        }
        else if (conditionId.equals("27")) {
            //冰粒
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ice));
        }
        else if (conditionId.equals("28")) {
            //雨夹雪
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.sleet));
        }
        else if (conditionId.equals("29")) {
            //小雨
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lightrain));
        }
        else if (conditionId.equals("30")) {
            //中雨
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.moderaterain));
        }
        else if (conditionId.equals("31")) {
            //大雨
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavyrain));
        }
        else if (conditionId.equals("32")) {
            //暴雨
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.rainstorm));
        }
        else if (conditionId.equals("33")) {
            //特大暴雨
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavy));
        }
        else if (conditionId.equals("34")) {
            //小雪
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lightsnow));
        }
        else if (conditionId.equals("35")) {
            //中雪
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.modeatesnow));
        }
        else if (conditionId.equals("36")) {
            //大雪
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavysnow));
        }
        else if (conditionId.equals("37")) {
            //暴雪
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.snowstorm));
        }
        else if (conditionId.equals("38")) {
            //冻雨
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.freezingrain));
        }
        else if (conditionId.equals("39")) {
            //大暴雨
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavyrainstorm));
        }
        else if (conditionId.equals("40")) {
            //雪
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.snow));
        }
        else if (conditionId.equals("41")) {
            //雨
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.rain));
        }
        else if (conditionId.equals("42")) {
            //小到中雨
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lighttomoderaterain));
        }
        else if (conditionId.equals("43")) {
            //中到大雨
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.moderatetoheavyrain));
        }
        else if (conditionId.equals("44")) {
            //大到暴雨
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.heavytostormrain));
        }
        else if (conditionId.equals("45")) {
            //小到中雪
            mivIMG.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.lighttomoderatesnow));
        }
    }
}
