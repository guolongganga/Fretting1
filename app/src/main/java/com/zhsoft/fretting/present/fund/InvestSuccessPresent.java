package com.zhsoft.fretting.present.fund;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.fund.InvestResp;
import com.zhsoft.fretting.model.fund.InvestSureResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.InvestBuyParams;
import com.zhsoft.fretting.params.InvestSuccessParams;
import com.zhsoft.fretting.ui.activity.fund.InvestSuccessActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/1/17 10:12
 * 描述：
 */

public class InvestSuccessPresent extends XPresent<InvestSuccessActivity> {
    /**
     * 定投成功回显数据
     *
     * @param token
     * @param userId
     * @param fundCode
     * @param scheduled_protocol_id
     */
    public void investSuccessData(String token, String userId, String fundCode, String scheduled_protocol_id) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        InvestSuccessParams params = new InvestSuccessParams();
        params.setFundCode(fundCode);
        params.setScheduled_protocol_id(scheduled_protocol_id);

        reqData.setData(params);

        Api.getApi()
                .fundTimesSuccDetails(reqData)
                .compose(XApi.<BaseResp>getApiTransformer())
                .compose(XApi.<BaseResp>getScheduler())
                .compose(getV().<BaseResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        //请求失败
                        getV().requestFail();
                        getV().showToast("请求失败");
                    }

                    @Override
                    public void onNext(BaseResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            //请求成功 返回实体
                            getV().requestSuccess(resp.getData());
                        } else {
                            //请求失败
                            getV().requestFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }

                    }
                });
    }
}
