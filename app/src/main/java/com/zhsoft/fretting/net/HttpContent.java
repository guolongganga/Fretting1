package com.zhsoft.fretting.net;

/**
 * Created by ${sunnyzeng}
 * data: 2017/12/04
 */

public class HttpContent {

    //    Base地址
//    public static String baseUrl = "http://jdw.twszbhwl.com/";

    /** 登录 */
    public static final String user_login = "noPermission/login";
    /** 注册 */
    public static final String user_register = "noPermission/regist";
    /** 银行列表 */
    public static final String common_bank_list = "noPermission/c401SelectBank";
    /** 图片验证码 */
    public static final String image_code = "noPermission/imageBase64";
    /** 短信验证码 */
    public static final String phone_code = "noPermission/phoneCode";
    /** 开户绑卡 */
    public static final String open_account = "permissionCheck/openAccount";
    /** 我的资产 */
    public static final String fund_home = "permissionCheck/fundHome";
    /** 基金页 */
//    public static final String newest_fund = "noPermission/s428NewestFund";
    public static final String newest_fund = "noPermission/fundTypeListOrderBy";
    /** 主页 */
    public static final String get_home = "noPermission/getHome";
    /** 我的银行卡 */
    public static final String my_bankcard = "permissionCheck/fundHome/turnToMyBankCard";
    /** 检查是否可以更换银行卡 */
    public static final String change_bankcard_check = "permissionCheck/fundHome/changeBankCardCheck";
    /** 更换银行卡操作 */
    public static final String change_bankcard = "permissionCheck/fundHome/changeBankCard";
    /** 发送短信验证码 不需要图片验证码 更换银行卡 */
    public static final String send_phone_code = "permissionCheck/fundHome/sendPhoneCode";
    /** 我的手机号码 */
    public static final String change_phone_index = "permissionCheck/change/phone/index";
    /** 发送短信验证码 不需要图片验证码 更换手机号码 */
    public static final String change_phone_sendcode = "permissionCheck/change/phone/sendCode";
    /** 更换手机号 */
    public static final String change_phone_save = "permissionCheck/change/phone/save";
    /** 变更登录密码 */
    public static final String password_change_login = "permissionCheck/password/change/login";
    /** 变更交易密码 */
    public static final String password_change_trade = "permissionCheck/password/change/trade";
    /** 获取职业信息 */
    public static final String get_occupation = "permissionCheck/fundHome/getOccupation";
    /** 我的个人信息 */
    public static final String my_informationpage = "permissionCheck/fundHome/MyInformationPage";
    /** 修改个人信息 */
    public static final String change_my_information = "permissionCheck/fundHome/changeMyInformation";
    /** 找回登录密码 的 短信验证码 */
    public static final String password_phonecode = "noPermission/password/phoneCode";
    /** 找回登录密码 第一步 */
    public static final String password_check = "noPermission/password/check";
    /** 找回登录密码 第二步 */
    public static final String password_reset = "noPermission/password/reset";
    /** 找回交易密码 的 短信验证码 */
    public static final String trade_password_phonecode = "noPermission/tradePassword/phoneCode";
    /** 找回交易密码 第一步 */
    public static final String trade_password_check = "noPermission/tradePassword/check";
    /** 找回交易密码 第二步 */
    public static final String trade_password_reset = "noPermission/tradepassword/reset";
    /** 基金立即购买验证 */
    public static final String buy_fund = "permissionCheck/buyFund";
    /** 基金立即购买 */
    public static final String buy_now = "permissionCheck/buyNow";
    /** 基金搜索 */
    public static final String find_fund_like = "noPermission/findFundLike";
    /** 基金定投 */
    public static final String fund_invest_time = "noPermission/fund/times/getData";
    /** 定投 根据选择时间 显示下次扣款日 */
    public static final String get_next_time = "noPermission/fund/times/getnextTime";
    /** 定投购买 */
    public static final String fund_times_save = "noPermission/fund/times/save";
    /** 定投成功页 */
    public static final String fund_times_succdetails = "noPermission/fund/times/succDetails";
    /** 优选定投 */
    public static final String fund_type_fixed = "noPermission/fundTypeFixedOrderBy";


    /************************************************ html页面 **************************************************/

    /** 基金详情页 */
    public static final String fund_detail = "htmlNoPermission/fundDetail";
    /** 风险测评 */
    public static final String risk_question = "htmlNoPermission/risk/getQuestions";


}
