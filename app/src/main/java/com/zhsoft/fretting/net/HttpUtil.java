package com.zhsoft.fretting.net;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.LoginResp;
import com.zhsoft.fretting.model.TaetResp;
import com.zhsoft.fretting.model.index.IndexResp;
import com.zhsoft.fretting.model.user.BankCardResp;
import com.zhsoft.fretting.model.user.BankResp;
import com.zhsoft.fretting.model.user.ImageResp;
import com.zhsoft.fretting.model.user.NewestFundListResp;
import com.zhsoft.fretting.model.user.UserAccountResp;
import com.zhsoft.fretting.params.CommonReqData;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.zhsoft.fretting.net.HttpContent.change_bankcard;
import static com.zhsoft.fretting.net.HttpContent.change_bankcard_check;
import static com.zhsoft.fretting.net.HttpContent.common_bank_list;
import static com.zhsoft.fretting.net.HttpContent.fund_home;
import static com.zhsoft.fretting.net.HttpContent.get_home;
import static com.zhsoft.fretting.net.HttpContent.image_code;
import static com.zhsoft.fretting.net.HttpContent.my_bankcard;
import static com.zhsoft.fretting.net.HttpContent.newest_fund;
import static com.zhsoft.fretting.net.HttpContent.open_account;
import static com.zhsoft.fretting.net.HttpContent.password_check;
import static com.zhsoft.fretting.net.HttpContent.password_reset;
import static com.zhsoft.fretting.net.HttpContent.phone_code;
import static com.zhsoft.fretting.net.HttpContent.send_phone_code;
import static com.zhsoft.fretting.net.HttpContent.test_test;
import static com.zhsoft.fretting.net.HttpContent.user_login;
import static com.zhsoft.fretting.net.HttpContent.user_register;

/**
 * Created by ${Yis}
 * data: 2017/11/21
 */

public interface HttpUtil {

    //获取首页商品列表数据
    @Headers("apptype:Android")
    @POST(test_test)
    Flowable<TaetResp> getTest(@Body CommonReqData request);

    //登录
    @Headers("apptype:Android")
    @POST(user_login)
    Flowable<LoginResp> login(@Body CommonReqData reqData);

    //找回密码 第一步 验证手机号
    @Headers("apptype:Android")
    @POST(password_check)
    Flowable<BaseResp> findPasswordCheck(@Body CommonReqData reqData);

    //找回密码 第二步
    @Headers("apptype:Android")
    @POST(password_reset)
    Flowable<BaseResp> findPasswordReset(@Body CommonReqData reqData);

    //注册 第一步 验证手机号
    @Headers("apptype:Android")
    @POST(user_register)
    Flowable<LoginResp> register(@Body CommonReqData reqData);

    //请求银行卡列表
    @Headers("apptype:Android")
    @POST(common_bank_list)
    Flowable<BankResp> bankList(@Body CommonReqData reqData);

    //图片验证码
    @Headers("apptype:Android")
    @POST(image_code)
    Flowable<ImageResp> getImageCode(@Body CommonReqData reqData);

    //短信验证码
    @Headers("apptype:Android")
    @POST(phone_code)
    Flowable<BaseResp<String>> getPhoneCode(@Body CommonReqData reqData);

    //开户绑卡
    @Headers("apptype:Android")
    @POST(open_account)
    Flowable<BaseResp<String>> openAccount(@Body CommonReqData reqData);

    //我的资产
    @Headers("apptype:Android")
    @POST(fund_home)
    Flowable<UserAccountResp> getFundHome(@Body CommonReqData reqData);

    //基金页
    @Headers("apptype:Android")
    @POST(newest_fund)
    Flowable<NewestFundListResp> getNewestFund(@Body CommonReqData reqData);

    //主页
    @Headers("apptype:Android")
    @POST(get_home)
    Flowable<IndexResp> getHome(@Body CommonReqData reqData);

    //我的银行卡
    @Headers("apptype:Android")
    @POST(my_bankcard)
    Flowable<BankCardResp> getMyBankCard(@Body CommonReqData reqData);

    //检查是否可以更换银行卡
    @Headers("apptype:Android")
    @POST(change_bankcard_check)
    Flowable<BankCardResp> changeBankCardCheck(@Body CommonReqData reqData);

    //更换银行卡操作
    @Headers("apptype:Android")
    @POST(change_bankcard)
    Flowable<BaseResp<String>> changeBankCard(@Body CommonReqData reqData);

    //发送短信验证码 不需要图片验证码
    @Headers("apptype:Android")
    @POST(send_phone_code)
    Flowable<BaseResp<String>> sendPhoneCode(@Body CommonReqData reqData);
}
