package com.zhsoft.fretting.net;

import com.zhsoft.fretting.model.BaseResp;
import com.zhsoft.fretting.model.LoginResp;
import com.zhsoft.fretting.model.TaetResp;
import com.zhsoft.fretting.model.user.BankResp;
import com.zhsoft.fretting.model.user.ImageResp;
import com.zhsoft.fretting.params.CommonReqData;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.zhsoft.fretting.net.HttpContent.common_bank_list;
import static com.zhsoft.fretting.net.HttpContent.image_code;
import static com.zhsoft.fretting.net.HttpContent.password_check;
import static com.zhsoft.fretting.net.HttpContent.password_reset;
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


}
