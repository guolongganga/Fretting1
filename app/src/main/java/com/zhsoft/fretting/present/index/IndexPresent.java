package com.zhsoft.fretting.present.index;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.fund.BuyFundResp;
import com.zhsoft.fretting.model.index.BannerModel;
import com.zhsoft.fretting.model.index.IndexResp;
import com.zhsoft.fretting.model.index.PopularityResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.BuyFundParams;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.fragment.index.IndexFragment;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 首页
 * Created by ${Yis}
 * data: 2017/12/16
 */

public class IndexPresent extends XPresent<IndexFragment> {

    public void loadData() {
//        //banner伪数据
//        List<String> bannerList = new ArrayList<>();
//        bannerList.add("http://img4.imgtn.bdimg.com/it/u=2430963138,1300578556&fm=23&gp=0.jpg");
//        bannerList.add("http://img1.imgtn.bdimg.com/it/u=2755648979,3568014048&fm=23&gp=0.jpg");
//        bannerList.add("http://img0.imgtn.bdimg.com/it/u=2272739960,4287902102&fm=23&gp=0.jpg");
//        bannerList.add("http://img3.imgtn.bdimg.com/it/u=1078051055,1310741362&fm=23&gp=0.jpg");
//
//        getV().showBanner(bannerList);
//
//        //人气产品伪数据
//        List<PopularityResp> popularityList = new ArrayList<>();
//        PopularityResp resp1 = new PopularityResp();
//        resp1.setValue("43.33%");
//        resp1.setDescribe("今年以来收益率");
//        resp1.setTitle("金鑫股票");
//        resp1.setLocationOne("金牛女神");
//        resp1.setLocationTwo("价值成长");
//
//        PopularityResp resp2 = new PopularityResp();
//        resp2.setValue("43.33%");
//        resp2.setDescribe("今年以来收益率");
//        resp2.setTitle("互联网+股票");
//        resp2.setLocationOne("业绩持久");
//        resp2.setLocationTwo("长期回报");
//
//        PopularityResp resp3 = new PopularityResp();
//        resp3.setValue("48.99%");
//        resp3.setDescribe("近一年收益率");
//        resp3.setTitle("新经济混合");
//        resp3.setLocationOne("把握拐点");
//        resp3.setLocationTwo("深挖潜力股");
//
//        popularityList.add(resp1);
//        popularityList.add(resp2);
//        popularityList.add(resp3);
//
//        getV().showPopularity(popularityList);
        CommonReqData reqData = new CommonReqData();
        reqData.setData("");

        Api.getApi().getHome(reqData)
                .compose(XApi.<IndexResp>getApiTransformer())
                .compose(XApi.<IndexResp>getScheduler())
                .compose(getV().<IndexResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<IndexResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestIndexDataFail();
                    }

                    @Override
                    public void onNext(IndexResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            List<BannerModel> bannerList = resp.getData().getBannerList();
                            getV().showIndexData(resp.getData());
                        }else {
                            getV().requestIndexDataFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }

}
