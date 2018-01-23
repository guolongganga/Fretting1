package com.zhsoft.fretting.ui.activity.user;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.ui.widget.CustomDialog;
import com.zhsoft.fretting.ui.widget.FundBuyDialog;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2018/1/23 11:21
 * 描述：交易详情
 */

public class TransactionDetailActivity extends XActivity {
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
    /** 支付成功 图标 */
    @BindView(R.id.iv_pay_success) ImageView ivPaySuccess;
    /** 支付成功 */
    @BindView(R.id.tv_pay_success) TextView tvPaySuccess;
    /** 基金公司确认份额 图标 */
    @BindView(R.id.iv_sure_number) ImageView ivSureNumber;
    /** 基金公司确认份额 */
    @BindView(R.id.tv_sure_number) TextView tvSureNumber;
    /** 查询收益 图标 */
    @BindView(R.id.iv_query_income) ImageView ivQueryIncome;
    /** 查询收益 */
    @BindView(R.id.tv_query_income) TextView tvQueryIncome;
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
    /** 支付失败，撤单成功，确认失败 */
    @BindView(R.id.rl_fail) RelativeLayout rlFail;
    /** 支付失败，撤单成功，确认失败 图标 */
    @BindView(R.id.iv_transaction) ImageView ivTransaction;
    /** 状态 */
    @BindView(R.id.tv_transaction_status) TextView tvTransactionStatus;
    /** 原因 */
    @BindView(R.id.tv_transaction_cause) TextView tvTransactionCause;
    /** 交易记录的状态 */
    private String recordStatus;
    /** 终止弹框 */
    private CustomDialog customDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_transaction_detail;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        if (bundle != null) {
            recordStatus = bundle.getString(Constant.INVEST_RECORD_STATUS);
        }
        headTitle.setText("交易详情");

        if ("定投成功".equals(recordStatus)) {          //交易成功，待确认份额
            //右侧撤单按钮
            headRight.setVisibility(View.VISIBLE);
            headRight.setText("撤单");
            //购买进度
            llProgressInfo.setVisibility(View.VISIBLE);
            //确认信息
            llSureInfo.setVisibility(View.GONE);
            //失败信息
            rlFail.setVisibility(View.GONE);
        } else if ("确认成功".equals(recordStatus)) {   //买入确认成功
            //右侧撤单按钮
            headRight.setVisibility(View.GONE);
            //购买进度
            llProgressInfo.setVisibility(View.VISIBLE);
            //确认信息
            llSureInfo.setVisibility(View.VISIBLE);
            //失败信息
            rlFail.setVisibility(View.GONE);
        } else if ("撤单成功".equals(recordStatus)) {   //撤单成功
            revokeSuccess();
        } else if ("支付失败".equals(recordStatus)) {   //支付失败
            //右侧撤单按钮
            headRight.setVisibility(View.GONE);
            //购买进度
            llProgressInfo.setVisibility(View.GONE);
            //确认信息
            llSureInfo.setVisibility(View.GONE);
            //失败信息
            rlFail.setVisibility(View.VISIBLE);
            //交易状态
            tvTransactionStatus.setText("支付失败");
            //原因字体颜色
            tvTransactionCause.setTextColor(getResources().getColor(R.color.color_696969));
            //原因
            tvTransactionCause.setText("请确保支付卡内金额充值或调整购买金额");
        } else if ("确认失败".equals(recordStatus)) {   //确认失败
            //右侧撤单按钮
            headRight.setVisibility(View.GONE);
            //购买进度
            llProgressInfo.setVisibility(View.GONE);
            //确认信息
            llSureInfo.setVisibility(View.GONE);
            //失败信息
            rlFail.setVisibility(View.VISIBLE);
            //交易状态
            tvTransactionStatus.setText("确认失败");
            //原因字体颜色
            tvTransactionCause.setTextColor(getResources().getColor(R.color.color_f7182d));
            //原因
            tvTransactionCause.setText("提示失败原因");
        }

    }

    /**
     * 撤单成功
     */
    private void revokeSuccess() {
        //右侧撤单按钮
        headRight.setVisibility(View.GONE);
        //购买进度
        llProgressInfo.setVisibility(View.GONE);
        //确认信息
        llSureInfo.setVisibility(View.GONE);
        //失败信息
        rlFail.setVisibility(View.VISIBLE);
        //交易状态
        tvTransactionStatus.setText("撤单成功");
        //原因字体颜色
        tvTransactionCause.setTextColor(getResources().getColor(R.color.color_696969));
        //资金返回时间
        tvTransactionCause.setText("资金将于X月x日x点前返回到银行卡");
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
                                    revokeSuccess();
                                }
                            }).create();
                }
                customDialog.show();
            }
        });
    }

}
