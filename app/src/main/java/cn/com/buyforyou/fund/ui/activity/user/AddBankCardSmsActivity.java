package cn.com.buyforyou.fund.ui.activity.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.ui.widget.ChenJingET;
import com.zhsoft.fretting.ui.widget.CountdownButton;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import cn.com.buyforyou.fund.App;
import cn.com.buyforyou.fund.R;
import cn.com.buyforyou.fund.constant.Constant;
import cn.com.buyforyou.fund.event.RefreshUserDataEvent;
import cn.com.buyforyou.fund.model.user.BankResp;

import cn.com.buyforyou.fund.model.user.OpenAccountResp;
import cn.com.buyforyou.fund.present.user.SmsAddBankCardPersent;
import cn.com.buyforyou.fund.present.user.SmsMessagePresent;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * Created by guolonggang on 2018/12/12.
 * Description:添加银行卡短信验证界面
 */

public class AddBankCardSmsActivity extends XActivity<SmsAddBankCardPersent> {

    /**
     * 用户编号
     */
    private String userId;
    /**
     * 登录标识
     */
    private String token;

    /**
     * 标题
     */
    @BindView(R.id.head_title)
    TextView headTitle;

    /**
     * 加载框
     */
    private HttpLoadingDialog httpLoadingDialog;
    /**
     * 返回按钮
     */
    @BindView(R.id.head_back)
    ImageButton headBack;

    /**
     * 预留手机号
     */
    @BindView(R.id.phone_number)
    EditText phoneNumber;

    /**
     * 获取验证码
     */
    @BindView(R.id.get_verify_code)
    CountdownButton getVerifyCode;

    /**
     * 短信验证码
     */
    @BindView(R.id.msg_code)
    EditText msgCode;

    /**
     * 下一步按钮
     */
    @BindView(R.id.btn_save)
    Button btnSave;
    private String strUsername;
    private String strIdentity;
    private String email;
    private BankResp bankResp;
    private String strBankNumber;
    private String strPhone;
    private String strPwd;

    /**
     * 短信验证码返回的参数
     */

    private String originalAppno;
    private String otherSerial;
    private String code;

    @Override
    public int getLayoutId() {
        return R.layout.activity_sms__add_message;
    }

    @Override
    public SmsAddBankCardPersent newP() {
        return new SmsAddBankCardPersent();
    }

    @Override
    public void initData(Bundle bundle) {
        //解决键盘弹出遮挡不滚动问题
        ChenJingET.assistActivity(context);
        httpLoadingDialog = new HttpLoadingDialog(context);
        headTitle.setText("添加银行卡");

        userId = App.getSharedPref().getString(Constant.USERID, "");
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        /**
         * 得到上个页面传过来的值
         */
        //姓名
        if (bundle != null) {
            strUsername = bundle.getString(Constant.NAME);
            //身份证号
            strIdentity = bundle.getString(Constant.CERT_NO);
            //邮箱
            email = bundle.getString(Constant.CHANGE_EMAIL);
//        //银行名称
            bankResp = bundle.getParcelable(Constant.CHOOSE_BANCK);

            //银行卡号
            strBankNumber = bundle.getString(Constant.CHANGE_BANK);
            //手机号
            strPhone = bundle.getString(Constant.PHONE);
            //交易密码
            strPwd = bundle.getString(Constant.TRADE_PASSWORD);
        }
        //得到手机号
        phoneNumber.setText(strPhone);

    }

    @Override
    public void initEvents() {
        /**
         * 返回键
         */
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        /**
         *   获取验证码
         */
        getVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpLoadingDialog.visible("手机短信正在发送中...");
                httpLoadingDialog.setCanceledOnKeyBack();
                getP().addAccountGetSms(userId, token, strUsername, strIdentity, email, bankResp, strBankNumber, strPhone, strPwd);
            }
        });

        /**
         * 验证添加银行卡开户是否成功
         *
         */
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code = getText(msgCode);
                if (!isNotEmpty(code)) {
                    //注册第一步
                    showToast("验证码不能为空");
                    return;
                }
                /**
                 * 请求接口
                 */
                httpLoadingDialog.visible("加载中...");
                getP().addAccount(code, otherSerial, originalAppno, userId, token, strUsername, strIdentity, email, bankResp, strBankNumber, strPhone, strPwd);
            }
        });


    }

    //获取增开交易短信验证码失败
    public void RequestCodeSmsFail() {
        httpLoadingDialog.dismiss();
        //获取失败的原因
        getVerifyCode.cancel();
        showToast(context.getString(R.string.wrong_phone_verify));
    }

    /**
     * 获取增开交易短信验证码成功
     */
    public void requestGetMessageCode(OpenAccountResp resp) {
        httpLoadingDialog.dismiss();
        //开始倒计时
        getVerifyCode.start();
        showToast(R.string.success_send_phone_verify);
        //得到对方流水号
        otherSerial = resp.getOtherSerial();
        //得到原申请单号
        originalAppno = resp.getOriginalAppno();
    }


    /**
     * 增开交易账号成功
     */
    public void openAccountNew() {
        httpLoadingDialog.dismiss();
        //更新银行卡列表
//        App.getSharedPref().putString(Constant.IS_OPEN_ACCOUNT, Constant.ALREADY_OPEN_ACCOUNT);
        EventBus.getDefault().post(new RefreshUserDataEvent());
//
//        Bundle bundle = new Bundle();
//        //姓名
//        bundle.putString(Constant.NAME, strUsername);
//        //身份证号
//        bundle.putString(Constant.CERT_NO, strIdentity);
        startActivity(AddBankCardListActivity.class);
        finish();

    }

    /**
     * 增开交易账户失败
     */
    public void openAccountNewError(String message) {
        httpLoadingDialog.dismiss();

        new AlertDialog.Builder(context).setTitle("开户失败").setMessage(message).setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(false).create().show();
    }
}
