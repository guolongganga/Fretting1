package com.zhsoft.fretting.model.user;

import com.zhsoft.fretting.model.BaseResp;

import java.util.List;

/**
 * @Author: Geyoung
 * @Description:
 * @Data: created in 16:52 2017/12/1
 * 基金页面数据
 **/
public class NewestFundListResp extends BaseResp<NewestFundListResp> {
    /**
     * 记录数 S 8 0 Y v4.1.8.0
     */
    private String rowcount;
    /**
     * 总记录数 S 10 0 Y v4.1.8.0
     */
    private String total_count;

    private List<NewestFundResp> fundMarketInfoQuerys;


    public String getRowcount() {
        return rowcount;
    }

    public void setRowcount(String rowcount) {
        this.rowcount = rowcount;
    }

    public String getTotal_count() {
        return total_count;
    }

    public void setTotal_count(String total_count) {
        this.total_count = total_count;
    }

    public List<NewestFundResp> getFundMarketInfoQuerys() {
        return fundMarketInfoQuerys;
    }

    public void setFundMarketInfoQuerys(List<NewestFundResp> fundMarketInfoQuerys) {
        this.fundMarketInfoQuerys = fundMarketInfoQuerys;
    }
}
