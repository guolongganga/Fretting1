package com.zhsoft.fretting.net;

import com.zhsoft.fretting.model.BaseModel;
import com.zhsoft.fretting.model.LoginModel;
import com.zhsoft.fretting.model.TaetModel;
import com.zhsoft.fretting.params.CommonReqData;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.zhsoft.fretting.net.HttpContent.test_test;
import static com.zhsoft.fretting.net.HttpContent.user_find_password;
import static com.zhsoft.fretting.net.HttpContent.user_login;

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
    Flowable<LoginModel> login(@Body CommonReqData reqData);

    //登录
    @Headers("apptype:Android")
    @POST(user_find_password)
    Flowable<BaseModel> findPassword(@Body CommonReqData reqData);

}
