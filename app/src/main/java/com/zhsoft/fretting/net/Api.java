package com.zhsoft.fretting.net;

import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by sunnyzeng on 2016/12/04.
 */

public class Api {
//    public static final String API_BASE_URL = "https://20.1.149.250:8443/";//测试服务器
    public static final String API_BASE_URL = "https://114.242.38.89:18443/";//测试服务器外网
//    public static final String API_BASE_URL = "https://20.1.149.115:8443/";//经理
//    public static final String API_BASE_URL = "https://20.1.149.116:8443/";//陈
//    public static final String API_BASE_URL = "https://20.1.149.117:8443/";//苑
//        public static final String API_BASE_URL = "https://20.1.149.125:8443/";//聂

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
