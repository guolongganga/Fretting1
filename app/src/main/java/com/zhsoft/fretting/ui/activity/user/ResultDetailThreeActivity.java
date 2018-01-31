package com.zhsoft.fretting.ui.activity.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.ResultDetailResp;
import com.zhsoft.fretting.model.user.StepResp;
import com.zhsoft.fretting.present.user.ResultDetailOnePresent;
import com.zhsoft.fretting.ui.widget.CustomDialog;
import com.zhsoft.fretting.ui.widget.FundBuyDialog;
import com.zhsoft.fretting.utils.BigDecimalUtil;

import java.util.ArrayList;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2018/1/23 11:21
 * 描述：结果页 交易详情
 */

public class ResultDetailThreeActivity extends XActivity<ResultDetailOnePresent> {
    /** 返回 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 右侧按钮 */
    @BindView(R.id.head_right) Button headRight;
    /** 基金名称 */
    @BindView(R.id.tv_fund_name) TextView tvFundName;
    /** 购买金额 */
    @BindView(R.id.tv_fund_amount) TextView tvFundAmount;
    /** 银行信息 */
    @BindView(R.id.tv_bank_name) TextView tvBankName;
    /** 第一步 图标 */
    @BindView(R.id.iv_pay_success) ImageView ivPaySuccess;
    /** 第一步 状态 */
    @BindView(R.id.font_pay_success) TextView fontPaySuccess;
    /** 第一步 时间 */
    @BindView(R.id.tv_pay_success) TextView tvPaySuccess;
    /** 第二步 图标 */
    @BindView(R.id.iv_sure_number) ImageView ivSureNumber;
    /** 第二步 时间 */
    @BindView(R.id.tv_sure_number) TextView tvSureNumber;
    /** 第二步 状态 */
    @BindView(R.id.font_sure_number) TextView fontSureNumber;
    /** 第三步 图标 */
    @BindView(R.id.iv_query_income) ImageView ivQueryIncome;
    /** 第三步 时间 */
    @BindView(R.id.tv_query_income) TextView tvQueryIncome;
    /** 第三步 状态 */
    @BindView(R.id.font_query_income) TextView fontQueryIncome;
    /** 进度 */
    @BindView(R.id.ll_progress_info) LinearLayout llProgressInfo;
    /** 确认金额 */
    @BindView(R.id.tv_sure_amount) TextView tvSureAmount;
    /** 确认份额 */
    @BindView(R.id.tv_sure_share) TextView tvSureShare;
    /** 确认净值 */
    @BindView(R.id.tv_sure_rate) TextView tvSureRate;
    /** 确认日期 */
    @BindView(R.id.tv_sure_date) TextView tvSureDate;
    /** 确认信息 */
    @BindView(R.id.ll_sure_info) LinearLayout llSureInfo;
    /** 交易记录的状态 */
    private String recordStatus;
    /** 终止弹框 */
    private CustomDialog customDialog;
    /** 交易流水号 */
    private String allot_no;
    /** 登录标识 */
    private String token;
    /** 用户编号 */
    private String userId;
    /** 输入密码弹框 */
    private FundBuyDialog fundBuyDialog;
    /** 密码错误弹框 */
    private CustomDialog errorDialog;
    /** 加载框 */
    private HttpLoadingDialog httpLoadingDialog;


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_result_one;
    }

    @Override
    public ResultDetailOnePresent newP() {
        return new ResultDetailOnePresent();
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("交易详情");
        httpLoadingDialog = new HttpLoadingDialog(context);
        //获取用户缓存的userid 和 token
        userId = App.getSharedPref().getString(Constant.USERID, "");
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        if (bundle != null) {
            recordStatus = bundle.getString(Constant.INVEST_RECORD_STATUS);
            allot_no = bundle.getString(Constant.INVEST_PROTOCOL_ID);
        }

        getP().withdrawApplyDetail(allot_no, token, userId);

    }


    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        headRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (customDialog == null) {
                    customDialog = new CustomDialog.Builder(context)
                            .setMessage("撤单不可以恢复，确认要撤单吗？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    customDialog.dismiss();
                                }
                            }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    customDialog.dismiss();
                                    if (fundBuyDialog == null) {
                                        fundBuyDialog = new FundBuyDialog
                                                .Builder(context)
                                                .setOnTextFinishListener(new FundBuyDialog.OnTextFinishListener() {
                                                    @Override
                                                    public void onFinish(String str) {
                                                        fundBuyDialog.dismiss();
                                                        httpLoadingDialog.visible();
                                                        getP().withdrawApplyOperate(allot_no, str, token, userId);

                                                    }
                                                }).create();
                                    }

                                    fundBuyDialog.show();


                                }
                            }).create();
                }
                customDialog.show();
            }
        });
    }

    /**
     * 撤单成功
     */
