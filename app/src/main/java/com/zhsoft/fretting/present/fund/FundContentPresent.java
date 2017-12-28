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

    public void loadFundData(int pageno, int pagesize, String type) {
        List<FundResp> list = new ArrayList<>();
        switch (type) {
            case "股票型":
                list.clear();
                for (int i = 0; i < 12; i++) {
                    FundResp fundResp = new FundResp();
                    fundResp.setName("基金招商安泰股票");
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
                    fundResp.setName("基金金鹰多元策略混合");
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
                    fundResp.setName("基金交银丰益收益债券");
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
                    fundResp.setName("基金国泰国证新能源汽车指数");
                    fundResp.setCode("2017001");
                    fundResp.setValue("0.3948");
                    fundResp.setRange("+6.36%");
                    list.add(fundResp);
                }
                break;
        }

        getV().showData(1, list);
    }

    public void loadPopularityData(int pageno, int pagesize,String type) {

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

        getV().showData(1, list);
    }
}
