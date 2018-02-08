package com.zhsoft.fretting.present.index;

import com.zhsoft.fretting.model.index.IndexResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.fragment.index.IndexFragment;

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
