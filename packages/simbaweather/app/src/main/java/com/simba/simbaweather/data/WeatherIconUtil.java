package com.simba.simbaweather.data;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.simba.simbaweather.R;

//通过天气类型来获取显示天气的图标
public class WeatherIconUtil {



    public static Drawable getWeatherIconByType(Context context, String conditionId){


        //进行天气的判断是晴天还是其他的一些天气
        if (conditionId.equals("1")) {
            //晴天
            return  context.getResources().getDrawable(R.mipmap.sunny);
        } else if (conditionId.equals("3")) {
            //大部晴朗
            return context.getResources().getDrawable(R.mipmap.mostlysunny);
        } else if (conditionId.equals("4")) {
            //多云
            return context.getResources().getDrawable(R.mipmap.cloudy);
        } else if (conditionId.equals("5")) {
            //少云
            return context.getResources().getDrawable(R.mipmap.lesscludy);
        } else if (conditionId.equals("6")) {
            //阴天
            return context.getResources().getDrawable(R.mipmap.overcast);
        } else if (conditionId.equals("7")) {
            //阵雨
            return context.getResources().getDrawable(R.mipmap.shower);
        } else if (conditionId.equals("8")) {
            //局部阵雨
            return context.getResources().getDrawable(R.mipmap.scatteredshower);
        } else if (conditionId.equals("9")) {
            //小阵雨
            return context.getResources().getDrawable(R.mipmap.lightshower);
        } else if (conditionId.equals("10")) {
            //强阵雨
            return context.getResources().getDrawable(R.mipmap.heavyshower);
        } else if (conditionId.equals("11")) {
            //阵雪
            return context.getResources().getDrawable(R.mipmap.snowshower);
        } else if (conditionId.equals("12")) {
            //小阵雪
            return context.getResources().getDrawable(R.mipmap.lightsnowshower);
        } else if (conditionId.equals("13")) {
            //雾
            return context.getResources().getDrawable(R.mipmap.fog);
        } else if (conditionId.equals("14")) {
            //冻雾
            return context.getResources().getDrawable(R.mipmap.freezingfog);
        } else if (conditionId.equals("15")) {
            //沙尘暴
            return context.getResources().getDrawable(R.mipmap.sandstorm);
        } else if (conditionId.equals("16")) {
            //浮尘
            return  context.getResources().getDrawable(R.mipmap.floatingdust);
        } else if (conditionId.equals("17")) {
            //尘卷风
            return  context.getResources().getDrawable(R.mipmap.dustdevil);
        }
        else if (conditionId.equals("18")) {
            //扬沙
            return context.getResources().getDrawable(R.mipmap.sanddust);
        }
        else if (conditionId.equals("19")) {
            //强沙尘暴
            return context.getResources().getDrawable(R.mipmap.strongsandstrong);
        }
        else if (conditionId.equals("20")) {
            //霾
            return context.getResources().getDrawable(R.mipmap.smog);
        }
        else if (conditionId.equals("21")) {
            //雷阵雨
            return context.getResources().getDrawable(R.mipmap.thundershower);
        }
        else if (conditionId.equals("22")) {
            //雷电
            return context.getResources().getDrawable(R.mipmap.thunder);
        }
        else if (conditionId.equals("23")) {
            //雷暴
            return context.getResources().getDrawable(R.mipmap.thunderstorm);
        }
        else if (conditionId.equals("24")) {
            //雷阵雨伴有冰雹
            return context.getResources().getDrawable(R.mipmap.thundershowerwithhail);
        }
        else if (conditionId.equals("25")) {
            //冰雹
            return context.getResources().getDrawable(R.mipmap.hail);
        }
        else if (conditionId.equals("26")) {
            //冰针
            return context.getResources().getDrawable(R.mipmap.iceneedle);
        }
        else if (conditionId.equals("27")) {
            //冰粒
            return context.getResources().getDrawable(R.mipmap.ice);
        }
        else if (conditionId.equals("28")) {
            //雨夹雪
            return context.getResources().getDrawable(R.mipmap.sleet);
        }
        else if (conditionId.equals("29")) {
            //小雨
            return context.getResources().getDrawable(R.mipmap.lightrain);
        }
        else if (conditionId.equals("30")) {
            //中雨
            return context.getResources().getDrawable(R.mipmap.moderaterain);
        }
        else if (conditionId.equals("31")) {
            //大雨
            return context.getResources().getDrawable(R.mipmap.heavyrain);
        }
        else if (conditionId.equals("32")) {
            //暴雨
            return context.getResources().getDrawable(R.mipmap.rainstorm);
        }
        else if (conditionId.equals("33")) {
            //特大暴雨
            return context.getResources().getDrawable(R.mipmap.heavy);
        }
        else if (conditionId.equals("34")) {
            //小雪
            return context.getResources().getDrawable(R.mipmap.lightsnow);
        }
        else if (conditionId.equals("35")) {
            //中雪
            return context.getResources().getDrawable(R.mipmap.modeatesnow);
        }
        else if (conditionId.equals("36")) {
            //大雪
            return context.getResources().getDrawable(R.mipmap.heavysnow);
        }
        else if (conditionId.equals("37")) {
            //暴雪
            return context.getResources().getDrawable(R.mipmap.snowstorm);
        }
        else if (conditionId.equals("38")) {
            //冻雨
            return context.getResources().getDrawable(R.mipmap.freezingrain);
        }
        else if (conditionId.equals("39")) {
            //大暴雨
            return context.getResources().getDrawable(R.mipmap.heavyrainstorm);
        }
        else if (conditionId.equals("40")) {
            //雪
            return context.getResources().getDrawable(R.mipmap.snow);
        }
        else if (conditionId.equals("41")) {
            //雨
            return context.getResources().getDrawable(R.mipmap.rain);
        }
        else if (conditionId.equals("42")) {
            //小到中雨
            return context.getResources().getDrawable(R.mipmap.lighttomoderaterain);
        }
        else if (conditionId.equals("43")) {
            //中到大雨
            return context.getResources().getDrawable(R.mipmap.moderatetoheavyrain);
        }
        else if (conditionId.equals("44")) {
            //大到暴雨
            return context.getResources().getDrawable(R.mipmap.heavytostormrain);
        }
        else if (conditionId.equals("45")) {
            //小到中雪
            return context.getResources().getDrawable(R.mipmap.lighttomoderatesnow);
        }
        return context.getResources().getDrawable(R.mipmap.sunny);
    }
}
