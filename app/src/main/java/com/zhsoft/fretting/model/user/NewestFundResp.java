package com.zhsoft.fretting.model.user;

import java.math.BigDecimal;

/**
 * 作者：sunnyzeng on 2017/12/29 10:54
 * 描述：基金页面数据
 * <p>
 * {
 * "access_type": null,
 * "allow_trustee_in": null,
 * "channel_web": null,
 * "cycle_step": "0",
 * "cycle_type": null,
 * "declare_endday": null,
 * "declare_state": "1",
 * "default_auto_buy": "1",
 * "expire_date": null,
 * "forbid_modi_autobuy_flag": "0",
 * "frozen_limit": "0",
 * "fund_code": "050001",
 * "fund_contract_flag": "0",
 * "fund_curr_ratio": 0,
 * "fund_full_name": "博时价值增长基金",
 * "fund_name": "博时价值增长基金",
 * "fund_name_web": null,
 * "fund_status": "0",
 * "fund_sub_type": "0",
 * "fund_sub_type_web": null,
 * "hopedeclare_state": "1",
 * "introduce_link_web": null,
 * "is_purpose_match": null,
 * "is_risk_match": null,
 * "issue_date": "20020902",
 * "manage_product_period": null,
 * "manage_product_style": null,
 * "manager_code": "205",
 * "manager_name": null,
 * "min_share": 100,
 * "money_type": "156",
 * "nav_date": "20171215",
 * "nav_total": 3.16,
 * "net_value": 0.66,
 * "ofund_risklevel": "2",
 * "ofund_type": "A",
 * "ofund_type_web": null,
 * "open_date": null,
 * "per_myriad_income": 0,
 * "pre_income_ratio": 0,
 * "pre_yield": 0,
 * "private_flag": "0",
 * "pro_series": null,
 * "prod_desc_web": null,
 * "product_due_time": "0",
 * "quick_exceed_flag": "1",
 * "redeem_endday": null,
 * "redeem_state": "1",
 * "remain_limit": "0",
 * "serial_no_web": null,
 * "setup_date": "20020930",
 * "share_type": "A",
 * "shelves_status": null,
 * "start_amount": null,
 * "subscribe_date": null,
 * "subscribe_end_date": null,
 * "subscribe_state": "0",
 * "support_limit_control": null,
 * "support_share_transfer": "0",
 * "ta_code": "05",
 * "tag_name": null,
 * "temporary_close": null,
 * "trans_state": "1",
 * "trustee_name": "建设银行",
 * "valuagr_state": "1",
 * "value_date": null,
 * "zh_detail_type": null
 * }
 */

public class NewestFundResp {

