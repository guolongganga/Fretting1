package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.user.UpdateBonusResp;
import com.zhsoft.fretting.ui.fragment.user.BonusModeFragment;

import java.util.ArrayList;

import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * 作者：sunnyzeng on 2018/1/25 10:54
 * 描述：
 */

public class BonusModePresent extends XPresent<BonusModeFragment> {

    public void loadBonusTypeData(int pageNo, int pageSize) {
        ArrayList<UpdateBonusResp> list = new ArrayList<>();

        UpdateBonusResp resp1 = new UpdateBonusResp("博时精选基金1", "050001", "现金分红", "1");
        UpdateBonusResp resp2 = new UpdateBonusResp("博时精选基金2", "050002", "分红再投资", "2");
        UpdateBonusResp resp3 = new UpdateBonusResp("博时精选基金3", "050003", "现金分红", "3");
        UpdateBonusResp resp4 = new UpdateBonusResp("博时精选基金4", "050004", "分红再投资", "4");

        list.add(resp1);
        list.add(resp2);
        list.add(resp3);
        list.add(resp4);

        getV().showData(pageNo,list);
    }
}
