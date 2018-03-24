package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.constant.Constant;
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

    public void myInvestData(final int pageno, int pageSize, String token, String userId, String fundCode, String dtStatus, final String isFirst) {

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
                            boolean first = false;
                            if (isFirst != null && "1".equals(isFirst)) {
                                first = true;
                            }
                            getV().requestDataSuccess(pageno, model.getData(), first);
                        } else if (model != null && model.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(model.getMessage());
                            getV().areadyLogout();
                        }  else {
                            getV().showToast(model.getMessage());
                            getV().requestDataFail();
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }


}
