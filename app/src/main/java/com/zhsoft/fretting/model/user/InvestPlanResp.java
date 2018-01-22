package com.zhsoft.fretting.model.user;

import com.zhsoft.fretting.model.BaseResp;

/**
 * 作者：sunnyzeng on 2018/1/22 13:17
 * 描述：
 */

public class InvestPlanResp extends BaseResp<InvestPlanResp> {
    public InvestPlanResp(String fundName, String fundCode, String invesType, String bankName, String bankTail, String nextTime, String investStatus) {
        this.fundName = fundName;
        this.fundCode = fundCode;
        this.invesType = invesType;
        this.bankName = bankName;
        this.bankTail = bankTail;
        this.nextTime = nextTime;
        this.investStatus = investStatus;
    }

    /** 基金名称 */
    private String fundName;
    /** 基金代码 */
    private String fundCode;
    /** 定投方式 */
    private String invesType;
    /** 银行名称 */
    private String bankName;
    /** 银行卡尾号 */
    private String bankTail;
    /** 下次扣款时间 */
    private String nextTime;
    /** 定投计划状态 */
    private String investStatus;

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

    public String getInvesType() {
        return invesType;
    }

    public void setInvesType(String invesType) {
        this.invesType = invesType;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankTail() {
        return bankTail;
    }

    public void setBankTail(String bankTail) {
        this.bankTail = bankTail;
    }

    public String getNextTime() {
        return nextTime;
    }

    public void setNextTime(String nextTime) {
        this.nextTime = nextTime;
    }

    public String getInvestStatus() {
        return investStatus;
    }

    public void setInvestStatus(String investStatus) {
        this.investStatus = investStatus;
    }
}
