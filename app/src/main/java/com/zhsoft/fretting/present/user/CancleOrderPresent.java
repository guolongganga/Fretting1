package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.user.CancleOrderResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.CancleOrderActivity;

import java.util.ArrayList;

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

//        Api.getApi().withdrawApplyList(reqData)
//                .compose(XApi.<CancleOrderResp>getApiTransformer())
//                .compose(XApi.<CancleOrderResp>getScheduler())
//                .compose(getV().<CancleOrderResp>bindToLifecycle())
//                .subscribe(new ApiSubscriber<CancleOrderResp>() {
//                    @Override
//                    protected void onFail(NetError error) {
//                        getV().requestOrderFail();
//                        getV().showToast("请求失败");
//                    }
//
//                    @Override
//                    public void onNext(CancleOrderResp resp) {
//                        if (resp != null && resp.getStatus() == 200) {
//                            getV().requestOrderSuccess(resp.getData());
//                        } else {
//                            getV().requestOrderFail();
//                            getV().showToast(resp.getMessage());
//                        }
//                    }
//                });


        ArrayList<CancleOrderResp> list = new ArrayList<>();
        for (int i=0;i<5;i++ ){
            CancleOrderResp resp1 = new CancleOrderResp();
            resp1.setFund_name("博时精选基金"+i);
            resp1.setFund_code("05000"+i);
            list.add(resp1);
        }
        getV().requestOrderSuccess(list);
    }
}
