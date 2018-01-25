package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.fund.NewestFundResp;
import com.zhsoft.fretting.model.user.SelfChooseResp;
import com.zhsoft.fretting.model.user.UserAccountResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.SelfChooseParams;
import com.zhsoft.fretting.ui.activity.user.SelfChooseActivity;

import java.math.BigDecimal;
import java.util.ArrayList;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/1/24 11:20
 * 描述：
 */

public class SelfChoosePresent extends XPresent<SelfChooseActivity> {
    /**
     * 自选基金
     *
     * @param sortJz
     * @param sortZdf
     * @param token
     * @param userId
     */
    public void selfChooseFund(String sortJz, String sortZdf, String token, String userId) {
//        ArrayList<NewestFundResp> list = new ArrayList();
//
//        NewestFundResp resp0 = new NewestFundResp();
//        resp0.setFund_code("05000" + 0);
//        resp0.setFund_name("招商安泰股票" + 0);
//        resp0.setFund_rose(new BigDecimal(5.36));
//        resp0.setIsOwn("1");
//        resp0.setNet_value(new BigDecimal(0.3948));
//        list.add(resp0);
//
//        for (int i = 1; i < 10; i++) {
//            NewestFundResp resp1 = new NewestFundResp();
//            resp1.setFund_code("05000" + i);
//            resp1.setFund_name("招商安泰股票" + i);
//            resp1.setFund_rose(new BigDecimal(5.36));
//            resp1.setIsOwn("0");
//            resp1.setNet_value(new BigDecimal(0.3948));
//            list.add(resp1);
//        }
//        if (pageno != 1) {
//            list = null;
//        }
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);
        SelfChooseParams params = new SelfChooseParams();
        params.setSortJz(sortJz);
        params.setSortZdf(sortZdf);
        reqData.setData(params);

        Api.getApi()
                .selfChooseFund(reqData)
                .compose(XApi.<SelfChooseResp>getApiTransformer())
                .compose(XApi.<SelfChooseResp>getScheduler())
                .compose(getV().<SelfChooseResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<SelfChooseResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().requestFundFail();
                    }

                    @Override
                    public void onNext(SelfChooseResp model) {
                        if (model != null && model.getStatus() == 200) {
                            getV().showData(model.getData());
                        } else {
                            getV().showToast(model.getMessage());
                            getV().requestFundFail();
                            XLog.e("返回数据为空");
                        }
                    }
                });


    }
}
