package com.zhsoft.fretting.net;

import com.zhsoft.fretting.model.BaseModel;
import com.zhsoft.fretting.model.Resp;
import com.zhsoft.fretting.model.TaetModel;
import com.zhsoft.fretting.params.CommonReqData;

import io.reactivex.Flowable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

import static com.zhsoft.fretting.net.HttpContent.test_test;

/**
 * Created by ${Yis}
 * data: 2017/11/21
 */

public interface HttpUtil {

    //获取首页商品列表数据
    @Headers("apptype:Android")
    @POST(test_test)
    Flowable<TaetModel> getTest(@Body CommonReqData request);


}
