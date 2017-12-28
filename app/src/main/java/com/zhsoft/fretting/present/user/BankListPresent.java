package com.zhsoft.fretting.present.user;

import android.util.Log;

import com.zhsoft.fretting.model.user.BankResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.BankListParams;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.BankListActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2017/12/22 17:34
 * 描述：银行列表
 */

public class BankListPresent extends XPresent<BankListActivity> {

    /**
     * 银行列表
     */
    public void getBankList() {
        //伪数据
//        ArrayList<BankResp> listResps = new ArrayList<>();
//        listResps.add(new BankResp("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513665029652&di=05ece74f34fb946211ef9ac7bcb2f94d&imgtype=0&src=http%3A%2F%2Fpic7.nipic.com%2F20100509%2F4893028_094928018289_2.jpg","工商银行","1111111111111"));
//        listResps.add(new BankResp("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513665104065&di=77b7f138410cce2dc49da0c6bd30a779&imgtype=0&src=http%3A%2F%2Fwww.highestchina.com%2FUploads%2F201401%2F52e0da38e36a6.jpg","招商银行","222222222222"));
//        listResps.add(new BankResp("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513665178868&di=2cda9c64d0a560ab9e0e8878e9c9cb3b&imgtype=0&src=http%3A%2F%2Fwww.yjcf360.com%2Fu%2Fcms%2Fwww%2F201601%2F090932486u32.png","建设","333333333333"));
//        listResps.add(new BankResp("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513665029652&di=05ece74f34fb946211ef9ac7bcb2f94d&imgtype=0&src=http%3A%2F%2Fpic7.nipic.com%2F20100509%2F4893028_094928018289_2.jpg","工商银行","444444444444444"));
//        listResps.add(new BankResp("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513665104065&di=77b7f138410cce2dc49da0c6bd30a779&imgtype=0&src=http%3A%2F%2Fwww.highestchina.com%2FUploads%2F201401%2F52e0da38e36a6.jpg","招商银行","555555555555555555"));
//        listResps.add(new BankResp("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513665178868&di=2cda9c64d0a560ab9e0e8878e9c9cb3b&imgtype=0&src=http%3A%2F%2Fwww.yjcf360.com%2Fu%2Fcms%2Fwww%2F201601%2F090932486u32.png","建设","666666666666666666666"));
//
//
//        getV().bankListData(listResps);

        CommonReqData reqData = new CommonReqData();

        BankListParams params = new BankListParams();
        params.setPartner_id("");
        reqData.setData(params);

        Api.getApi()
                .bankList(reqData)
                .compose(XApi.<BankResp>getApiTransformer())
                .compose(XApi.<BankResp>getScheduler())
                .compose(getV().<BankResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<BankResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        getV().requestFail();
                    }

                    @Override
                    public void onNext(BankResp model) {
                        if (model != null && model.getStatus() == 200) {
                            Log.e("hahah", "访问成功");
                            getV().bankListData(model.getData());
                        } else {
                            getV().requestFail();
                            getV().showToast(model.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }
}