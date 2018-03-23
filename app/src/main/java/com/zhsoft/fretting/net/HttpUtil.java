package com.zhsoft.fretting.net;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.LoginResp;
import com.zhsoft.fretting.model.fund.BuyFundResp;
import com.zhsoft.fretting.model.fund.BuyNowResp;
import com.zhsoft.fretting.model.fund.CalculationResp;
import com.zhsoft.fretting.model.fund.FundStatusResp;
import com.zhsoft.fretting.model.fund.GetNextTimeResp;
import com.zhsoft.fretting.model.fund.InvestResp;
import com.zhsoft.fretting.model.fund.InvestSureResp;
import com.zhsoft.fretting.model.fund.NewestFundResp;
import com.zhsoft.fretting.model.fund.SellResp;
import com.zhsoft.fretting.model.index.IndexResp;
import com.zhsoft.fretting.model.user.BankCardResp;
import com.zhsoft.fretting.model.user.BankResp;
import com.zhsoft.fretting.model.user.CancleOrderResp;
import com.zhsoft.fretting.model.user.ImageResp;
import com.zhsoft.fretting.model.user.InvestPlanResp;
import com.zhsoft.fretting.model.user.MyBonusResp;
import com.zhsoft.fretting.model.user.OccupationResp;
import com.zhsoft.fretting.model.user.PersonInfoResp;
import com.zhsoft.fretting.model.user.PhoneResp;
import com.zhsoft.fretting.model.user.ResultDetailResp;
import com.zhsoft.fretting.model.user.RiskInfoResp;
import com.zhsoft.fretting.model.user.SelfChooseResp;
import com.zhsoft.fretting.model.user.TransactionResp;
import com.zhsoft.fretting.model.user.UpdateBonusResp;
import com.zhsoft.fretting.model.user.UserAccountResp;
import com.zhsoft.fretting.params.CommonReqData;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.zhsoft.fretting.net.HttpContent.bonus_his_page;
import static com.zhsoft.fretting.net.HttpContent.bonus_xg_details;
import static com.zhsoft.fretting.net.HttpContent.bonus_xg_modifybonus;
import static com.zhsoft.fretting.net.HttpContent.bonus_xg_page;
import static com.zhsoft.fretting.net.HttpContent.buy_fund;
import static com.zhsoft.fretting.net.HttpContent.buy_fund_calculation;
import static com.zhsoft.fretting.net.HttpContent.buy_on_fund;
import static com.zhsoft.fretting.net.HttpContent.buy_updata_data;
import static com.zhsoft.fretting.net.HttpContent.change_bankcard;
import static com.zhsoft.fretting.net.HttpContent.change_bankcard_check;
import static com.zhsoft.fretting.net.HttpContent.change_my_information;
import static com.zhsoft.fretting.net.HttpContent.change_phone_index;
import static com.zhsoft.fretting.net.HttpContent.change_phone_save;
import static com.zhsoft.fretting.net.HttpContent.change_phone_sendcode;
import static com.zhsoft.fretting.net.HttpContent.common_bank_list;
import static com.zhsoft.fretting.net.HttpContent.find_fixed_fund_like;
import static com.zhsoft.fretting.net.HttpContent.find_fund_like;
import static com.zhsoft.fretting.net.HttpContent.fund_home;
import static com.zhsoft.fretting.net.HttpContent.fund_invest_time;
import static com.zhsoft.fretting.net.HttpContent.fund_times_save;
import static com.zhsoft.fretting.net.HttpContent.fund_times_succdetails;
import static com.zhsoft.fretting.net.HttpContent.fund_type_fixed;
import static com.zhsoft.fretting.net.HttpContent.get_home;
import static com.zhsoft.fretting.net.HttpContent.get_next_time;
import static com.zhsoft.fretting.net.HttpContent.get_occupation;
import static com.zhsoft.fretting.net.HttpContent.image_code;
import static com.zhsoft.fretting.net.HttpContent.my_bankcard;
import static com.zhsoft.fretting.net.HttpContent.my_informationpage;
import static com.zhsoft.fretting.net.HttpContent.my_times_buy_detail;
import static com.zhsoft.fretting.net.HttpContent.my_times_buy_index;
import static com.zhsoft.fretting.net.HttpContent.my_times_buy_state_change;
import static com.zhsoft.fretting.net.HttpContent.newest_fund;
import static com.zhsoft.fretting.net.HttpContent.open_account;
import static com.zhsoft.fretting.net.HttpContent.open_remitbranch_bank;
import static com.zhsoft.fretting.net.HttpContent.password_change_login;
import static com.zhsoft.fretting.net.HttpContent.password_change_trade;
import static com.zhsoft.fretting.net.HttpContent.password_check;
import static com.zhsoft.fretting.net.HttpContent.password_phonecode;
import static com.zhsoft.fretting.net.HttpContent.password_reset;
import static com.zhsoft.fretting.net.HttpContent.phone_code;
import static com.zhsoft.fretting.net.HttpContent.purchase;
import static com.zhsoft.fretting.net.HttpContent.redeem_init;
import static com.zhsoft.fretting.net.HttpContent.risk_question;
import static com.zhsoft.fretting.net.HttpContent.send_phone_code;
import static com.zhsoft.fretting.net.HttpContent.setup_index;
import static com.zhsoft.fretting.net.HttpContent.share_out_bonus_trade;
import static com.zhsoft.fretting.net.HttpContent.single_trade_query;
import static com.zhsoft.fretting.net.HttpContent.trade_password_check;
import static com.zhsoft.fretting.net.HttpContent.trade_password_phonecode;
import static com.zhsoft.fretting.net.HttpContent.trade_password_reset;
import static com.zhsoft.fretting.net.HttpContent.trade_query;
import static com.zhsoft.fretting.net.HttpContent.user_follow_data;
import static com.zhsoft.fretting.net.HttpContent.user_login;
import static com.zhsoft.fretting.net.HttpContent.user_register;
import static com.zhsoft.fretting.net.HttpContent.withdraw_apply_detail;
import static com.zhsoft.fretting.net.HttpContent.withdraw_apply_list;
import static com.zhsoft.fretting.net.HttpContent.withdraw_apply_operate;
import static com.zhsoft.fretting.net.HttpContent.withdraw_apply_succDetail;

