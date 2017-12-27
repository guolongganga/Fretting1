package com.zhsoft.fretting.params;

/**
 * 作者：sunnyzeng on 2017/12/27 16:49
 * 描述：修改交易密码参数
 */

public class ChangeTradePwdParams extends CommonReqData<ChangeTradePwdParams> {
    private String oldPassword;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
}
