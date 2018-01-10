package com.zhsoft.fretting.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.event.ChangeBankCardEvent;
import com.zhsoft.fretting.model.user.BankResp;
import com.zhsoft.fretting.present.user.BankCardChangePresent;
import com.zhsoft.fretting.ui.widget.CountdownButton;
import com.zhsoft.fretting.ui.widget.ChenJingET;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/14 10:13
 * 描述：变更银行卡 第二步
 */

public class BankCardChangeActivity extends XActivity<BankCardChangePresent> {
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 选择银行 */
    @BindView(R.id.ll_choose_bank) LinearLayout llChooseBank;
    /** 银行名称 */
    @BindView(R.id.banck_name) TextView bankName;
    /** 银行卡号 */
    @BindView(R.id.banknumber) EditText banknumber;
    /** 预留手机号 */
    @BindView(R.id.phone) EditText phone;
    /** 短信验证码 */
    @BindView(R.id.msg_code) EditText msgCode;
    /** 获取短信验证码 */
    @BindView(R.id.get_verify_code) CountdownButton getVerifyCode;
    /** 保存按钮 */
    @BindView(R.id.btn_save) Button btnSave;

    /** 已选择的银行 */
    private BankResp bankResp;
    /** 注册手机号 */
    private String strPhone;
    /** 登录标识 */
    private String token;
    /** 用户编号 */
    private String userId;
    /** 加载框 */
    private HttpLoadingDialog httpLoadingDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_bankcard_change;
    }

    @Override
    public BankCardChangePresent newP() {
        return new BankCardChangePresent();
    }

    @Override
    public void initData(Bundle bundle) {
        //解决键盘弹出遮挡不滚动问题
        ChenJingET.assistActivity(context);
        //设置标题
        headTitle.setText("我的银行卡");

        //获取本地缓存token,userId,注册手机号
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        userId = App.getSharedPref().getString(Constant.USERID, "");
        strPhone = App.getSharedPref().getString(Constant.USER_PHONE, "");
        phone.setText(strPhone);
        //弹出框
        httpLoadingDialog = new HttpLoadingDialog(context);
        httpLoadingDialog.setCanceledOnKeyBack();
    }


    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        llChooseBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //选择银行
                startActivity(BankListActivity.class, Constant.BANKLIST_ACTIVITY);
            }
        });
        getVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = getText(phone);

                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(getText(bankName))) {
                    showToast("请选择银行名称");
                    return;
                }
                if (!isNotEmpty(getText(banknumber))) {
                    showToast("银行卡号不能为空");
                    return;
                }
                if (!isNotEmpty(phoneNumber)) {
                    showToast("预留手机号码不能为空");
                    return;
                }
                if (phoneNumber.length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }

                //发送请求验证码
                httpLoadingDialog.visible();
                getP().getMessageCode(phoneNumber);

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phoneNumber = getText(phone);

                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(getText(bankName))) {
                    showToast("请选择银行名称");
                    return;
                }
                if (!isNotEmpty(getText(banknumber))) {
                    showToast("银行卡号不能为空");
                    return;
                }
                if (!isNotEmpty(phoneNumber)) {
                    showToast("预留手机号码不能为空");
                    return;
                }
                if (phoneNumber.length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (!isNotEmpty(getText(msgCode))) {
                    showToast("验证码不能为空");
                    return;
                }
                //绑定银行卡接口
                httpLoadingDialog.visible();
                getP().changeBankCard(token, userId, bankResp, getText(banknumber), phoneNumber, getText(msgCode));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.BANKLIST_RESULT_CODE && requestCode == Constant.BANKLIST_ACTIVITY) {
            bankResp = data.getParcelableExtra(Constant.CHOOSE_BANCK);
            bankName.setText(bankResp.getBank_name());
        }
    }

    /**
     * 重试，发送请求验证码成功
     */
    public void requestMessageCodeFail() {
        httpLoadingDialog.dismiss();
        getVerifyCode.cancel();
    }

    /**
     * 发送请求验证码成功
     */
    public void requestMessageCodeSuccess() {
        httpLoadingDialog.dismiss();
        getVerifyCode.start();
    }

    /**
     * 修改银行卡成功
     */
    public void requestChangeSuccess() {
        httpLoadingDialog.dismiss();
        showToast("更换成功");
        //跳转回我的银行卡页面，并更新页面信息
        EventBus.getDefault().post(new ChangeBankCardEvent());
        finish();
    }


    /**
     * 修改银行卡失败
     */
    public void requestChangeFail() {
        httpLoadingDialog.dismiss();
    }
}
