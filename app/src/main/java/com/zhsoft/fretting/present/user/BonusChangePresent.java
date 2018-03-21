package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.user.UpdateBonusResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.BonusChangeParams;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.InvestParams;
import com.zhsoft.fretting.ui.activity.user.BonusChangeActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/3/21 16:04
 * 描述：
 */

public class BonusChangePresent extends XPresent<BonusChangeActivity> {
    public void loadBonusXgDeatilData(String fundCode, String autoBuy, String sharetype, String tradeacco, String password, String token, String userId) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        BonusChangeParams params = new BonusChangeParams();
        params.setFundCode(fundCode);
        params.setAutoBuy(autoBuy);
        params.setSharetype(sharetype);
        params.setTradeacco(tradeacco);
        params.setPassword(password);
        reqData.setData(params);

        Api.getApi()
                .bonusXgModifyBonus(reqData)
                .compose(XApi.<BaseResp>getApiTransformer())
                .compose(XApi.<BaseResp>getScheduler())
                .compose(getV().<BaseResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().showError();
                    }

                    @Override
                    public void onNext(BaseResp model) {
                        if (model != null && model.getStatus() == 200) {
                            getV().showData();
                        } else {
                            getV().showToast(model.getMessage());
                            getV().showError();
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }
}
