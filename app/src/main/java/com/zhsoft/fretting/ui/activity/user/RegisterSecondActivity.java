package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.ui.activity.boot.WebPublicActivity;
import com.zhsoft.fretting.ui.widget.CountdownButton;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/14 13:54
 * 描述：
 */

public class RegisterSecondActivity extends XActivity {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.head_right) Button headRight;
    @BindView(R.id.user_name) EditText userName;
    @BindView(R.id.identity) EditText identity;
    @BindView(R.id.email) EditText email;
    @BindView(R.id.banck_name) TextView banckName;
    @BindView(R.id.ll_choose_bank) LinearLayout llChooseBank;
    @BindView(R.id.banknumber) EditText banknumber;
    @BindView(R.id.phone) EditText phone;
    @BindView(R.id.msg_code) EditText msgCode;
    @BindView(R.id.get_verify_code) CountdownButton getVerifyCode;
    @BindView(R.id.register_service_select) ImageView registerServiceSelect;
    @BindView(R.id.register_service) TextView registerService;
    @BindView(R.id.btn_next) Button btnNext;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_register_bindcard;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("基金开户");
        registerServiceSelect.setSelected(true);
    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        getVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(phone.getText().toString())) {
                    showToast("手机号不能为空");
                    return;
                }
                if (phone.getText().toString().length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                //TODO 短信验证码
                getVerifyCode.start();

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(userName.getText().toString())) {
                    showToast("姓名不能为空");
                    return;
                }
                if (TextUtils.isEmpty(identity.getText().toString())) {
                    showToast("身份证号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(banckName.getText().toString())) {
                    showToast("请选择银行名称");
                    return;
                }
                if (TextUtils.isEmpty(banknumber.getText().toString())) {
                    showToast("银行卡号不能为空");
                    return;
                }
                if (TextUtils.isEmpty(phone.getText().toString())) {
                    showToast("手机号不能为空");
                    return;
                }
                if (phone.getText().toString().length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (TextUtils.isEmpty(msgCode.getText().toString())) {
                    showToast("验证码不能为空");
                }
                if (!registerServiceSelect.isSelected()) {
                    showToast("请阅读并同意协议");
                    return;
                }
                //TODO 下一步
                startActivity(RegisterSetTradePwdActivity.class);
            }
        });
        registerServiceSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registerServiceSelect.isSelected()) {
                    registerServiceSelect.setSelected(false);
                } else {
                    registerServiceSelect.setSelected(true);
                }
            }
        });

        llChooseBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 选择银行
                showToast("选择银行");
                banckName.setText("招商银行");
            }
        });

        registerService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.WEB_TITLE, R.string.user_register_service);
                bundle.putString(Constant.WEB_LINK, "https://www.baidu.com/?tn=96928074_hao_pg");
                startActivity(WebPublicActivity.class, bundle);
            }
        });
    }


}
