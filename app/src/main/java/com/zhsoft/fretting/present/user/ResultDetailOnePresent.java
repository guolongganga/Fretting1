package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.user.ResultDetailResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.ResultParams;
import com.zhsoft.fretting.ui.activity.user.ResultDetailOneActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/1/31 10:59
 * 描述：
 */

public class ResultDetailOnePresent extends XPresent<ResultDetailOneActivity> {

    /**
     * 撤单交易详情
     *
     * @param allot_no
     * @param token
     * @param userId
     */
    public void withdrawApplyDetail(String allot_no, String token, String userId) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        ResultParams params = new ResultParams();
        params.setAllot_no(allot_no);
        reqData.setData(params);

        Api.getApi().withdrawApplyDetail(reqData)
                .compose(XApi.<ResultDetailResp>getApiTransformer())
                .compose(XApi.<ResultDetailResp>getScheduler())
                .compose(getV().<ResultDetailResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<ResultDetailResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestDetailFail();
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(ResultDetailResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestDetailSuccess(resp.getData());
                        } else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                            getV().areadyLogout();
                        } else {
                            getV().requestDetailFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }

    /**
     * 撤单操作
     *
     * @param allot_no
     * @param token
     * @param userId
     */
    public void withdrawApplyOperate(String allot_no, String password, String token, String userId) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        ResultParams params = new ResultParams();
        params.setAllot_no(allot_no);
        params.setPassword(password);
        reqData.setData(params);

        Api.getApi().withdrawApplyOperate(reqData)
                .compose(XApi.<BaseResp>getApiTransformer())
                .compose(XApi.<BaseResp>getScheduler())
                .compose(getV().<BaseResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestCancleFail();
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(BaseResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestCancleSuccess();
                        } else if (resp != null && resp.getStatus() == Constant.PASSWORD_ERROR_STATUS) {
                            getV().passwordError(resp.getMessage());
                        } else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                            getV().areadyLogout();
                        } else {
                            getV().requestCancleFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }
}
