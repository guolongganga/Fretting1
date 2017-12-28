package com.zhsoft.fretting.model.user;

import com.zhsoft.fretting.model.BaseResp;

/**
 * 作者：sunnyzeng on 2017/12/28 11:21
 * 描述：我的电话号码
 */

public class PhoneResp extends BaseResp<PhoneResp> {
    //运营商
    private String carrieroperator;
    //手机号码
    private String phone;

    public String getCarrieroperator() {
        return carrieroperator;
    }

    public void setCarrieroperator(String carrieroperator) {
        this.carrieroperator = carrieroperator;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
