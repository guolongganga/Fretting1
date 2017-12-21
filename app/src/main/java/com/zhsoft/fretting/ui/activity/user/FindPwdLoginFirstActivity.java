package com.zhsoft.fretting.ui.activity.user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.model.user.ImageResp;
import com.zhsoft.fretting.present.user.FindPwdLoginFirstPresent;
import com.zhsoft.fretting.ui.widget.CountdownButton;
import com.zhsoft.fretting.utils.Base64ImageUtil;
import com.zhsoft.fretting.widget.ChenJingET;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/13 15:48
 * 描述：找回登录密码第一步
 */

public class FindPwdLoginFirstActivity extends XActivity<FindPwdLoginFirstPresent> {
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 手机号码 */
    @BindView(R.id.phone) EditText phone;
    /** 图片验证码 */
    @BindView(R.id.image_code) EditText imageCode;
    /** 图片 */
    @BindView(R.id.image) ImageView image;
    /** 短信验证码 */
    @BindView(R.id.msg_code) EditText msgCode;
    /** 获取短信验证码 */
    @BindView(R.id.get_verify_code) CountdownButton getVerifyCode;
    /** 下一步按钮 */
    @BindView(R.id.btn_next) Button btnNext;

    /** 加载框 */
    private HttpLoadingDialog httpLoadingDialog;
    /** 图片验证码id */
    private String image_code_id;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_findpwd_first;
    }

    @Override
    public FindPwdLoginFirstPresent newP() {
        return new FindPwdLoginFirstPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        //解决键盘弹出遮挡不滚动问题
        ChenJingET.assistActivity(context);
        httpLoadingDialog = new HttpLoadingDialog(context);
        //设置标题
        headTitle.setText("找回登录密码");
        //访问图片验证码请求接口
        httpLoadingDialog.visible();
        getP().getImageCode();
    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //访问图片验证码请求接口
                httpLoadingDialog.visible();
                getP().getImageCode();
            }
        });
        getVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = getText(phone);
                String imgcode = getText(imageCode);

                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(phoneNumber)) {
                    showToast("手机号码不能为空");
                    return;
                }
                if (phoneNumber.length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (!isNotEmpty(imgcode)) {
                    showToast("图形验证码不能为空");
                    return;
                }
                //TODO 发送请求验证码，getSmsCode(phoneStr, imageCodeStr）

                //TODO 发送请求验证码成功
                getVerifyCode.start();
                //TODO 重试，发送请求验证码成功
                //getVerifyCode.cancel();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = getText(phone);
                String imgcode = getText(imageCode);
                String messageCode = getText(msgCode);

                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(phoneNumber)) {
                    showToast("手机号码不能为空");
                    return;
                }
                if (phoneNumber.length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (!isNotEmpty(imgcode)) {
                    showToast("图形验证码不能为空");
                    return;
                }
                if (!isNotEmpty(messageCode)) {
                    showToast("短信验证码不能为空");
                    return;
                }
                //显示加载框
                httpLoadingDialog.visible("加载中...");
                getP().findCheckPassword(phoneNumber, messageCode);

            }
        });


    }


    /**
     * 请求失败
     */
    public void requestFail() {
        httpLoadingDialog.dismiss();
        showToast("验证失败");
    }

    /**
     * 手机和验证码验证成功
     *
     * @param data
     */
    public void disposeUpdateResult(Object data) {
        httpLoadingDialog.dismiss();

        //传递电话号码
        FindPwdLoginSecondActivity.launch(this, getText(phone));

        finish();
    }

    /**
     * 获取图片验证码
     *
     * @param data
     */
    public void getImageCode(ImageResp data) {
        httpLoadingDialog.dismiss();
        //获取 图片Base64 字符串
        String strimage = data.getBase64Image();
        image_code_id = data.getImageCodeId();
        if (!TextUtils.isEmpty(strimage)) {
            //将Base64图片串转换成Bitmap
            Bitmap bitmap = Base64ImageUtil.base64ToBitmap(strimage);
            image.setImageBitmap(bitmap);
        }
    }

    public void requestMessageFail() {
        httpLoadingDialog.dismiss();
    }
}
