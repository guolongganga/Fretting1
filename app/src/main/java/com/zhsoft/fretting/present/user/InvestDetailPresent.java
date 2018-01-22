package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.user.InvestRecordResp;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.InvestDeatilActivity;

import java.util.ArrayList;

import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * 作者：sunnyzeng on 2018/1/22 17:03
 * 描述：
 */

public class InvestDetailPresent extends XPresent<InvestDeatilActivity> {
    public void investDetailData(String token, String userId) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        ArrayList<InvestRecordResp> list = new ArrayList<>();
        InvestRecordResp resp = new InvestRecordResp("2017-10-17", "星期一", "10.00", "定投成功");
        InvestRecordResp resp2 = new InvestRecordResp("2017-10-28", "星期三", "20.00", "定投成功");
        list.add(resp);
        list.add(resp2);

        if (true) {
            getV().requestInvestDetailSuccess(list);
        } else {
            getV().requestInvestDetailFail();
        }
    }
}
