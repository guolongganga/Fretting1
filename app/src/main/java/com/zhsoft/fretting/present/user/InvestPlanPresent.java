package com.zhsoft.fretting.present.user;

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

    public void investPlanData(String token, String userId) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);
        ArrayList<InvestPlanResp> list = new ArrayList<>();
        InvestPlanResp resp1 = new InvestPlanResp("博时精选基金" + 1, "05000" + 1, "每周三定投11.00元",
                "招商银行 储蓄卡", "2339", "2017-12-27", "定投中");
        InvestPlanResp resp2 = new InvestPlanResp("博时精选基金" + 2, "05000" + 2, "每周三定投12.00元",
                "招商银行 储蓄卡", "2339", "2017-12-28", "暂停");
        InvestPlanResp resp3 = new InvestPlanResp("博时精选基金" + 3, "05000" + 3, "每周三定投13.00元",
                "招商银行 储蓄卡", "2339", "2017-12-29", "终止");
        list.add(resp1);
        list.add(resp2);
        list.add(resp3);
        if (true) {
            getV().requestDataSuccess(list);
        } else {
            getV().requestDataFail();
        }
    }

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
                        getV().showToast("请求失败");
                    }

                    @Override
                    public void onNext(InvestResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestInvestSuccess(resp.getData());
                        } else {
                            getV().requestInvestFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }
}
