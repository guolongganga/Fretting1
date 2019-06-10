package cn.com.buyforyou.fund.ui.activity.fund;

import android.content.DialogInterface;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;


import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cn.com.buyforyou.fund.App;
import cn.com.buyforyou.fund.R;
import cn.com.buyforyou.fund.constant.Constant;
import cn.com.buyforyou.fund.event.ChangeBankCardMessageEvent;

import cn.com.buyforyou.fund.event.RefreshUserDataEvent;
import cn.com.buyforyou.fund.model.ApplyBaseInfo;
import cn.com.buyforyou.fund.model.fund.BankCard;

import cn.com.buyforyou.fund.model.fund.BuyFundResp;

import cn.com.buyforyou.fund.model.fund.CalculationResp;
import cn.com.buyforyou.fund.model.fund.FundStatusResp;

import cn.com.buyforyou.fund.net.Api;
import cn.com.buyforyou.fund.net.HttpContent;
import cn.com.buyforyou.fund.present.fund.BuyPresent;
import cn.com.buyforyou.fund.ui.activity.boot.WebPublicActivity;

import cn.com.buyforyou.fund.ui.activity.user.ChangeBankCardListActivity;
import cn.com.buyforyou.fund.ui.activity.user.FindPwdTradeFirstActivity;
import cn.com.buyforyou.fund.ui.activity.user.LoginActivity;
import com.zhsoft.fretting.ui.widget.CustomDialog;
import com.zhsoft.fretting.ui.widget.FundBuyDialog;
import com.zhsoft.fretting.ui.widget.MyClickText;
import com.zhsoft.fretting.ui.widget.OnTvClick;
import com.zhsoft.fretting.ui.widget.PopShow;



import cn.com.buyforyou.fund.utils.Base64ImageUtil;
import cn.com.buyforyou.fund.utils.BigDecimalUtil;
import cn.com.buyforyou.fund.utils.RuntimeHelper;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;

import java.util.List;


import butterknife.BindView;

import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XActivity;


/**
 * 作者：sunnyzeng on 2018/1/8 11:55
 * 描述：基金详情页-购买页面
 */

public class BuyActivity extends XActivity<BuyPresent> {
    private static final String TAG ="BuyActivity";
    /**
     * 返回
     */
    @BindView(R.id.head_back)
    ImageButton headBack;
    /**
     * 标题
     */
    @BindView(R.id.head_title)
    TextView headTitle;
    /**
     * 银行logo
     */
    @BindView(R.id.bank_image)
    ImageView bankImage;
    /**
     * 银行卡名称和尾号
     */
    @BindView(R.id.bank_name)
    TextView bankName;
    /**
     * 银行卡限额
     */
    @BindView(R.id.bank_limit)
    TextView bankLimit;
    /**
     * 更换银行卡
     */
    @BindView(R.id.rl_change)
    RelativeLayout rlChange;
    /**
     * 申购费
     */
    @BindView(R.id.tv_apply_fee)
    TextView tvApplyFee;
    /**
     * 确认份额时间
     */
    @BindView(R.id.tv_sure_time)
    TextView tvSureTime;
    /**
     * 查看收益时间
     */
    @BindView(R.id.tv_look_time)
    TextView tvLookTime;
    /**
     * 购买金额
     */
    @BindView(R.id.et_amount)
    EditText etAmount;
    /**
     * 确认购买
     */
    @BindView(R.id.sure)
    Button sure;
    /**
     * 分红方式
     */
    @BindView(R.id.ll_bonus)
    LinearLayout llBonus;
    /**
     * 分红方式
     */
    @BindView(R.id.tv_bonus_type)
    TextView tvBonusType;
    /**
     * 手续费
     */
    @BindView(R.id.tv_poundage)
    TextView tvPoundage;
    /**
     * 费率
     */
    @BindView(R.id.tv_rate)
    TextView tvRate;
    /**
     * 基金合同和招募说明书
     */
//    @BindView(R.id.tv_fund_contract)
//    TextView tvFundContract;

    /**
     * 勾选框
     */
    @BindView(R.id.register_service_select)
    ImageView registerServiceSelect;

    /**
     * 注册协议
     */
    @BindView(R.id.tv_agreement)
    TextView tvAgreement;
    /**
     * 输入密码弹框
     */
    private FundBuyDialog fundBuyDialog;
    /**
     * 密码错误弹框
     */
    private CustomDialog customDialog;
    /**
     * 得到的用户购买准备数据
     */
    private BuyFundResp buyFundResp;
    /**
     * 基金代码
     */
    private String fundCode;
    /**
     * 基金名称
     */
    private String fundName;
    /**
     * 登录标识
     */
    private String token;
    /**
     * 用户编号
     */
    private String userId;
    /**
     * 加载框
     */
    private HttpLoadingDialog httpLoadingDialog;
    /**
     * 分红方式 选中选项
     */
    private int isSelector = 0;
    /**
     * 分红方式
     */
    private List<ApplyBaseInfo> list;
    private String logo;
    private String name;
    private String bankInfo;
    private String source_rate;
    private String curr_rate;
    private String trade_acco;
    private   String bankNoTail;


