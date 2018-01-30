package com.zhsoft.fretting.ui.activity.fund;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.ApplyBaseInfo;
import com.zhsoft.fretting.model.fund.GetNextTimeResp;
import com.zhsoft.fretting.model.fund.InvestResp;
import com.zhsoft.fretting.model.fund.InvestSureResp;
import com.zhsoft.fretting.model.user.BankCardResp;
import com.zhsoft.fretting.present.fund.InvestPersent;
import com.zhsoft.fretting.ui.activity.user.BankCardActivity;
import com.zhsoft.fretting.ui.activity.user.FindPwdTradeFirstActivity;
import com.zhsoft.fretting.ui.widget.CustomDialog;
import com.zhsoft.fretting.ui.widget.FundBuyDialog;
import com.zhsoft.fretting.ui.widget.SelectPopupWindow;
import com.zhsoft.fretting.utils.Base64ImageUtil;
import com.zhsoft.fretting.utils.KeyBoardUtils;

import java.util.ArrayList;
import java.util.HashMap;
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
    /** 定投周期选择编号 */
    private String cycleSelectorCode;
    /** 定投日选择编号 */
    private String daySelectorCode;
    /** 周期集合 */
    private ArrayList<ApplyBaseInfo> cycleList;
    /** 周期对应的定投日总集合 */
    private Map<String, ArrayList<ApplyBaseInfo>> dayMap;
    /** 周期弹出框 */
    private SelectPopupWindow cyclePopupWindow;
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
    /** 得到的用户购买准备数据 */
    private InvestResp investResp;
    /** 基金代码 */
    private String fundCode;
    /** 基金名称 */
    private String fundName;
    /** 首次交易月 */
    private String first_trade_month;
    private String protocol_id;

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
        //设置标题
        headTitle.setText("定投");
        //初始化加载框
        httpLoadingDialog = new HttpLoadingDialog(context);

        //获取缓存数据
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        userId = App.getSharedPref().getString(Constant.USERID, "");

        //获取页面类型 定投购买/定投修改
        type = bundle.getString(Constant.INVEST_ACTIVITY_TYPE);
        //如果是定投页面
        if (Constant.INVEST_ACTIVITY.equals(type)) {
            sure.setText("确定购买");
//            getP().myBankCard(token, userId);
        } else if (Constant.INVEST_ACTIVITY_UPDATE.equals(type)) {
            sure.setText("确定修改");
//            getP().myBankCard(token, userId);
        }
        //初始化选择器数据
        initWeeklyList();
        //初始化选择器
        initPopWindow();

        if (bundle != null) {
            //基金代码
            fundCode = bundle.getString(Constant.FUND_DETAIL_CODE);
            //基金名称
            fundName = bundle.getString(Constant.FUND_DETAIL_NAME);
            //协议编号
            protocol_id = bundle.getString(Constant.INVEST_PROTOCOL_ID);
            //显示数据
            investResp = (InvestResp) bundle.getParcelable(Constant.INVEST_FUND_OBJECT);
            //获取到基金数据
            //获取 图片Base64 字符串
            if (investResp != null) {
                //刷新银行卡信息
                refreshBankView(investResp.getBankCardPageEntity());

                //最小投资金额
                etAmount.setHint("最低购买金额" + investResp.getMinPurchaseAmt().intValue() + "元");
                //首次交易月
                first_trade_month = investResp.getFirst_trade_month();
                //下次扣款日
                tvNextTime.setText("注：下次扣款日：" + investResp.getExchdate() + " " + investResp.getExchWeek());
                //定投周期
                cycleSelectorCode = investResp.getProtocol_period_unit();
                for (int i = 0; i < cycleList.size(); i++) {
                    if (cycleList.get(i).getCode().equals(investResp.getProtocol_period_unit())) {
                        cycleSelector = i;
                        etInvestWeek.setText(cycleList.get(i).getContent());
                        break;
                    }
                }
                //定投日
                daySelectorCode = investResp.getFirst_exchdate();
                ArrayList<ApplyBaseInfo> selectList = dayMap.get(cycleList.get(cycleSelector).getContent());
                for (int j = 0; j < selectList.size(); j++) {
                    if (selectList.get(j).getCode().equals(investResp.getFirst_exchdate())) {
                        etInvestDay.setText(selectList.get(j).getContent());
                        break;
                    }
                }

            }
        }

    }

    /**
     * 初始化周期集合
     */
    public void initWeeklyList() {
        //定投周期分类 集合
        cycleList = new ArrayList<>();
        ApplyBaseInfo per_week = new ApplyBaseInfo("1", "每周");
        ApplyBaseInfo per_month = new ApplyBaseInfo("0", "每月");
        cycleList.add(per_week);
        cycleList.add(per_month);
        //周集合
        ArrayList<ApplyBaseInfo> weekList = new ArrayList<>();
        ApplyBaseInfo monday = new ApplyBaseInfo("02", "星期一");
        ApplyBaseInfo tuesday = new ApplyBaseInfo("03", "星期二");
        ApplyBaseInfo wednesday = new ApplyBaseInfo("04", "星期三");
        ApplyBaseInfo thursday = new ApplyBaseInfo("05", "星期四");
        ApplyBaseInfo friday = new ApplyBaseInfo("06", "星期五");
        weekList.add(monday);
        weekList.add(tuesday);
        weekList.add(wednesday);
        weekList.add(thursday);
        weekList.add(friday);

        //日集合
        ArrayList<ApplyBaseInfo> dayList = new ArrayList<>();
        for (int i = 1; i < 29; i++) {
            String stri;
            if (i < 10) {
                stri = "0" + i;
            } else {
                stri = "" + i;
            }
            ApplyBaseInfo dayInfo = new ApplyBaseInfo(stri, stri);
            dayList.add(dayInfo);
        }

        dayMap = new HashMap<>();
        dayMap.put(cycleList.get(0).getContent(), weekList);
        dayMap.put(cycleList.get(1).getContent(), dayList);
    }

    /**
     * 初始化弹出框
     */
    private void initPopWindow() {
        //初始化周期弹出框
        cyclePopupWindow = new SelectPopupWindow(this, cycleList);
        cyclePopupWindow.setCallBack(new SelectPopupWindow.CallBack() {
            @Override
            public void onSelectChange(int currentItem, String name) {
                //上一次选择的周期
                String lastChoose = getText(etInvestWeek);
                //设置选择的内容
                etInvestWeek.setText(name);
                //选择的位置（周期类型）
                cycleSelector = currentItem;
                //获得选择的周期编码
                cycleSelectorCode = cycleList.get(currentItem).getCode();
                //如果周期选择发生改变，则日期选择置空
                if (!getText(etInvestWeek).equals(lastChoose)) {
                    etInvestDay.setText("");
                }
            }
        });
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
                //显示窗口
                cyclePopupWindow.showAtLocation(etInvestWeek, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
            }
        });

        etInvestDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //关闭当前输入框
                KeyBoardUtils.closeKeybord(InvestActivity.this);
                if (!isNotEmpty(getText(etInvestWeek))) {
                    showToast("请选择定投周期");
                    return;
                }
                //上一次选择的定投日
                final String lastDayChoose = getText(etInvestDay);
                final ArrayList<ApplyBaseInfo> selectList = dayMap.get(cycleList.get(cycleSelector).getContent());

                //初始化 日弹出框
                SelectPopupWindow dayPopupWindow = new SelectPopupWindow(context, selectList);
                dayPopupWindow.setCallBack(new SelectPopupWindow.CallBack() {
                    @Override
                    public void onSelectChange(int currentItem, String name) {
                        etInvestDay.setText(name);
                        daySelectorCode = selectList.get(currentItem).getCode();
                        if (isNotEmpty(getText(etInvestDay)) && !getText(etInvestDay).equals(lastDayChoose)) {
                            //请求接口得到扣款日期 下次扣款时间：2017-12-18，遇非交易日顺延
                            getP().nextDeductTime(token, userId, fundCode, cycleSelectorCode, daySelectorCode);
                        }
                    }
                });
                //显示窗口
                dayPopupWindow.showAtLocation(etInvestDay, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置

//                final PopShow popShow = new PopShow(context, etInvestDay);
//                popShow.showTextWithCode(selectList);
//                popShow.setOnClickPop(new PopShow.OnClickPop() {
//                    @Override
//                    public void setRange(int position) {
//                        etInvestDay.setText(selectList.get(position).getContent());
////                        daySelector = position;
//                        if (isNotEmpty(getText(etInvestDay)) && !getText(etInvestDay).equals(lastDayChoose)) {
////                            showToast("内容改变啦");
//                            getP().nextDeductTime("7af37b692611438cbda677386223bd0d", "ffa68a63c1e34aa48d17088e33d39b4f",
//                                    fundCode, cycleList.get(cycleSelector).getCode(), selectList.get(position).getCode());
//                        }
//
//                    }
//                });
            }
        });
        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String strAmount = getText(etAmount);

                //表单验证通过才弹出Dialog
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(strAmount)) {
                    showToast("请输入购买金额");
                    return;
                }

                int amount = Integer.parseInt(strAmount);
                int minAmt = investResp.getMinPurchaseAmt().intValue();
                //如果amount小于最小购买金额，重新填写购买金额
                if (amount < minAmt) {
                    showToast("最小投资金额为" + minAmt + "元");
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
                        .setFundName(fundName)
                        .setFundAmount("￥" + strAmount + ".00")
                        .setOnTextFinishListener(new FundBuyDialog.OnTextFinishListener() {
                            @Override
                            public void onFinish(String str) {
                                fundBuyDialog.dismiss();
                                //请求接口 跳转到定投成功
                                //如果是定投页面
                                if (Constant.INVEST_ACTIVITY.equals(type)) {
                                    //确定购买
                                    httpLoadingDialog.visible();
                                    getP().sureInvest(token, userId, fundCode, fundName, strAmount,
                                            first_trade_month, cycleSelectorCode, daySelectorCode, str, null);
                                } else if (Constant.INVEST_ACTIVITY_UPDATE.equals(type)) {
                                    //确定修改 增加protocol_id
                                    httpLoadingDialog.visible();
                                    getP().sureInvest(token, userId, fundCode, fundName, strAmount,
                                            first_trade_month, cycleSelectorCode, daySelectorCode, str, protocol_id);
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
    public void requestInvestSuccess(BankCardResp resp) {
        httpLoadingDialog.dismiss();
        //刷新银行卡数据
        refreshBankView(resp);
    }

    /**
     * 请求我的银行卡信息失败
     */
    public void requestInvestFail() {
        httpLoadingDialog.dismiss();
    }


    /**
     * 请求扣款时间成功 返回扣款时间
     */
    public void requestDeductTimeSuccess(GetNextTimeResp timeResp) {
        first_trade_month = timeResp.getFirst_trade_month();
        tvNextTime.setText("注：下次扣款日：" + timeResp.getExchdate() + " " + timeResp.getExchWeek());
    }

    /**
     * 请求扣款时间失败 返回扣款时间
     */
    public void requestDeductTimeFail() {

    }

    /**
     * 确认购买成功
     */
    public void requestSureInvestSuccess(InvestSureResp sureResp) {
        httpLoadingDialog.dismiss();
        //传值定成功
        Bundle bundle = new Bundle();
        bundle.putString(Constant.FUND_DETAIL_CODE, fundCode);
        bundle.putParcelable(Constant.INVEST_SUCCESS_OBJECT, sureResp);
        startActivity(InvestSuccessActivity.class, bundle);
    }

    /**
     * 确认购买失败
     */
    public void requestSureInvestFail() {
        httpLoadingDialog.dismiss();
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
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.INVEST_BANK_ACTIVITY && resultCode == Constant.SUCCESS_BACK_BANK) {
            String isChange = data.getStringExtra(Constant.CHANGE_BANK);
            //如果修改了银行卡就刷新本页面数据
            if (Constant.CHANGE_BANK_SUCCESS.equals(isChange)) {
                //获取银行卡数据 判断是否能够定投
                httpLoadingDialog.visible();
                getP().investTime(token, userId, fundCode, fundName);
            }
        }
    }

    /**
     * 刷新银行卡视图
     *
     * @param bankCardResp
     */
    public void refreshBankView(BankCardResp bankCardResp) {
        String strimage = bankCardResp.getBankLogo();
        if (!TextUtils.isEmpty(strimage)) {
            //将Base64图片串转换成Bitmap
            Bitmap bitmap = Base64ImageUtil.base64ToBitmap(strimage);
            bankImage.setImageBitmap(bitmap);
        }
        //银行卡名称和尾号
        bankName.setText(bankCardResp.getBankName() + "（" + bankCardResp.getBankNoTail() + "）");
        //银行限额
        bankLimit.setText("单笔限额" + bankCardResp.getLimit_per_payment() + "万，单日限额" + bankCardResp.getLimit_per_day() + "万");

    }

    /**
     * 提交定投数据，返回输入密码错误
     */
    public void passwordError() {
        httpLoadingDialog.dismiss();
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
