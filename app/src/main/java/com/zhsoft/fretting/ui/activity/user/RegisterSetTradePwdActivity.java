package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.ui.widget.PayPwdEditText;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/12 18:44
 * 描述：
 */

public class RegisterSetTradePwdActivity extends XActivity {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.ppe_pwd) PayPwdEditText ppePwd;
    @BindView(R.id.ppe_pwd_again) PayPwdEditText ppePwdAgain;
    @BindView(R.id.btn_finish) Button btnFinish;
    private String password;
    private String passwordAgain;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_register_settradepwd;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("基金开户");
        ppePwd.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.color999999, R.color.color999999, 20);
        ppePwdAgain.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.color999999, R.color.color999999, 20);

    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ppePwd.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                password = str;
                showToast(str);
            }
        });
        ppePwdAgain.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                passwordAgain = str;
                showToast(str);
                if (TextUtils.isEmpty(password)) {
                    showToast("请输入第一遍交易密码");
                    return;
                }


            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(password)) {
                    showToast("请输入第一遍交易密码");
                    return;
                }
                if (TextUtils.isEmpty(passwordAgain)) {
                    showToast("请输入第二遍交易密码");
                    return;
                }
                if (!password.equals(passwordAgain)) {
                    showToast("两次密码不一致");
                    return;
                }
                //TODO 提交设置交易密码接口，成功则跳转开户成功页面
                startActivity(RegisterSuccessActivity.class);

            }
        });


    }

}
