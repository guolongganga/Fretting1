package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.MainActivity;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.LoginModel;
import com.zhsoft.fretting.present.user.LoginPresent;
import com.zhsoft.fretting.utils.RuntimeHelper;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/14 11:46
 * 描述：
 */

public class LoginActivity extends XActivity<LoginPresent> {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.head_right) Button headRight;
    @BindView(R.id.username) EditText username;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.register) TextView register;
    @BindView(R.id.find_password) TextView findPassword;
    @BindView(R.id.btn_next) Button btnNext;

    private HttpLoadingDialog httpLoadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_login;
    }

    @Override
    public LoginPresent newP() {
        return new LoginPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        httpLoadingDialog = new HttpLoadingDialog(context);
        initView();

    }

    private void initView() {
        headTitle.setText("登录");
    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(RegisterFirstActivity.class);
            }
        });
        findPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(FindPwdLoginFirstActivity.class);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strname = getText(username);
                String strpwd = getText(password);

                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(strname)) {
                    showToast("用户名不能为空");
                    return;
                }
                if (!isNotEmpty(strpwd)) {
                    showToast("密码不能为空");
                    return;
                }
                //TODO 登录接口
//                showToast("登录接口");
                //请求登录接口
                httpLoadingDialog.visible("登录中...");
                getP().login(strname, strpwd);

            }
        });
    }


    public void showData(LoginModel model) {
        showToast("userID" + model.getUserId() + "，token=" + model.getToken());
        httpLoadingDialog.dismiss();
        showToast("登录成功");

        App.getSharedPref().putString(Constant.USERID, model.getUserId());
        App.getSharedPref().putString(Constant.TOKEN, model.getToken());
        App.getSharedPref().putString(Constant.USER_NAME, getText(username));

        //全局变量设置为登录状态
        RuntimeHelper.getInstance().setLogin(true);

        startActivity(MainActivity.class);
        finish();

    }

    /**
     * 登录失败 关闭掉dialog
     */
    public void loginFail() {
        httpLoadingDialog.dismiss();
    }
}
