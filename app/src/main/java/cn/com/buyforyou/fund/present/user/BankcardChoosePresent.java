package cn.com.buyforyou.fund.present.user;

import com.zhsoft.fretting.ui.widget.ChenJingET;

import cn.com.buyforyou.fund.R;
import cn.com.buyforyou.fund.model.BaseResp;
import cn.com.buyforyou.fund.model.user.BankResp;
import cn.com.buyforyou.fund.net.Api;
import cn.com.buyforyou.fund.params.CommonReqData;
import cn.com.buyforyou.fund.params.OpenAccountParams;
import cn.com.buyforyou.fund.params.SmsMessageParams;
import cn.com.buyforyou.fund.ui.activity.user.BankCardChooseActivity;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by guolonggang on 2018/12/12.
 * Description:
 * /**
 */


public class BankcardChoosePresent extends XPresent<BankCardChooseActivity> {

    /**
     private String userName;
     private String certNo;
     private String email;
     private BankResp selectBank;
     private String bankAccount;
     private String mobile;
     private String tradePassword;
     */


    public void bankCardChoose(String userId, String token, String UserName, String certNo,String email, BankResp selectBank,String mobile, String bankAccount,String tradePassword) {
        final CommonReqData reqData = new CommonReqData();
        reqData.setUserId(userId);
        reqData.setToken(token);

        OpenAccountParams params = new OpenAccountParams();
        params.setUserName(UserName);
        params.setCertNo(certNo);
        params.setEmail("");
        params.setSelectBank(selectBank);
        params.setBankAccount(bankAccount);
        params.setMobile(mobile);
        params.setTradePassword(tradePassword);
        reqData.setData(params);


        Api.getApi().addAccountCheck(reqData)
                .compose(XApi.<BaseResp>getApiTransformer())
                .compose(XApi.<BaseResp>getScheduler())
                .compose(getV().<BaseResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp>() {


                    @Override
                    protected void onFail(NetError error) {
                        getV().addAccountCheckFail(error.getMessage());
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(BaseResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().addAccountCheck();
                        }
                        else {
                             getV().addAccountCheckFail(resp.getMessage());
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }


}
