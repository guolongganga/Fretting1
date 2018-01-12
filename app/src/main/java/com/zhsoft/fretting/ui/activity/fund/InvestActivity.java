package com.zhsoft.fretting.ui.activity.fund;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.present.fund.InvestPersent;
import com.zhsoft.fretting.ui.activity.user.BankCardActivity;
import com.zhsoft.fretting.ui.activity.user.FindPwdTradeFirstActivity;
import com.zhsoft.fretting.ui.widget.CustomDialog;
import com.zhsoft.fretting.ui.widget.FundBuyDialog;
import com.zhsoft.fretting.ui.widget.PopShow;
import com.zhsoft.fretting.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2018/1/9 10:57
 * 描述：定投页面
 */

public class InvestActivity extends XActivity<InvestPersent> {
    /** 返回 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 银行logo */
    @BindView(R.id.bank_image) ImageView bankImage;
    /** 银行名称 */
    @BindView(R.id.bank_name) TextView bankName;
    /** 银行限额 */
    @BindView(R.id.bank_limit) TextView bankLimit;
    /** 修改银行卡 */
    @BindView(R.id.tv_change) TextView tvChange;
    /** 购买金额 */
    @BindView(R.id.et_amount) EditText etAmount;
    /** 定投周期 */
    @BindView(R.id.tv_invest_week) TextView etInvestWeek;
    /** 定投日 */
    @BindView(R.id.tv_invest_day) TextView etInvestDay;
    /** 下次扣款时间 */
    @BindView(R.id.tv_next_time) TextView tvNextTime;
    /** 确定按钮 */
    @BindView(R.id.sure) Button sure;

