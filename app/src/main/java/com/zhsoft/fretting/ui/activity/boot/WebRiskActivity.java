package com.zhsoft.fretting.ui.activity.boot;

import android.os.Bundle;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.ui.activity.base.BaseWebActivity;

/**
 * 作者：sunnyzeng on 2018/2/1 14:13
 * 描述：带token和userID
 */

public class WebRiskActivity extends BaseWebActivity {
    /** 用户登录标识 */
    private String token = "";
    /** 用户编号 */
    private String userId = "";

    @Override
    protected void myLoadUrl(Bundle bundle) {
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        userId = App.getSharedPref().getString(Constant.USERID, "");
        //添加header
        String ua = mWeb.getSettings().getUserAgentString();
        mWeb.getSettings().setUserAgentString(ua.replace("appType", "Android"));

        link = link + "?token=" + token + "&userId=" + userId;
        mWeb.loadUrl(link);
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
