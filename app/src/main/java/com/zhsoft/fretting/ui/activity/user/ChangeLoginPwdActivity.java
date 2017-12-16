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
 * 描述：变更登录密码
 */

public class ChangeLoginPwdActivity extends XActivity {
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 新登录密码 */
    @BindView(R.id.password) EditText password;
    /** 再次输入登录密码 */
    @BindView(R.id.password_again) EditText passwordAgain;
    /** 保存 */
    @BindView(R.id.btn_save) Button btnSave;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_changepwd_login;
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
        headTitle.setText("变更登录密码");

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

                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
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
                //TODO 请求修改登录密码接口
                showToast("修改登录密码");
            }
        });
    }


}
