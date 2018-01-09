package com.zhsoft.fretting.present.fund;

import com.zhsoft.fretting.ui.activity.fund.InvestActivity;

import cn.droidlover.xdroidmvp.mvp.XPresent;

/**
 * 作者：sunnyzeng on 2018/1/9 18:10
 * 描述：定投
 */

public class InvestPersent extends XPresent<InvestActivity> {

    /**
     * 获得我的银行卡信息
     *
     * @param token
     * @param userId
     */
    public void myBankCard(String token, String userId) {
        if (true) {
            //请求成功 返回实体
            getV().requestMyBankSuccess();
        } else {
            //请求失败
            getV().requestMyBankFail();
        }
    }

    /**
     * 获取下次扣款时间
     *
     * @param token
     * @param userId
     */
    public void nextDeductTime(String token, String userId) {
        if (true) {
            //请求成功 返回实体
            getV().requestDeductTimeSuccess();
        } else {
            //请求失败
            getV().requestDeductTimeFail();
        }
    }

    /**
     * @param token      登录标识
     * @param userId     用户编号
     * @param fundCode   基金编码
     * @param strAmount  购买金额
     * @param investWeek 定投周期
     * @param investDay  定投日
     */
    public void sureInvest(String token, String userId, String fundCode, String strAmount, String investWeek, String investDay) {

        if (true) {
            //请求成功 返回实体
            getV().requestSureInvestSuccess();
        } else {
            //请求失败
            getV().requestSureInvestFail();
        }

    }

    public void updateInvest(String token, String userId, String fundCode, String strAmount, String investWeek, String investDay) {

        if (true) {
            //请求成功 返回实体
            getV().requestUpdateInvestSuccess();
        } else {
            //请求失败
            getV().requestUpdateInvestFail();
        }

    }
}