    /**
     * 准入类型 C 1 0 N v4.1.8.0
     */
    private String access_type;
    /**
     * 允许转托管入 C 1 0 N v4.1.8.0  0-否 1-是
     */
    private String allow_trustee_in;
    /**
     * web渠道 C 50 0 N v4.1.8.0
     */
    private String channel_web;
    /**
     * 周期间隔 S 8 0 N v4.1.8.0
     */
    private String cycle_step;
    /**
     * 周期类型 S 4 0 N v4.1.8.0
     */
    private String cycle_type;
    /**
     * 预期申购截至日 S 8 0 N v4.1.8.0
     */
    private String declare_endday;
    /**
     * 申购状态 C 1 0 N v4.1.8.0  1：可申购
     */
    private String declare_state;
    /**
     * 默认分红方式 C 1 0 N v4.1.8.0
     */
    private String default_auto_buy;
    /**
     * 到期日 S 8 0 N v4.1.8.0
     */
    private String expire_date;
    /**
     * 禁止修改分红方式标志 C 1 0 N v4.1.8.0
     */
    private String forbid_modi_autobuy_flag;
    /**
     * 冻结额度 C 1 0 N v4.1.8.0
     */
    private String frozen_limit;
    /**
     * 基金代码 S 6 0 N v4.1.8.0
     */
    private String fund_code;
    /**
     * 是否需要签署电子合同 C 1 0 N v4.1.8.0  1-需要签署,空或者0-不需要签署
     */
    private String fund_contract_flag;
    /**
     * 七日年化收益率 F 9 4 N v4.1.8.0
     */
    private BigDecimal fund_curr_ratio;
    /**
     * 基金全称 S 100 0 N v4.1.8.0
     */
    private String fund_full_name;
    /**
     * 基金名称 S 20 0 N v4.1.8.0
     */
    private String fund_name;
    /**
     * web基金名称 S 40 0 N v4.1.8.0
     */
    private String fund_name_web;
    /**
     * 基金状态 C 1 0 N v4.1.8.0  字典[基金状态]
     */
    private String fund_status;
    /**
     * 基金子类型 C 1 0 N v4.1.8.0  字典[基金子类型]
     */
    private String fund_sub_type;
    /**
     * web基金子类型 S 40 0 N v4.1.8.0
     */
    private String fund_sub_type_web;
    /**
     * 预约申购状态 C 1 0 N v4.1.8.0  1：可预约申购
     */
    private String hopedeclare_state;
    /**
     * web介绍连接 S 200 0 N v4.1.8.0
     */
    private String introduce_link_web;
    /**
     * 是否投资目标匹配 C 1 0 N v4.1.8.0
     */
    private String is_purpose_match;
    /**
     * 是否风险匹配 C 1 0 N v4.1.8.0
     */
    private String is_risk_match;
    /**
     * 基金发行日 S 8 0 N v4.1.8.0
     */
    private String issue_date;
    /**
     * 产品期数 S 50 0 N v4.1.8.0
     */
    private String manage_product_period;
    /**
     * 产品风格 C 1 0 N v4.1.8.0
     */
    private String manage_product_style;
    /**
     * 基金管理人代码 S 8 0 N v4.1.8.0
     */
    private String manager_code;
    /**
     * 基金管理人名称 S 36 0 N v4.1.8.0
     */
    private String manager_name;
    /**
     * 基金最小持有份额 F 16 2 N v4.1.8.0
     */
    private BigDecimal min_share;
    /**
     * 币种类别 S 3 0 N v4.1.8.0
     */
    private String money_type;
    /**
     * 净值日期 S 8 0 N v4.1.8.0
     */
    private String nav_date;
    /**
     * 累计净值 F 11 4 N v4.1.8.0
     */
    private BigDecimal nav_total;
    /**
     * 基金净值 F 11 4 N v4.1.8.0
     */
    private BigDecimal net_value;
    /**
     * 基金风险等级 S 8 0 N v4.1.8.0
     */
    private String ofund_risklevel;
    /**
     * 基金类别 C 1 0 N v4.1.8.0  字典[基金类型]
     */
    private String ofund_type;
    /**
     * web基金类型 S 2 0 N v4.1.8.0  字典[产品类型显示]
     */
    private String ofund_type_web;
    /**
     * 产品开放日 S 8 0 N v4.1.8.0
     */
    private String open_date;
    /**
     * 万份基金单位收益 F 16 5 N v4.1.8.0
     */
    private BigDecimal per_myriad_income;
    /**
     * 预期年化收益率 F 9 4 N v4.1.8.0
     */
    private BigDecimal pre_income_ratio;
    /**
     * 收益率 F 9 4 N v4.1.8.0
     */
    private BigDecimal pre_yield;
    /**
     * 是否私募 C 1 0 N v4.1.8.0  0-否 1-是
     */
    private String private_flag;
    /**
     * 产品系列 S 10 0 N v4.1.8.0
     */
    private String pro_series;
    /**
     * web产品描述 S 150 0 N v4.1.8.0
     */
    private String prod_desc_web;
    /**
     * 产品期限 S 5 0 N v4.1.8.0
     */
    private String product_due_time;
    /**
     * 基金是否支持快速赎回 C 1 0 N v4.1.8.0  0-不支持，1-支持，为空则默认为1
     */
    private String quick_exceed_flag;
    /**
     * 预期赎回截至日 S 8 0 N v4.1.8.0
     */
    private String redeem_endday;
    /**
     * 赎回状态 C 1 0 N v4.1.8.0
     */
    private String redeem_state;
    /**
     * 剩余额度 C 1 0 N v4.1.8.0
     */
    private String remain_limit;
    /**
     * web编号 S 8 0 N v4.1.8.0
     */
    private String serial_no_web;
    /**
     * 基金成立日期 S 8 0 N v4.1.8.0
     */
    private String setup_date;
    /**
     * 份额类别 C 1 0 N v4.1.8.0  字典[份额类别]
     */
    private String share_type;
    /**
     * 上架状态 S 1 0 N v4.1.8.0  0:未上架，1：上架，2：下架
     */
    private String shelves_status;
    /**
     * 起投金额 S 14 0 N v4.1.8.0
     */
    private String start_amount;
    /**
     * 认购开发时间 S 14 0 N v4.1.8.0
     */
    private String subscribe_date;
    /**
     * 认购结束日期 S 8 0 N v4.1.8.0
     */
    private String subscribe_end_date;
    /**
     * 认购状态 C 1 0 N v4.1.8.0
     */
    private String subscribe_state;
    /**
     * 是否支持根据交易控制基金规模 C 1 0 N v4.1.8.0  1：支持根据交易控制基金规模
     */
    private String support_limit_control;
    /**
     * 份额转受让业务标志 C 1 0 N v4.1.8.0
     */
    private String support_share_transfer;
    /**
     * TA代码 S 4 0 N v4.1.8.0
     */
    private String ta_code;
    /**
     * 标签名称 S 20 0 N v4.1.8.0
     */
    private String tag_name;
    /**
     * 临时休市 C 1 0 N v4.1.8.0
     */
    private String temporary_close;
    /**
     * 转换出状态 C 1 0 N v4.1.8.0  1：可转换出
     */
    private String trans_state;
    /**
     * 基金托管人名称 S 36 0 N v4.1.8.0
     */
    private String trustee_name;
    /**
     * 定投状态 C 1 0 N v4.1.8.0  1：可定投
     */
    private String valuagr_state;
    /**
     * 起息日 S 8 0 N v4.1.8.0
     */
    private String value_date;
    /**
     * 专户明细类别 C 1 0 N v4.1.8.0
     */
    private String zh_detail_type;

