package com.zhsoft.fretting.model.user;

/**
 * 作者：sunnyzeng on 2018/1/24 16:52
 * 描述：
 */

public class TransactionResp {
    private String type;
    private String fundName;
    private String fundCode;
    private String time;
    private String amount;
    private String status;

    public TransactionResp(String type, String fundName, String fundCode, String time, String amount, String status) {
        this.type = type;
        this.fundName = fundName;
        this.fundCode = fundCode;
        this.time = time;
        this.amount = amount;
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
