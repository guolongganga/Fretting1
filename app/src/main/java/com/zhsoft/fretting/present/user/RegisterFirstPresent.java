package com.zhsoft.fretting.present.user;

import android.util.Log;

import com.zhsoft.fretting.model.BaseModel;
import com.zhsoft.fretting.model.LoginModel;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.LoginParams;
import com.zhsoft.fretting.params.RegisterFirstParams;
import com.zhsoft.fretting.ui.activity.user.RegisterFirstActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2017/12/15 15:43
 * 描述：
 */

public class RegisterFirstPresent extends XPresent<RegisterFirstActivity> {

    /**
     * 注册
     * @param mobile_tel 手机号码
     * @param password 密码
     */
    public void register(String mobile_tel, String password) {

        CommonReqData reqData = new CommonReqData();

        RegisterFirstParams params = new RegisterFirstParams();
        params.setMobile_tel(mobile_tel);
        params.setPassword(password);
        reqData.setData(params);

        Api.getApi()
                .register(reqData)
                .compose(XApi.<BaseModel>getApiTransformer())
                .compose(XApi.<BaseModel>getScheduler())
                .compose(getV().<BaseModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().requestFail();
                    }

                    @Override
                    public void onNext(BaseModel model) {
                        if (model != null && model.getStatus() == 200) {
                            Log.e("hahah", "访问成功");
                            getV().commitSuccess(model.getData());
                        } else {
                            getV().requestFail();
                            getV().showToast(model.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }
}
