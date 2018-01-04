package com.zhsoft.fretting.present.user;

import android.util.Log;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.user.ImageResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.FindPwdFirstParams;
import com.zhsoft.fretting.params.PhoneAndCodeParams;
import com.zhsoft.fretting.params.PhoneCodeParams;
import com.zhsoft.fretting.params.SendPhoneCodeParams;
import com.zhsoft.fretting.ui.activity.user.FindPwdLoginFirstActivity;
import com.zhsoft.fretting.ui.activity.user.PhoneChangeActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2017/12/15 13:43
 * 描述：找回登录密码第一步请求
 */

public class PhoneChangePresent extends XPresent<PhoneChangeActivity> {

    /**
     * 找回密码
     *
     * @param phone        用户名
     * @param validateCode 密码
     *                     "phone":"15032269871","phoneCode":"1234"
     */
    public void changePhone(String phone, String validateCode, String token, String userId) {

        CommonReqData reqData = new CommonReqData();
        reqData.setUserId(userId);
        reqData.setToken(token);

        //手机号验证码
        PhoneAndCodeParams params = new PhoneAndCodeParams();
        params.setPhoneNo(phone);
        params.setPhoneCode(validateCode);
        reqData.setData(params);

        Api.getApi()
                .changePhoneSave(reqData)
                .compose(XApi.<BaseResp<String>>getApiTransformer())
                .compose(XApi.<BaseResp<String>>getScheduler())
                .compose(getV().<BaseResp<String>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp<String>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestFail();
                        getV().showToast("更换银行卡请求失败");
                    }

                    @Override
                    public void onNext(BaseResp<String> resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().disposeChangeResult();
                        } else {
                            getV().requestFail();
                            getV().showToast(resp.getMessage());
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

        //


    }

    /**
     * 获取短信验证码 不需要图片验证码
     *
     * @param phone
     */
    public void getMessageCodeNoImage(String phone, String token, String userId) {
        final CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        SendPhoneCodeParams params = new SendPhoneCodeParams();
        params.setPhoneNo(phone);
        reqData.setData(params);

        Api.getApi()
                .changePhoneSendcode(reqData)
                .compose(XApi.<BaseResp<String>>getApiTransformer())
                .compose(XApi.<BaseResp<String>>getScheduler())
                .compose(getV().<BaseResp<String>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp<String>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestPhoneCodeNoImageFail();
                        getV().showToast("获取短信验证码请求失败");
                    }

                    @Override
                    public void onNext(BaseResp<String> resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestPhoneCodeNoImageSuccess(resp.getData());
                        } else {
                            getV().requestPhoneCodeNoImageFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }
}