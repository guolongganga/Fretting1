//package com.zhsoft.fretting.ui.activity.fund;
//
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.zhsoft.fretting.R;
//import com.zhsoft.fretting.ui.activity.user.BankCardActivity;
//import com.zhsoft.fretting.ui.activity.user.FindPwdTradeFirstActivity;
//import com.zhsoft.fretting.ui.widget.CustomDialog;
//import com.zhsoft.fretting.ui.widget.FundBuyDialog;
//import com.zhsoft.fretting.ui.widget.PopShow;
//import com.zhsoft.fretting.utils.KeyBoardUtils;
//
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import butterknife.BindView;
//import cn.droidlover.xdroidmvp.mvp.XActivity;
//
///**
// * 作者：sunnyzeng on 2018/1/9 10:57
// * 描述：定投页面
// */
//
//public class InvestActivity extends XActivity {
//    @BindView(R.id.head_back) ImageButton headBack;
//    @BindView(R.id.head_title) TextView headTitle;
//    @BindView(R.id.bank_image) ImageView bankImage;
//    @BindView(R.id.bank_name) TextView bankName;
//    @BindView(R.id.bank_limit) TextView bankLimit;
//    @BindView(R.id.tv_change) TextView tvChange;
//    @BindView(R.id.et_amount) EditText etAmount;
//    @BindView(R.id.tv_invest_week) TextView etInvestWeek;
//    @BindView(R.id.tv_invest_day) TextView etInvestDay;
//    @BindView(R.id.sure) Button sure;
//
//    /** 周期选择 */
//    private int isSelector = 1;
//    /** 周期集合 */
//    private List<String> cycleList;
//    private Map<String, ArrayList<String>> dayMap;
//    private FundBuyDialog fundBuyDialog;
//    private CustomDialog customDialog;
//
//    @Override
//    public int getLayoutId() {
//        return R.layout.activity_fund_invest;
//    }
//
//    @Override
//    public Object newP() {
//        return null;
//    }
//
//    @Override
//    public void initData(Bundle bundle) {
//        headTitle.setText("定投");
//        cycleList = new ArrayList<>();
//        cycleList.add("每周");
//        cycleList.add("每月");
//        //周集合
//        ArrayList<String> weekList = new ArrayList<>();
//        weekList.add("星期一");
//        weekList.add("星期二");
//        weekList.add("星期三");
//        weekList.add("星期四");
//        weekList.add("星期五");
//        //日集合
//        ArrayList<String> dayList = new ArrayList<>();
//        for (int i = 1; i < 29; i++) {
//            dayList.add(i + "日");
//        }
//
//        dayMap = new HashMap<>();
//        dayMap.put(cycleList.get(0), weekList);
//        dayMap.put(cycleList.get(1), dayList);
//        //一进入定投页面，默认周期为月
//        etInvestWeek.setText(cycleList.get(isSelector));
//        //定投日为明天 获取今天的日期
//        Calendar calendar = Calendar.getInstance();
//        int day = calendar.get(Calendar.DAY_OF_MONTH);
//        etInvestDay.setText(dayList.get(day));
//
//    }
//
//    @Override
//    public void initEvents() {
//        headBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                finish();
//            }
//        });
//        etInvestWeek.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //关闭当前输入框
//                KeyBoardUtils.closeKeybord(InvestActivity.this);
//
//                final String lastChoose = getText(etInvestWeek);
//                PopShow popShow = new PopShow(context, etInvestWeek);
//                popShow.showText(cycleList);
//                popShow.setOnClickPop(new PopShow.OnClickPop() {
//                    @Override
//                    public void setRange(int position) {
//                        etInvestWeek.setText(cycleList.get(position));
//                        isSelector = position;
//                        if (!getText(etInvestWeek).equals(lastChoose)) {
//                            etInvestDay.setText("");
//                        }
//                    }
//                });
//
//            }
//        });
//
//        etInvestDay.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //关闭当前输入框
//                KeyBoardUtils.closeKeybord(InvestActivity.this);
//                final ArrayList<String> selectList = dayMap.get(cycleList.get(isSelector));
//
//                PopShow popShow = new PopShow(context, etInvestDay);
//                popShow.showText(selectList);
//                popShow.setOnClickPop(new PopShow.OnClickPop() {
//                    @Override
//                    public void setRange(int position) {
//                        etInvestDay.setText(selectList.get(position));
//                    }
//                });
//            }
//        });
//        sure.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //表单验证通过才弹出Dialog
//                if (noNetWork()) {
//                    showNetWorkError();
//                    return;
//                }
//                if (!isNotEmpty(getText(etAmount))) {
//                    showToast("请输入购买金额");
//                    return;
//                }
//                if (!isNotEmpty(getText(etInvestWeek))) {
//                    showToast("请选择定投周期");
//                    return;
//                }
//                if (!isNotEmpty(getText(etInvestDay))) {
//                    showToast("请选择定投日");
//                    return;
//                }
//                fundBuyDialog = new FundBuyDialog
//                        .Builder(context)
//                        .setFundName("国泰哈哈哈基金")
//                        .setFundAmount("￥" + etAmount.getText().toString() + ".00")
//                        .setOnTextFinishListener(new FundBuyDialog.OnTextFinishListener() {
//                            @Override
//                            public void onFinish(String str) {
//                                //TODO 判断密码是否正确
//                                fundBuyDialog.dismiss();
//
//                                if ("123456".equals(str)) {
////                                    showToast("密码正确");
//                                    startActivity(InvestSuccessActivity.class);
//                                } else {
//                                    customDialog = new CustomDialog.Builder(context)
//                                            .setMessage("交易密码错误，请重试")
//                                            .setNegativeButton("忘记密码", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialogInterface, int i) {
//                                                    startActivity(FindPwdTradeFirstActivity.class);
//                                                }
//                                            }).setPositiveButton("再试一次", new DialogInterface.OnClickListener() {
//                                                @Override
//                                                public void onClick(DialogInterface dialogInterface, int i) {
//                                                    customDialog.dismiss();
//                                                    fundBuyDialog.show();
//                                                }
//                                            }).create();
//                                    customDialog.show();
//                                }
//                            }
//                        }).create();
//                fundBuyDialog.show();
//            }
//        });
//
//        tvChange.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(BankCardActivity.class);
//            }
//        });
//    }
//
//
//}
