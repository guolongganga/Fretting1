package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.R;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/13 18:18
 * 描述：
 */

public class FindPwdLoginSecondActivity extends XActivity {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.head_right) Button headRight;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.password_again) EditText passwordAgain;
    @BindView(R.id.btn_save) Button btnSave;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_findpwd_second_login;
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

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pwdnumbe = password.getText().toString();
                String pwdAgainnumbe = passwordAgain.getText().toString();
                if (TextUtils.isEmpty(pwdnumbe)) {
                    showToast("新登录密码不能为空");
                    return;
                }
                if (pwdnumbe.length() < 8) {
                    showToast("登录密码为8-16位数字、字母、特殊字符等");
                    return;
                }
                if (TextUtils.isEmpty(pwdAgainnumbe)) {
                    showToast("再次输入登录密码不能为空");
                    return;
                }
                if (!pwdnumbe.equals(pwdAgainnumbe)) {
                    showToast("两次密码不一致");
                    return;
                }
                //TODO 请求找回登录密码接口
                showToast("找回登录密码");
            }
        });
    }
}
