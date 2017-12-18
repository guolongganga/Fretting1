package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.ui.widget.CountdownButton;
import com.zhsoft.fretting.widget.ChenJingET;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/13 15:48
 * 描述：找回交易密码第一步
 */

public class FindPwdTradeFirstActivity extends XActivity {
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
    /** 获取验证码 */
    @BindView(R.id.get_verify_code) CountdownButton getVerifyCode;
    /** 下一步按钮 */
    @BindView(R.id.btn_next) Button btnNext;

    /** 加载框 */
    private HttpLoadingDialog httpLoadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_findpwd_first;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        //解决键盘弹出遮挡不滚动问题
        ChenJingET.assistActivity(context);
        httpLoadingDialog = new HttpLoadingDialog(context);
        //设置标题
        headTitle.setText("找回交易密码");
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
                //TODO 获取图片验证码
            }
        });
        getVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = getText(phone);
                String imgcode = getText(imageCode);
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(phoneNumber)) {
                    //表单验证

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
                //跳转找回密码第二步
                startActivity(FindPwdTradeSecondActivity.class);
            }
        });


    }

}