package com.zhsoft.fretting.present.fund;

import com.zhsoft.fretting.model.fund.BuyFundResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.BuyFundParams;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.fund.FundDetailWebActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/1/12 16:43
 * 描述：
 */

public class FundDetailPresent extends XPresent<FundDetailWebActivity> {

    public void buyFund(String token, String userId, String fund_code) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);
        BuyFundParams params = new BuyFundParams();
        params.setFund_code(fund_code);
        reqData.setData(params);

        Api.getApi().buyFund(reqData)
                .compose(XApi.<BuyFundResp>getApiTransformer())
                .compose(XApi.<BuyFundResp>getScheduler())
                .compose(getV().<BuyFundResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BuyFundResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestBuyFundFail();
                        getV().showToast("请求失败");
                    }

                    @Override
                    public void onNext(BuyFundResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestBuyFundSuccess(resp.getData());
                        } else {
                            getV().requestBuyFundFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }
}
