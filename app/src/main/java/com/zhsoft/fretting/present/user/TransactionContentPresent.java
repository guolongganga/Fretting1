package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.user.TransactionResp;
import com.zhsoft.fretting.ui.fragment.user.TransactionContentFragment;

import java.util.ArrayList;

import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * 作者：sunnyzeng on 2018/1/24 16:08
 * 描述：
 */

public class TransactionContentPresent extends XPresent<TransactionContentFragment> {

    public void loadTransactionData(int pageno, int pageSize, String tabType) {
        ArrayList<TransactionResp> list = new ArrayList();

        if ("101".equals(tabType)) {

            TransactionResp resp1 = new TransactionResp("申购", "博时精选基金1",
                    "050001", "2017-12-11 17:59", "125.00", "确认成功");
            TransactionResp resp2 = new TransactionResp("申购", "博时精选基金2",
                    "050001", "2017-12-12 17:59", "125.00", "确认成功");
            TransactionResp resp3 = new TransactionResp("定投", "博时精选基金3",
                    "050001", "2017-12-13 17:59", "125.00", "确认成功");
            TransactionResp resp4 = new TransactionResp("定投", "博时精选基金4",
                    "050001", "2017-12-14 17:59", "125.00", "确认成功");
            list.add(resp1);
            list.add(resp2);
            list.add(resp3);
            list.add(resp4);

        } else if ("102".equals(tabType)) {
            TransactionResp resp1 = new TransactionResp("卖出", "博时精选基金1",
                    "050001", "2017-12-15 17:59", "1.00", "确认成功");
            TransactionResp resp2 = new TransactionResp("卖出", "博时精选基金2",
                    "050001", "2017-12-16 17:59", "1.00", "确认成功");
            TransactionResp resp3 = new TransactionResp("卖出", "博时精选基金3",
                    "050001", "2017-12-17 17:59", "1.00", "确认成功");
            TransactionResp resp4 = new TransactionResp("卖出", "博时精选基金4",
                    "050001", "2017-12-18 17:59", "1.00", "确认成功");
            list.add(resp1);
            list.add(resp2);
            list.add(resp3);
            list.add(resp4);

        } else if ("103".equals(tabType)) {
            TransactionResp resp1 = new TransactionResp("申购", "博时精选基金1",
                    "050001", "2017-12-15 17:59", "134.00", "份额信息确认中");
            TransactionResp resp2 = new TransactionResp("申购", "博时精选基金2",
                    "050001", "2017-12-16 17:59", "134.00", "份额信息确认中");
            TransactionResp resp3 = new TransactionResp("卖出", "博时精选基金3",
                    "050001", "2017-12-17 17:59", "12.00", "申请提交，待基金公司确认");
            TransactionResp resp4 = new TransactionResp("卖出", "博时精选基金4",
                    "050001", "2017-12-18 17:59", "12.00", "申请提交，待基金公司确认");
            TransactionResp resp5 = new TransactionResp("定投", "博时精选基金3",
                    "050001", "2017-12-13 17:59", "125.00", "份额信息确认中");
            TransactionResp resp6 = new TransactionResp("定投", "博时精选基金4",
                    "050001", "2017-12-14 17:59", "125.00", "份额信息确认中");
            list.add(resp1);
            list.add(resp2);
            list.add(resp3);
            list.add(resp4);
            list.add(resp5);
            list.add(resp6);
        } else if ("104".equals(tabType)) {
            TransactionResp resp1 = new TransactionResp("分红再投资", "博时精选基金1",
                    "050001", "2017-12-15 17:59", "13 .00", null);
            TransactionResp resp2 = new TransactionResp("现金分红", "博时精选基金2",
                    "050001", "2017-12-16 17:59", "125.00", null);
            list.add(resp1);
            list.add(resp2);
        }

        getV().showData(pageno, list);
    }

}
