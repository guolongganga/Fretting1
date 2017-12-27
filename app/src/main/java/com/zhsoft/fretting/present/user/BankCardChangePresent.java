package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.user.BankResp;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.BankCardChangeActivity;

import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * 作者：sunnyzeng on 2017/12/27 13:13
 * 描述：更换银行卡
 */

public class BankCardChangePresent extends XPresent<BankCardChangeActivity> {

    /**
     * 银行预留手机号验证
     *
     * @param phone
     */
    public void getMessageCode(String phone) {
        CommonReqData reqData = new CommonReqData();

        //模拟短信验证码请求
        if (true) {
            getV().requestMessageCodeSuccess();
        } else {
            getV().requestMessageCodeFail();
        }
    }

    /**
     * 更换银行卡
     *
     * @param bankResp    选择的银行
     * @param bankNumber  银行卡号
     * @param phoneNumber 银行预留手机号
     * @param msgCode     短信验证码
     */
    public void changeBankCard(BankResp bankResp, String bankNumber, String phoneNumber, String msgCode) {
        CommonReqData reqData = new CommonReqData();

        getV().requestChangeSuccess();
        getV().requestChangeFail();
    }
}
