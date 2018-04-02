package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.LoginResp;
import com.zhsoft.fretting.model.user.ImageResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.PhoneCodeParams;
import com.zhsoft.fretting.params.RegisterFirstCheckPhoneParams;
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
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(LoginResp model) {
                        if (model != null && model.getStatus() == 200) {
                            getV().commitSuccess(model.getData());
                        } else {
                            getV().requestFail();
                            getV().showToast(model.getMessage());
//                            XLog.e("返回数据为空");
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
                        getV().getImageCodeFail();
                        getV().showToast(R.string.request_error);
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
                .compose(XApi.<BaseResp>getApiTransformer())
                .compose(XApi.<BaseResp>getScheduler())
                .compose(getV().<BaseResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestPhoneCodeFail();
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(BaseResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestPhoneCodeSuccess();
                        } else {
                            getV().requestPhoneCodeFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }

    public void checkPhoneExist(String text) {
        CommonReqData reqData = new CommonReqData();
        RegisterFirstCheckPhoneParams params = new RegisterFirstCheckPhoneParams();
        params.setPhoneNo(text);
        reqData.setData(params);
        Api.getApi().checkPhoneExist(reqData)
                .compose(XApi.<BaseResp>getApiTransformer())
                .compose(XApi.<BaseResp>getScheduler())
                .compose(getV().<BaseResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().checkPhoneExistFail(false);
                    }

                    @Override
                    public void onNext(BaseResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().checkPhoneExistSuccess();
                        } else {
                            getV().checkPhoneExistFail(true);
                            getV().showToast(resp.getMessage());

                        }
                    }
                });
    }
}
