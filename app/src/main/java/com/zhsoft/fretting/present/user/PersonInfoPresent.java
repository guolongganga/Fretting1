package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.user.OccupationResp;
import com.zhsoft.fretting.model.user.PersonInfoResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.PersonInfoParams;
import com.zhsoft.fretting.ui.activity.user.PersonInfoActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2017/12/28 14:21
 * 描述：我的个人信息
 */

public class PersonInfoPresent extends XPresent<PersonInfoActivity> {

    /**
     * 获取用户信息
     *
     * @param token
     * @param userId
     */
    public void getUserInfo(String token, String userId) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        Api.getApi()
                .myInformationPage(reqData)
                .compose(XApi.<PersonInfoResp>getApiTransformer())
                .compose(XApi.<PersonInfoResp>getScheduler())
                .compose(getV().<PersonInfoResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<PersonInfoResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestUserInfoFail();
                        getV().showToast("请求失败");
                    }

                    @Override
                    public void onNext(PersonInfoResp personInfoResp) {
                        if (personInfoResp != null && personInfoResp.getStatus() == 200) {
                            getV().requestUserInfoSuccess(personInfoResp.getData());
                        } else {
                            getV().requestUserInfoFail();
                            getV().showToast(personInfoResp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });

    }

    /**
     * 获取职业集合
     *
     * @param token
     * @param userId
     */
    public void getOccupation(String token, String userId) {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        Api.getApi()
                .getOccupation(reqData)
                .compose(XApi.<OccupationResp>getApiTransformer())
                .compose(XApi.<OccupationResp>getScheduler())
                .compose(getV().<OccupationResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<OccupationResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().getOccupationFail();
                        getV().showToast("请求职业失败");
                    }

                    @Override
                    public void onNext(OccupationResp occupationResp) {
                        if (occupationResp != null && occupationResp.getStatus() == 200) {
                            getV().getOccupationSuccess(occupationResp.getData());
                        } else {
                            getV().getOccupationFail();
                            getV().showToast(occupationResp.getMessage());
                            XLog.e("返回数据为空");
                        }

                    }
                });
    }

    /**
     *
     * @param token
     * @param userId
     * @param id_enddate
     * @param address
     * @param detaile_address
     * @param email
     * @param selectOccupation
     */
    public void changeMyInformation(String token, String userId,String id_enddate,String address,String detaile_address,String email,OccupationResp selectOccupation){
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);

        PersonInfoParams params = new PersonInfoParams();
        params.setId_enddate(id_enddate);
        params.setAddress(address);
        params.setDetaile_address(detaile_address);
        params.setEmail(email);
        params.setOccupation(selectOccupation);
        reqData.setData(params);

        Api.getApi()
                .changeMyInformation(reqData)
                .compose(XApi.<BaseResp<String>>getApiTransformer())
                .compose(XApi.<BaseResp<String>>getScheduler())
                .compose(getV().<BaseResp<String>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp<String>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().changeMyInformationFail();
                        getV().showToast("请求失败");
                    }

                    @Override
                    public void onNext(BaseResp<String> resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().changeMyInformationSuccess();
                        } else {
                            getV().changeMyInformationFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }
}