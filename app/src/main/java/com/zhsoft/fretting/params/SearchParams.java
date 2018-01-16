package com.zhsoft.fretting.params;

/**
 * 作者：sunnyzeng on 2018/1/15 13:19
 * 描述：
 */

public class SearchParams extends CommonReqData<SearchParams> {
    private String fund_name;
    private String fund_code;

    public String getFund_name() {
        return fund_name;
    }

    public void setFund_name(String fund_name) {
        this.fund_name = fund_name;
    }

    public String getFund_code() {
        return fund_code;
    }

    public void setFund_code(String fund_code) {
        this.fund_code = fund_code;
    }
}
