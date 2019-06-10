package cn.com.buyforyou.fund.present.user;

import android.view.View;

import cn.com.buyforyou.fund.R;
import cn.com.buyforyou.fund.model.LoginResp;
import cn.com.buyforyou.fund.model.user.BankAddResp;
import cn.com.buyforyou.fund.model.user.BankResp;
import cn.com.buyforyou.fund.model.user.UserMessageResp;
import cn.com.buyforyou.fund.net.Api;
import cn.com.buyforyou.fund.params.BankListParams;
import cn.com.buyforyou.fund.params.CommonReqData;
import cn.com.buyforyou.fund.params.TradePasswordParams;
import cn.com.buyforyou.fund.ui.activity.user.AddBankCardListActivity;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by guolonggang on 2018/12/10.
 * Description:
 * 展示银行卡列表以及添加银行卡
 */

public class AddbankCardPresent extends XPresent<AddBankCardListActivity> {
    //得到用户信息
    public void getUserMessage(String token,String userId)
    {
        CommonReqData reqData = new CommonReqData();

        reqData.setToken(token);
        reqData.setUserId(userId);


        Api.getApi()
                .getUserMessage(reqData)
                .compose(XApi.<UserMessageResp>getApiTransformer())
                .compose(XApi.<UserMessageResp>getScheduler())
                .compose(getV().<UserMessageResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<UserMessageResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().getUserMessageCodeFail();
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(UserMessageResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().getUserMessageCode(resp.getData());
                        } else {
                            getV().getUserMessageCodeFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });



    }


    /**
     * 展示银行卡列表接口
     */
    public  void addBankcardList(String token, String userId)
    {
        CommonReqData reqData = new CommonReqData();

        reqData.setToken(token);
        reqData.setUserId(userId);

        Api.getApi()
                .getMyBankCard(reqData)
                .compose(XApi.<BankAddResp>getApiTransformer())
                .compose(XApi.<BankAddResp>getScheduler())
                .compose(getV().<BankAddResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BankAddResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().bankCardAddFail();
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(BankAddResp model) {
                        if (model != null && model.getStatus() == 200) {
                            getV().addBankListDatas(model.getData());
                        } else {
                            getV().bankCardAddFail();
                            getV().showToast(model.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });




    }
}
