package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.TransactionResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.TransactionQueryParams;
import com.zhsoft.fretting.ui.fragment.user.TransactionContentFragment;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/1/24 16:08
 * 描述：
 */

public class TransactionContentPresent extends XPresent<TransactionContentFragment> {

    public void loadTransactionData(String token, String userId, final int pageno, int pageSize, String tabType) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        TransactionQueryParams params = new TransactionQueryParams();
        params.setPageNum(pageno);
        params.setPageSize(pageSize);
        params.setTransactionCategory(tabType);
        reqData.setData(params);

        Api.getApi()
                .tradeQueryData(reqData)
                .compose(XApi.<TransactionResp>getApiTransformer())
                .compose(XApi.<TransactionResp>getScheduler())
                .compose(getV().<TransactionResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<TransactionResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().showError();
                    }

                    @Override
                    public void onNext(TransactionResp model) {
                        if (model != null && model.getStatus() == 200) {
                            getV().showData(pageno, model.getData());
                        }  else if (model != null && model.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(model.getMessage());
                            getV().areadyLogout();
                        } else {
                            getV().showToast(model.getMessage());
                            getV().showError();
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }

    public void shareOutBonusTradeQuery(String token, String userId, final int pageno, int pageSize, String tabType) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        TransactionQueryParams params = new TransactionQueryParams();
        params.setPageNum(pageno);
        params.setPageSize(pageSize);
        params.setTransactionCategory(tabType);
        reqData.setData(params);

        Api.getApi()
                .shareOutBonusTradeQuery(reqData)
                .compose(XApi.<TransactionResp>getApiTransformer())
                .compose(XApi.<TransactionResp>getScheduler())
                .compose(getV().<TransactionResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<TransactionResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().showError();
                    }

                    @Override
                    public void onNext(TransactionResp model) {
                        if (model != null && model.getStatus() == 200) {
                            getV().showData(pageno, model.getData());
                        }  else if (model != null && model.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(model.getMessage());
                            getV().areadyLogout();
                        } else {
                            getV().showToast(model.getMessage());
                            getV().showError();
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }

}
