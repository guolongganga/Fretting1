package com.zhsoft.fretting.present.user;

import android.util.Log;

import com.zhsoft.fretting.model.BaseModel;
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
 * 描述：
 */

public class LoginPresent extends XPresent<LoginActivity> {
    public void login(String username, String password) {

        CommonReqData reqData = new CommonReqData();

        LoginParams loginParams = new LoginParams();
        loginParams.setUsername(username);
        loginParams.setPassword(password);
        reqData.setData(loginParams);

        Api.getApi()
                .login(reqData)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        Log.e("hahah", "fail");
                    }

                    @Override
                    public void onNext(BaseModel model) {
                        if (model != null && model.getStatus() == 200) {
                            Log.e("hahah", "访问成功");
                            getV().showData(model.getData());
                        } else {
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }

}