/**
 * Created by ${Yis}
 * data: 2017/11/21
 */

public interface HttpUtil {

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
    Flowable<BaseResp> getPhoneCode(@Body CommonReqData reqData);

    //开户绑卡
    @Headers("appType:Android")
    @POST(open_account)
    Flowable<BaseResp> openAccount(@Body CommonReqData reqData);

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
    Flowable<BaseResp> goRiskTest(@Body String reqData);

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
    Flowable<BaseResp> changeBankCard(@Body CommonReqData reqData);

    //发送短信验证码 不需要图片验证码
    @Headers("appType:Android")
    @POST(send_phone_code)
    Flowable<BaseResp> sendPhoneCode(@Body CommonReqData reqData);

    //我的手机号码
    @Headers("appType:Android")
    @POST(change_phone_index)
    Flowable<PhoneResp> changePhoneIndex(@Body CommonReqData reqData);

    //发送短信验证码 不需要图片验证码 更换手机号码
    @Headers("appType:Android")
    @POST(change_phone_sendcode)
    Flowable<BaseResp> changePhoneSendcode(@Body CommonReqData reqData);

    //更换手机号
    @Headers("appType:Android")
    @POST(change_phone_save)
    Flowable<BaseResp> changePhoneSave(@Body CommonReqData reqData);

    //变更登录密码
    @Headers("appType:Android")
    @POST(password_change_login)
    Flowable<BaseResp> passwordChangeLogin(@Body CommonReqData reqData);

    //变更交易密码
    @Headers("appType:Android")
    @POST(password_change_trade)
    Flowable<BaseResp> passwordChangeTrade(@Body CommonReqData reqData);

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
    Flowable<BaseResp> changeMyInformation(@Body CommonReqData reqData);

    //找回登录密码 短信验证码
    @Headers("appType:Android")
    @POST(password_phonecode)
    Flowable<BaseResp> passwordPhoneCode(@Body CommonReqData reqData);

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
    Flowable<BaseResp> tradePasswordPhoneCode(@Body CommonReqData reqData);

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
    @POST(purchase)
    Flowable<FundStatusResp> purchase(@Body CommonReqData reqData);

    //搜索基金
    @Headers("appType:Android")
    @POST(find_fund_like)
    Flowable<NewestFundResp> findFundLike(@Body CommonReqData reqData);

