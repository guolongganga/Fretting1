package com.zhsoft.fretting.ui.activity.fund;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.fund.BuyFundResp;
import com.zhsoft.fretting.model.fund.BuyNowResp;
import com.zhsoft.fretting.present.fund.BuyPresent;
import com.zhsoft.fretting.present.fund.SellPresent;
import com.zhsoft.fretting.ui.activity.user.BankCardActivity;
import com.zhsoft.fretting.ui.activity.user.FindPwdTradeFirstActivity;
import com.zhsoft.fretting.ui.widget.CustomDialog;
import com.zhsoft.fretting.ui.widget.FundBuyDialog;
import com.zhsoft.fretting.utils.Base64ImageUtil;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2018/1/8 11:55
 * 描述：基金详情页-购买页面
 */

public class SellActivity extends XActivity<SellPresent> {
    /** 返回 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 银行logo */
    @BindView(R.id.bank_image) ImageView bankImage;
    /** 银行卡名称和尾号 */
    @BindView(R.id.bank_name) TextView bankName;
    /** 银行卡限额 */
    @BindView(R.id.bank_limit) TextView bankLimit;
    /** 更换银行卡 */
    @BindView(R.id.tv_change) TextView tvChange;
    /** 可用份额 */
    @BindView(R.id.available_share) TextView availableShare;
    /** 全部份额 */
    @BindView(R.id.btn_all_share) Button btnAllShare;
    /** 购买金额 */
    @BindView(R.id.et_amount) EditText etAmount;
    /** 确认购买 */
    @BindView(R.id.sure) Button sure;
    /** 输入密码弹框 */
    private FundBuyDialog fundBuyDialog;
    /** 密码错误弹框 */
    private CustomDialog customDialog;
    /** 得到的用户购买准备数据 */
    private BuyFundResp buyFundResp;
    /** 基金代码 */
    private String fundCode;
    /** 基金名称 */
    private String fundName;
    /** 登录标识 */
    private String token;
    /** 用户编号 */
    private String userId;
    /** 加载框 */
    private HttpLoadingDialog httpLoadingDialog;
    /** 最大份额 */
    private int maxShare = 50;


    @Override
    public int getLayoutId() {
        return R.layout.activity_fund_sell;
    }

    @Override
    public SellPresent newP() {
        return new SellPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("卖出");
        httpLoadingDialog = new HttpLoadingDialog(context);
        //获取缓存数据
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        userId = App.getSharedPref().getString(Constant.USERID, "");
        if (bundle != null) {
            fundCode = bundle.getString(Constant.FUND_DETAIL_CODE);
            fundName = bundle.getString(Constant.FUND_DETAIL_NAME);

            httpLoadingDialog.visible();
            getP().buyFund(token, userId, fundCode);
        }

    }

    public void refreshBankView(BuyFundResp fundResp) {
        availableShare.setText(maxShare + "");
        String strimage = fundResp.getLogo();
        if (!TextUtils.isEmpty(strimage)) {
            //将Base64图片串转换成Bitmap
            Bitmap bitmap = Base64ImageUtil.base64ToBitmap(strimage);
            bankImage.setImageBitmap(bitmap);
        }
        //银行卡名称和尾号
        bankName.setText(fundResp.getName());
        //银行卡限额
        bankLimit.setText(fundResp.getInfo());
    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String strAmount = getText(etAmount);
                int amount = Integer.parseInt(strAmount);
                //表单验证通过才弹出Dialog
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(getText(etAmount))) {
                    showToast("请输入购买金额");
                    return;
                }
                //TODO 如果amount大于可用份额，重新填写购买金额
                if (amount > maxShare) {
                    showToast("你的可用份额为" + maxShare + "份");
                    return;
                }
                //TODO 弹出框
                fundBuyDialog = new FundBuyDialog
                        .Builder(context)
                        .setFundName(fundName)
                        .setFundAmount("￥" + getText(etAmount) + ".00")
                        .setOnTextFinishListener(new FundBuyDialog.OnTextFinishListener() {
                            @Override
                            public void onFinish(String str) {
                                fundBuyDialog.dismiss();
                                httpLoadingDialog.visible();
                                //TODO 卖出接口
                                getP().sellFund(token, userId, fundCode, strAmount, str);

                            }
                        }).create();
                fundBuyDialog.show();

            }
        });
        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(BankCardActivity.class, Constant.INVEST_BANK_ACTIVITY);
            }
        });
        btnAllShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etAmount.setText(maxShare + "");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.INVEST_BANK_ACTIVITY && resultCode == Constant.SUCCESS_BACK_BANK) {
            String isChange = data.getStringExtra(Constant.CHANGE_BANK);
            //如果修改了银行卡就刷新本页面数据
            if (Constant.CHANGE_BANK_SUCCESS.equals(isChange)) {
                //TODO 获取银行卡数据
                httpLoadingDialog.visible();
                getP().buyFund(token, userId, fundCode);
            }
        }
    }

    /**
     * 刷新银行卡信息 失败
     */
    public void requestBuyFundFail() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 刷新银行卡信息 成功
     */
    public void requestBuyFundSuccess(BuyFundResp data) {
        httpLoadingDialog.dismiss();
        refreshBankView(data);
    }

    /**
     * 立即卖出请求失败
     */
    public void requestSellFail() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 立即卖出成功
     */
    public void requestSellSuccess() {
        httpLoadingDialog.dismiss();
//        Bundle bundle = new Bundle();
//        bundle.putParcelable(Constant.BUY_SUCCESS_OBJECT, data);
//        startActivity(SellSuccessActivity.class, bundle);
        startActivity(SellSuccessActivity.class);
        finish();

    }

    /**
     * 立即购买 密码错误
     */
    public void passwordError() {
        httpLoadingDialog.dismiss();
        if (customDialog == null) {
            customDialog = new CustomDialog.Builder(context)
                    .setMessage("交易密码错误，请重试")
                    .setNegativeButton("忘记密码", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            customDialog.dismiss();
                            startActivity(FindPwdTradeFirstActivity.class);
                        }
                    }).setPositiveButton("再试一次", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            customDialog.dismiss();
                            fundBuyDialog.show();
                        }
                    }).create();
        }
        customDialog.show();
    }
}