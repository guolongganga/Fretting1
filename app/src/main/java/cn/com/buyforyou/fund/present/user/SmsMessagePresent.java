package cn.com.buyforyou.fund.present.user;

import cn.com.buyforyou.fund.R;
import cn.com.buyforyou.fund.constant.Constant;
import cn.com.buyforyou.fund.model.BaseResp;
import cn.com.buyforyou.fund.model.user.BankResp;
import cn.com.buyforyou.fund.model.user.OpenAccountResp;
import cn.com.buyforyou.fund.net.Api;
import cn.com.buyforyou.fund.params.CommonReqData;
import cn.com.buyforyou.fund.params.SendPhoneCodeParams;
import cn.com.buyforyou.fund.params.SmsMessageParams;
import cn.com.buyforyou.fund.ui.activity.user.SmsVerificationActivity;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by guolonggang on 2018/12/10.
 * Description:
 */

public class SmsMessagePresent extends XPresent<SmsVerificationActivity> {
    /**
     * userId,token,strUsername, strIdentity, email, bankResp, strBankNumber, strPhone, strPwd
     */
    public void openAccountGetSms(String userId, String token, String strUsername, String strIdentity, String email, BankResp selectBank,String strBankNumber,String strPhone,String strPwd)
    {
        final CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);
        SmsMessageParams params=new SmsMessageParams();
        params.setUserName(strUsername);
        params.setCertNo(strIdentity);
        params.setEmail(email);
        params.setSelectBank(selectBank);
        params.setBankAccount(strBankNumber);
        params.setMobile(strPhone);
        params.setTradePassword(strPwd);

        reqData.setData(params);

        Api.getApi().openAccountgetSms(reqData)
                .compose(XApi.<OpenAccountResp>getApiTransformer())
                .compose(XApi.<OpenAccountResp>getScheduler())
                .compose(getV().<OpenAccountResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<OpenAccountResp>() {


                    @Override
                    protected void onFail(NetError error) {
                        getV().RequestCodeSmsFail();
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(OpenAccountResp resp) {
                        if (resp != null && resp.getStatus() == 200) {

                            getV().requestGetMessageCode(resp.getData());
                        }
                        else {
                           // getV().requestOpenAccountFail(resp.getMessage());
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }

    /**
     *  getP().openAccountNew(code,otherSerial, originalAppno, userId,
     *  token,strUsername, strIdentity, email, bankResp, strBankNumber, strPhone, strPwd);
     */
    public  void  openAccountNew(String code,String otherSerial,String originalAppno,String userId,String token,String strUsername,String strIdentity,String email,BankResp selectBank,String strBankNumber,String strPhone,String strPwd)
    {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);
        SmsMessageParams params=new SmsMessageParams();
        params.setMobileAuthcode(code);
        params.setOtherSerial(otherSerial);
        params.setOriginalAppno(originalAppno);
        params.setTradePassword(strPwd);
        params.setMobile(strPhone);
        params.setBankAccount(strBankNumber);
        params.setSelectBank(selectBank);
        params.setEmail(email);
        params.setUserName(strUsername);
        params.setCertNo(strIdentity);

        reqData.setData(params);

        Api.getApi().openAccountNew(reqData)
                .compose(XApi.<BaseResp>getApiTransformer())
                .compose(XApi.<BaseResp>getScheduler())
                .compose(getV().<BaseResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp>() {


                    @Override
                    protected void onFail(NetError error) {
                        getV().openAccountNewError(error.getMessage());
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(BaseResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().openAccountNew();
                        }
                        else {
                            // getV().requestOpenAccountFail(resp.getMessage());
                           getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });



    }
}
