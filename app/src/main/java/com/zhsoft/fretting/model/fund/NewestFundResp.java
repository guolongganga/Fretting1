package com.zhsoft.fretting.model.fund;

import com.zhsoft.fretting.model.BaseResp;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 作者：sunnyzeng on 2017/12/29 10:54
 * 描述：基金页面数据
 * <p>
 * "fund_code": "050004",
 * "fund_name": "博时精选股票",
 * "fund_rose": 5.27,
 * "net_value": 1.9406
 */

public class NewestFundResp extends BaseResp<ArrayList<NewestFundResp>> {
    /**
     * 基金代码
     */
    private String fund_code;
    /**
     * 基金名称
     */
    private String fund_name;
    /**
     * 基金净值
     */
    private BigDecimal net_value;
    /**
     * 基金涨跌幅（用来展示具体涨幅：日涨幅、周涨幅、月涨幅等）
     */
    private BigDecimal fund_rose;

    public String getFund_code() {
        return fund_code;
    }

    public void setFund_code(String fund_code) {
        this.fund_code = fund_code;
    }

    public String getFund_name() {
        return fund_name;
    }

    public void setFund_name(String fund_name) {
        this.fund_name = fund_name;
    }

    public BigDecimal getNet_value() {
        return net_value;
    }

    public void setNet_value(BigDecimal net_value) {
        this.net_value = net_value;
    }

    public BigDecimal getFund_rose() {
        return fund_rose;
    }

    public void setFund_rose(BigDecimal fund_rose) {
        this.fund_rose = fund_rose;
    }
}
