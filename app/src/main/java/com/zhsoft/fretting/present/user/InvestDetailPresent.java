package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.fund.InvestResp;
import com.zhsoft.fretting.model.user.InvestRecordResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.InvestParams;
import com.zhsoft.fretting.ui.activity.user.InvestDeatilActivity;

import java.util.ArrayList;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/1/22 17:03
 * 描述：
 */

public class InvestDetailPresent extends XPresent<InvestDeatilActivity> {
    public void investDetailData(String token, String userId) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        ArrayList<InvestRecordResp> list = new ArrayList<>();
        InvestRecordResp resp1 = new InvestRecordResp("2017-10-17", "星期一", "10.00", "定投成功");
        InvestRecordResp resp2 = new InvestRecordResp("2017-10-17", "星期一", "10.00", "确认成功");
        InvestRecordResp resp3 = new InvestRecordResp("2017-10-17", "星期一", "10.00", "撤单成功");
        InvestRecordResp resp4 = new InvestRecordResp("2017-10-28", "星期三", "20.00", "支付失败");
        InvestRecordResp resp5 = new InvestRecordResp("2017-10-28", "星期三", "20.00", "确认失败");
        list.add(resp1);
        list.add(resp2);
        list.add(resp3);
        list.add(resp4);
        list.add(resp5);

        if (true) {
            getV().requestInvestDetailSuccess(list);
        } else {
            getV().requestInvestDetailFail();
        }
    }

    /**
     * 修改定投
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

        //TODO 换修改接口
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
