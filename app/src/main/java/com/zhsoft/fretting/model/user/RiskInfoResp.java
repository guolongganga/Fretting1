package com.zhsoft.fretting.model.user;

import com.zhsoft.fretting.model.BaseResp;

/**
 * 作者：sunnyzeng on 2018/2/2 15:49
 * 描述：{"data":{"riskEvaluteStatus":"1","riskEvaluteVal":"稳健型"},"message":"成功","status":200}
 */

public class RiskInfoResp extends BaseResp<RiskInfoResp> {
    //1 表示已测， 0 表示 未测
    private String riskEvaluteStatus;
    private String riskEvaluteVal;

    public String getRiskEvaluteStatus() {
        return riskEvaluteStatus;
    }

    public void setRiskEvaluteStatus(String riskEvaluteStatus) {
        this.riskEvaluteStatus = riskEvaluteStatus;
    }

    public String getRiskEvaluteVal() {
        return riskEvaluteVal;
    }

    public void setRiskEvaluteVal(String riskEvaluteVal) {
        this.riskEvaluteVal = riskEvaluteVal;
    }
}
