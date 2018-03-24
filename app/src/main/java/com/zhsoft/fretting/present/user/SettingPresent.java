package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.user.RiskInfoResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.SettingActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/1/3 17:16
 * 描述：
 */

public class SettingPresent extends XPresent<SettingActivity> {

    /**
     * 风险等级或是否做了风险测评
     *
     * @param token
     * @param userId
     */
    public void riskGrade(String token, String userId) {
        CommonReqData reqData = new CommonReqData();
        reqData.setUserId(userId);
        reqData.setToken(token);

        Api.getApi()
                .setupIndex(reqData)
                .compose(XApi.<RiskInfoResp>getApiTransformer())
                .compose(XApi.<RiskInfoResp>getScheduler())
                .compose(getV().<RiskInfoResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<RiskInfoResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestRiskFail();
                    }

                    @Override
                    public void onNext(RiskInfoResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestRiskSuccess(resp.getData());
                        } else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                            getV().areadyLogout();
                        } else {
                            getV().requestRiskFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }

}
