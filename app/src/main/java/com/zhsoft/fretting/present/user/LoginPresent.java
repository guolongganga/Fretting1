package com.zhsoft.fretting.present.user;

import android.util.Log;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.LoginResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.LoginParams;
import com.zhsoft.fretting.ui.activity.user.LoginActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2017/12/14 18:08
 * 描述：登录Present
 */

public class LoginPresent extends XPresent<LoginActivity> {

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     */
    public void login(String username, String password) {

        CommonReqData reqData = new CommonReqData();

        LoginParams loginParams = new LoginParams();
        loginParams.setUsername(username);
        loginParams.setPassword(password);
        reqData.setData(loginParams);

        Api.getApi()
                .login(reqData)
                .compose(XApi.<LoginResp>getApiTransformer())
                .compose(XApi.<LoginResp>getScheduler())
                .compose(getV().<LoginResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<LoginResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().loginFail();
                        getV().showToast("登录失败");
                    }

                    @Override
                    public void onNext(LoginResp model) {
                        if (model != null && model.getStatus() == 200) {
                            Log.e("hahah", "访问成功");
                            getV().showData(model.getData());
                        } else {
                            getV().loginFail();
                            getV().showToast(model.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }

}
