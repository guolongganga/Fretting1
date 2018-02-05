package com.zhsoft.fretting.model.user;

import java.math.BigDecimal;

/**
 * 作者：sunnyzeng on 2017/12/26 17:01
 * 描述：我的持仓基金
 */

public class FoundResp {
    /**
     * 基金名称
     */
    private String fundName;
    /**
     * 基金代码
     */
    private String fundCode;
    /**
     * 持仓金额
     */
    private BigDecimal holdAmount;
    /**
     * 昨日收益
     */
    private BigDecimal earningsLastDay;
    /**
     * 累计收益
     */
    private BigDecimal totalEarn;
    /**
     * X笔交易确认中
     */
    private String sureNumber;

    public String getSureNumber() {
        return sureNumber;
    }

    public void setSureNumber(String sureNumber) {
        this.sureNumber = sureNumber;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getFundCode() {
        return fundCode;
    }

    public void setFundCode(String fundCode) {
        this.fundCode = fundCode;
    }

    public BigDecimal getHoldAmount() {
        return holdAmount;
    }

    public void setHoldAmount(BigDecimal holdAmount) {
        this.holdAmount = holdAmount;
    }

    public BigDecimal getEarningsLastDay() {
        return earningsLastDay;
    }

    public void setEarningsLastDay(BigDecimal earningsLastDay) {
        this.earningsLastDay = earningsLastDay;
    }

    public BigDecimal getTotalEarn() {
        return totalEarn;
    }

    public void setTotalEarn(BigDecimal totalEarn) {
        this.totalEarn = totalEarn;
    }
}
