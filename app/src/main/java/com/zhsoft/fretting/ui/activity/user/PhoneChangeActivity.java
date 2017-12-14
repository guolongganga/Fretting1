package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.ui.widget.CountdownButton;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/13 13:05
 * 描述：
 */

public class PhoneChangeActivity extends XActivity {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.head_right) Button headRight;
    @BindView(R.id.phone_number) EditText phoneNumber;
    @BindView(R.id.verify_code) EditText verifyCode;
    @BindView(R.id.get_verify_code) CountdownButton getVerifyCode;
    @BindView(R.id.btn_save) Button btnSave;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_phone_change;
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
        headTitle.setText("变更手机号");
    }

    @Override
    public void initEvents() {
//        final String phone = ;
//        final String code = ;

        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
                    showToast("手机号码不能为空");
                    return;
                }
                if (phoneNumber.getText().toString().length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                //TODO 发送请求验证码成功
                getVerifyCode.start();
                //TODO 重试，发送请求验证码成功
                //getVerifyCode.cancel();
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
                    showToast("手机号码不能为空");
                    return;
                }
                if (phoneNumber.getText().toString().length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (TextUtils.isEmpty(verifyCode.getText().toString())) {
                    showToast("验证码不能为空");
                    return;
                }
                //TODO 将验证码和手机号码提交到后台
                showToast("保存更换的手机号");
            }
        });

    }


}
