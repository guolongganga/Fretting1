package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.user.ResultDetailResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.ResultParams;
import com.zhsoft.fretting.ui.activity.user.ResultDetailTwoActivity;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/1/31 15:02
 * 描述：
 */

public class ResultDetailTwoPresent extends XPresent<ResultDetailTwoActivity> {

    /**
     * 交易状态
     *
     * @param allot_no
     * @param token
     * @param userId
     */
    public void succDetailData(String allot_no, String token, String userId) {
        final CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        ResultParams params = new ResultParams();
        params.setAllot_no(allot_no);
        reqData.setData(params);

        Api.getApi().withdrawApplySuccDetail(reqData)
                .compose(XApi.<ResultDetailResp>getApiTransformer())
                .compose(XApi.<ResultDetailResp>getScheduler())
                .compose(getV().<ResultDetailResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<ResultDetailResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestDetailFail();
                        getV().showToast("请求失败");
                    }

                    @Override
                    public void onNext(ResultDetailResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestDetailSuccess(resp.getData());
                        } else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                            getV().areadyLogout();
                        }  else {
                            getV().requestDetailFail();
                            getV().showToast(resp.getMessage());
                        }
                    }
                });
    }
}
