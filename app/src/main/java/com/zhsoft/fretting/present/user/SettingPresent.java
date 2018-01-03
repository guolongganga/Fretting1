package com.zhsoft.fretting.present.user;

import com.google.gson.Gson;
import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.SettingActivity;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import cn.droidlover.xdroidmvp.utils.EncryptDecrypt;

/**
 * 作者：sunnyzeng on 2018/1/3 17:16
 * 描述：
 */

public class SettingPresent extends XPresent<SettingActivity> {

    public void goRiskTest(String token, String userId) {
        CommonReqData reqData = new CommonReqData();
        reqData.setUserId(userId);
        reqData.setToken(token);
        Gson gson = new Gson();
        String strData = gson.toJson(reqData);
        //加密请求数据
        String encrypt = EncryptDecrypt.encryptByAES(strData);
        Map<String, String> map = new HashMap<>();
        map.put("reqData", encrypt);
        String params = gson.toJson(map);
        XLog.e("qqq", "params:" + params);

        Api.getApi()
                .goRiskTest(params)
                .compose(XApi.<BaseResp<String>>getApiTransformer())
                .compose(XApi.<BaseResp<String>>getScheduler())
                .compose(getV().<BaseResp<String>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp<String>>() {
                    @Override
                    protected void onFail(NetError error) {

                    }

                    @Override
                    public void onNext(BaseResp<String> resp) {
                        getV().requestRiskTestSuccess(resp.getData());
                    }
                });

    }

}
