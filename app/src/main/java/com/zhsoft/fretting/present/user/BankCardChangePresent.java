package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.user.BankResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.ChangeBankCardParams;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.SendPhoneCodeParams;
import com.zhsoft.fretting.ui.activity.user.BankCardChangeActivity;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2017/12/27 13:13
 * 描述：更换银行卡
 */

public class BankCardChangePresent extends XPresent<BankCardChangeActivity> {

    /**
     * 银行预留手机号验证
     *
     * @param phone
     */
    public void getMessageCode(String phone, String token, String userId) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        SendPhoneCodeParams params = new SendPhoneCodeParams();
        params.setPhoneNo(phone);
        reqData.setData(params);

        Api.getApi().sendPhoneCode(reqData)
                .compose(XApi.<BaseResp>getApiTransformer())
                .compose(XApi.<BaseResp>getScheduler())
                .compose(getV().<BaseResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestMessageCodeFail();
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(BaseResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestMessageCodeSuccess();
                        } else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                            getV().areadyLogout();
                        }  else {
                            getV().requestMessageCodeFail();
                            getV().showToast(resp.getMessage());
                        }
                    }
                });


    }


    /**
     * 更换银行卡
     *
     * @param bankResp    选择的银行
     * @param bankNumber  银行卡号
     * @param phoneNumber 银行预留手机号
     * @param msgCode     短信验证码
     */
    public void changeBankCard(String token, String userId, BankResp bankResp, String bankNumber, String phoneNumber, String msgCode,String trade_password) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        ChangeBankCardParams params = new ChangeBankCardParams();
        params.setSelectBank(bankResp);
        params.setBankAccout(bankNumber);
        params.setMobile(phoneNumber);
        params.setPhoneCode(msgCode);
        params.setTrade_password(trade_password);

        reqData.setData(params);

        Api.getApi().changeBankCard(reqData)
                .compose(XApi.<BaseResp>getApiTransformer())
                .compose(XApi.<BaseResp>getScheduler())
                .compose(getV().<BaseResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestChangeFail();
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(BaseResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestChangeSuccess();
                        } else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                            getV().areadyLogout();
                        }  else {
                            getV().requestChangeFail();
                            getV().showToast(resp.getMessage());
                        }
                    }
                });
    }
}
