package com.zhsoft.fretting.net;

import com.zhsoft.fretting.model.BaseModel;
import com.zhsoft.fretting.model.LoginModel;
import com.zhsoft.fretting.model.TaetModel;
import com.zhsoft.fretting.params.CommonReqData;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.zhsoft.fretting.net.HttpContent.common_bank_list;
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
    Flowable<TaetModel> getTest(@Body CommonReqData request);

    //登录
    @Headers("apptype:Android")
    @POST(user_login)
    Flowable<BaseModel<LoginModel>> login(@Body CommonReqData reqData);

    //找回密码 第一步 验证手机号
    @Headers("apptype:Android")
    @POST(password_check)
    Flowable<BaseModel> findPasswordCheck(@Body CommonReqData reqData);

    //找回密码 第二步
    @Headers("apptype:Android")
    @POST(password_reset)
    Flowable<BaseModel> findPasswordReset(@Body CommonReqData reqData);

    //注册 第一步 验证手机号
    @Headers("apptype:Android")
    @POST(user_register)
    Flowable<BaseModel> register(@Body CommonReqData reqData);

    //请求银行卡列表
    @Headers("apptype:Android")
    @POST(common_bank_list)
    Flowable<BaseModel> bankList(@Body CommonReqData reqData);


}