    public String getAccess_type() {
        return access_type;
    }

    public void setAccess_type(String access_type) {
        this.access_type = access_type;
    }

    public String getAllow_trustee_in() {
        return allow_trustee_in;
    }

    public void setAllow_trustee_in(String allow_trustee_in) {
        this.allow_trustee_in = allow_trustee_in;
    }

    public String getChannel_web() {
        return channel_web;
    }

    public void setChannel_web(String channel_web) {
        this.channel_web = channel_web;
    }

    public String getCycle_step() {
        return cycle_step;
    }

    public void setCycle_step(String cycle_step) {
        this.cycle_step = cycle_step;
    }

    public String getCycle_type() {
        return cycle_type;
    }

    public void setCycle_type(String cycle_type) {
        this.cycle_type = cycle_type;
    }

    public String getDeclare_endday() {
        return declare_endday;
    }

    public void setDeclare_endday(String declare_endday) {
        this.declare_endday = declare_endday;
    }

    public String getDeclare_state() {
        return declare_state;
    }

    public void setDeclare_state(String declare_state) {
        this.declare_state = declare_state;
    }

    public String getDefault_auto_buy() {
        return default_auto_buy;
    }

    public void setDefault_auto_buy(String default_auto_buy) {
        this.default_auto_buy = default_auto_buy;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }

    public String getForbid_modi_autobuy_flag() {
        return forbid_modi_autobuy_flag;
    }

    public void setForbid_modi_autobuy_flag(String forbid_modi_autobuy_flag) {
        this.forbid_modi_autobuy_flag = forbid_modi_autobuy_flag;
    }

    public String getFrozen_limit() {
        return frozen_limit;
    }

    public void setFrozen_limit(String frozen_limit) {
        this.frozen_limit = frozen_limit;
    }

    public String getFund_code() {
        return fund_code;
    }

    public void setFund_code(String fund_code) {
        this.fund_code = fund_code;
    }

    public String getFund_contract_flag() {
        return fund_contract_flag;
    }

    public void setFund_contract_flag(String fund_contract_flag) {
        this.fund_contract_flag = fund_contract_flag;
    }

    public BigDecimal getFund_curr_ratio() {
        return fund_curr_ratio;
    }

    public void setFund_curr_ratio(BigDecimal fund_curr_ratio) {
        this.fund_curr_ratio = fund_curr_ratio;
    }

    public String getFund_full_name() {
        return fund_full_name;
    }

    public void setFund_full_name(String fund_full_name) {
        this.fund_full_name = fund_full_name;
    }

