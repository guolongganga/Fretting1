package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.user.MyBonusResp;
import com.zhsoft.fretting.model.user.TransactionResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.TransactionQueryParams;
import com.zhsoft.fretting.ui.fragment.user.MyBonusFragment;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/3/20 20:10
 * 描述：
 */

public class MyBonusPresent extends XPresent<MyBonusFragment> {
    public void loadMyBonusData(String token, String userId, final int pageno, int pageSize) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        TransactionQueryParams params = new TransactionQueryParams();
        params.setPageNum(pageno);
        params.setPageSize(pageSize);
        reqData.setData(params);

        Api.getApi()
                .bonusHisPage(reqData)
                .compose(XApi.<MyBonusResp>getApiTransformer())
                .compose(XApi.<MyBonusResp>getScheduler())
                .compose(getV().<MyBonusResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<MyBonusResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().showError();
                    }

                    @Override
                    public void onNext(MyBonusResp model) {
                        if (model != null && model.getStatus() == 200) {
                            getV().showData(pageno, model.getData());
                        } else {
                            getV().showToast(model.getMessage());
                            getV().showError();
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }
}
