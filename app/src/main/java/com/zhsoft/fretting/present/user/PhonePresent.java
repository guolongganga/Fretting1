package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.user.PhoneResp;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.PhoneActivity;

import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * 作者：sunnyzeng on 2017/12/28 11:02
 * 描述：
 */

public class PhonePresent extends XPresent<PhoneActivity> {
    public void getPhoneInfo(String token, String userId) {

        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        //返回
        if (true) {
            PhoneResp resp = new PhoneResp();
            resp.setCarrieroperator("yidong");
            resp.setPhone("13717830000");
            getV().requestSuccess(resp);
        } else {
            getV().requestFail();
        }
    }
}