    @Override
    public int getLayoutId() {
        return R.layout.activity_fund_buy;
    }

    @Override
    public BuyPresent newP() {
        return new BuyPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        EventBus.getDefault().register(this);
        headTitle.setText(R.string.fund_buy);
        //xrvBankList.verticalLayoutManager(context);//设置RecycleView类型 - 不设置RecycleView不显示
        httpLoadingDialog = new HttpLoadingDialog(context);
        registerServiceSelect.setSelected(false);
        agreementText();
       // agreementFund();
        //获取缓存数据
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        userId = App.getSharedPref().getString(Constant.USERID, "");

        //中间横线
        tvApplyFee.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        // 抗锯齿
        tvApplyFee.getPaint().setAntiAlias(true);
        tvPoundage.setText("申购费  0.00元");

        if (bundle != null) {
            fundCode = bundle.getString(Constant.FUND_DETAIL_CODE);
            fundName = bundle.getString(Constant.FUND_DETAIL_NAME);
            buyFundResp = (BuyFundResp)bundle.getParcelable(Constant.BUY_FUND_OBJECT);
            //获取到基金数据
            //获取 图片Base64 字符串
            if (buyFundResp != null) {
                refreshBankView(buyFundResp);
                list = buyFundResp.getDefault_auto_buy();
                if (list != null && list.size() > 0) {
                    tvBonusType.setText(list.get(isSelector).getContent());
                }
                //确认时间
                tvSureTime.setText("·" + buyFundResp.getInfo1());
                //查看收益时间
                tvLookTime.setText("·" + buyFundResp.getInfo2());
                tvRate.setText(curr_rate + "%");
                tvApplyFee.setText(source_rate + "%");
                String minValue;
                if (buyFundResp.getLow_value() != null) {
                    minValue = BigDecimalUtil.bigdecimalToString(buyFundResp.getLow_value());
                } else {
                    minValue = "0.00";
                }

                etAmount.setHint("最低购买金额" + minValue + "元");
            }
        }


    }

    /**
     * 刷新银行卡视图
     *
     * @param fundResp
     */
    public void refreshBankView(BuyFundResp fundResp) {
       // httpLoadingDialog.dismiss();
        List<BankCard> data = fundResp.getBankCard();
        for (BankCard bankList:data)
        {
            //判断select 如果是0 的话  显示默认的银行卡

            if(bankList.getSelect()!=null&&bankList.getSelect().equals("0"))
            {
                //银行名称
                name = bankList.getName();
                //银行logo
                logo = bankList.getLogo();
                //银行限额
                bankInfo = bankList.getInfo();
                //折扣后申购费率
                curr_rate = bankList.getCurr_rate();
                //原始申购费率
                source_rate = bankList.getSource_rate();
                //银行尾号
                 bankNoTail = bankList.getBankNoTail();
                trade_acco = bankList.getTrade_acco();

            }


        }
        if (!TextUtils.isEmpty(logo)) {
            //将Base64图片串转换成Bitmap
            Bitmap bitmap = Base64ImageUtil.base64ToBitmap(logo);
            bankImage.setImageBitmap(bitmap);
        }
        //银行卡名称和尾号
        bankName.setText(name);
        //银行卡限额
        bankLimit.setText(bankInfo);

    }


    @Override
    public void initEvents() {
        etAmount.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(2)});
        /*返回*/
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        /*确定购买*/
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String strAmount = getText(etAmount);

                //表单验证通过才弹出Dialog
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(getText(etAmount))) {
                    showToast("请输入购买金额");
                    return;
                }
                BigDecimal amount = BigDecimal.valueOf(Double.parseDouble(strAmount));
                //如果amount小于0，重新填写购买金额
                if (amount.compareTo(BigDecimal.ZERO) <= 0) {
                    showToast("请输入正确的投资金额");
                    return;
                }
                //如果amount小于最小购买金额，重新填写购买金额
                if (amount.compareTo(buyFundResp.getLow_value()) < 0) {
                    showToast("最小投资金额为" + BigDecimalUtil.bigdecimalToString(buyFundResp.getLow_value()) + "元");
                    return;
                }
                //如果amount大于最大购买金额，重新填写购买金额
                XLog.d(buyFundResp.getHigh_value()+"");
                if (amount.compareTo(buyFundResp.getHigh_value()) > 0) {
                    showToast("最大投资金额为" + BigDecimalUtil.bigdecimalToString(buyFundResp.getHigh_value()) + "元");
                    return;
                }
                if (!registerServiceSelect.isSelected()) {
                    showToast("请阅读并同意协议");
                    return;
                }
                //格式化输入金额
