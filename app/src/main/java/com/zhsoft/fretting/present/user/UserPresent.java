package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.UserAccountResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.LoginActivity;
import com.zhsoft.fretting.ui.fragment.user.UserFragment;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2017/12/6 13:02
 * 描述：
 */

public class UserPresent extends XPresent<UserFragment> {


    /**
     * 主页面 我的 - 数据
     *
     * @param token
     * @param userId
     */
    public void getFundHome(String token, String userId) {

        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);
        reqData.setData("");

        Api.getApi()
                .getFundHome(reqData)
                .compose(XApi.<UserAccountResp>getApiTransformer())
                .compose(XApi.<UserAccountResp>getScheduler())
                .compose(getV().<UserAccountResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<UserAccountResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().requestFundFail();
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(UserAccountResp model) {
                        if (model != null && model.getStatus() == 200) {
                            getV().showMyFund(model.getData());
                        } else if (model != null && model.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(model.getMessage());
                            getV().areadyLogout();
                        } else {
                            getV().showToast(model.getMessage());
                            getV().requestFundFail();
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }

}
