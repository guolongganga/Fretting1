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

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.BankResp;
import com.zhsoft.fretting.present.user.RegisterSecondPresent;
import com.zhsoft.fretting.ui.activity.boot.WebPublicActivity;
import com.zhsoft.fretting.ui.widget.CountdownButton;
import com.zhsoft.fretting.widget.ChenJingET;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/14 13:54
 * <p>
 * 描述：基金开户 第二步 绑定银行卡
 */

public class RegisterSecondActivity extends XActivity<RegisterSecondPresent> {
    /** 传递银行列表数据 */
    private static final String BANK = "bank";
    private static final int TOBANKLIST = 100;
    private static final int RESULT_CODE = 200;
    private static final String CHOOSE_BANCK = "choosebank";
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
    /** 短信验证码 */
    @BindView(R.id.msg_code) EditText msgCode;
    /** 获取验证码 */
    @BindView(R.id.get_verify_code) CountdownButton getVerifyCode;
    /** 勾选框 */
    @BindView(R.id.register_service_select) ImageView registerServiceSelect;
    /** 注册协议 */
    @BindView(R.id.register_service) TextView registerService;
    /** 下一步按钮 */
    @BindView(R.id.btn_next) Button btnNext;

    /** 加载框 */
    private HttpLoadingDialog httpLoadingDialog;

    private ArrayList<BankResp> listResps;

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
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(BANK,listResps);
                startActivity(BankListActivity.class,bundle,TOBANKLIST);

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

    /**
     * 绑定银行卡失败
     */
    public void requestFail() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 访问银行卡列表
     *
     * @param data
     */
    public void bankListData(ArrayList<BankResp> data) {
        if (data != null && data.size() > 0) {

            listResps = data;
            httpLoadingDialog.dismiss();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_CODE && requestCode == TOBANKLIST){
            BankResp resp = data.getParcelableExtra(CHOOSE_BANCK);
            banckName.setText(resp.getBankName());
        }
    }
}
