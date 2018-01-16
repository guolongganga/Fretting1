package com.zhsoft.fretting.model.fund;

import com.zhsoft.fretting.model.BaseResp;

/**
 * 作者：sunnyzeng on 2018/1/16 18:45
 * 描述：
 */

public class InvestSureResp extends BaseResp<InvestSureResp> {
    /**
     * 申请编号 S 24 0 N v4.0.3.0
     */
    private String allot_no;
    /**
     * 下次交易日期 N 8 0 N v4.0.3.0
     */
    private String next_fixrequest_date;
    /**
     * 定投协议号 S 20 0 N v4.0.3.0
     */
    private String scheduled_protocol_id;

    public String getAllot_no() {
        return allot_no;
    }

    public void setAllot_no(String allot_no) {
        this.allot_no = allot_no;
    }

    public String getNext_fixrequest_date() {
        return next_fixrequest_date;
    }

    public void setNext_fixrequest_date(String next_fixrequest_date) {
        this.next_fixrequest_date = next_fixrequest_date;
    }

    public String getScheduled_protocol_id() {
        return scheduled_protocol_id;
    }

    public void setScheduled_protocol_id(String scheduled_protocol_id) {
        this.scheduled_protocol_id = scheduled_protocol_id;
    }
}
