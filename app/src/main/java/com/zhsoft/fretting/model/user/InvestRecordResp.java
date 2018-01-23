package com.zhsoft.fretting.model.user;

/**
 * 作者：sunnyzeng on 2018/1/22 17:18
 * 描述： 定投记录
 */

public class InvestRecordResp {
    private String day;
    private String dayWeek;
    private String amount;
    private String status;

    public InvestRecordResp(String day, String dayWeek, String amount, String status) {
        this.day = day;
        this.dayWeek = dayWeek;
        this.amount = amount;
        this.status = status;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDayWeek() {
        return dayWeek;
    }

    public void setDayWeek(String dayWeek) {
        this.dayWeek = dayWeek;
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