package com.zhsoft.fretting.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.event.ChangeTabEvent;
import com.zhsoft.fretting.event.OpenAccountEvent;
import com.zhsoft.fretting.model.user.BankResp;
import com.zhsoft.fretting.present.user.RegisterSecondPresent;
import com.zhsoft.fretting.ui.activity.MainActivity;
import com.zhsoft.fretting.ui.activity.boot.WebPublicActivity;
import com.zhsoft.fretting.ui.widget.ChenJingET;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/14 13:54
 * <p>
 * 描述：基金开户 第二步 绑定银行卡
 */

public class RegisterSecondActivity extends XActivity<RegisterSecondPresent> {

    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 用户名 */
    @BindView(R.id.user_name) EditText userName;
    /** 身份证号 */
    @BindView(R.id.identity) EditText identity;
    /** 邮件 */
    @BindView(R.id.email) EditText email;
    /** 银行名称 */
    @BindView(R.id.banck_name) TextView banckName;
    /** 选择银行 */
    @BindView(R.id.ll_choose_bank) LinearLayout llChooseBank;
    /** 银行卡号 */
    @BindView(R.id.banknumber) EditText banknumber;
    /** 手机号码 */
    @BindView(R.id.phone) EditText phone;
    //    /** 短信验证码 */
//    @BindView(R.id.msg_code) EditText msgCode;
//    /** 获取验证码 */
//    @BindView(R.id.get_verify_code) CountdownButton getVerifyCode;
    /** 交易密码 */
    @BindView(R.id.password) EditText password;
    /** 确认交易密码 */
    @BindView(R.id.password_again) EditText passwordAgain;
    /** 勾选框 */
    @BindView(R.id.register_service_select) ImageView registerServiceSelect;
    /** 注册协议 */
    @BindView(R.id.register_service) TextView registerService;
    /** 下一步按钮 */
    @BindView(R.id.btn_save) Button btnSave;

    /** 加载框 */
    private HttpLoadingDialog httpLoadingDialog;
    /** 已选择的银行 */
    private BankResp bankResp;
    /** 用户编号 */
    private String userId;
    /** 登录标识 */
    private String token;
    /** 注册的手机号 */
    private String strPhone;

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
        //解决键盘弹出遮挡不滚动问题
        ChenJingET.assistActivity(context);
        httpLoadingDialog = new HttpLoadingDialog(context);
        //设置标题
        headTitle.setText("基金开户");
        registerServiceSelect.setSelected(true);
        //获取用户缓存的userid 和 token
        userId = App.getSharedPref().getString(Constant.USERID, "");
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        strPhone = App.getSharedPref().getString(Constant.USER_PHONE, "");
        phone.setText(strPhone);

    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backDeal();
            }
        });
//        getVerifyCode.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String strPhone = getText(phone);
//                //表单验证
//                if (noNetWork()) {
//                    showNetWorkError();
//                    return;
//                }
//                //表单验证
//                if (noNetWork()) {
//                    showNetWorkError();
//                    return;
//                }
//                if (!isNotEmpty(strPhone)) {
//                    showToast("手机号不能为空");
//                    return;
//                }
//                if (strPhone.length() < 11) {
//                    showToast("请输入正确的手机号码");
//                    return;
//                }
//                //TODO 短信验证码
//                getVerifyCode.start();
//
//            }
//        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String strUsername = getText(userName);
                String strIdentity = getText(identity);
                String strBankName = getText(banckName);
                String strBanknumber = getText(banknumber);
                String strPhone = getText(phone);
                String strpwd = getText(password);
                String strpwdAgain = getText(passwordAgain);
//                String strMsgCode = getText(msgCode);
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
                if (!isNotEmpty(strpwd)) {
                    showToast("交易密码不能为空");
                    return;
                }
                if (strpwd.length() < 6) {
                    showToast("请输入6位纯数字");
                    return;
                }
                if (!isNotEmpty(strpwdAgain)) {
                    showToast("确认交易密码不能为空");
                    return;
                }
                if (!strpwd.equals(strpwdAgain)) {
                    showToast("两次密码不一致");
                    return;
                }
//                if (!isNotEmpty(strMsgCode)) {
//                    showToast("验证码不能为空");
//                }
                if (!registerServiceSelect.isSelected()) {
                    showToast("请阅读并同意协议");
                    return;
                }
                //TODO 下一步 请求注册接口
                httpLoadingDialog.visible("开户中...");
                httpLoadingDialog.setCanceledOnKeyBack();
                getP().openAccount(userId, token, strUsername, strIdentity, getText(email), bankResp, strBanknumber, strPhone, strpwd);
//                startActivity(RegisterSuccessActivity.class);
//                finish();
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
                //选择银行
                startActivity(BankListActivity.class, Constant.BANKLIST_ACTIVITY);

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.BANKLIST_RESULT_CODE && requestCode == Constant.BANKLIST_ACTIVITY) {
            bankResp = data.getParcelableExtra(Constant.CHOOSE_BANCK);
            banckName.setText(bankResp.getBank_name());
        }
    }

    /**
     * 开户失败
     */
    public void requestOpenAccountFail() {
        httpLoadingDialog.dismiss();
        showToast("开户失败");
    }

    /**
     * 开户成功
     */
    public void requestOpenAccountSuccess() {
        httpLoadingDialog.dismiss();
//        showToast(data);
        //更新开户状态 改成已开户
        App.getSharedPref().putString(Constant.IS_OPEN_ACCOUNT, Constant.ALREADY_OPEN_ACCOUNT);
        EventBus.getDefault().post(new OpenAccountEvent());

        Bundle bundle = new Bundle();
        //姓名
        bundle.putString(Constant.NAME, getText(userName));
        //身份证号
        bundle.putString(Constant.CERT_NO, getText(identity));
        startActivity(RegisterSuccessActivity.class, bundle);
        finish();
    }

    private void backDeal() {
        EventBus.getDefault().post(new ChangeTabEvent(Constant.MAIN_MY));
        startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backDeal();
    }
}