    /** 定投周期选择 */
    private int cycleSelector = 1;
    /** 定投日选择 */
    private int daySelector;
    /** 周期集合 */
    private List<String> cycleList;
    /** 周期对应的定投日总集合 */
    private Map<String, ArrayList<String>> dayMap;
    /** 交易密码弹出框 */
    private FundBuyDialog fundBuyDialog;
    /** 结果弹出框 */
    private CustomDialog customDialog;
    /** 投资页的类型，是定投，还是定投修改 */
    private String type;
    /** 加载框 */
    private HttpLoadingDialog httpLoadingDialog;
    /** 登录标识 */
    private String token;
    /** 用户编号 */
    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fund_invest_update;
    }

    @Override
    public InvestPersent newP() {
        return new InvestPersent();
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("定投");
        httpLoadingDialog = new HttpLoadingDialog(context);

        //获取缓存数据
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        userId = App.getSharedPref().getString(Constant.USERID, "");

        //获取页面类型 定投购买/定投修改
        type = bundle.getString(Constant.INVEST_ACTIVITY_TYPE);
        //如果是定投页面
        if (Constant.INVEST_ACTIVITY.equals(type)) {
            sure.setText("确定购买");
            getP().myBankCard(token, userId);
        } else if (Constant.INVEST_ACTIVITY_UPDATE.equals(type)) {
            sure.setText("确定修改");
            getP().myBankCard(token, userId);
        }

        //定投周期分类 集合
        cycleList = new ArrayList<>();
        cycleList.add("每周");
        cycleList.add("每月");
        //周集合
        ArrayList<String> weekList = new ArrayList<>();
        weekList.add("星期一");
        weekList.add("星期二");
        weekList.add("星期三");
        weekList.add("星期四");
        weekList.add("星期五");
        //日集合
        ArrayList<String> dayList = new ArrayList<>();
        for (int i = 1; i < 29; i++) {
            dayList.add(i + "日");
        }

        dayMap = new HashMap<>();
        dayMap.put(cycleList.get(0), weekList);
        dayMap.put(cycleList.get(1), dayList);
        //一进入定投页面，默认周期为月
        etInvestWeek.setText(cycleList.get(cycleSelector));
        //TODO 定投日为明天 获取今天的日期
        Calendar calendar = Calendar.getInstance();
        daySelector = calendar.get(Calendar.DAY_OF_MONTH);

        etInvestDay.setText(dayList.get(daySelector));

    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        etInvestWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭当前输入框
                KeyBoardUtils.closeKeybord(InvestActivity.this);
                //上一次选择的周期
                final String lastChoose = getText(etInvestWeek);
                PopShow popShow = new PopShow(context, etInvestWeek);
                popShow.showText(cycleList);
                popShow.setOnClickPop(new PopShow.OnClickPop() {
                    @Override
                    public void setRange(int position) {
                        etInvestWeek.setText(cycleList.get(position));
                        cycleSelector = position;
                        if (!getText(etInvestWeek).equals(lastChoose)) {
                            etInvestDay.setText("");
                        }
                    }
                });

            }
        });

        etInvestDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭当前输入框
                KeyBoardUtils.closeKeybord(InvestActivity.this);
                //上一次选择的定投日
                final String lastDayChoose = getText(etInvestDay);
                final ArrayList<String> selectList = dayMap.get(cycleList.get(cycleSelector));

                final PopShow popShow = new PopShow(context, etInvestDay);
                popShow.showText(selectList);
                popShow.setOnClickPop(new PopShow.OnClickPop() {
                    @Override
                    public void setRange(int position) {
                        etInvestDay.setText(selectList.get(position));
//                        daySelector = position;
                        if (isNotEmpty(getText(etInvestDay)) && !getText(etInvestDay).equals(lastDayChoose)) {
//                            showToast("内容改变啦");
                            //TODO 请求接口得到扣款日期 下次扣款时间：2017-12-18，遇非交易日顺延
                            getP().nextDeductTime(token, userId);
                            tvNextTime.setText("下次扣款时间：2018-1-" + (position + 1) + "，遇非交易日顺延");
                        }

                    }
                });
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
                if (!isNotEmpty(strAmount)) {
                    showToast("请输入购买金额");
                    return;
                }
                //TODO 如果amount小于最小购买金额，重新填写购买金额
                if (amount < 100) {
                    showToast("最小投资金额为100元");
                    return;
                }
                if (!isNotEmpty(getText(etInvestWeek))) {
                    showToast("请选择定投周期");
                    return;
                }
                if (!isNotEmpty(getText(etInvestDay))) {
                    showToast("请选择定投日");
                    return;
                }
                fundBuyDialog = new FundBuyDialog
                        .Builder(context)
                        .setFundName("国泰哈哈哈基金")
                        .setFundAmount("￥" + strAmount + ".00")
                        .setOnTextFinishListener(new FundBuyDialog.OnTextFinishListener() {
                            @Override
                            public void onFinish(String str) {
                                fundBuyDialog.dismiss();
                                //TODO 判断密码是否正确
                                if ("123456".equals(str)) {
//                                    showToast("密码正确");
                                    //TODO 密码正确 请求接口 跳转到定投成功
                                    //如果是定投页面
                                    if (Constant.INVEST_ACTIVITY.equals(type)) {
                                        //TODO 确定购买
                                        getP().sureInvest(token, userId, "22222222", strAmount, getText(etInvestWeek), getText(etInvestDay));
                                    } else if (Constant.INVEST_ACTIVITY_UPDATE.equals(type)) {
                                        //TODO 确定修改
                                        getP().updateInvest(token, userId, "22222222", strAmount, getText(etInvestWeek), getText(etInvestDay));
                                    }
                                    startActivity(InvestSuccessActivity.class);
                                } else {
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
                                    customDialog.show();
                                }
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
    }

    /**
     * 请求我的银行卡信息成功 数据显示
     */
    public void requestMyBankSuccess() {
        //获取到基金数据
//        bankCardResp = resp;
        //获取 图片Base64 字符串
//        String strimage = resp.getBankLogo();
//        if (!TextUtils.isEmpty(strimage)) {
//            //将Base64图片串转换成Bitmap
//            Bitmap bitmap = Base64ImageUtil.base64ToBitmap(strimage);
//            bankImage.setImageBitmap(bitmap);
//        }
        //银行卡名称和尾号
//        bankName.setText(resp.getBankName() + "（" + resp.getBankNoTail() + "）");
        //银行限额
//        bankLimit.setText("单笔限额" + resp.getLimit_per_payment() + "万，单日限额" + resp.getLimit_per_day() + "万，单月限额" + resp.getLimit_per_month() + "万");

    }

    /**
     * 请求我的银行卡信息失败
     */
    public void requestMyBankFail() {
    }

    /**
     * 请求扣款时间成功 返回扣款时间
     */
    public void requestDeductTimeSuccess() {
//        tvNextTime.setText("下次扣款时间：2018-1-" + (position + 1) + "，遇非交易日顺延");
    }

    /**
     * 请求扣款时间失败 返回扣款时间
     */
    public void requestDeductTimeFail() {

    }

    /**
     * 确认购买成功
     */
    public void requestSureInvestSuccess() {
//        startActivity(InvestSuccessActivity.class);
    }

    /**
     * 确认购买失败
     */
    public void requestSureInvestFail() {
    }

    /**
     * 修改定投成功
     */
    public void requestUpdateInvestSuccess() {

    }

    /**
     * 修改定投失败
     */
    public void requestUpdateInvestFail() {
        //startActivity(InvestSuccessActivity.class);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.INVEST_BANK_ACTIVITY && resultCode == Constant.SUCCESS_BACK_BANK) {
            String isChange = data.getStringExtra(Constant.CHANGE_BANK);
            //如果修改了银行卡就刷新本页面数据
            if (Constant.CHANGE_BANK_SUCCESS.equals(isChange)) {
                //TODO 获取银行卡数据
                getP().myBankCard(token, userId);
            }
        }
    }
}
