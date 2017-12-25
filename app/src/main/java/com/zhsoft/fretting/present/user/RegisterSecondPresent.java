package com.zhsoft.fretting.present.user;

import android.util.Log;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.user.BankResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.params.BankListParams;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.params.OpenAccountParams;
import com.zhsoft.fretting.ui.activity.user.RegisterSecondActivity;

import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;

/**
 * 作者：sunnyzeng on 2017/12/15 16:26
 * 描述：
 */

public class RegisterSecondPresent extends XPresent<RegisterSecondActivity> {
//    /**
//     * 银行列表
//     */
//    public void getBankList() {
//        //伪数据
////        ArrayList<BankResp> listResps = new ArrayList<>();
////        listResps.add(new BankResp("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513665029652&di=05ece74f34fb946211ef9ac7bcb2f94d&imgtype=0&src=http%3A%2F%2Fpic7.nipic.com%2F20100509%2F4893028_094928018289_2.jpg","工商银行","1111111111111"));
////        listResps.add(new BankResp("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513665104065&di=77b7f138410cce2dc49da0c6bd30a779&imgtype=0&src=http%3A%2F%2Fwww.highestchina.com%2FUploads%2F201401%2F52e0da38e36a6.jpg","招商银行","222222222222"));
////        listResps.add(new BankResp("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513665178868&di=2cda9c64d0a560ab9e0e8878e9c9cb3b&imgtype=0&src=http%3A%2F%2Fwww.yjcf360.com%2Fu%2Fcms%2Fwww%2F201601%2F090932486u32.png","建设","333333333333"));
////        listResps.add(new BankResp("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513665029652&di=05ece74f34fb946211ef9ac7bcb2f94d&imgtype=0&src=http%3A%2F%2Fpic7.nipic.com%2F20100509%2F4893028_094928018289_2.jpg","工商银行","444444444444444"));
////        listResps.add(new BankResp("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513665104065&di=77b7f138410cce2dc49da0c6bd30a779&imgtype=0&src=http%3A%2F%2Fwww.highestchina.com%2FUploads%2F201401%2F52e0da38e36a6.jpg","招商银行","555555555555555555"));
////        listResps.add(new BankResp("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513665178868&di=2cda9c64d0a560ab9e0e8878e9c9cb3b&imgtype=0&src=http%3A%2F%2Fwww.yjcf360.com%2Fu%2Fcms%2Fwww%2F201601%2F090932486u32.png","建设","666666666666666666666"));
////
////
////        getV().bankListData(listResps);
//
//        CommonReqData reqData = new CommonReqData();
//
//        BankListParams params = new BankListParams();
//        params.setPartner_id("");
//        reqData.setData(params);
//
//        Api.getApi()
//                .bankList(reqData)
//                .compose(XApi.<BankResp>getApiTransformer())
//                .compose(XApi.<BankResp>getScheduler())
//                .compose(getV().<BankResp>bindToLifecycle())
//                .subscribe(new ApiSubscriber<BankResp>() {
//                    @Override
//                    protected void onFail(NetError error) {
//                        error.printStackTrace();
//                        getV().requestFail();
//                    }
//
//                    @Override
//                    public void onNext(BankResp model) {
//                        if (model != null && model.getStatus() == 200) {
//                            Log.e("hahah", "访问成功");
//                            getV().bankListData(model.getData());
//                        } else {
//                            getV().requestFail();
//                            getV().showToast(model.getMessage());
//                            XLog.e("返回数据为空");
//                        }
//                    }
//                });
//
//    }

    /**
     * 开户绑卡
     *
     * @param userId        用户编号
     * @param token         登录标识
     * @param userName      姓名
     * @param certNo        身份证号
     * @param email         邮箱（选填）
     * @param bankResp      选择银行
     * @param bankAccount   银行卡号
     * @param mobile        预留手机号
     * @param tradePassword 交易密码
     */
    public void openAccount(String userId, String token, String userName, String certNo, String email, BankResp bankResp, String bankAccount, String mobile, String tradePassword) {
        final CommonReqData reqData = new CommonReqData();
        reqData.setUserId(userId);
        reqData.setToken(token);

        OpenAccountParams params = new OpenAccountParams();
        params.setUserName(userName);
        params.setCertNo(certNo);
        params.setEmail(email);
        params.setSelectBank(bankResp);
        params.setBankAccount(bankAccount);
        params.setMobile(mobile);
        params.setTradePassword(tradePassword);

        reqData.setData(params);

        Api.getApi().openAccount(reqData)
                .compose(XApi.<BaseResp<String>>getApiTransformer())
                .compose(XApi.<BaseResp<String>>getScheduler())
                .compose(getV().<BaseResp<String>>bindToLifecycle())
                .subscribe(new ApiSubscriber<BaseResp<String>>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().requestOpenAccountFail();
                    }

                    @Override
                    public void onNext(BaseResp<String> resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestOpenAccountSuccess(resp.getData());
                        }
                    }
                });


    }


}
