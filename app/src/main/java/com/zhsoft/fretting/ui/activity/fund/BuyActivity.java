package com.zhsoft.fretting.ui.activity.fund;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.ui.activity.user.FindPwdTradeFirstActivity;
import com.zhsoft.fretting.ui.widget.CustomDialog;
import com.zhsoft.fretting.ui.widget.FundBuyDialog;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2018/1/8 11:55
 * 描述：基金详情页-购买页面
 */

public class BuyActivity extends XActivity {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.bank_image) ImageView bankImage;
    @BindView(R.id.bank_name) TextView bankName;
    @BindView(R.id.bank_limit) TextView bankLimit;
    @BindView(R.id.tv_change) TextView tvChange;
    @BindView(R.id.et_amount) EditText etAmount;
    @BindView(R.id.sure) Button sure;
    private FundBuyDialog fundBuyDialog;
    private CustomDialog customDialog;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fund_buy;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText(R.string.fund_buy);
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
                //TODO 弹出框
                fundBuyDialog = new FundBuyDialog
                        .Builder(context)
                        .setFundName("国泰哈哈哈基金")
                        .setFundAmount("￥" + etAmount.getText().toString())
                        .setOnTextFinishListener(new FundBuyDialog.OnTextFinishListener() {
                            @Override
                            public void onFinish(String str) {
                                //TODO 判断密码是否正确
                                fundBuyDialog.dismiss();

                                if ("123456".equals(str)) {
//                                    showToast("密码正确");
                                    startActivity(BuySuccessActivity.class);
                                } else {
                                    customDialog = new CustomDialog.Builder(context)
                                            .setMessage("交易密码错误，请重试")
                                            .setNegativeButton("忘记密码", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialogInterface, int i) {
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
    }

}
