package com.zhsoft.fretting.present;

import android.util.Log;

import com.zhsoft.fretting.model.BaseModel;
import com.zhsoft.fretting.model.Resp;
import com.zhsoft.fretting.model.TaetModel;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.fragment.user.UserFragment;

import java.util.HashMap;
import java.util.Map;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2017/12/6 13:02
 * 描述：
 */

public class UserPresent extends XPresent<UserFragment> {
    public void loadTestData() {

        CommonReqData reqData = new CommonReqData();

        reqData.setData("测试数据");

        Api.getApi()
                .getTest(reqData)
                .compose(XApi.<TaetModel>getApiTransformer())
                .compose(XApi.<TaetModel>getScheduler())
                .compose(getV().<TaetModel>bindToLifecycle())
                .subscribe(new ApiSubscriber<TaetModel>() {
                    @Override
                    protected void onFail(NetError error) {
                        error.printStackTrace();
                        Log.e("hahah", "fail");
                    }

                    @Override
                    public void onNext(TaetModel model) {
                        if (model != null && model.getStatus() == 200) {
                            Log.e("hahah", "访问成功");
                            getV().showData(model.getData());
                        } else {
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }


}
