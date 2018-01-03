package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.user.BankCardResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.BankCardActivity;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2017/12/22 15:48
 * 描述：
 */

public class BankCardPresent extends XPresent<BankCardActivity> {
    /**
     * 获取银行卡信息
     *
     * @param token
     * @param userId
     */
    public void getBankCardInfo(String token, String userId) {
        CommonReqData reqData = new CommonReqData();

        reqData.setToken(token);
        reqData.setUserId(userId);

        //我的银行卡伪数据
//        BankCardResp resp = new BankCardResp("https://timgsa.baidu.com/timg?image&quality=80&size=b10000_10000&sec=1513929817&di=14747966c80ca373dc7666fe07f3817e&src=http://hiphotos.baidu.com/exp/pic/item/b8405490f603738d6716214ab11bb051f819ec3f.jpg",
//                "工商银行", "2588", "25785584559", "1");
        //如果成功
        Api.getApi()
                .getMyBankCard(reqData)
                .compose(XApi.<BankCardResp>getApiTransformer())
                .compose(XApi.<BankCardResp>getScheduler())
                .compose(getV().<BankCardResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BankCardResp>() {
                    @Override
                    public void onNext(BankCardResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().showBankCardData(resp.getData());
                        } else {
                            getV().requestFail();
                            getV().showToast(resp.getMessage());
                        }
                    }

                    @Override
                    protected void onFail(NetError error) {
                        getV().requestFail();
                        getV().showToast("更换银行卡请求失败");
                    }
                });


//        if (true) {
//            getV().showData(resp);
//        } else {
//            getV().requestFail();
//        }

    }

    /**
     * 点击更换银行卡 检查是否可以更换银行卡
     *
     * @param token
     * @param userId
     */
    public void changeBankCardCheck(String token, String userId) {
        CommonReqData reqData = new CommonReqData();

        reqData.setToken(token);
        reqData.setUserId(userId);

        Api.getApi().changeBankCardCheck(reqData)
                .compose(XApi.<BankCardResp>getApiTransformer())
                .compose(XApi.<BankCardResp>getScheduler())
                .compose(getV().<BankCardResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BankCardResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestFail();
                        getV().showToast("请求失败");
                    }

                    @Override
                    public void onNext(BankCardResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().isCanChange(resp.getData());
                        } else {
                            getV().requestFail();
                            getV().showToast(resp.getMessage());
                        }
                    }
                });
    }

}
