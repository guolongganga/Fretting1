package com.zhsoft.fretting.constant;

/**
 * 作者：sunnyzeng on 2017/12/5
 * 描述：静态常量
 */

public class Constant {

    public static final String BASE_CONSTANT = "fretting_";
    //token 登录标识 存本地
    public static final String TOKEN = BASE_CONSTANT + "token";

    //userId 用户编号 存本地
    public static final String USERID = BASE_CONSTANT + "userId";

    //手机号 存本地
    public static final String USER_PHONE = BASE_CONSTANT + "user_phone";

    //身份证号
    public static final String USER_CERTNO = BASE_CONSTANT + "user_certno";

    //是否开户 1位未开户  0 位开户 存本地
    public static final String IS_OPEN_ACCOUNT = BASE_CONSTANT + "is_open_account";

    //已开户
    public static final String ALREADY_OPEN_ACCOUNT = "0";

    //密码
    public static final String PWD = BASE_CONSTANT + "pwd";

    //是否第一次使用APP true:未使用
    public static final String FIRST_USE = BASE_CONSTANT + "first_use";

    //32位长度加密Key
    public static final String AESKEY = "0e441613d8a611e784ef6c92bf314e43";

    //WebView标题
    public static final String WEB_TITLE = BASE_CONSTANT + "web_title";

    //WebView链接
    public static final String WEB_LINK = BASE_CONSTANT + "web_link";

    //基金页面中的tab名称
    public static final String FUND_TAB_NAME = BASE_CONSTANT + "fund_tab_name";

    //页面名称，人气产品或基金
    public static final String ACTIVITY_NAME = BASE_CONSTANT + "activity_name";

    //选择的银行
    public static final String CHOOSE_BANCK = BASE_CONSTANT + "";

    //注册传递电话号码
    public static final String PHONE = BASE_CONSTANT + "phone";

    //注册成功传递姓名
    public static final String NAME = BASE_CONSTANT + "name";

    //注册成功传递身份证号
    public static final String CERT_NO = BASE_CONSTANT + "cert_no";

    //修改手机号传递数据
    public static final String CHANGE_PHONE = "change_phone";

    //修改银行卡传递数据
    public static final String CHANGE_BANK = "change_bank";

    //修改银行卡成功
    public static final String CHANGE_BANK_SUCCESS = "0";

    //跳转登录页面标识
    public static final String SKIP_SIGN = "skip_sign";

    //变更登录密码 跳转登录页面 skip_sign
    public static final String CHANGE_LOGIN_ACTIVITY = "300";

    //变更交易密码 跳转登录页面 skip_sign
    public static final String CHANGE_TRADE_ACTIVITY = "301";

    //找回交易密码 跳转登录页面 skip_sign
    public static final String FIND_TRADE_ACTIVITY = "302";
    //web页面 跳转登录页面 skip_sign
    public static final String WEB_ACTIVITY = "303";

    //人气产品
    public static final int POPULARITY = 1;
    //基金主页
    public static final int FUND_INDEX = 2;

    //跳转银行列表 requestcode
    public static final int BANKLIST_ACTIVITY = 100;

    //银行卡列表选择之后回到之前页面 resultcode
    public static final int BANKLIST_RESULT_CODE = 200;

    //去修改手机页面 requestcode
    public static final int CHANGE_PHONE_ACTIVITY = 101;

    //修改手机号码成功后返回我的手机号码页面 resultcode
    public static final int SUCCESS_BACK_PHONE = 201;

    //定投购买 到 修改银行卡 requestcode
    public static final int INVEST_BANK_ACTIVITY = 102;

    //我的银行卡后 返回我的手机号码页面 resultcode
    public static final int SUCCESS_BACK_BANK = 201;

    //投资页的类型，是定投，还是定投修改
    public static final String INVEST_ACTIVITY_TYPE = "invest_activity_type";

    //投资页的类型，定投
    public static final String INVEST_ACTIVITY = "invest_activity";

    //投资页的类型，定投修改
    public static final String INVEST_ACTIVITY_UPDATE = "invest_activity_update";

    //跳转到主页面，我的tab
    public static final String MAIN_MY = "main_my";

    //可以购买跳转购买页面携带参数
    public static final String BUY_FUND_OBJECT = "buy_fund_object";

    //购买验证，未开户，跳转开户页面
    public static final Object TO_OPEN_ACCOUNT = "toOpenAccount";

    //购买验证，补全个人信息，跳转个人信息
    public static final Object TO_PERSON_INFO = "toPersonInfo";

    //购买验证，风险测评，跳转风险测评
    public static final Object TO_RISK_TEST = "toRiskTest";

    //购买验证，验证风险等级，弹出风险等级弹出框
    public static final Object TO_VALIDATE = "toValidate";

    //购买验证，去购买
    public static final Object TO_BUY = "toBuy";

    //购买成功传递数据
    public static final String BUY_SUCCESS_OBJECT = "buy_success_object";

    //基金列表-->基金详情页传递基金数据
    public static final String FUND_RESP_OBJECT ="fund_resp_object" ;
}
