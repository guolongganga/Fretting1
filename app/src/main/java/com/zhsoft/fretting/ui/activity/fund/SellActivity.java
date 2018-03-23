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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.ApplyBaseInfo;
import com.zhsoft.fretting.model.fund.BuyFundResp;
import com.zhsoft.fretting.model.fund.FundStatusResp;
import com.zhsoft.fretting.model.fund.SellResp;
import com.zhsoft.fretting.model.user.BankCardResp;
import com.zhsoft.fretting.present.fund.SellPresent;
import com.zhsoft.fretting.ui.activity.user.BankCardActivity;
import com.zhsoft.fretting.ui.activity.user.FindPwdTradeFirstActivity;
import com.zhsoft.fretting.ui.widget.CustomDialog;
import com.zhsoft.fretting.ui.widget.FundBuyDialog;
import com.zhsoft.fretting.ui.widget.PopShow;
import com.zhsoft.fretting.utils.Base64ImageUtil;
import com.zhsoft.fretting.utils.BigDecimalUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.widget.ToastUtils;

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
    @BindView(R.id.tv_deal_type) TextView tvDealType;
    @BindView(R.id.tv_min_sell) TextView tvMinSell;
    @BindView(R.id.ll_big_deal) LinearLayout llBigDeal;
    @BindView(R.id.view_line) View viewLine;
    /** 输入密码弹框 */
    private FundBuyDialog fundBuyDialog;
    /** 密码错误弹框 */
    private CustomDialog customDialog;
    /** 得到的用户购买准备数据 */
    private SellResp sellResp;
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
    private int isSelector = 0;
    private List<ApplyBaseInfo> list;


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
        headTitle.setText("赎回");
        httpLoadingDialog = new HttpLoadingDialog(context);
        //获取缓存数据
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        userId = App.getSharedPref().getString(Constant.USERID, "");
        if (bundle != null) {
            fundCode = bundle.getString(Constant.FUND_DETAIL_CODE);
            fundName = bundle.getString(Constant.FUND_DETAIL_NAME);

            httpLoadingDialog.visible();
            getP().sellFundPre(token, userId, fundCode);
        }

    }

    public void refreshBankView(BankCardResp cardResp) {
        String strimage = cardResp.getBankLogo();
        if (!TextUtils.isEmpty(strimage)) {
            //将Base64图片串转换成Bitmap
            Bitmap bitmap = Base64ImageUtil.base64ToBitmap(strimage);
            bankImage.setImageBitmap(bitmap);
        }
        //银行卡名称和尾号
        bankName.setText(cardResp.getBankName());
        //银行卡限额
        bankLimit.setText("单笔上限" + cardResp.getLimit_per_payment() + "万，单日限额" + cardResp.getLimit_per_day() + "万");
    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                etAmount.setText(BigDecimalUtil.bigdecimalToString(sellResp.getEnable_shares()) + "");
            }
        });
        llBigDeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopShow popShow = new PopShow(context, viewLine);
                popShow.showRangeSelector(list, isSelector);
                popShow.setOnClickPop(new PopShow.OnClickPop() {
                    @Override
                    public void setRange(int position) {
                        isSelector = position;
                        tvDealType.setText(list.get(position).getContent());
                    }
                });
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String strAmount = getText(etAmount);
                Double amount = Double.parseDouble(strAmount);
                //表单验证通过才弹出Dialog
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(getText(etAmount))) {
                    showToast("请输入购买金额");
                    return;
                }

                // 如果amount小于份额，重新填写购买金额
                if (amount < sellResp.getMinVar().doubleValue()) {
                    showToast("剩余份额低于" + BigDecimalUtil.bigdecimalToString(sellResp.getMinVar()) + "份，请全部赎回");
                    return;
                }

                // 如果amount小于份额，重新填写购买金额
                int comValue = BigDecimal.valueOf(amount).compareTo(sellResp.getEnable_shares());

                //表示bigdemical大于bigdemical2 输入金额大于可赎回份额
                if (comValue == 1) {
                    ToastUtils.show(SellActivity.this, "可赎回份额为" + BigDecimalUtil.bigdecimalToString(sellResp.getEnable_shares()) + "份", Toast.LENGTH_LONG);
                    return;
                }

                BigDecimal subValue = sellResp.getEnable_shares().subtract(sellResp.getRemainVar());

                int comValueTwo = BigDecimal.valueOf(amount).compareTo(subValue);

                if (comValue != 0 && comValueTwo == 1) {
                    ToastUtils.show(SellActivity.this, "剩余份额大于" + BigDecimalUtil.bigdecimalToString(subValue) + "份，且不等于" + BigDecimalUtil.bigdecimalToString(sellResp.getEnable_shares()) + "份，请全部赎回", Toast.LENGTH_LONG);
                    return;
                }

                //TODO 弹出框
                if (fundBuyDialog == null) {
                    fundBuyDialog = new FundBuyDialog
                            .Builder(context)
                            .setFundName(fundName)
                            .setFundAmount("￥" + getText(etAmount) + ".00")
                            .setOnTextFinishListener(new FundBuyDialog.OnTextFinishListener() {
                                @Override
                                public void onFinish(String str) {
                                    fundBuyDialog.dismiss();
//                                    // 卖出接口
                                    getP().sellFund(token, userId, fundCode, sellResp.getTrade_acco(),
                                            str, fundName, strAmount, sellResp.getShare_type(), list.get(isSelector).getCode());
//                                    startActivity(SellSuccessActivity.class);
                                }
                            }).create();
                }
                fundBuyDialog.show();

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
                getP().sellFundPre(token, userId, fundCode);
            }
        }
    }

    /**
     * 刷新银行卡信息 失败
     */
    public void requestFundFail() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 刷新银行卡信息 成功
     */
    public void requestFundSuccess(SellResp data) {
        httpLoadingDialog.dismiss();
        sellResp = data;
        refreshBankView(data.getBankCardPageEntity());
        tvMinSell.setText("（最低赎回" + data.getMinVar() + "份）");
        availableShare.setText(BigDecimalUtil.bigdecimalToString(data.getEnable_shares()) + "份");
        list = data.getFundExceedFlagList();
        tvDealType.setText(list.get(isSelector).getContent());

    }

    /**
     * 立即卖出请求失败
     */
    public void requestSellFail() {

    }

    /**
     * 立即卖出成功
     */
    public void requestSellSuccess(FundStatusResp resp) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.ACTIVITY_OBJECT, resp);
//        bundle.putString("12",resp.getFundCode());
        startActivity(SellSuccessActivity.class,bundle);
        finish();

    }

    /**
     * 卖出 密码错误
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

    @Override
    protected void onDestroy() {
        if (customDialog != null) {
            customDialog.dismiss();
            customDialog = null;
        }
        if (fundBuyDialog != null) {
            fundBuyDialog.dismiss();
            fundBuyDialog = null;
        }
        super.onDestroy();
    }

}
