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

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/13 15:48
 * 描述：找回登录密码第一步
 */

public class FindPwdLoginFirstActivity extends XActivity {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.head_right) Button headRight;
    @BindView(R.id.phone) EditText phone;
    @BindView(R.id.image_code) EditText imageCode;
    @BindView(R.id.image) ImageView image;
    @BindView(R.id.msg_code) EditText msgCode;
    @BindView(R.id.get_verify_code) CountdownButton getVerifyCode;
    @BindView(R.id.btn_next) Button btnNext;

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
        initView();
    }

    private void initView() {
        headTitle.setText("找回登录密码");
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
                String phoneNumber = phone.getText().toString().trim();
                String imgcode = imageCode.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    showToast("手机号码不能为空");
                    return;
                }
                if (phoneNumber.length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (TextUtils.isEmpty(imgcode)) {
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
                String phoneNumber = phone.getText().toString().trim();
                String imgcode = imageCode.getText().toString().trim();
                String messageCode = msgCode.getText().toString().trim();
                if (TextUtils.isEmpty(phoneNumber)) {
                    showToast("手机号码不能为空");
                    return;
                }
                if (phoneNumber.length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (TextUtils.isEmpty(imgcode)) {
                    showToast("图形验证码不能为空");
                    return;
                }
                if (TextUtils.isEmpty(messageCode)) {
                    showToast("短信验证码不能为空");
                    return;
                }
                startActivity(FindPwdLoginSecondActivity.class);
            }
        });



    }

}
