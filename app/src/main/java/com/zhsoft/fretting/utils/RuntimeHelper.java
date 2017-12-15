package com.zhsoft.fretting.utils;

/**
 * 单例存储唯一变量
 * Created by ${Yis}
 * data: 2017/12/5
 */

public class RuntimeHelper {

    private static RuntimeHelper mInstance;

    private boolean isLogin;

    private String url;

    public static synchronized RuntimeHelper getInstance() {
        if (mInstance == null) {
            mInstance = new RuntimeHelper();
        }
        return mInstance;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static RuntimeHelper getmInstance() {
        return mInstance;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }
}
