package com.zhsoft.fretting.net;

/**
 * Created by ${sunnyzeng}
 * data: 2017/12/04
 */

public class HttpContent {

    //    Base地址
//    public static String baseUrl = "http://jdw.twszbhwl.com/";

    /** 测试 */
    public static final String test_test = "noPermission/testCache";
    /** 登录 */
    public static final String user_login = "noPermission/login";
    /** 找回登录密码 第一步 */
    public static final String password_check = "noPermission/password/check";
    /** 找回登录密码 第二步 */
    public static final String password_reset = "noPermission/password/reset";
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
    public static final String newest_fund = "noPermission/s428NewestFund";
    /** 主页 */
    public static final String get_home = "noPermission/getHome";
    /** 风险测评 */
    public static final String risk_question = "htmlNoPermission/risk/getQuestions";
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


}
