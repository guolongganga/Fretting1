package com.zhsoft.fretting.present.fund;

import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.fund.BuyFundResp;
import com.zhsoft.fretting.model.fund.BuyNowResp;
import com.zhsoft.fretting.model.fund.CalculationResp;
import com.zhsoft.fretting.model.fund.FundStatusResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.BuyCalculationParams;
import com.zhsoft.fretting.params.BuyFundParams;
import com.zhsoft.fretting.params.BuyNowParams;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.fund.BuyActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/1/12 14:35
 * 描述：
 */

public class BuyPresent extends XPresent<BuyActivity> {

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
                        } else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                            getV().areadyLogout();
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
    public void purchase(String token, String userId, String fund_code, String balance, String password, String auto_buy) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        BuyNowParams params = new BuyNowParams();
        params.setFund_code(fund_code);
        params.setBalance(balance);
        params.setPassword(password);
        params.setAuto_buy(auto_buy);

        reqData.setData(params);

        Api.getApi().purchase(reqData)
                .compose(XApi.<FundStatusResp>getApiTransformer())
                .compose(XApi.<FundStatusResp>getScheduler())
                .compose(getV().<FundStatusResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<FundStatusResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestBuyNowFail();
                        getV().showToast("请求失败1111111");
                    }

                    @Override
                    public void onNext(FundStatusResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestBuyNowSuccess(resp.getData());
                        } else if (resp != null && resp.getStatus() == Constant.PASSWORD_ERROR_STATUS) {
                            //密码错误状态码
                            getV().passwordError();
                        } else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                            getV().areadyLogout();
                        } else {
                            getV().requestBuyNowFail();
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
     */
    public void buyFundCalculation(String token, String userId, String fund_code, String apply_sum) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        BuyCalculationParams params = new BuyCalculationParams();
        params.setFund_code(fund_code);
        params.setApply_sum(apply_sum);

        reqData.setData(params);

        Api.getApi().buyFundCalculation(reqData)
                .compose(XApi.<CalculationResp>getApiTransformer())
                .compose(XApi.<CalculationResp>getScheduler())
                .compose(getV().<CalculationResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<CalculationResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestCalculationFail();
                        getV().showToast("请求失败1111111");
                    }

                    @Override
                    public void onNext(CalculationResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestCalculationSuccess(resp.getData());
                        } else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                            getV().areadyLogout();
                        } else {
                            getV().requestCalculationFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }

}
