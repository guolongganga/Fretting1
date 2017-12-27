package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.params.ChangeLoginPwdParams;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.ChangeLoginPwdActivity;

import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * 作者：sunnyzeng on 2017/12/27 16:36
 * 描述：更改登录密码
 */

public class ChangeLoginPwdPresent extends XPresent<ChangeLoginPwdActivity> {

    /**
     * 修改登录密码
     *
     * @param token
     * @param userId
     * @param pwdnumbe
     */
    public void changePassword(String token, String userId, String pwdnumbe) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        ChangeLoginPwdParams params = new ChangeLoginPwdParams();
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
