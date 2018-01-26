package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.user.InvestPlanResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.MyInvestParams;
import com.zhsoft.fretting.params.NewestFundParams;
import com.zhsoft.fretting.ui.activity.user.MyInvestActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/1/22 13:45
 * 描述：
 */

public class MyInvestPresent extends XPresent<MyInvestActivity> {

    public void myInvestData(final int pageno, int pageSize, String token, String userId, String fundCode, String dtStatus, String isFirst) {


//        ArrayList<InvestPlanResp> list = new ArrayList<>();
//        InvestPlanResp resp1 = new InvestPlanResp("博时精选基金" + 1, "05000" + 1, "每周三定投11.00元",
//                "招商银行 储蓄卡", "2339", "2017-12-27", "定投中");
//        InvestPlanResp resp2 = new InvestPlanResp("博时精选基金" + 2, "05000" + 2, "每周三定投12.00元",
//                "招商银行 储蓄卡", "2339", "2017-12-28", "暂停");
//        InvestPlanResp resp3 = new InvestPlanResp("博时精选基金" + 3, "05000" + 3, "每周三定投13.00元",
//                "招商银行 储蓄卡", "2339", "2017-12-29", "终止");
//        list.add(resp1);
//        list.add(resp2);
//        list.add(resp3);
//        if (true) {
//            getV().requestDataSuccess(pageno,list);
//        } else {
//            getV().requestDataFail();
//        }
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        MyInvestParams params = new MyInvestParams();
        params.setPageNum(pageno);
        params.setPageSize(pageSize);
        params.setFundCode(fundCode);
        params.setDtStatus(dtStatus);
        params.setIsFirst(isFirst);
        reqData.setData(params);

        Api.getApi()
                .myTimesBuyIndex(reqData)
                .compose(XApi.<InvestPlanResp>getApiTransformer())
                .compose(XApi.<InvestPlanResp>getScheduler())
                .compose(getV().<InvestPlanResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<InvestPlanResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().requestDataFail();
                    }

                    @Override
                    public void onNext(InvestPlanResp model) {
                        if (model != null && model.getStatus() == 200) {
                            getV().requestDataSuccess(pageno, model.getData());
                        } else {
                            getV().showToast(model.getMessage());
                            getV().requestDataFail();
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }

}
