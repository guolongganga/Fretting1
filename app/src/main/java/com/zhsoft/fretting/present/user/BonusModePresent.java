package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.UpdateBonusResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.fragment.user.BonusModeFragment;

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
                        } else if (model != null && model.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(model.getMessage());
                            getV().areadyLogout();
                        } else {
                            getV().showToast(model.getMessage());
                            getV().showError();
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }
}
