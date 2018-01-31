package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.user.CancleOrderResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.CancleOrderActivity;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/1/25 13:22
 * 描述：
 */

public class CancleOrderPresent extends XPresent<CancleOrderActivity> {

    public void cancleOrderData(String token, String userId) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        Api.getApi().withdrawApplyList(reqData)
                .compose(XApi.<CancleOrderResp>getApiTransformer())
                .compose(XApi.<CancleOrderResp>getScheduler())
                .compose(getV().<CancleOrderResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<CancleOrderResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestOrderFail();
                        getV().showToast("请求失败");
                    }

                    @Override
                    public void onNext(CancleOrderResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestOrderSuccess(resp.getData());
                        } else {
                            getV().requestOrderFail();
                            getV().showToast(resp.getMessage());
                        }
                    }
                });


//        ArrayList<TransactionResp> list = new ArrayList<>();
//        TransactionResp resp1 = new TransactionResp("买入", "博时精选基金1",
//                "050001", "2017-12-11 17:59", "12.00", "确认成功");
//        TransactionResp resp2 = new TransactionResp("卖出", "博时精选基金2",
//                "050002", "2017-12-12 17:59", "1.00", "确认成功");
//        TransactionResp resp3 = new TransactionResp("买入", "博时精选基金3",
//                "050003", "2017-12-13 17:59", "5.00", "确认成功");
//        TransactionResp resp4 = new TransactionResp("买入", "博时精选基金4",
//                "050004", "2017-12-14 17:59", "15.00", "确认成功");
//        list.add(resp1);
//        list.add(resp2);
//        list.add(resp3);
//        list.add(resp4);
//        getV().showData(list);
    }
}