    //搜索定投
    @Headers("appType:Android")
    @POST(find_fixed_fund_like)
    Flowable<NewestFundResp> findFixedFundLike(@Body CommonReqData reqData);

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
    Flowable<InvestResp> fundTimesSuccDetails(@Body CommonReqData reqData);

    //优选定投
    @Headers("appType:Android")
    @POST(fund_type_fixed)
    Flowable<NewestFundResp> fundTypeFixedOrderBy(@Body CommonReqData reqData);

    //自选基金
    @Headers("appType:Android")
    @POST(user_follow_data)
    Flowable<SelfChooseResp> selfChooseFund(@Body CommonReqData reqData);

    //我的定投
    @Headers("appType:Android")
    @POST(my_times_buy_index)
    Flowable<InvestPlanResp> myTimesBuyIndex(@Body CommonReqData reqData);

    //定投详情
    @Headers("appType:Android")
    @POST(my_times_buy_detail)
    Flowable<InvestResp> myTimesBuyDetail(@Body CommonReqData reqData);

    //改变状态
    @Headers("appType:Android")
    @POST(my_times_buy_state_change)
    Flowable<BaseResp> myTimesBuyStateChange(@Body CommonReqData reqData);

    //交易查询
    @Headers("appType:Android")
    @POST(trade_query)
    Flowable<TransactionResp> tradeQueryData(@Body CommonReqData reqData);

    //分红方式
    @Headers("appType:Android")
    @POST(share_out_bonus_trade)
    Flowable<TransactionResp> shareOutBonusTradeQuery(@Body CommonReqData reqData);

    //分红方式
    @Headers("appType:Android")
    @POST(bonus_his_page)
    Flowable<MyBonusResp> bonusHisPage(@Body CommonReqData reqData);

    //修改分红方式列表
    @Headers("appType:Android")
    @POST(bonus_xg_page)
    Flowable<UpdateBonusResp> bonusXgPage(@Body CommonReqData reqData);

    //修改分红方式详情
    @Headers("appType:Android")
    @POST(bonus_xg_details)
    Flowable<UpdateBonusResp> bonusXgDeatil(@Body CommonReqData reqData);

    //修改分红方式详情
    @Headers("appType:Android")
    @POST(bonus_xg_modifybonus)
    Flowable<BaseResp> bonusXgModifyBonus(@Body CommonReqData reqData);

    //单只基金 交易查询
    @Headers("appType:Android")
    @POST(single_trade_query)
    Flowable<TransactionResp> singleTradeQueryData(@Body CommonReqData reqData);

    //修改定投
    @Headers("appType:Android")
    @POST(buy_updata_data)
    Flowable<InvestResp> buyUpdataData(@Body CommonReqData reqData);

    //定投计划
    @Headers("appType:Android")
    @POST(buy_on_fund)
    Flowable<InvestPlanResp> buyOnFundData(@Body CommonReqData reqData);

    //撤单列表页
    @Headers("appType:Android")
    @POST(withdraw_apply_list)
    Flowable<CancleOrderResp> withdrawApplyList(@Body CommonReqData reqData);

    //撤单交易详情页
    @Headers("appType:Android")
    @POST(withdraw_apply_detail)
    Flowable<ResultDetailResp> withdrawApplyDetail(@Body CommonReqData reqData);

    //撤单操作
    @Headers("appType:Android")
    @POST(withdraw_apply_operate)
    Flowable<BaseResp> withdrawApplyOperate(@Body CommonReqData reqData);

    //撤单成功
    @Headers("appType:Android")
    @POST(withdraw_apply_succDetail)
    Flowable<ResultDetailResp> withdrawApplySuccDetail(@Body CommonReqData reqData);

    //撤单成功
    @Headers("appType:Android")
    @POST(setup_index)
    Flowable<RiskInfoResp> setupIndex(@Body CommonReqData reqData);

    //赎回准备
    @Headers("appType:Android")
    @POST(redeem_init)
    Flowable<SellResp> sellFundPre(@Body CommonReqData reqData);

    //赎回准备
    @Headers("appType:Android")
    @POST(open_remitbranch_bank)
    Flowable<FundStatusResp> sellFundSure(@Body CommonReqData reqData);

    //计算这个手续费
    @Headers("appType:Android")
    @POST(buy_fund_calculation)
    Flowable<CalculationResp> buyFundCalculation(@Body CommonReqData reqData);
}
