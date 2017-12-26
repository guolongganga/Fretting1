package com.zhsoft.fretting.model.user;

import com.zhsoft.fretting.model.BaseResp;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * 作者：sunnyzeng on 2017/12/26 17:00
 * 描述：我的用户账户信息
 */

public class UserAccountResp extends BaseResp<UserAccountResp> {
    /*
    * 总资产
     */
    private BigDecimal totalAssets;
    /**
     * 昨日收益
     */
    private BigDecimal earningsLastDay;
    /**
     * 累计收益
     */
    private BigDecimal cumulativeIncome;

    /**
     * 我的持仓基金
     */
    private ArrayList<FoundResp> fundList;
    /**
     * 昨天
     */
    private String yesterday;

    public BigDecimal getTotalAssets() {
        return totalAssets;
    }

    public void setTotalAssets(BigDecimal totalAssets) {
        this.totalAssets = totalAssets;
    }

    public BigDecimal getEarningsLastDay() {
        return earningsLastDay;
    }

    public void setEarningsLastDay(BigDecimal earningsLastDay) {
        this.earningsLastDay = earningsLastDay;
    }

    public BigDecimal getCumulativeIncome() {
        return cumulativeIncome;
    }

    public void setCumulativeIncome(BigDecimal cumulativeIncome) {
        this.cumulativeIncome = cumulativeIncome;
    }

    public ArrayList<FoundResp> getFundList() {
        return fundList;
    }

    public void setFundList(ArrayList<FoundResp> fundList) {
        this.fundList = fundList;
    }

    public String getYesterday() {
        return yesterday;
    }

    public void setYesterday(String yesterday) {
        this.yesterday = yesterday;
    }
}
