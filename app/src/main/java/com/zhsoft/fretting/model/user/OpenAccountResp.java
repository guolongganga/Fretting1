package com.zhsoft.fretting.model.user;

import com.zhsoft.fretting.model.BaseResp;

/**
 * 作者：sunnyzeng on 2017/12/26 11:23
 * 描述：
 */

public class OpenAccountResp extends BaseResp<OpenAccountResp> {
    private String isopenAccount;

    public String getIsopenAccount() {
        return isopenAccount;
    }

    public void setIsopenAccount(String isopenAccount) {
        this.isopenAccount = isopenAccount;
    }
}
