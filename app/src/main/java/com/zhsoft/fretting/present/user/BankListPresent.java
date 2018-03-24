package com.zhsoft.fretting.present.user;

import android.util.Log;

import com.zhsoft.fretting.model.user.BankResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.BankListParams;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.BankListActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2017/12/22 17:34
 * 描述：银行列表
 */

public class BankListPresent extends XPresent<BankListActivity> {

    /**
     * 银行列表
     */
    public void getBankList() {

        CommonReqData reqData = new CommonReqData();

        BankListParams params = new BankListParams();
        params.setPartner_id("");
        reqData.setData(params);

        Api.getApi()
                .bankList(reqData)
                .compose(XApi.<BankResp>getApiTransformer())
                .compose(XApi.<BankResp>getScheduler())
                .compose(getV().<BankResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BankResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().requestFail();
                    }

                    @Override
                    public void onNext(BankResp model) {
                        if (model != null && model.getStatus() == 200) {
                            getV().bankListData(model.getData());
                        } else {
                            getV().requestFail();
                            getV().showToast(model.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }
}
