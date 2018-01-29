package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.user.TransactionResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.TransactionQueryParams;
import com.zhsoft.fretting.ui.fragment.user.TransactionSingleContentFragment;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2018/1/24 16:08
 * 描述：
 */

public class TransactionSingleContentPresent extends XPresent<TransactionSingleContentFragment> {

    public void loadTransactionSingleData(String token, String userId, final int pageno, int pageSize, String tabType, String fundCode) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        TransactionQueryParams params = new TransactionQueryParams();
        params.setPageNum(pageno);
        params.setPageSize(pageSize);
        params.setType(tabType);
        params.setFundCode(fundCode);
        reqData.setData(params);

        Api.getApi()
                .singleTradeQueryData(reqData)
                .compose(XApi.<TransactionResp>getApiTransformer())
                .compose(XApi.<TransactionResp>getScheduler())
                .compose(getV().<TransactionResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<TransactionResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().showError();
                    }

                    @Override
                    public void onNext(TransactionResp model) {
                        if (model != null && model.getStatus() == 200) {
                            getV().showData(pageno, model.getData());
                        } else {
                            getV().showToast(model.getMessage());
                            getV().showError();
                            XLog.e("返回数据为空");
                        }
                    }
                });


//        ArrayList<TransactionResp> list = new ArrayList();
//
//        if ("101".equals(tabType)) {
//
//            TransactionResp resp1 = new TransactionResp("申购", "博时精选基金1",
//                    "050001", "2017-12-11 17:59", "125.00", "确认成功");
//            TransactionResp resp2 = new TransactionResp("申购", "博时精选基金2",
//                    "050001", "2017-12-12 17:59", "125.00", "确认成功");
//            TransactionResp resp3 = new TransactionResp("定投", "博时精选基金3",
//                    "050001", "2017-12-13 17:59", "125.00", "确认成功");
//            TransactionResp resp4 = new TransactionResp("定投", "博时精选基金4",
//                    "050001", "2017-12-14 17:59", "125.00", "确认成功");
//            list.add(resp1);
//            list.add(resp2);
//            list.add(resp3);
//            list.add(resp4);
//
//        } else if ("102".equals(tabType)) {
//            TransactionResp resp1 = new TransactionResp("卖出", "博时精选基金1",
//                    "050001", "2017-12-15 17:59", "1.00", "确认成功");
//            TransactionResp resp2 = new TransactionResp("卖出", "博时精选基金2",
//                    "050001", "2017-12-16 17:59", "1.00", "确认成功");
//            TransactionResp resp3 = new TransactionResp("卖出", "博时精选基金3",
//                    "050001", "2017-12-17 17:59", "1.00", "确认成功");
//            TransactionResp resp4 = new TransactionResp("卖出", "博时精选基金4",
//                    "050001", "2017-12-18 17:59", "1.00", "确认成功");
//            list.add(resp1);
//            list.add(resp2);
//            list.add(resp3);
//            list.add(resp4);
//
//        } else if ("103".equals(tabType)) {
//            TransactionResp resp1 = new TransactionResp("申购", "博时精选基金1",
//                    "050001", "2017-12-15 17:59", "134.00", "份额信息确认中");
//            TransactionResp resp2 = new TransactionResp("申购", "博时精选基金2",
//                    "050001", "2017-12-16 17:59", "134.00", "份额信息确认中");
//            TransactionResp resp3 = new TransactionResp("卖出", "博时精选基金3",
//                    "050001", "2017-12-17 17:59", "12.00", "申请提交，待基金公司确认");
//            TransactionResp resp4 = new TransactionResp("卖出", "博时精选基金4",
//                    "050001", "2017-12-18 17:59", "12.00", "申请提交，待基金公司确认");
//            TransactionResp resp5 = new TransactionResp("定投", "博时精选基金3",
//                    "050001", "2017-12-13 17:59", "125.00", "份额信息确认中");
//            TransactionResp resp6 = new TransactionResp("定投", "博时精选基金4",
//                    "050001", "2017-12-14 17:59", "125.00", "份额信息确认中");
//            list.add(resp1);
//            list.add(resp2);
//            list.add(resp3);
//            list.add(resp4);
//            list.add(resp5);
//            list.add(resp6);
//
//        } else if ("104".equals(tabType)) {
//            TransactionResp resp1 = new TransactionResp("分红再投资", "博时精选基金1",
//                    "050001", "2017-12-15 17:59", "13 .00", null);
//            TransactionResp resp2 = new TransactionResp("现金分红", "博时精选基金2",
//                    "050001", "2017-12-16 17:59", "125.00", null);
//            list.add(resp1);
//            list.add(resp2);
//
//        } else if ("105".equals(tabType)) {
//            TransactionResp resp1 = new TransactionResp("分红再投资", "博时精选基金1",
//                    "050001", "2017-12-15 17:59", "13 .00", null);
//            TransactionResp resp2 = new TransactionResp("现金分红", "博时精选基金2",
//                    "050001", "2017-12-16 17:59", "125.00", null);
//            list.add(resp1);
//            list.add(resp2);
//        }

//        getV().showData(pageno, list);

    }

}
