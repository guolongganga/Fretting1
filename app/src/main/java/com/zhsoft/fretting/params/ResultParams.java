package com.zhsoft.fretting.params;

/**
 * 作者：sunnyzeng on 2018/1/31 11:06
 * 描述： 撤单参数
 */

public class ResultParams extends CommonReqData<ResultParams> {
    private String allot_no;
    private String password;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAllot_no() {
        return allot_no;
    }

    public void setAllot_no(String allot_no) {
        this.allot_no = allot_no;
    }
}
