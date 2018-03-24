package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.user.CancleOrderResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.ResultParams;
import com.zhsoft.fretting.ui.activity.user.CancleOrderActivity;

import java.util.ArrayList;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/1/25 13:22
 * 描述：
 */

public class CancleOrderPresent extends XPresent<CancleOrderActivity> {

    public void cancleOrderListData(String token, String userId) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        Api.getApi().withdrawApplyList(reqData)
                .compose(XApi.<CancleOrderResp>getApiTransformer())
                .compose(XApi.<CancleOrderResp>getScheduler())
                .compose(getV().<CancleOrderResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<CancleOrderResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestOrderFail();
                        getV().showToast("请求失败");
                    }

                    @Override
                    public void onNext(CancleOrderResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestOrderSuccess(resp.getData());
                        } else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                            getV().areadyLogout();
                        }  else {
                            getV().requestOrderFail();
                            getV().showToast(resp.getMessage());
                        }
                    }
                });
    }

    /**
     * @param allot_no
     * @param token
     * @param userId
     */
    public void withdrawApplyOperate(String allot_no, String password, String fund_code, String token, String userId) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        ResultParams params = new ResultParams();
        params.setAllot_no(allot_no);
        params.setPassword(password);
        params.setBusin_board_type("");
        params.setComb_requestno("");
        params.setFund_code(fund_code);
        reqData.setData(params);

        Api.getApi().withdrawApplyOperate(reqData)
                .compose(XApi.<BaseResp>getApiTransformer())
                .compose(XApi.<BaseResp>getScheduler())
                .compose(getV().<BaseResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestCancleFail();
                        getV().showToast("请求失败");
                    }

                    @Override
                    public void onNext(BaseResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestCancleSuccess();
                        } else if (resp != null && resp.getStatus() == Constant.PASSWORD_ERROR_STATUS) {
                            getV().passwordError();
                        } else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                            getV().areadyLogout();
                        }   else {
                            getV().requestCancleFail();
                            getV().showToast(resp.getMessage());
                        }
                    }
                });
    }
}
