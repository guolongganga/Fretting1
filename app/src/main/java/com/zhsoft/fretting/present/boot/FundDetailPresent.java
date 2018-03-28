package com.zhsoft.fretting.present.boot;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.fund.BuyFundResp;
import com.zhsoft.fretting.model.fund.InvestResp;
import com.zhsoft.fretting.model.fund.SellResp;
import com.zhsoft.fretting.model.user.InvestPlanResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.BuyFundParams;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.InvestParams;
import com.zhsoft.fretting.ui.activity.boot.FundDetailWebActivity;

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

    /**
     * 购买基金 验证 （是否具备购买资格）
     *
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
                        getV().showToast(R.string.request_error);
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
     * 基金定投 准备（是否具备定投资格）
     *
     * @param token
     * @param userId
     * @param fund_code
     * @param fund_name
     */
    public void investTime(String token, String userId, String fund_code, String fund_name) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);
        InvestParams params = new InvestParams();
        params.setFundCode(fund_code);
        params.setFund_name(fund_name);
        reqData.setData(params);

        Api.getApi().fundInvestTime(reqData)
                .compose(XApi.<InvestResp>getApiTransformer())
                .compose(XApi.<InvestResp>getScheduler())
                .compose(getV().<InvestResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<InvestResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestInvestFail();
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(InvestResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestInvestSuccess(resp.getData());
                        } else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                            getV().areadyLogout();
                        } else {
                            getV().requestInvestFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }

    /***
     * 赎回验证 （是否能够赎回）
     * @param token
     * @param userId
     * @param fund_code
     */
    public void sellFundPre(String token, String userId, String fund_code) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);
        BuyFundParams params = new BuyFundParams();
        params.setFund_code(fund_code);
        reqData.setData(params);

        Api.getApi().sellFundPre(reqData)
                .compose(XApi.<SellResp>getApiTransformer())
                .compose(XApi.<SellResp>getScheduler())
                .compose(getV().<SellResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<SellResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestSellPreFail();
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(SellResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestSellPreSuccess();
                        } else if (resp != null && resp.getStatus() == 529) {
                            getV().showToast("不可赎回");
                        } else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                            getV().areadyLogout();
                        } else {
                            getV().requestSellPreFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }

    /**
     * 定投计划 验证 （是否有定投计划）
     *
     * @param token
     * @param userId
     * @param fundCode
     */
    public void buyOnFundData(String token, String userId, String fundCode) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);
        InvestParams params = new InvestParams();
        params.setFundCode(fundCode);
        reqData.setData(params);

        Api.getApi().buyOnFundData(reqData)
                .compose(XApi.<InvestPlanResp>getApiTransformer())
                .compose(XApi.<InvestPlanResp>getScheduler())
                .compose(getV().<InvestPlanResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<InvestPlanResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestInvestPlanFail();
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(InvestPlanResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestInvestPlanSuccess(resp.getData());
                        } else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                            getV().areadyLogout();
                        } else {
                            getV().requestInvestPlanFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }
}
