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
import com.zhsoft.fretting.widget.ChenJingET;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/13 13:05
 * 描述：变更手机号 页面
 */

public class PhoneChangeActivity extends XActivity {
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 手机号码 */
    @BindView(R.id.phone_number) EditText phoneNumber;
    /** 验证码 */
    @BindView(R.id.verify_code) EditText verifyCode;
    /** 获取验证码 */
    @BindView(R.id.get_verify_code) CountdownButton getVerifyCode;
    /** 保存按钮 */
    @BindView(R.id.btn_save) Button btnSave;

    /** 加载框 */
    private HttpLoadingDialog httpLoadingDialog;

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
        //解决键盘弹出遮挡不滚动问题
        ChenJingET.assistActivity(context);
        httpLoadingDialog = new HttpLoadingDialog(context);
        //设置标题
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
                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(getText(phoneNumber))) {
                    showToast("手机号码不能为空");
                    return;
                }
                if (getText(phoneNumber).length() < 11) {
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
                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(getText(phoneNumber))) {
                    showToast("手机号码不能为空");
                    return;
                }
                if (getText(phoneNumber).length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (!isNotEmpty(getText(verifyCode))) {
                    showToast("验证码不能为空");
                    return;
                }
                //TODO 将验证码和手机号码提交到后台
                showToast("保存更换的手机号");
            }
        });

    }


}
