//package com.zhsoft.fretting.present.user;
//
//import android.app.Activity;
//
//import com.zhsoft.fretting.model.user.ImageResp;
//import com.zhsoft.fretting.net.Api;
//import com.zhsoft.fretting.params.CommonReqData;
//import com.zhsoft.fretting.params.MessageCodeParams;
//
//import cn.droidlover.xdroidmvp.log.XLog;
//import cn.droidlover.xdroidmvp.mvp.XActivity;
//import cn.droidlover.xdroidmvp.mvp.XPresent;
//import cn.droidlover.xdroidmvp.net.ApiSubscriber;
//import cn.droidlover.xdroidmvp.net.NetError;
//import cn.droidlover.xdroidmvp.net.XApi;
//
///**
// * 作者：sunnyzeng on 2017/12/22 11:28
// * 描述：
// */
//
//public class ImgCodePersenter{
//    private XActivity activity;
//
//    public ImgCodePersenter(XActivity activity) {
//        this.activity = activity;
//    }
//
//    /**
//     * 获取图片验证码
//     */
//    public void getImgCode(final ISuccessDataLoadView dataLoadView) {
//        CommonReqData reqData = new CommonReqData();
//        reqData.setData("");
//
//        Api.getApi()
//                .getImageCode(reqData)
//                .compose(XApi.<ImageResp>getApiTransformer())
//                .compose(XApi.<ImageResp>getScheduler())
//                .compose(activity.<ImageResp>bindToLifecycle())
//                .subscribe(new ApiSubscriber<ImageResp>() {
//                    @Override
//                    protected void onFail(NetError error) {
//                        error.printStackTrace();
//                    }
//
//                    @Override
//                    public void onNext(ImageResp imageResp) {
//                        if (imageResp != null && imageResp.getStatus() == 200) {
//                            if (imageResp.getData() != null) {
//                                dataLoadView.callData(imageResp.getData());
////                                getV().getImageCode();
//                            }
//
//                        } else {
////                            getV().requestMessageFail();
////                            getV().showToast(imageResp.getMessage());
//                            XLog.e("返回数据为空");
//                        }
//                    }
//                });
//
//    }
//
//    /**
//     * 获取短信验证码
//     *
//     * @param phone
//     */
//    public void getMessageCode(String phone, String imgCode, String image_code_id,ISuccessDataLoadView dataLoadView) {
//        CommonReqData reqData = new CommonReqData();
//        MessageCodeParams params = new MessageCodeParams();
//        params.setPhone(phone);
//        params.setImage_code(imgCode);
//        params.setImage_code_id(image_code_id);
//        reqData.setData(params);
//
//        //成功
//        dataLoadView.onSuccessful();
//    }
//}
