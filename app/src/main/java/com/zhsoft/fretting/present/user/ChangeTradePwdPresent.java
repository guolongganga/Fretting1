package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.ChangeTradePwdParams;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.ChangeTradePwdActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

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
     * @param oldpwd
     * @param pwd
     * @param pwdnumbe
     */
    public void changePassword(String token, String userId, String oldpwd, String pwd, String pwdnumbe) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        ChangeTradePwdParams params = new ChangeTradePwdParams();
        params.setOldTradePassword(oldpwd);
        params.setTradePassword(pwd);
        params.setRepetTradePassword(pwdnumbe);
        reqData.setData(params);

        Api.getApi()
                .passwordChangeTrade(reqData)
                .compose(XApi.<BaseResp<String>>getApiTransformer())
                .compose(XApi.<BaseResp<String>>getScheduler())
                .compose(getV().<BaseResp<String>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp<String>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestFail();
//                        getV().showToast("变更登录密码请求失败");
                    }

                    @Override
                    public void onNext(BaseResp<String> resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestSuccess();
                        } else {
                            getV().requestFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });


    }
}
