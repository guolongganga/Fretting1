package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.user.RiskInfoResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.SettingActivity;

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

                    }

                    @Override
                    public void onNext(RiskInfoResp resp) {
                        getV().requestRiskTestSuccess(resp.getData());
                    }
                });

    }

}
