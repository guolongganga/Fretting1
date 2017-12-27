package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.params.ChangeTradePwdParams;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.ChangeTradePwdActivity;

import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * 作者：sunnyzeng on 2017/12/27 16:36
 * 描述：更改交易密码
 */

public class ChangeTradePwdPresent extends XPresent<ChangeTradePwdActivity> {

    /**
     * 修改交易密码
     *
     * @param token
     * @param userId
     * @param pwdnumbe
     */
    public void changePassword(String token, String userId, String oldpwd, String pwdnumbe) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        ChangeTradePwdParams params = new ChangeTradePwdParams();
        params.setOldPassword(oldpwd);
        params.setPassword(pwdnumbe);
        reqData.setData(params);

        //模拟请求
        if (true) {
            getV().requestSuccess();
        } else {
            getV().requestFail();
        }

    }
}