//    private void revokeSuccess() {
//        //右侧撤单按钮
//        headRight.setVisibility(View.GONE);
//        //购买进度
//        llProgressInfo.setVisibility(View.GONE);
//        //确认信息
//        llSureInfo.setVisibility(View.GONE);
//        //失败信息
//        rlFail.setVisibility(View.VISIBLE);
//        //交易状态
//        tvTransactionStatus.setText("撤单成功");
//        //原因字体颜色
//        tvTransactionCause.setTextColor(getResources().getColor(R.color.color_696969));
//        //资金返回时间
//        tvTransactionCause.setText("资金将于X月x日x点前返回到银行卡");
//    }

    /**
     * 请求结果详情成功
     */
    public void requestDetailSuccess(ResultDetailResp resp) {
        httpLoadingDialog.dismiss();
        //TODO 头部交易信息
        tvFundName.setText(resp.getRecord().getFund_name());
        tvFundAmount.setText(BigDecimalUtil.bigdecimalToString(resp.getRecord().getFund_amount()) + "元");
        tvBankName.setText(resp.getRecord().getBankName() + " (" + resp.getRecord().getBankAcco() + ") " + "支付成功");

        //进度
        ArrayList<StepResp> stepList = resp.getStepList();

        //第一步
        if ("1".equals(stepList.get(0).getIscpl())) {
            //选中
            ivPaySuccess.setSelected(true);
        }
        fontPaySuccess.setText(stepList.get(0).getName());
        tvPaySuccess.setText(stepList.get(0).getTime());

        //第二步
        if ("1".equals(stepList.get(1).getIscpl())) {
            //选中
            ivSureNumber.setSelected(true);
        }
        fontSureNumber.setText(stepList.get(1).getName());
        tvSureNumber.setText(stepList.get(1).getTime());

        //第三步
        if ("1".equals(stepList.get(2).getIscpl())) {
            //选中
            ivQueryIncome.setSelected(true);
        }
        fontQueryIncome.setText(stepList.get(2).getName());
        tvQueryIncome.setText(stepList.get(2).getTime());


//        resp.getStatus()
        if ("定投成功".equals(recordStatus)) {          //交易成功，待确认份额
            //右侧撤单按钮
            headRight.setVisibility(View.VISIBLE);
            headRight.setText("撤单");
            //购买进度
            llProgressInfo.setVisibility(View.VISIBLE);
            //确认信息
            llSureInfo.setVisibility(View.GONE);
        } else if ("确认成功".equals(recordStatus)) {   //买入确认成功
            //右侧撤单按钮
            headRight.setVisibility(View.GONE);
            //购买进度
            llProgressInfo.setVisibility(View.VISIBLE);
            //确认信息
            llSureInfo.setVisibility(View.VISIBLE);
        } else if ("撤单成功".equals(recordStatus)) {   //撤单成功
//            revokeSuccess();
        } else if ("支付失败".equals(recordStatus)) {   //支付失败
            //右侧撤单按钮
            headRight.setVisibility(View.GONE);
            //购买进度
            llProgressInfo.setVisibility(View.GONE);
            //确认信息
            llSureInfo.setVisibility(View.GONE);
        } else if ("确认失败".equals(recordStatus)) {   //确认失败
            //右侧撤单按钮
            headRight.setVisibility(View.GONE);
            //购买进度
            llProgressInfo.setVisibility(View.GONE);
            //确认信息
            llSureInfo.setVisibility(View.GONE);
        }

    }

    /**
     * 请求结果详情失败
     */
    public void requestDetailFail() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 撤单成功
     */
    public void requestCancleSuccess() {
        httpLoadingDialog.dismiss();
        //撤单成功
        Bundle bundle = new Bundle();
        //标题
        bundle.putString(Constant.ACTIVITY_TITLE, "交易详情");
//        bundle.putString(Constant.INVEST_RECORD_STATUS, "撤单成功");
        //交易流水号
        bundle.putString(Constant.INVEST_PROTOCOL_ID, allot_no);
        startActivity(ResultDetailTwoActivity.class, bundle);
        finish();
    }

    /**
     * 撤单失败
     */
    public void requestCancleFail() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 撤单 密码错误
     */
    public void passwordError() {
        httpLoadingDialog.dismiss();
        if (errorDialog == null) {
            errorDialog = new CustomDialog.Builder(context)
                    .setMessage("交易密码错误，请重试")
                    .setNegativeButton("忘记密码", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            errorDialog.dismiss();
                            startActivity(FindPwdTradeFirstActivity.class);
                        }
                    }).setPositiveButton("再试一次", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            errorDialog.dismiss();
                            fundBuyDialog.show();
                        }
                    }).create();
        }
        errorDialog.show();
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
        if (errorDialog != null) {
            errorDialog.dismiss();
            errorDialog = null;
        }
        super.onDestroy();
    }
}
