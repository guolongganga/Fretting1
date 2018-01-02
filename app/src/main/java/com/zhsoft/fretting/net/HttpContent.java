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
    public static final String password_reset = "/noPermission/password/reset";
    /** 注册 */
    public static final String user_register = "noPermission/regist";
    /** 银行列表 */
    public static final String common_bank_list = "noPermission/c401SelectBank";
    /** 图片验证码 */
    public static final String image_code = "noPermission/imageBase64";
    /** 短信验证码 */
    public static final String phone_code = "noPermission/phoneCode";
    /** 开户绑卡 */
    public static final String open_account = "/permissionCheck/openAccount";
    /** 我的资产 */
    public static final String fund_home = "/permissionCheck/fundHome";
    /** 基金页 */
    public static final String newest_fund = "noPermission/s428NewestFund";
    /** 主页 */
    public static final String get_home = "noPermission/getHome";
    /** 风险测评 */
    public static final String risk_question = "/htmlNoPermission/risk/getQuestions";

}
