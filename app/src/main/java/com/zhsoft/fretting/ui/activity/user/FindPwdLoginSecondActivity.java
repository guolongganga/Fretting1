package com.zhsoft.fretting.ui.activity.user;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.present.user.FindPwdLoginSecondPresent;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.router.Router;

/**
 * 作者：sunnyzeng on 2017/12/13 18:18
 * 描述：
 */

public class FindPwdLoginSecondActivity extends XActivity<FindPwdLoginSecondPresent> {
    private static final String PHONE = "phone";
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.head_right) Button headRight;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.password_again) EditText passwordAgain;
    @BindView(R.id.btn_save) Button btnSave;

    private HttpLoadingDialog httpLoadingDialog;
    private String mPhone;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_findpwd_second_login;
    }

    @Override
    public FindPwdLoginSecondPresent newP() {
        return new FindPwdLoginSecondPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        httpLoadingDialog = new HttpLoadingDialog(context);
        headTitle.setText("找回登录密码");
        mPhone = getIntent().getStringExtra(PHONE);
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
                String pwdnumbe = getText(password);
                String pwdAgainnumbe = getText(passwordAgain);
                if (!isNotEmpty(pwdnumbe)) {
                    showToast("新登录密码不能为空");
                    return;
                }
                if (pwdnumbe.length() < 8) {
                    showToast("登录密码为8-16位数字、字母、特殊字符等");
                    return;
                }
                if (!isNotEmpty(pwdAgainnumbe)) {
                    showToast("再次输入登录密码不能为空");
                    return;
                }
                if (!pwdnumbe.equals(pwdAgainnumbe)) {
                    showToast("两次密码不一致");
                    return;
                }
                //TODO 请求找回登录密码接口
                httpLoadingDialog.visible();
                getP().findPassword(mPhone, pwdnumbe, pwdAgainnumbe);
            }
        });
    }

    public static void launch(Activity activity, String phone) {
        Router.newIntent(activity)
                .to(FindPwdLoginSecondActivity.class)
                .putString(PHONE, phone)
                .launch();
    }

    public void requestFail() {
        httpLoadingDialog.dismiss();
        showToast("找回登录密码失败");
    }

    public void requestSuccess(Object data) {
        httpLoadingDialog.dismiss();
        showToast("找回登录密码成功");
        startActivity(LoginActivity.class);
    }
}
