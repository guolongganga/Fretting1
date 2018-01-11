package com.zhsoft.fretting.present.boot;

import com.zhsoft.fretting.model.fund.NewestFundResp;
import com.zhsoft.fretting.ui.activity.boot.SearchActivity;

import java.util.ArrayList;

import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * 作者：sunnyzeng on 2018/1/10 17:33
 * 描述：搜索页面控制器
 */

public class SearchPersent extends XPresent<SearchActivity> {

    public void hotListData() {

        ArrayList<NewestFundResp> list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            NewestFundResp newestFundResp = new NewestFundResp();
            newestFundResp.setFund_name("招商安泰股票" + i);
            newestFundResp.setFund_code("21700" + i);
            list.add(newestFundResp);
        }
        if (true) {
            getV().requestHotListSuccess(list);
        } else {
            getV().requestHotListFail();
        }

    }

    public void searchData(String keyword) {

        ArrayList<NewestFundResp> list = new ArrayList<>();
        ArrayList<NewestFundResp> result = new ArrayList<>();
        NewestFundResp newestFundResp1 = new NewestFundResp();
        newestFundResp1.setFund_name("招商安泰股票" + 2);
        newestFundResp1.setFund_code("21700" + 2);
        list.add(newestFundResp1);
        for (int i = 0; i < 5; i++) {
            NewestFundResp newestFundResp = new NewestFundResp();
            newestFundResp.setFund_name("招商安泰股票" + i);
            newestFundResp.setFund_code("21700" + i);
            list.add(newestFundResp);
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getFund_name().contains(keyword)) {
                result.add(list.get(i));
            }
        }


        if (true) {
            getV().requestSearchDataSuccess(result);
        } else {
            getV().requestSearchDataFail();
        }
    }
}
