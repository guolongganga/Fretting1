package com.zhsoft.fretting.net;

import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by wanglei on 2016/12/31.
 */

public class Api {

    public static final String API_BASE_URL = "https://10.0.8.15:8443/";

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
