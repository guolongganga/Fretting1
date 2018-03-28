package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.fund.InvestResp;
import com.zhsoft.fretting.model.user.InvestPlanResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.InvestParams;
import com.zhsoft.fretting.ui.activity.user.InvestPlanActivity;

import java.util.ArrayList;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/1/22 13:45
 * 描述：
 */

public class InvestPlanPresent extends XPresent<InvestPlanActivity> {

    /**
     * 定投计划
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

    /**
     * 基金定投
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
}
