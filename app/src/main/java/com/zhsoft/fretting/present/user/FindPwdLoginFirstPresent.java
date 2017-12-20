package com.zhsoft.fretting.present.user;

import android.util.Log;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.FindPwdFirstParams;
import com.zhsoft.fretting.ui.activity.user.FindPwdLoginFirstActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2017/12/15 13:43
 * 描述：找回登录密码第一步请求
 */

public class FindPwdLoginFirstPresent extends XPresent<FindPwdLoginFirstActivity> {

    /**
     * 找回密码
     *
     * @param phone        用户名
     * @param validateCode 密码
     *                     "phone":"15032269871","validateCode":"1234"
     */
    public void findCheckPassword(String phone, String validateCode) {

        CommonReqData reqData = new CommonReqData();

        FindPwdFirstParams params = new FindPwdFirstParams();
        params.setPhone(phone);
        params.setValidateCode(validateCode);
        reqData.setData(params);

        Api.getApi()
                .findPasswordCheck(reqData)
                .compose(XApi.<BaseResp>getApiTransformer())
                .compose(XApi.<BaseResp>getScheduler())
                .compose(getV().<BaseResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().requestFail();
                    }

                    @Override
                    public void onNext(BaseResp model) {
                        if (model != null && model.getStatus() == 200) {
                            Log.e("hahah", "访问成功");
                            getV().disposeUpdateResult(model.getData());
                        } else {
                            getV().requestFail();
                            getV().showToast(model.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }
}
