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
import com.zhsoft.fretting.present.user.RegisterSecondPresent;
import com.zhsoft.fretting.ui.activity.boot.WebPublicActivity;
import com.zhsoft.fretting.ui.widget.CountdownButton;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/14 13:54
 * <p>
 * 描述：基金开户第二部 绑定银行卡
 */

public class RegisterSecondActivity extends XActivity<RegisterSecondPresent> {
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

    private HttpLoadingDialog httpLoadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_register_bindcard;
    }

    @Override
    public RegisterSecondPresent newP() {
        return new RegisterSecondPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        httpLoadingDialog = new HttpLoadingDialog(context);
        headTitle.setText("基金开户");
        registerServiceSelect.setSelected(true);
        //请求银行卡列表
        httpLoadingDialog.visible("加载中...");
        getP().getBankList();
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
                String strPhone = getText(phone);
                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(strPhone)) {
                    showToast("手机号不能为空");
                    return;
                }
                if (strPhone.length() < 11) {
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
                String strUsername = getText(userName);
                String strIdentity = getText(identity);
                String strBankName = getText(banckName);
                String strBanknumber = getText(banknumber);
                String strPhone = getText(phone);
                String strMsgCode = getText(msgCode);
                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(strUsername)) {
                    showToast("姓名不能为空");
                    return;
                }
                if (!isNotEmpty(strIdentity)) {
                    showToast("身份证号不能为空");
                    return;
                }
                if (!isNotEmpty(strBankName)) {
                    showToast("请选择银行名称");
                    return;
                }
                if (!isNotEmpty(strBanknumber)) {
                    showToast("银行卡号不能为空");
                    return;
                }
                if (!isNotEmpty(strPhone)) {
                    showToast("手机号不能为空");
                    return;
                }
                if (strPhone.length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (!isNotEmpty(strMsgCode)) {
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


    public void requestFail() {
        httpLoadingDialog.dismiss();
    }

    public void bankListData(Object data) {
        httpLoadingDialog.dismiss();

    }
}
