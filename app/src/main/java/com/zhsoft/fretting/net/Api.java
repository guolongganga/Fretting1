package com.zhsoft.fretting.net;

import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by sunnyzeng on 2016/12/04.
 */

public class Api {
    //    public static final String API_BASE_URL = "https://192.168.0.107:8443/";//陈
        public static final String API_BASE_URL = "https://20.1.149.117:8443/";//苑
//        public static final String API_BASE_URL = "https://20.1.149.125:8443/";//聂
//    public static final String API_BASE_URL = "https://20.1.149.120:8443/";//蔡

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


}
