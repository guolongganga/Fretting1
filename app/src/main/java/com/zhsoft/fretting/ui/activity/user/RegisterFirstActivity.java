package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.present.user.RegisterFirstPresent;
import com.zhsoft.fretting.ui.widget.CountdownButton;
import com.zhsoft.fretting.widget.ChenJingET;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/11 17:35
 * 描述：基金开户 第一步界面
 */

public class RegisterFirstActivity extends XActivity<RegisterFirstPresent> {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.get_verify_code) CountdownButton getVerifyCode;
    @BindView(R.id.head_right) Button headRight;
    @BindView(R.id.phone_number) EditText phoneNumber;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.password_again) EditText passwordAgain;
    @BindView(R.id.msg_code) EditText msgCode;
    @BindView(R.id.to_login) TextView toLogin;
    @BindView(R.id.message_fail) TextView messageFail;
    @BindView(R.id.btn_next) Button btnNext;
    private HttpLoadingDialog httpLoadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_register_first;
    }

    @Override
    public RegisterFirstPresent newP() {
        return new RegisterFirstPresent();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        ChenJingET.assistActivity(context);
        httpLoadingDialog = new HttpLoadingDialog(context);
        headTitle.setText("基金开户");

    }

    @Override
    public void initEvents() {

        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(LoginActivity.class);
            }
        });

        messageFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("收不到短信验证码");
            }
        });

        getVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phoneNumber.getText().toString();
                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    showToast("手机号码不能为空");
                    return;
                }

                if (phone.length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }

                getVerifyCode.start();

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = getText(phoneNumber);
                String pwd = getText(password);
                String pwdagain = getText(passwordAgain);
                String code = getText(msgCode);
                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(phone)) {
                    showToast("手机号码不能为空");
                    return;
                }
                if (phone.length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (!isNotEmpty(pwd)) {
                    showToast("登录密码不能为空");
                }
                if (!isNotEmpty(pwd)) {
                    showToast("登录密码不能为空");
                }
                if (pwd.length() < 8) {
                    showToast("登录密码为8-16位数字、字母、特殊字符等");
                    return;
                }
                if (!isNotEmpty(pwdagain)) {
                    showToast("登录密码不能为空");
                }
                if (pwdagain.length() < 8) {
                    showToast("登录密码为8-16位数字、字母、特殊字符等");
                    return;
                }
                if (!isNotEmpty(code)) {
                    //注册第一步
                    showToast("验证码不能为空");
                    return;
                }
                //TODO 下一步
                httpLoadingDialog.visible("加载中...");
                getP().register(phone, pwd);


            }
        });
    }

    public void requestFail() {
        httpLoadingDialog.dismiss();
    }

    public void commitSuccess(Object data) {
        httpLoadingDialog.dismiss();
        startActivity(RegisterSecondActivity.class);
    }

}