//                DecimalFormat df = new DecimalFormat(",###,##0.00"); //保留两位小数
//                String dealAmount = df.format(amount);
                //弹出框
                fundBuyDialog = new FundBuyDialog
                        .Builder(context)
                        .setFundName(fundName)
                        .setFundAmount("￥" + amount)
                        .setOnTextFinishListener(new FundBuyDialog.OnTextFinishListener() {
                            @Override
                            public void onFinish(String str) {
                                fundBuyDialog.dismiss();
                                httpLoadingDialog.visible();
                                getP().purchase(token, userId, fundCode, strAmount, str, list.get(isSelector).getCode(),trade_acco);

                            }
                        }).create();
                fundBuyDialog.show();

            }
        });
        /*变更银行卡*/
        rlChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle=new Bundle();
                bundle.putParcelable(Constant.BUY_FUND_OBJECT,buyFundResp);
                startActivity(ChangeBankCardListActivity.class,bundle);
                //startActivity(ChangeBankCardListActivity.class, Constant.INVEST_BANK_ACTIVITY);
            }
        });
         /*服务协议勾选*/
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
        /*选择分红方式*/
        llBonus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopShow popShow = new PopShow(context, llBonus);
                popShow.showRangeSelector(list, isSelector);
                popShow.setOnClickPop(new PopShow.OnClickPop() {
                    @Override
                    public void setRange(int position) {
                        isSelector = position;
                        tvBonusType.setText(list.get(position).getContent());
                    }
                });
            }
        });
        /*金额输入监控*/
        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!"".equals(s.toString())) {
                    //如果输入的内容不为空，查询费率,确认按钮可点击
                    sure.setBackgroundColor(getResources().getColor(R.color.color_4D7BFE));
                    getP().buyFundCalculation(token, userId, fundCode, getText(etAmount),trade_acco);
                    sure.setClickable(true);
                } else {
                    tvPoundage.setText("申购费  0.00元");
                    tvRate.setText(curr_rate + "%");

                 //   tvApplyFee.setText(buyFundResp.getBankCard().() + "%");
                    //如果输入的内容为空，则不显示申购费，按钮不可点击
                    sure.setBackgroundColor(getResources().getColor(R.color.color_B9D1F8));
                    sure.setClickable(false);

                }
            }
        });
    }

    /**
     * 修改了银行卡就刷新本页面数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == Constant.INVEST_BANK_ACTIVITY && resultCode == Constant.SUCCESS_BACK_BANK) {
//            String isChange = data.getStringExtra(Constant.CHANGE_BANK);
//            //如果修改了银行卡就刷新本页面数据
//            if (Constant.CHANGE_BANK_SUCCESS.equals(isChange)) {
//                //获取银行卡数据
//                httpLoadingDialog.visible();
//                getP().buyFund(token, userId, fundCode);
//            }
//        }
//    }


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == Constant.BANKLIST_RESULT_CODE && requestCode == Constant.BANKLIST_ACTIVITY) {
//            BankCard bankCard = data.getParcelableExtra(Constant.CHOOSE_BANCK);
//
//
//                String   bank_name = bankCard.getName();
//                String info = bankCard.getInfo();
//                String logo = bankCard.getLogo();
//                if (!TextUtils.isEmpty(logo)) {
//                    //将Base64图片串转换成Bitmap
//                    Bitmap bitmap = Base64ImageUtil.base64ToBitmap(logo);
//                    bankImage.setImageBitmap(bitmap);
//                }
//                //银行卡名称和尾号
//                bankName.setText(bank_name);
//                //银行卡限额
//                bankLimit.setText(info);
//            }
//
//        }


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
        //BankCardBuyResp buyResp = data.getBuyResp();
        refreshBankView(data);
    }

    /**
     * 立即购买请求失败
     */
    public void requestBuyNowFail() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 立即购买成功
     *
     * @param data
     */
    public void requestBuyNowSuccess(FundStatusResp data) {
        httpLoadingDialog.dismiss();
        EventBus.getDefault().post(new RefreshUserDataEvent());
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constant.BUY_SUCCESS_OBJECT, data);
        startActivity(BuySuccessActivity.class, bundle);
        finish();

    }

    /**
     * 更新银行卡信息
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void onChangeTabEvent(ChangeBankCardMessageEvent event) {

        String info = event.getInfo();
        String logo = event.getLogo();
        String name = event.getName();
        trade_acco = event.getTrade_acco();
        if (!TextUtils.isEmpty(logo)) {
            //将Base64图片串转换成Bitmap
            Bitmap bitmap = Base64ImageUtil.base64ToBitmap(logo);
            bankImage.setImageBitmap(bitmap);
        }
        //银行卡名称和尾号
        bankName.setText(name);
        //银行卡限额
        bankLimit.setText(info);

        Log.e(TAG, "onChangeTabEvent: "+name+info );



    }

    /**
     * 立即购买 密码错误
     *
     * @param message
     */
    public void passwordError(String message) {
        httpLoadingDialog.dismiss();
        if (customDialog == null) {
            customDialog = new CustomDialog.Builder(context)
                    .setMessage(message)
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

    /**
     * 销毁
     */
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
        EventBus.getDefault().unregister(this);
    }

    /**
     * 计算申购费 失败
     */
    public void requestCalculationFail() {
    }

    /**
     * 计算申购费 成功
     *
     * @param calculationResp
     */
    public void requestCalculationSuccess(CalculationResp calculationResp) {
        if (sure.isClickable()) {
            //申购费
            String rate = calculationResp.getFare_sx();
            if (rate.equals("null")) {
                rate = "0.00";
            }
            tvPoundage.setText("申购费  " + rate + "元");
            tvRate.setText(curr_rate + "%");
            tvApplyFee.setText(source_rate + "%");
        }else {
            tvRate.setText(curr_rate + "%");
            tvApplyFee.setText(source_rate + "%");
        }
    }

    /**
     * 已经登出系统，请重新登录
     */
    public void areadyLogout() {
        httpLoadingDialog.dismiss();
//        EventBus.getDefault().post(new InvalidTokenEvent());
        //清除本地缓存，设置成未登录
        RuntimeHelper.getInstance().isInvalidToken();
        //跳转登录界面
        Bundle bundle = new Bundle();
        bundle.putString(Constant.SKIP_SIGN, Constant.SKIP_INDEX_ACTIVITY);
        startActivity(LoginActivity.class, bundle);
    }

    public void agreementText() {

        SpannableString spannableString = new SpannableString("购买之前请阅读并同意《公募基金风险揭示及售前告知书》");

        MyClickText click1 = new MyClickText(this);
        click1.setOnTvClick(new OnTvClick() {
            @Override
            public void onClick(View widget) {
                Bundle bundle = new Bundle();
//                bundle.putInt(Constant.WEB_TITLE, R.string.user_register_service1);
                bundle.putString(Constant.WEB_LINK, Api.API_BASE_URL + HttpContent.agreement_buyagreement);
                startActivity(WebPublicActivity.class, bundle);
            }
        });

        //设置下划线
        spannableString.setSpan(click1, 10, 26, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//        spannableString.setSpan(click2, 24, 35, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        //当然这里也可以通过setSpan来设置哪些位置的文本哪些颜色
        tvAgreement.setText(spannableString);
        tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());
        tvAgreement.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明

    }
//    public void agreementFund() {
//
//        SpannableString spannableString = new SpannableString("该产品为高风险产品,可能因为市场波动等原因导致本金出现亏损,请务必关注本网披露的费率,交易规则及公告,仔细阅读《基金合同》、" +
//                "《招募说明书》,了解您的主要权益和产品特定风险,审慎决策。");
//
//        MyClickText click1 = new MyClickText(this);
//        click1.setOnTvClick(new OnTvClick() {
//            @Override
//            public void onClick(View widget) {
//                Bundle bundle = new Bundle();
////                bundle.putInt(Constant.WEB_TITLE, R.string.user_register_service1);
//                bundle.putString(Constant.WEB_LINK, Api.API_BASE_URL + HttpContent.agreement_buyagreement);
//                startActivity(WebPublicActivity.class, bundle);
//            }
//        });
//
//
//        MyClickText click2 = new MyClickText(this);
//        click2.setOnTvClick(new OnTvClick() {
//            @Override
//            public void onClick(View widget) {
//                Bundle bundle = new Bundle();
//                bundle.putString(Constant.WEB_LINK, Api.API_BASE_URL + HttpContent.openaccount_instructions);
//                startActivity(WebPublicActivity.class, bundle);
//            }
//        });
//
//        //设置下划线
//        spannableString.setSpan(click1, 55, 61, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//
//        spannableString.setSpan(click2, 62, 69, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
//
//
//        //当然这里也可以通过setSpan来设置哪些位置的文本哪些颜色+
//        tvFundContract.setText(spannableString);
//        tvFundContract.setMovementMethod(LinkMovementMethod.getInstance());
//        tvFundContract.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明
//    }

}
