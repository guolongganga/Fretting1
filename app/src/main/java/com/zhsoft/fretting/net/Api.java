package com.zhsoft.fretting.net;

import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by sunnyzeng on 2016/12/04.
 */

public class Api {

    public static final String API_BASE_URL = "https://10.0.8.15:8443/";//苑
//    public static final String API_BASE_URL = "https://10.0.8.74:8443/";//聂
//    public static final String API_BASE_URL = "https://10.0.8.10:8443/";//陈

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
