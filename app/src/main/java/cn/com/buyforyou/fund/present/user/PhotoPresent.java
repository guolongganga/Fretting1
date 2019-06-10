package cn.com.buyforyou.fund.present.user;

import android.util.Log;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.buyforyou.fund.R;
import cn.com.buyforyou.fund.constant.Constant;
import cn.com.buyforyou.fund.model.user.PhotoResp;
import cn.com.buyforyou.fund.model.user.UserInforResp;
import cn.com.buyforyou.fund.model.user.UserInformationResp;
import cn.com.buyforyou.fund.net.Api;
import cn.com.buyforyou.fund.params.CommonReqData;
import cn.com.buyforyou.fund.ui.activity.user.PhotoActivity;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XPresent;
import cn.droidlover.xdroidmvp.net.ApiSubscriber;
import cn.droidlover.xdroidmvp.net.NetError;
import cn.droidlover.xdroidmvp.net.XApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by guolonggang on 2019/4/17.
 * Description:
 */

public class PhotoPresent extends XPresent<PhotoActivity> {
    public void getImage(String token,String userId)
    {
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);
        Api.getApi()
                .userMessage(reqData)
                .compose(XApi.<UserInformationResp>getApiTransformer())
                .compose(XApi.<UserInformationResp>getScheduler())
                .compose(getV().<UserInformationResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<UserInformationResp>() {
                    @Override
                    protected void onFail(NetError error) {
                        getV().getImageFail();
                        getV().showToast(R.string.request_error);
                    }

                    @Override
                    public void onNext(UserInformationResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().getImageData(resp);
                        } else if (resp != null && resp.getStatus() == Constant.NO_LOGIN_STATUS) {
                            getV().showToast(resp.getMessage());
                           // getV().areadyLogout();
                        } else {
                            getV().getImageFail();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }

    private static final String TAG = "PhotoPresent";
    private File file;
    private RequestBody requestBody;
    private String str;
    private MultipartBody.Part body;
    private MultipartBody.Part body2;
    private MultipartBody.Part body3;
    private MultipartBody.Part body4;
    private MultipartBody.Part[] bodys = new MultipartBody.Part[4];

    /**
     * 上传图片
     *
     * @param
     * @param
     */
    public void ftpData(String token, String userId, HashMap<String, String> path) {
        HashMap<String, Object> requestBodyHashMap = new HashMap<>();
        CommonReqData reqData = new CommonReqData();
        reqData.setToken(token);
        reqData.setUserId(userId);
        requestBodyHashMap.put("reqData", reqData);


           for (String flag : path.keySet()) {
               // str = path.get(flag);
               file = new File(path.get(flag));
               requestBody = RequestBody.create(MediaType.parse("image/*"), file);
               //map.put("picMap", requestBody);
               if (flag.equals("idCardZ")) {
                   body = MultipartBody.Part.createFormData("idCardZ", file.getName(), requestBody);

               } else if (flag.equals("idCardF")) {
                   body2 = MultipartBody.Part.createFormData("idCardF", file.getName(), requestBody);
               } else if (flag.equals("bankCardZ")) {
                   body3 = MultipartBody.Part.createFormData("bankCardZ", file.getName(), requestBody);
               } else if (flag.equals("bankCardF")) {
                   body4 = MultipartBody.Part.createFormData("bankCardF", file.getName(), requestBody);
               }
           }






        Api.getApi()
                .ftpUpload(requestBodyHashMap, body, body2, body3, body4)

                .compose(XApi.<PhotoResp>getApiTransformer())
                .compose(XApi.<PhotoResp>getScheduler())
                .compose(getV().<PhotoResp>bindToLifecycle())
                .subscribe(new ApiSubscriber<PhotoResp>() {


                    @Override
                    protected void onFail(NetError error) {
                        getV().requestFailed();
                        getV().showToast(R.string.request_error);
                    }

                    public void onNext(PhotoResp resp) {
                        if (resp != null && resp.getStatus() == 200) {
                            getV().requestSuccess(resp);
                        } else {
                            getV().requestFailed();
                            getV().showToast(resp.getMessage());
                            XLog.e("返回数据为空");
                        }
                    }
                });
    }

}
