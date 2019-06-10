package cn.com.buyforyou.fund.ui.activity.user;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.ui.widget.ChenJingET;
import com.zhsoft.fretting.ui.widget.CustomDialog;
import com.zhsoft.fretting.ui.widget.FundBuyDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import cn.com.buyforyou.fund.App;
import cn.com.buyforyou.fund.R;
import cn.com.buyforyou.fund.constant.Constant;
import cn.com.buyforyou.fund.event.BankCardMessageEvent;
import cn.com.buyforyou.fund.event.ChangeTabEvent;
import cn.com.buyforyou.fund.model.user.BankResp;
import cn.com.buyforyou.fund.present.user.BankcardChoosePresent;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * Created by guolonggang on 2018/12/12.
 * Description:我的银行卡(添加银行卡)所填信息
 */

public class BankCardChooseActivity extends XActivity<BankcardChoosePresent> {

    /**
     * 返回按钮
     */
    @BindView(R.id.head_back)
    ImageButton headBack;
    /**
     * 标题
     */
    @BindView(R.id.head_title)
    TextView headTitle;
    /**
     * 用户名
     */
    @BindView(R.id.user_name)
    EditText userName;
    /**
     * 身份证号
     */
    @BindView(R.id.identity)
    EditText identity;
    /**
     * 银行名称
     */
    @BindView(R.id.banck_name)
    TextView banckName;
    /**
     * 选择银行
     */
    @BindView(R.id.ll_choose_bank)
    LinearLayout llChooseBank;
    /**
     * 银行卡号
     */
    @BindView(R.id.banknumber)
    EditText banknumber;
    /**
     * 手机号码
     */
    @BindView(R.id.phone)
    EditText phone;

    /**
     * 下一步按钮
     */
    @BindView(R.id.btn_save)
    Button btnSave;

    /**
     * 加载框
     */
    private HttpLoadingDialog httpLoadingDialog;
    /**
     * 已选择的银行
     */
    private BankResp bankResp;

    private String userId;
    private String token;
    //身份证号
    private String certNo;
    //用户名
    private String struserName;
    //手机号
    private String strPhone;
    //银行号
    private String strBanknumber;
    private String bank_name;

    /** 输入密码弹框 */
    private FundBuyDialog fundBuyDialog;
    /** 密码错误弹框 */
    private CustomDialog errorDialog;
    /** 交易密码 */
    private String trade_password;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_oenaccount_bindcard;
    }

    @Override
    public BankcardChoosePresent newP() {
        return new BankcardChoosePresent();
    }

    @Override
    public void initData(Bundle bundle) {

         if(bundle!=null)
         {
             certNo=bundle.getString(Constant.CERT_NO);
             struserName= bundle.getString(Constant.NAME);
         }

        //解决键盘弹出遮挡不滚动问题
        ChenJingET.assistActivity(context);
        httpLoadingDialog = new HttpLoadingDialog(context);
        //设置标题
        headTitle.setText("添加银行卡");
        //获取用户缓存的userid 和 token
        userId = App.getSharedPref().getString(Constant.USERID, "");
        token = App.getSharedPref().getString(Constant.TOKEN, "");


        llChooseBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //选择银行
                startActivity(BankListActivity.class, Constant.BANKLIST_ACTIVITY);

            }
        });
        userName.setText(struserName);
        identity.setText(certNo);



        //请求接口
       btnSave.setOnClickListener(new View.OnClickListener() {



           @Override
           public void onClick(View v) {
              //银行卡号
               strBanknumber = getText(banknumber);
               //手机号
               strPhone = getText(phone);


               //表单验证
               if (noNetWork()) {
                   showNetWorkError();
                   return;
               }
               if (!isNotEmpty(bank_name)) {
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
               //弹出交易密码框
               fundBuyDialog = new FundBuyDialog
                       .Builder(context)
                       .setOnTextFinishListener(new FundBuyDialog.OnTextFinishListener() {
                           @Override
                           public void onFinish(String str) {
                           }
                       }).setPositiveButton("确定", new FundBuyDialog.OnPositiveButtonListener() {
                           @Override
                           public void onButtonClick(DialogInterface dialog, String str) {
                               //验证是否符合更换条件
                               dialog.dismiss();
                               httpLoadingDialog.visible();
                               trade_password = str;
                               // getP().changeBankCardCheck(token, userId, str);
                               //String userId, String token, String UserName, String certNo,
                               // String email, BankResp selectBank,String mobile, String bankAccount,String tradePassword
                               httpLoadingDialog.visible("加载中...");
                               httpLoadingDialog.setCanceledOnKeyBack();
                               getP().bankCardChoose(userId,token,struserName,certNo,"",bankResp,strPhone,strBanknumber,trade_password);


                           }
                       }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                           @Override
                           public void onClick(DialogInterface dialog, int which) {
                               dialog.dismiss();
                           }
                       }).create();

               fundBuyDialog.show();


           }
       });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.BANKLIST_RESULT_CODE && requestCode == Constant.BANKLIST_ACTIVITY) {
            bankResp = data.getParcelableExtra(Constant.CHOOSE_BANCK);
            bank_name = bankResp.getBank_name();
            banckName.setText(bank_name);
        }
    }


    /**
     * 添加银行卡成功的方法
     */
    public void  addAccountCheck()
    {
        httpLoadingDialog.dismiss();
        Bundle bundle=new Bundle();
        //手机号
        bundle.putString(Constant.PHONE,strPhone);
        //用户名
        bundle.putString(Constant.NAME,struserName);
        //身份证号
        bundle.putString(Constant.CERT_NO,certNo);
        //银行名称
        bundle.putParcelable(Constant.CHOOSE_BANCK,bankResp);
        //银行卡号
        bundle.putString(Constant.CHANGE_BANK,strBanknumber);
        //手机号
        bundle.putString(Constant.PHONE,strPhone);
        //交易密码
        bundle.putString(Constant.TRADE_PASSWORD,trade_password);

        //跳转到短信验证界面
         startActivity(AddBankCardSmsActivity.class,bundle);
         //finish();

    }
    public void addAccountCheckFail(String message)
    {

        httpLoadingDialog.dismiss();

        new AlertDialog.Builder(context).setTitle("增开预校验失败").setMessage(message).setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setCancelable(false).create().show();
    }



    @Override
    public void initEvents() {

        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backDeal();
            }
        });

    }

    private void backDeal() {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backDeal();
    }



}
