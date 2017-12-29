package com.zhsoft.fretting.present.user;

import com.zhsoft.fretting.model.user.PersonInfoResp;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.activity.user.PersonInfoActivity;

import cn.droidlover.xdroidmvp.mvp.XPresent;

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

        if (true) {
            PersonInfoResp personInfoResp = new PersonInfoResp();
            personInfoResp.setName("张哈哈");
            personInfoResp.setGender("女");
            personInfoResp.setCertNo("362425199208****2X");
            personInfoResp.setLimitTime("");
            personInfoResp.setIsPermanent("0");
            personInfoResp.setNationality("中国");
            personInfoResp.setOccupation("老师");
            personInfoResp.setAddress("江西省吉安市永丰县");
            personInfoResp.setFundAccount("111111111111");
            getV().requestUserInfoSuccess(personInfoResp);
        }else{
            getV().requestUserInfoFail();
        }
    }
}
