package com.zhsoft.fretting.net;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.LoginResp;
import com.zhsoft.fretting.model.TaetResp;
import com.zhsoft.fretting.model.fund.BuyFundResp;
import com.zhsoft.fretting.model.fund.BuyNowResp;
import com.zhsoft.fretting.model.fund.GetNextTimeResp;
import com.zhsoft.fretting.model.fund.InvestResp;
import com.zhsoft.fretting.model.fund.InvestSureResp;
import com.zhsoft.fretting.model.fund.NewestFundResp;
import com.zhsoft.fretting.model.index.IndexResp;
import com.zhsoft.fretting.model.user.BankCardResp;
import com.zhsoft.fretting.model.user.BankResp;
import com.zhsoft.fretting.model.user.ImageResp;
import com.zhsoft.fretting.model.user.OccupationResp;
import com.zhsoft.fretting.model.user.PersonInfoResp;
import com.zhsoft.fretting.model.user.PhoneResp;
import com.zhsoft.fretting.model.user.UserAccountResp;
import com.zhsoft.fretting.params.CommonReqData;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.zhsoft.fretting.net.HttpContent.buy_fund;
import static com.zhsoft.fretting.net.HttpContent.buy_now;
import static com.zhsoft.fretting.net.HttpContent.change_bankcard;
import static com.zhsoft.fretting.net.HttpContent.change_bankcard_check;
import static com.zhsoft.fretting.net.HttpContent.change_my_information;
import static com.zhsoft.fretting.net.HttpContent.change_phone_index;
import static com.zhsoft.fretting.net.HttpContent.change_phone_save;
import static com.zhsoft.fretting.net.HttpContent.change_phone_sendcode;
import static com.zhsoft.fretting.net.HttpContent.common_bank_list;
import static com.zhsoft.fretting.net.HttpContent.find_fund_like;
import static com.zhsoft.fretting.net.HttpContent.fund_home;
import static com.zhsoft.fretting.net.HttpContent.fund_invest_time;
import static com.zhsoft.fretting.net.HttpContent.fund_times_save;
import static com.zhsoft.fretting.net.HttpContent.fund_times_succdetails;
import static com.zhsoft.fretting.net.HttpContent.get_home;
import static com.zhsoft.fretting.net.HttpContent.get_next_time;
import static com.zhsoft.fretting.net.HttpContent.get_occupation;
import static com.zhsoft.fretting.net.HttpContent.image_code;
import static com.zhsoft.fretting.net.HttpContent.my_bankcard;
import static com.zhsoft.fretting.net.HttpContent.my_informationpage;
import static com.zhsoft.fretting.net.HttpContent.newest_fund;
import static com.zhsoft.fretting.net.HttpContent.open_account;
import static com.zhsoft.fretting.net.HttpContent.password_change_login;
import static com.zhsoft.fretting.net.HttpContent.password_change_trade;
import static com.zhsoft.fretting.net.HttpContent.password_check;
import static com.zhsoft.fretting.net.HttpContent.password_phonecode;
import static com.zhsoft.fretting.net.HttpContent.password_reset;
import static com.zhsoft.fretting.net.HttpContent.phone_code;
import static com.zhsoft.fretting.net.HttpContent.risk_question;
import static com.zhsoft.fretting.net.HttpContent.send_phone_code;
import static com.zhsoft.fretting.net.HttpContent.test_test;
import static com.zhsoft.fretting.net.HttpContent.trade_password_check;
import static com.zhsoft.fretting.net.HttpContent.trade_password_phonecode;
import static com.zhsoft.fretting.net.HttpContent.trade_password_reset;
import static com.zhsoft.fretting.net.HttpContent.user_login;
import static com.zhsoft.fretting.net.HttpContent.user_register;

/**
 * Created by ${Yis}
 * data: 2017/11/21
 */

public interface HttpUtil {

    //获取首页商品列表数据
    @Headers("appType:Android")
    @POST(test_test)
    Flowable<TaetResp> getTest(@Body CommonReqData request);

    //登录
    @Headers("appType:Android")
    @POST(user_login)
    Flowable<LoginResp> login(@Body CommonReqData reqData);

    //注册 第一步 验证手机号
    @Headers("appType:Android")
    @POST(user_register)
    Flowable<LoginResp> register(@Body CommonReqData reqData);

    //请求银行卡列表
    @Headers("appType:Android")
    @POST(common_bank_list)
    Flowable<BankResp> bankList(@Body CommonReqData reqData);

    //图片验证码
    @Headers("appType:Android")
    @POST(image_code)
    Flowable<ImageResp> getImageCode(@Body CommonReqData reqData);

    //短信验证码
    @Headers("appType:Android")
    @POST(phone_code)
    Flowable<BaseResp<String>> getPhoneCode(@Body CommonReqData reqData);

    //开户绑卡
    @Headers("appType:Android")
    @POST(open_account)
    Flowable<BaseResp<String>> openAccount(@Body CommonReqData reqData);

    //我的资产
    @Headers("appType:Android")
    @POST(fund_home)
    Flowable<UserAccountResp> getFundHome(@Body CommonReqData reqData);

