package com.zhsoft.fretting.net;

import com.zhsoft.fretting.App;

import cn.droidlover.xdroidmvp.cache.SharedPref;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by sunnyzeng on 2016/12/04.
 */

public class Api {

    public static String[] urls = new String[]{"appservice.buyforyou.com.cn","114.242.38.89:18443", "20.1.149.115:8443",
            "20.1.149.116:8443", "20.1.149.114:8443", "20.1.149.130:8443",
            "20.1.149.250:8443", "20.1.149.173:8443"};

    //    public static final String API_BASE_URL = "https://20.1.149.250:8443/";//测试服务器
//    public static final String API_BASE_URL = "https://114.242.38.89:18443/";//测试服务器外网
//    public static final String API_BASE_URL = "https://20.1.149.115:8443/";//经理
//    public static final String API_BASE_URL = "https://20.1.149.116:8443/";//陈
//    public static final String API_BASE_URL = "https://20.1.149.114:8443/";//苑

    public static String API_BASE_URL = "https://" + urls[App._urlindex] + "/";//聂 老竹


    private static HttpUtil httpUtil;

    public static HttpUtil getApi() {
        if (httpUtil == null) {
            synchronized (Api.class) {
                if (httpUtil == null) {
                    httpUtil = XApi.getInstance().getRetrofit(API_BASE_URL, true).create(HttpUtil.class);
                }
            }
        }
        return httpUtil;
    }

    /**
     * 重置指向服务器
     *
     * @param which
     */
    public static void resetUrl(int which) {
        App.getSharedPref().putInt("url", which);
        App._urlindex = which;
        API_BASE_URL = "https://" + urls[App._urlindex] + "/";//聂 老竹
        httpUtil = null;
    }
}
