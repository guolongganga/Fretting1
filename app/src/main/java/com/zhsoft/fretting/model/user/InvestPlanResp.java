package com.zhsoft.fretting.model.user;

import com.zhsoft.fretting.model.ApplyBaseInfo;
import com.zhsoft.fretting.model.BaseResp;

import java.util.List;

/**
 * 作者：sunnyzeng on 2018/1/22 13:17
 * 描述：
 */

public class InvestPlanResp extends BaseResp<InvestPlanResp> {
    private List<ApplyBaseInfo> allFunds;
    private List<ApplyBaseInfo> allStatus;
    private List<InvestInfoResp> resResult;

    public List<ApplyBaseInfo> getAllFunds() {
        return allFunds;
    }

    public void setAllFunds(List<ApplyBaseInfo> allFunds) {
        this.allFunds = allFunds;
    }

    public List<ApplyBaseInfo> getAllStatus() {
        return allStatus;
    }

    public void setAllStatus(List<ApplyBaseInfo> allStatus) {
        this.allStatus = allStatus;
    }

    public List<InvestInfoResp> getResResult() {
        return resResult;
    }

    public void setResResult(List<InvestInfoResp> resResult) {
        this.resResult = resResult;
    }
}
