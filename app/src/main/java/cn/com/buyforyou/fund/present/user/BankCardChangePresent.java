package cn.com.buyforyou.fund.present.user;

import android.app.ProgressDialog;

import cn.com.buyforyou.fund.R;
import cn.com.buyforyou.fund.constant.Constant;
import cn.com.buyforyou.fund.model.BaseResp;
import cn.com.buyforyou.fund.model.user.BankResp;
import cn.com.buyforyou.fund.model.user.OpenAccountResp;
import cn.com.buyforyou.fund.net.Api;
import cn.com.buyforyou.fund.params.ChangeBankCardParams;
import cn.com.buyforyou.fund.params.CommonReqData;
import cn.com.buyforyou.fund.params.SendPhoneCodeParams;
import cn.com.buyforyou.fund.ui.activity.user.BankCardChangeActivity;

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
     *   //(String phone,String bankAccout,String phoneCode,
     *   BankResp selectBank,String originalAppno,String otherSerial, String token, String userId)
     * @param phone
     */
    public void getMessageCode(String phone, String bankAccout,String phoneCode,
        BankResp selectBank,String originalAppno,String otherSerial,String token, String userId) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        SendPhoneCodeParams params = new SendPhoneCodeParams();
        params.setMobile(phone);
        params.setBankAccout(bankAccout);
        params.setPhoneCode(phoneCode);
        params.setSelectBank(selectBank);
        params.setOriginalAppno(originalAppno);
        params.setOtherSerial(otherSerial);
        reqData.setData(params);

        Api.getApi().sendPhoneCode(reqData)
                .compose(XApi.<OpenAccountResp>getApiTransformer())
                .compose(XApi.<OpenAccountResp>getScheduler())
                .compose(getV().<OpenAccountResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<OpenAccountResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestMessageCodeFail();
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(OpenAccountResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestMessageCodeSuccess(resp.getData());
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
    public void changeBankCard(String token, String userId, BankResp bankResp, String bankNumber, String phoneNumber, String msgCode,String trade_password,String originalAppno,String otherSerial) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        ChangeBankCardParams params = new ChangeBankCardParams();
        params.setSelectBank(bankResp);
        params.setBankAccout(bankNumber);
        params.setMobile(phoneNumber);
        params.setPhoneCode(msgCode);
        params.setTrade_password(trade_password);
        params.setOtherSerial(otherSerial);
        params.setOriginalAppno(originalAppno);


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
