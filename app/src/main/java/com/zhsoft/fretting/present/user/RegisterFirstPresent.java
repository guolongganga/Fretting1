package com.zhsoft.fretting.present.user;

import android.util.Log;

import com.zhsoft.fretting.model.LoginModel;
import com.zhsoft.fretting.model.user.ImageResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
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
     *
     * @param mobile_tel 手机号码
     * @param password   密码
     */
    public void register(String mobile_tel, String password, String imgCode, String image_code_id) {

        CommonReqData reqData = new CommonReqData();

        RegisterFirstParams params = new RegisterFirstParams();
        params.setMobile_tel(mobile_tel);
        params.setPassword(password);
        params.setImage_code(imgCode);
        params.setImage_code_id(image_code_id);
        reqData.setData(params);

        Api.getApi()
                .register(reqData)
                .compose(XApi.<LoginModel>getApiTransformer())
                .compose(XApi.<LoginModel>getScheduler())
                .compose(getV().<LoginModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<LoginModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().requestFail();
                        getV().showToast("注册失败");
                    }

                    @Override
                    public void onNext(LoginModel model) {
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

    /**
     * 获取图片验证码
     */
    public void getImageCode() {
        CommonReqData reqData = new CommonReqData();
        reqData.setData("");

        Api.getApi()
                .getImageCode(reqData)
                .compose(XApi.<ImageResp>getApiTransformer())
                .compose(XApi.<ImageResp>getScheduler())
                .compose(getV().<ImageResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<ImageResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().requestFail();
                        getV().showToast("请求图片验证码失败");
                    }

                    @Override
                    public void onNext(ImageResp imageResp) {
                        if (imageResp != null && imageResp.getStatus() == 200) {
                            if (imageResp.getData() != null) {
                                getV().getImageCode(imageResp.getData());
                            }

                        } else {
                            getV().requestFail();
                            getV().showToast(imageResp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });


    }

}
