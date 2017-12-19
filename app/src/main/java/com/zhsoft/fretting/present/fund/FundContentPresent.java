package com.zhsoft.fretting.present.fund;

import com.zhsoft.fretting.model.fund.FundResp;
import com.zhsoft.fretting.ui.fragment.fund.FundContentFragment;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * Created by ${sunny}
 * data: 2017/12/19
 */

public class FundContentPresent extends XPresent<FundContentFragment> {

    public void loadData() {

        List<FundResp> list = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            FundResp fundResp = new FundResp();
            fundResp.setName("招商安泰股票");
            fundResp.setCode("2017001");
            fundResp.setValue("0.3948");
            fundResp.setRange("+6.36%");
            list.add(fundResp);
        }

        getV().showData(1, list);
    }
}
