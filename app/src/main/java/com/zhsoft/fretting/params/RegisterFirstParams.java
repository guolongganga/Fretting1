package com.zhsoft.fretting.params;

/**
 * 作者：sunnyzeng on 2017/12/14 18:05
 * 描述：基金开户 手机号（注册第一步）参数
 * {
 * "reqData":{
 * "data":{
 * "mobile_tel":13718690595,
 * "password":"cjh123456"
 * }
 * }
 * }
 */

public class RegisterFirstParams extends CommonReqData<RegisterFirstParams> {

    private String mobile_tel;
    private String password;

    public String getMobile_tel() {
        return mobile_tel;
    }

    public void setMobile_tel(String mobile_tel) {
        this.mobile_tel = mobile_tel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
