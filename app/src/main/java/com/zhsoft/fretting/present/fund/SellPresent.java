package com.zhsoft.fretting.present.fund;

import com.zhsoft.fretting.model.fund.BuyFundResp;
import com.zhsoft.fretting.model.fund.BuyNowResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.BuyFundParams;
import com.zhsoft.fretting.params.BuyNowParams;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.fund.BuyActivity;
import com.zhsoft.fretting.ui.activity.fund.SellActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/1/12 14:35
 * 描述：
 */

public class SellPresent extends XPresent<SellActivity> {

    /***
     * 更换银行卡刷新购买验证接口，得到最新的银行卡信息
     * @param token
     * @param userId
     * @param fund_code
     */
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

    /**
     * @param token
     * @param userId
     * @param fund_code
     * @param balance
     * @param password
     */
    public void sellFund(String token, String userId, String fund_code, String balance, String password) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        BuyNowParams params = new BuyNowParams();
        params.setFund_code(fund_code);
        params.setBalance(balance);
        params.setPassword(password);
        reqData.setData(params);

//        Api.getApi().buyNow(reqData)
//                .compose(XApi.<BuyNowResp>getApiTransformer())
//                .compose(XApi.<BuyNowResp>getScheduler())
//                .compose(getV().<BuyNowResp>bindToLifecycle())
//                .subscribe(new ApiSubscriber<BuyNowResp>() {
//                    @Override
//                    protected void onFail(NetError error) {
//                        getV().requestSellFail();
//                        getV().showToast("请求失败1111111");
//                    }
//
//                    @Override
//                    public void onNext(BuyNowResp resp) {
//                        if (resp != null && resp.getStatus() == 200) {
//                            getV().requestSellSuccess(resp.getData());
//                        } else if (resp != null && resp.getStatus() == 526) {
//                            //密码错误状态码
//                            getV().passwordError();
//                        } else {
//                            getV().requestSellFail();
//                            getV().showToast(resp.getMessage());
//                            XLog.e("返回数据为空");
//                        }
//                    }
//                });
        getV().requestSellSuccess();

    }

}
