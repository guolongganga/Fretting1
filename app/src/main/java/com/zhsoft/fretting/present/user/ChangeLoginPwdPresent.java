package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.ChangeLoginPwdParams;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.ChangeLoginPwdActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

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
     * @param password
     * @param repetPassword
     */
    public void changePassword(String token, String userId, String password, String repetPassword) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        ChangeLoginPwdParams params = new ChangeLoginPwdParams();
        params.setPassword(password);
        params.setRepetPassword(repetPassword);
        reqData.setData(params);


        Api.getApi()
                .passwordChangeLogin(reqData)
                .compose(XApi.<BaseResp>getApiTransformer())
                .compose(XApi.<BaseResp>getScheduler())
                .compose(getV().<BaseResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestFail();
                    }

                    @Override
                    public void onNext(BaseResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestSuccess();
                        } else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                            getV().areadyLogout();
                        }  else {
                            getV().requestFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });


    }
}
