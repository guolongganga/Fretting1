package com.zhsoft.fretting.present.user;

import android.util.Log;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.LoginResp;
import com.zhsoft.fretting.model.user.ImageResp;
import com.zhsoft.fretting.model.user.PhoneCodeResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.PhoneCodeParams;
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
    public void register(String mobile_tel, String password, String code) {

        CommonReqData reqData = new CommonReqData();

        RegisterFirstParams params = new RegisterFirstParams();
        params.setMobile_tel(mobile_tel);
        params.setPassword(password);
        params.setPhone_code(code);
        reqData.setData(params);

        Api.getApi()
                .register(reqData)
                .compose(XApi.<LoginResp>getApiTransformer())
                .compose(XApi.<LoginResp>getScheduler())
                .compose(getV().<LoginResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<LoginResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().requestFail();
                        getV().showToast("注册失败");
                    }

                    @Override
                    public void onNext(LoginResp model) {
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
        //假装成功了
//        ImageResp data = new ImageResp();
//        getV().getImageCodeSuccess(data);
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
                        getV().getImageCodeFail();
                    }

                    @Override
                    public void onNext(ImageResp imageResp) {
                        if (imageResp != null && imageResp.getStatus() == 200) {
                            if (imageResp.getData() != null) {
                                getV().getImageCodeSuccess(imageResp.getData());
                            }
                        } else {
                            getV().getImageCodeFail();
                            getV().showToast(imageResp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }

    /**
     * 获取短信验证码
     *
     * @param phone
     */
    public void getMessageCode(String phone, String imgCode, String image_code_id) {
        final CommonReqData reqData = new CommonReqData();
        PhoneCodeParams params = new PhoneCodeParams();
        params.setPhoneNo(phone);
        params.setImage_code(imgCode);
        params.setImage_code_id(image_code_id);
        reqData.setData(params);

        Api.getApi().getPhoneCode(reqData)
                .compose(XApi.<BaseResp<String>>getApiTransformer())
                .compose(XApi.<BaseResp<String>>getScheduler())
                .compose(getV().<BaseResp<String>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp<String>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestPhoneCodeFail();
                    }

                    @Override
                    public void onNext(BaseResp<String> resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestPhoneCodeSuccess(resp.getData());
                        } else {
                            getV().requestPhoneCodeFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }
}
