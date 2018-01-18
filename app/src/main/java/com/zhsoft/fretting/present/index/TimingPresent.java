package com.zhsoft.fretting.present.index;

import com.zhsoft.fretting.model.fund.FundResp;
import com.zhsoft.fretting.model.fund.NewestFundResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.NewestFundParams;
import com.zhsoft.fretting.ui.fragment.fund.FundContentFragment;
import com.zhsoft.fretting.ui.fragment.index.TimingFragment;

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

public class TimingPresent extends XPresent<TimingFragment> {

    public void loadData(final int pageno, int pagesize, String type, String orderBy) {

//        List<FundResp> list = new ArrayList<>();
//        for (int i = 0; i < 12; i++) {
//            FundResp fundResp = new FundResp();
//            fundResp.setName("招商安泰股票");
//            fundResp.setCode("2017001");
//            fundResp.setValue("0.3948");
//            fundResp.setRange("+6.36%");
//            list.add(fundResp);
//        }
//
//        getV().showData(1, list);

        CommonReqData reqData = new CommonReqData();
        NewestFundParams params = new NewestFundParams();
        params.setPageSize(pagesize);
        params.setPageNum(pageno);
        params.setOfund_type(type);
        params.setPerformance_term(orderBy);
        reqData.setData(params);

        Api.getApi().fundTypeFixedOrderBy(reqData)
                .compose(XApi.<NewestFundResp>getApiTransformer())
                .compose(XApi.<NewestFundResp>getScheduler())
                .compose(getV().<NewestFundResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<NewestFundResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().showError();
                        getV().showToast("请求失败");
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
}
