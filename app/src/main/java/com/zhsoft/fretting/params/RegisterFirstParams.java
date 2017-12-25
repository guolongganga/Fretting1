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
    private String messageCode;

    public String getMessageCode() {
        return messageCode;
    }

    public void setMessageCode(String messageCode) {
        this.messageCode = messageCode;
    }
    //    private String image_code;
//    private String image_code_id;

//    public String getImage_code() {
//        return image_code;
//    }
//
//    public void setImage_code(String image_code) {
//        this.image_code = image_code;
//    }
//
//    public String getImage_code_id() {
//        return image_code_id;
//    }
//
//    public void setImage_code_id(String image_code_id) {
//        this.image_code_id = image_code_id;
//    }

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
