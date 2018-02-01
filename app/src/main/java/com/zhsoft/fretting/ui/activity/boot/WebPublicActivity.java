package com.zhsoft.fretting.ui.activity.boot;

import com.zhsoft.fretting.ui.activity.base.BaseWebActivity;

/**
 * 作者：sunnyzeng on 2018/2/1 14:13
 * 描述：不带token userid
 */

public class WebPublicActivity extends BaseWebActivity {
    @Override
    protected void myLoadUrl() {
        mWeb.loadUrl(link);
    }

    @Override
    public Object newP() {
        return null;
    }
}
