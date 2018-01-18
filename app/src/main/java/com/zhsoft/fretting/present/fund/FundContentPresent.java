package com.zhsoft.fretting.present.fund;

import com.zhsoft.fretting.model.fund.FundResp;
import com.zhsoft.fretting.model.fund.NewestFundResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.NewestFundParams;
import com.zhsoft.fretting.ui.fragment.fund.FundContentFragment;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * Created by ${sunny}
 * data: 2017/12/19
 */

public class FundContentPresent extends XPresent<FundContentFragment> {

    /**
     * 基金数据
     *
     * @param pageno
     * @param pagesize
     * @param type
     */
    public void loadFundData(final int pageno, int pagesize, String type, String orderBy) {
//        List<FundResp> list = new ArrayList<>();
//        switch (type) {
//            case "股票型":
//                list.clear();
//                for (int i = 0; i < 12; i++) {
//                    FundResp fundResp = new FundResp();
//                    fundResp.setName("基金招商安泰股票");
//                    fundResp.setCode("2017001");
//                    fundResp.setValue("0.3948");
//                    fundResp.setRange("+6.36%");
//                    list.add(fundResp);
//                }
//                break;
//            case "混合型":
//                list.clear();
//                for (int i = 0; i < 12; i++) {
//                    FundResp fundResp = new FundResp();
//                    fundResp.setName("基金金鹰多元策略混合");
//                    fundResp.setCode("2017001");
//                    fundResp.setValue("0.3948");
//                    fundResp.setRange("+6.36%");
//                    list.add(fundResp);
//                }
//                break;
//            case "债券型":
//                list.clear();
//                for (int i = 0; i < 12; i++) {
//                    FundResp fundResp = new FundResp();
//                    fundResp.setName("基金交银丰益收益债券");
//                    fundResp.setCode("2017001");
//                    fundResp.setValue("0.3948");
//                    fundResp.setRange("+6.36%");
//                    list.add(fundResp);
//                }
//                break;
//            case "指数型":
//                list.clear();
//                for (int i = 0; i < 12; i++) {
//                    FundResp fundResp = new FundResp();
//                    fundResp.setName("基金国泰国证新能源汽车指数");
//                    fundResp.setCode("2017001");
//                    fundResp.setValue("0.3948");
//                    fundResp.setRange("+6.36%");
//                    list.add(fundResp);
//                }
//                break;
//        }
        CommonReqData reqData = new CommonReqData();
        NewestFundParams params = new NewestFundParams();
        params.setPageSize(pagesize);
        params.setPageNum(pageno);
        params.setOfund_type(type);
        params.setPerformance_term(orderBy);
        reqData.setData(params);

        Api.getApi().getNewestFund(reqData)
                .compose(XApi.<NewestFundResp>getApiTransformer())
                .compose(XApi.<NewestFundResp>getScheduler())
                .compose(getV().<NewestFundResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<NewestFundResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().showError(error);
                    }

                    @Override
                    public void onNext(NewestFundResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().showData(pageno, resp.getData());
                        } else {
                            getV().showToast(resp.getMessage());
                        }

                    }
                });

    }

    /**
     * 人气产品
     *
     * @param pageno
     * @param pagesize
     * @param type
     */
    public void loadPopularityData(int pageno, int pagesize, String type) {

        List<FundResp> list = new ArrayList<>();
        switch (type) {
            case "股票型":
                list.clear();
                for (int i = 0; i < 12; i++) {
                    FundResp fundResp = new FundResp();
                    fundResp.setName("人气招商安泰股票");
                    fundResp.setCode("2017001");
                    fundResp.setValue("0.3948");
                    fundResp.setRange("+6.36%");
                    list.add(fundResp);
                }
                break;
            case "混合型":
                list.clear();
                for (int i = 0; i < 12; i++) {
                    FundResp fundResp = new FundResp();
                    fundResp.setName("人气金鹰多元策略混合");
                    fundResp.setCode("2017001");
                    fundResp.setValue("0.3948");
                    fundResp.setRange("+6.36%");
                    list.add(fundResp);
                }
                break;
            case "债券型":
                list.clear();
                for (int i = 0; i < 12; i++) {
                    FundResp fundResp = new FundResp();
                    fundResp.setName("人气交银丰益收益债券");
                    fundResp.setCode("2017001");
                    fundResp.setValue("0.3948");
                    fundResp.setRange("+6.36%");
                    list.add(fundResp);
                }
                break;
            case "指数型":
                list.clear();
                for (int i = 0; i < 12; i++) {
                    FundResp fundResp = new FundResp();
                    fundResp.setName("人气国泰国证新能源汽车指数");
                    fundResp.setCode("2017001");
                    fundResp.setValue("0.3948");
                    fundResp.setRange("+6.36%");
                    list.add(fundResp);
                }
                break;
        }

//        getV().showData(1, list);
    }
}
