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
import com.zhsoft.fretting.widget.ChenJingET;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/14 10:13
 * 描述：变更银行卡 第二步
 */

public class BankCardChangeActivity extends XActivity {
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 选择银行 */
    @BindView(R.id.ll_choose_bank) LinearLayout llChooseBank;
    /** 银行名称 */
    @BindView(R.id.banck_name) TextView bankName;
    /** 银行卡号 */
    @BindView(R.id.banknumber) EditText banknumber;
    /** 预留手机号 */
    @BindView(R.id.phone) EditText phone;
    /** 短信验证码 */
    @BindView(R.id.msg_code) EditText msgCode;
    /** 获取短信验证码 */
    @BindView(R.id.get_verify_code) CountdownButton getVerifyCode;
    /** 保存按钮 */
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
        //解决键盘弹出遮挡不滚动问题
        ChenJingET.assistActivity(context);
        //设置标题
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
                String phoneNumber = getText(phone);

                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(phoneNumber)) {
                    showToast("预留手机号码不能为空");
                    return;
                }
                if (phoneNumber.length() < 11) {
                    showToast("请输入正确的手机号码");
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

                String phoneNumber = getText(phone);

                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(getText(bankName))) {
                    showToast("请选择银行名称");
                    return;
                }
                if (!isNotEmpty(getText(banknumber))) {
                    showToast("银行卡号不能为空");
                    return;
                }
                if (!isNotEmpty(phoneNumber)) {
                    showToast("预留手机号码不能为空");
                    return;
                }
                if (phoneNumber.length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (!isNotEmpty(getText(msgCode))) {
                    showToast("验证码不能为空");
                    return;
                }
                //TODO 绑定银行卡接口
                showToast("绑定银行卡");
            }
        });

    }

}
