package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.widget.ChenJingET;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/13 13:58
 * 描述：变更交易密码
 */

public class ChangeTradePwdActivity extends XActivity {
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 旧交易密码 */
    @BindView(R.id.oldpassword) EditText oldpassword;
    /** 新交易密码 */
    @BindView(R.id.password) EditText password;
    /** 再次输入交易密码 */
    @BindView(R.id.password_again) EditText passwordAgain;
    /** 重置交易密码 */
    @BindView(R.id.reset_password) TextView resetPassword;
    /** 保存 */
    @BindView(R.id.btn_save) Button btnSave;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_changepwd_trade;
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
        headTitle.setText("变更交易密码");
    }


    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(FindPwdTradeFirstActivity.class);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldpwd = getText(oldpassword);
                String pwd = getText(password);
                String againpwd = getText(passwordAgain);
                if (!isNotEmpty(oldpwd)) {
                    showToast("旧交易密码不能为空");
                    return;
                }
                if (oldpwd.length() != 6) {
                    showToast("请输入正确的旧交易密码");
                    return;
                }
                if (!isNotEmpty(pwd)) {
                    showToast("新交易密码不能为空");
                    return;
                }
                if (pwd.length() < 6) {
                    showToast("请输入正确的新交易密码");
                    return;
                }
                if (!isNotEmpty(againpwd)) {
                    showToast("再次输入交易密码不能为空");
                    return;
                }
                if (!pwd.equals(againpwd)) {
                    showToast("两次密码不一致");
                    return;
                }
                //TODO 请求修改交易密码接口
                showToast("修改交易密码");
//                finish();
            }
        });


    }


}
