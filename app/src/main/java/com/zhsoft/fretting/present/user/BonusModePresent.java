package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.user.MyBonusResp;
import com.zhsoft.fretting.model.user.TransactionResp;
import com.zhsoft.fretting.model.user.UpdateBonusResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.BuyNowParams;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.TransactionQueryParams;
import com.zhsoft.fretting.ui.fragment.user.BonusModeFragment;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/1/25 10:54
 * 描述：
 */

public class BonusModePresent extends XPresent<BonusModeFragment> {

    public void loadBonusTypeData(String token, String userId) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        reqData.setData("");

//        reqData.setToken("ad970168f83e484c8aba19ec2f033d71");
//        reqData.setUserId("37f3f3b6e87843d8a50ff119adfd0f15");
//
//        BuyNowParams params = new BuyNowParams();
//        params.setFund_code("3Q0103");
//
//        reqData.setData(params);

        Api.getApi()
                .bonusXgPage(reqData)
                .compose(XApi.<UpdateBonusResp>getApiTransformer())
                .compose(XApi.<UpdateBonusResp>getScheduler())
                .compose(getV().<UpdateBonusResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<UpdateBonusResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().showError();
                    }

                    @Override
                    public void onNext(UpdateBonusResp model) {
                        if (model != null && model.getStatus() == 200) {
                            getV().showData(model.getData());
                        } else {
                            getV().showToast(model.getMessage());
                            getV().showError();
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }
}
