package com.zhsoft.fretting.present.user;

import android.util.Log;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.user.ImageResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.FindPwdFirstParams;
import com.zhsoft.fretting.ui.activity.user.FindPwdLoginFirstActivity;
import com.zhsoft.fretting.ui.activity.user.FindPwdTradeFirstActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2017/12/15 13:43
 * 描述：找回登录密码第一步请求
 */

public class FindPwdTradeFirstPresent extends XPresent<FindPwdTradeFirstActivity> {

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
                        getV().requestMessageFail();
                        getV().showToast("请求图片验证码失败，点击图片重试");
                    }

                    @Override
                    public void onNext(ImageResp imageResp) {
                        if (imageResp != null && imageResp.getStatus() == 200) {
                            if (imageResp.getData() != null) {
                                getV().getImageCode(imageResp.getData());
                            }

                        } else {
                            getV().requestMessageFail();
                            getV().showToast(imageResp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });


    }


}