    //基金页
    @Headers("appType:Android")
    @POST(newest_fund)
    Flowable<NewestFundResp> getNewestFund(@Body CommonReqData reqData);

    //主页
    @Headers("appType:Android")
    @POST(get_home)
    Flowable<IndexResp> getHome(@Body CommonReqData reqData);

    //风险测评
    @Headers("appType:Android")
    @POST(risk_question)
    Flowable<BaseResp<String>> goRiskTest(@Body String reqData);

    //我的银行卡
    @Headers("appType:Android")
    @POST(my_bankcard)
    Flowable<BankCardResp> getMyBankCard(@Body CommonReqData reqData);

    //检查是否可以更换银行卡
    @Headers("appType:Android")
    @POST(change_bankcard_check)
    Flowable<BankCardResp> changeBankCardCheck(@Body CommonReqData reqData);

    //更换银行卡操作
    @Headers("appType:Android")
    @POST(change_bankcard)
    Flowable<BaseResp<String>> changeBankCard(@Body CommonReqData reqData);

    //发送短信验证码 不需要图片验证码
    @Headers("appType:Android")
    @POST(send_phone_code)
    Flowable<BaseResp<String>> sendPhoneCode(@Body CommonReqData reqData);

    //我的手机号码
    @Headers("appType:Android")
    @POST(change_phone_index)
    Flowable<PhoneResp> changePhoneIndex(@Body CommonReqData reqData);

    //发送短信验证码 不需要图片验证码 更换手机号码
    @Headers("appType:Android")
    @POST(change_phone_sendcode)
    Flowable<BaseResp<String>> changePhoneSendcode(@Body CommonReqData reqData);

    //更换手机号
    @Headers("appType:Android")
    @POST(change_phone_save)
    Flowable<BaseResp<String>> changePhoneSave(@Body CommonReqData reqData);

    //变更登录密码
    @Headers("appType:Android")
    @POST(password_change_login)
    Flowable<BaseResp<String>> passwordChangeLogin(@Body CommonReqData reqData);

    //变更交易密码
    @Headers("appType:Android")
    @POST(password_change_trade)
    Flowable<BaseResp<String>> passwordChangeTrade(@Body CommonReqData reqData);

    //请求职业列表
    @Headers("appType:Android")
    @POST(get_occupation)
    Flowable<OccupationResp> getOccupation(@Body CommonReqData reqData);

    //个人信息
    @Headers("appType:Android")
    @POST(my_informationpage)
    Flowable<PersonInfoResp> myInformationPage(@Body CommonReqData reqData);

    //变更登录密码
    @Headers("appType:Android")
    @POST(change_my_information)
    Flowable<BaseResp<String>> changeMyInformation(@Body CommonReqData reqData);

    //找回登录密码 短信验证码
    @Headers("appType:Android")
    @POST(password_phonecode)
    Flowable<BaseResp<String>> passwordPhoneCode(@Body CommonReqData reqData);

    //找回登录密码 第一步 验证手机号
    @Headers("appType:Android")
    @POST(password_check)
    Flowable<BaseResp> findPasswordCheck(@Body CommonReqData reqData);

    //找回登录密码 第二步
    @Headers("appType:Android")
    @POST(password_reset)
    Flowable<BaseResp> findPasswordReset(@Body CommonReqData reqData);

    //找回交易密码 短信验证码
    @Headers("appType:Android")
    @POST(trade_password_phonecode)
    Flowable<BaseResp<String>> tradePasswordPhoneCode(@Body CommonReqData reqData);

    //找回交易密码 第一步 验证手机号
    @Headers("appType:Android")
    @POST(trade_password_check)
    Flowable<BaseResp> findTradePasswordCheck(@Body CommonReqData reqData);

    //找回交易密码 第二步
    @Headers("appType:Android")
    @POST(trade_password_reset)
    Flowable<BaseResp> findTradePasswordReset(@Body CommonReqData reqData);

    //购买基金验证
    @Headers("appType:Android")
    @POST(buy_fund)
    Flowable<BuyFundResp> buyFund(@Body CommonReqData reqData);

    //购买基金验证
    @Headers("appType:Android")
    @POST(buy_now)
    Flowable<BuyNowResp> buyNow(@Body CommonReqData reqData);

    //搜索基金
    @Headers("appType:Android")
    @POST(find_fund_like)
    Flowable<NewestFundResp> findFundLike(@Body CommonReqData reqData);

    //基金定投
    @Headers("appType:Android")
    @POST(fund_invest_time)
    Flowable<InvestResp> fundInvestTime(@Body CommonReqData reqData);

    //定投 根据选择时间 显示下次扣款日
    @Headers("appType:Android")
    @POST(get_next_time)
    Flowable<GetNextTimeResp> getNextTime(@Body CommonReqData reqData);

    //定投 购买
    @Headers("appType:Android")
    @POST(fund_times_save)
    Flowable<InvestSureResp> fundTimesSave(@Body CommonReqData reqData);

    //定投 购买
    @Headers("appType:Android")
    @POST(fund_times_succdetails)
    Flowable<BaseResp> fundTimesSuccDetails(@Body CommonReqData reqData);
}
