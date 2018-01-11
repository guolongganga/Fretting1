package com.zhsoft.fretting.ui.activity.fund;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.event.ChangeTabEvent;
import com.zhsoft.fretting.ui.activity.user.BankCardActivity;
import com.zhsoft.fretting.ui.activity.user.BankCardChangeActivity;
import com.zhsoft.fretting.ui.activity.user.FindPwdTradeFirstActivity;
import com.zhsoft.fretting.ui.widget.CustomDialog;
import com.zhsoft.fretting.ui.widget.FundBuyDialog;
import com.zhsoft.fretting.utils.Base64ImageUtil;

import org.greenrobot.eventbus.EventBus;

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
        //获取到基金数据
//        bankCardResp = resp;
        //获取 图片Base64 字符串
//        String strimage = resp.getBankLogo();
//        if (!TextUtils.isEmpty(strimage)) {
//            //将Base64图片串转换成Bitmap
//            Bitmap bitmap = Base64ImageUtil.base64ToBitmap(strimage);
//            bankImage.setImageBitmap(bitmap);
//        }
//        bankName.setText(resp.getBankName() + "（" + resp.getBankNoTail() + "）");
//        bankLimit.setText("单笔限额" + resp.getLimit_per_payment() + "万，单日限额" + resp.getLimit_per_day() + "万，单月限额" + resp.getLimit_per_month() + "万");

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
                //TODO 如果amount小于最小购买金额，重新填写购买金额
                if (amount < 100) {
                    showToast("最小投资金额为100元");
                    return;
                }
                //TODO 弹出框
                fundBuyDialog = new FundBuyDialog
                        .Builder(context)
                        .setFundName("国泰哈哈哈基金")
                        .setFundAmount("￥" + getText(etAmount) + ".00")
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
        tvChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(BankCardActivity.class);
            }
        });
    }

}
