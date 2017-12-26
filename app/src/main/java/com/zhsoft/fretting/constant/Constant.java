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

    //用户名 存本地
    public static final String USER_NAME = BASE_CONSTANT + "user_name";

    //是否开户 1位未开户  0 位开户 存本地
    public static final String IS_OPEN_ACCOUNT = BASE_CONSTANT + "is_open_account";

    //已开户
    public static final String ALREADY_OPEN_ACCOUNT ="0" ;

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

    //跳转银行列表requestcode
    public static final int TO_BANKLIST = 100;

    //银行卡列表选择之后回到之前页面 resultcode
    public static final int BANKLIST_RESULT_CODE = 200;

    //选择的银行
    public static final String CHOOSE_BANCK = BASE_CONSTANT + "";

    //注册传递电话号码
    public static final String PHONE = BASE_CONSTANT + "phone";

    //注册成功传递姓名
    public static final String NAME = BASE_CONSTANT + "name";

    //注册成功传递身份证号
    public static final String CERT_NO = BASE_CONSTANT + "cert_no";


}
