package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.ui.widget.CountdownButton;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/14 10:13
 * 描述：
 */

public class BankCardChangeActivity extends XActivity {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.ll_choose_bank) LinearLayout llChooseBank;
    @BindView(R.id.banck_name) TextView bankName;
    @BindView(R.id.banknumber) EditText banknumber;
    @BindView(R.id.phone) EditText phone;
    @BindView(R.id.msg_code) EditText msgCode;
    @BindView(R.id.get_verify_code) CountdownButton getVerifyCode;
    @BindView(R.id.btn_save) Button btnSave;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_bankcard_change;
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
        headBack.setVisibility(View.VISIBLE);
        headTitle.setText("我的银行卡");
    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        llChooseBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("选择银行卡");
                bankName.setText("招商银行");
            }
        });
        getVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkInput()) {
                    return;
                }

                //TODO 发送请求验证码，getSmsCode(phoneStr, imageCodeStr）

                //TODO 发送请求验证码成功
                getVerifyCode.start();
                //TODO 重试，发送请求验证码成功
                //getVerifyCode.cancel();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkInput()) {
                    return;
                }

                if (TextUtils.isEmpty(msgCode.getText().toString())) {
                    showToast("验证码不能为空");
                    return;
                }
                //TODO 绑定银行卡接口
                showToast("绑定银行卡");
            }
        });

    }

    private boolean checkInput() {
        String phoneNumber = phone.getText().toString().trim();
        if (TextUtils.isEmpty(bankName.getText().toString())) {
            showToast("请选择银行名称");
            return false;
        }
        if (TextUtils.isEmpty(banknumber.getText().toString())) {
            showToast("银行卡号不能为空");
            return false;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            showToast("预留手机号码不能为空");
            return false;
        }
        if (phoneNumber.length() < 11) {
            showToast("请输入正确的手机号码");
            return false;
        }
        return true;
    }


}
