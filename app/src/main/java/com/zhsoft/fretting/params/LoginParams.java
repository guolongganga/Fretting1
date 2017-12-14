package com.zhsoft.fretting.params;

/**
 * 作者：sunnyzeng on 2017/12/14 18:05
 * 描述：
 */

public class LoginParams {

    private String token;
    private String userId;
    private LoginP data;

    public LoginP getData() {
        return data;
    }

    public void setData(LoginP data) {
        this.data = data;
    }


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


    public class LoginP {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }


}
