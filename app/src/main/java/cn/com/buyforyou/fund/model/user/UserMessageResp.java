package cn.com.buyforyou.fund.model.user;

import cn.com.buyforyou.fund.model.BaseResp;

/**
 * Created by guolonggang on 2018/12/12.
 * Description:
 */

public class UserMessageResp extends BaseResp<UserMessageResp> {
    private String token;
    private String userId;
    //用户名
    private String client_name;
    //身份证号
    private String id_no;

    public String getClient_name() {
        return client_name;
    }

    public void setClient_name(String client_name) {
        this.client_name = client_name;
    }

    public String getId_no() {
        return id_no;
    }

    public void setId_no(String id_no) {
        this.id_no = id_no;
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
}
