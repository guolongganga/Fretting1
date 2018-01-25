package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.user.UserAccountResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.fragment.user.UserFragment;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2017/12/6 13:02
 * 描述：
 */

public class UserPresent extends XPresent<UserFragment> {


    public void getFundHome(String token,String userId) {

        //伪数据
//        List<MyFundResp> myFundRespList = new ArrayList<>();
//        myFundRespList.add(new MyFundResp("华夏大盘精选混合1", 6793.44, 55.70, 206.67));
//        myFundRespList.add(new MyFundResp("华夏大盘精选混合2", 6780.33, 56.70, 206.67));
//        myFundRespList.add(new MyFundResp("华夏大盘精选混合3", 6772.22, 58.70, 206.67));
//        myFundRespList.add(new MyFundResp("华夏大盘精选混合4", 6767.11, 58.70, 206.67));
//        getV().showMyFund(myFundRespList);

        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);
        reqData.setData("");

        Api.getApi()
                .getFundHome(reqData)
                .compose(XApi.<UserAccountResp>getApiTransformer())
                .compose(XApi.<UserAccountResp>getScheduler())
                .compose(getV().<UserAccountResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<UserAccountResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().requestFundFail();
                    }

                    @Override
                    public void onNext(UserAccountResp model) {
                        if (model != null && model.getStatus() == 200) {
                            getV().showMyFund(model.getData());
                        } else {
                            getV().showToast(model.getMessage());
                            getV().requestFundFail();
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }

}