    public String getFund_name() {
        return fund_name;
    }

    public void setFund_name(String fund_name) {
        this.fund_name = fund_name;
    }

    public String getFund_name_web() {
        return fund_name_web;
    }

    public void setFund_name_web(String fund_name_web) {
        this.fund_name_web = fund_name_web;
    }

    public String getFund_status() {
        return fund_status;
    }

    public void setFund_status(String fund_status) {
        this.fund_status = fund_status;
    }

    public String getFund_sub_type() {
        return fund_sub_type;
    }

    public void setFund_sub_type(String fund_sub_type) {
        this.fund_sub_type = fund_sub_type;
    }

    public String getFund_sub_type_web() {
        return fund_sub_type_web;
    }

    public void setFund_sub_type_web(String fund_sub_type_web) {
        this.fund_sub_type_web = fund_sub_type_web;
    }

    public String getHopedeclare_state() {
        return hopedeclare_state;
    }

    public void setHopedeclare_state(String hopedeclare_state) {
        this.hopedeclare_state = hopedeclare_state;
    }

    public String getIntroduce_link_web() {
        return introduce_link_web;
    }

    public void setIntroduce_link_web(String introduce_link_web) {
        this.introduce_link_web = introduce_link_web;
    }

    public String getIs_purpose_match() {
        return is_purpose_match;
    }

    public void setIs_purpose_match(String is_purpose_match) {
        this.is_purpose_match = is_purpose_match;
    }

    public String getIs_risk_match() {
        return is_risk_match;
    }

