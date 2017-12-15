package com.zhsoft.fretting.model;

/**
 * 作者：sunnyzeng on 2017/12/15 10:46
 * 描述：{"data":
 * {"data":"","token":"c9a3ce92cf3d4b6994cc4bf0bfba3598","userId":"135884f8144a4ed3b9b326c8f3459976"},"message":"成功","status":200}
 */

public class LoginModel extends BaseModel<LoginModel> {
    private String token;
    private String userId;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
