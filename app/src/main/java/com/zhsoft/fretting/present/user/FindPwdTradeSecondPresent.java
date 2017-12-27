package com.zhsoft.fretting.present.user;

import android.util.Log;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.FindPwdSecondParams;
import com.zhsoft.fretting.ui.activity.user.FindPwdLoginSecondActivity;
import com.zhsoft.fretting.ui.activity.user.FindPwdTradeSecondActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2017/12/15 13:43
 * 描述：找回交易密码
 */

public class FindPwdTradeSecondPresent extends XPresent<FindPwdTradeSecondActivity> {

    /**
     * 找回交易密码 第二步
     *
     * @param password 密码
     */
    public void findPassword(String phone, String password, String repetPassword) {

        CommonReqData reqData = new CommonReqData();

        FindPwdSecondParams params = new FindPwdSecondParams();
        params.setPhone(phone);
        params.setPassword(password);
        params.setRepetPassword(repetPassword);
        reqData.setData(params);
        //TODO 换成找回交易密码接口
        Api.getApi()
                .findPasswordReset(reqData)
                .compose(XApi.<BaseResp>getApiTransformer())
                .compose(XApi.<BaseResp>getScheduler())
                .compose(getV().<BaseResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().requestFail();
                    }

                    @Override
                    public void onNext(BaseResp model) {
                        if (model != null && model.getStatus() == 200) {
                            Log.e("hahah", "访问成功");
                            getV().requestSuccess(model.getData());
                        } else {
                            getV().requestFail();
                            getV().showToast(model.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }
}