    public void setIs_risk_match(String is_risk_match) {
        this.is_risk_match = is_risk_match;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(String issue_date) {
        this.issue_date = issue_date;
    }

    public String getManage_product_period() {
        return manage_product_period;
    }

    public void setManage_product_period(String manage_product_period) {
        this.manage_product_period = manage_product_period;
    }

    public String getManage_product_style() {
        return manage_product_style;
    }

    public void setManage_product_style(String manage_product_style) {
        this.manage_product_style = manage_product_style;
    }

    public String getManager_code() {
        return manager_code;
    }

    public void setManager_code(String manager_code) {
        this.manager_code = manager_code;
    }

    public String getManager_name() {
        return manager_name;
    }

    public void setManager_name(String manager_name) {
        this.manager_name = manager_name;
    }

    public BigDecimal getMin_share() {
        return min_share;
    }

    public void setMin_share(BigDecimal min_share) {
        this.min_share = min_share;
    }

    public String getMoney_type() {
        return money_type;
    }

    public void setMoney_type(String money_type) {
        this.money_type = money_type;
    }

    public String getNav_date() {
        return nav_date;
    }

    public void setNav_date(String nav_date) {
        this.nav_date = nav_date;
    }

    public BigDecimal getNav_total() {
        return nav_total;
    }

    public void setNav_total(BigDecimal nav_total) {
        this.nav_total = nav_total;
    }

    public BigDecimal getNet_value() {
        return net_value;
    }

    public void setNet_value(BigDecimal net_value) {
        this.net_value = net_value;
    }

    public String getOfund_risklevel() {
        return ofund_risklevel;
    }

    public void setOfund_risklevel(String ofund_risklevel) {
        this.ofund_risklevel = ofund_risklevel;
    }

    public String getOfund_type() {
        return ofund_type;
    }

    public void setOfund_type(String ofund_type) {
        this.ofund_type = ofund_type;
    }

    public String getOfund_type_web() {
        return ofund_type_web;
    }

    public void setOfund_type_web(String ofund_type_web) {
        this.ofund_type_web = ofund_type_web;
    }

    public String getOpen_date() {
        return open_date;
    }

    public void setOpen_date(String open_date) {
        this.open_date = open_date;
    }

    public BigDecimal getPer_myriad_income() {
        return per_myriad_income;
    }

    public void setPer_myriad_income(BigDecimal per_myriad_income) {
        this.per_myriad_income = per_myriad_income;
    }

    public BigDecimal getPre_income_ratio() {
        return pre_income_ratio;
    }

    public void setPre_income_ratio(BigDecimal pre_income_ratio) {
        this.pre_income_ratio = pre_income_ratio;
    }

    public BigDecimal getPre_yield() {
        return pre_yield;
    }

    public void setPre_yield(BigDecimal pre_yield) {
        this.pre_yield = pre_yield;
    }

    public String getPrivate_flag() {
        return private_flag;
    }

    public void setPrivate_flag(String private_flag) {
        this.private_flag = private_flag;
    }

    public String getPro_series() {
        return pro_series;
    }

    public void setPro_series(String pro_series) {
        this.pro_series = pro_series;
    }

    public String getProd_desc_web() {
        return prod_desc_web;
    }

    public void setProd_desc_web(String prod_desc_web) {
        this.prod_desc_web = prod_desc_web;
    }

    public String getProduct_due_time() {
        return product_due_time;
    }

    public void setProduct_due_time(String product_due_time) {
        this.product_due_time = product_due_time;
    }

    public String getQuick_exceed_flag() {
        return quick_exceed_flag;
    }

    public void setQuick_exceed_flag(String quick_exceed_flag) {
        this.quick_exceed_flag = quick_exceed_flag;
    }

    public String getRedeem_endday() {
        return redeem_endday;
    }

    public void setRedeem_endday(String redeem_endday) {
        this.redeem_endday = redeem_endday;
    }

    public String getRedeem_state() {
        return redeem_state;
    }

    public void setRedeem_state(String redeem_state) {
        this.redeem_state = redeem_state;
    }

    public String getRemain_limit() {
        return remain_limit;
    }

    public void setRemain_limit(String remain_limit) {
        this.remain_limit = remain_limit;
    }

    public String getSerial_no_web() {
        return serial_no_web;
    }

    public void setSerial_no_web(String serial_no_web) {
        this.serial_no_web = serial_no_web;
    }

    public String getSetup_date() {
        return setup_date;
    }

    public void setSetup_date(String setup_date) {
        this.setup_date = setup_date;
    }

    public String getShare_type() {
        return share_type;
    }

    public void setShare_type(String share_type) {
        this.share_type = share_type;
    }

    public String getShelves_status() {
        return shelves_status;
    }

    public void setShelves_status(String shelves_status) {
        this.shelves_status = shelves_status;
    }

    public String getStart_amount() {
        return start_amount;
    }

    public void setStart_amount(String start_amount) {
        this.start_amount = start_amount;
    }

    public String getSubscribe_date() {
        return subscribe_date;
    }

    public void setSubscribe_date(String subscribe_date) {
        this.subscribe_date = subscribe_date;
    }

    public String getSubscribe_end_date() {
        return subscribe_end_date;
    }

    public void setSubscribe_end_date(String subscribe_end_date) {
        this.subscribe_end_date = subscribe_end_date;
    }

    public String getSubscribe_state() {
        return subscribe_state;
    }

    public void setSubscribe_state(String subscribe_state) {
        this.subscribe_state = subscribe_state;
    }

    public String getSupport_limit_control() {
        return support_limit_control;
    }

    public void setSupport_limit_control(String support_limit_control) {
        this.support_limit_control = support_limit_control;
    }

    public String getSupport_share_transfer() {
        return support_share_transfer;
    }

    public void setSupport_share_transfer(String support_share_transfer) {
        this.support_share_transfer = support_share_transfer;
    }

    public String getTa_code() {
        return ta_code;
    }

    public void setTa_code(String ta_code) {
        this.ta_code = ta_code;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getTemporary_close() {
        return temporary_close;
    }

    public void setTemporary_close(String temporary_close) {
        this.temporary_close = temporary_close;
    }

    public String getTrans_state() {
        return trans_state;
    }

    public void setTrans_state(String trans_state) {
        this.trans_state = trans_state;
    }

    public String getTrustee_name() {
        return trustee_name;
    }

    public void setTrustee_name(String trustee_name) {
        this.trustee_name = trustee_name;
    }

    public String getValuagr_state() {
        return valuagr_state;
    }

    public void setValuagr_state(String valuagr_state) {
        this.valuagr_state = valuagr_state;
    }

    public String getValue_date() {
        return value_date;
    }

    public void setValue_date(String value_date) {
        this.value_date = value_date;
    }

    public String getZh_detail_type() {
        return zh_detail_type;
    }

    public void setZh_detail_type(String zh_detail_type) {
        this.zh_detail_type = zh_detail_type;
    }


}
