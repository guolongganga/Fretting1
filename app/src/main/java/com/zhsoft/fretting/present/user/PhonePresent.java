package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.PhoneResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.PhoneActivity;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2017/12/28 11:02
 * 描述：
 */

public class PhonePresent extends XPresent<PhoneActivity> {
    public void getPhoneInfo(String token, String userId) {

        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        Api.getApi()
                .changePhoneIndex(reqData)
                .compose(XApi.<PhoneResp>getApiTransformer())
                .compose(XApi.<PhoneResp>getScheduler())
                .compose(getV().<PhoneResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<PhoneResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestFail();
                        getV().showToast("请求我的手机号码失败");
                    }

                    @Override
                    public void onNext(PhoneResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestSuccess(resp.getData());
                        }  else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                            getV().areadyLogout();
                        } else {
                            getV().requestFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }
}
