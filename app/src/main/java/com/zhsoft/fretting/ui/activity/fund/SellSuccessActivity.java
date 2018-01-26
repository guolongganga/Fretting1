package com.zhsoft.fretting.ui.activity.fund;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.event.ChangeTabEvent;
import com.zhsoft.fretting.model.fund.BuyNowResp;
import com.zhsoft.fretting.ui.activity.MainActivity;
import com.zhsoft.fretting.ui.widget.ChenJingET;
import com.zhsoft.fretting.utils.BigDecimalUtil;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2018/1/8 17:59
 * 描述：购买成功页
 */

public class SellSuccessActivity extends XActivity {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.head_right) Button headRight;
    @BindView(R.id.tv_fund_name) TextView tvFundName;
    @BindView(R.id.tv_fund_amount) TextView tvFundAmount;
    @BindView(R.id.tv_bank_name) TextView tvBankName;
    @BindView(R.id.iv_pay_success) ImageView ivPaySuccess;
    @BindView(R.id.tv_pay_success) TextView tvPaySuccess;
    @BindView(R.id.iv_sure_number) ImageView ivSureNumber;
    @BindView(R.id.tv_sure_number) TextView tvSureNumber;
    @BindView(R.id.iv_query_income) ImageView ivQueryIncome;
    @BindView(R.id.tv_query_income) TextView tvQueryIncome;
    @BindView(R.id.scroll_view) ScrollView scrollView;
    /** 购买成功传递过来的数据 */
    private BuyNowResp buyNowResp;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fund_sell_success;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        //解决键盘弹出遮挡不滚动问题
        ChenJingET.assistActivity(context);
        headBack.setVisibility(View.GONE);
        headTitle.setText("卖出详情");
        headRight.setText("完成");
        headRight.setVisibility(View.VISIBLE);
//        if (bundle != null) {
//            buyNowResp = (BuyNowResp) bundle.getParcelable(Constant.BUY_SUCCESS_OBJECT);
//            if (buyNowResp != null) {
//                tvFundName.setText(buyNowResp.getFund_name());
//                tvFundAmount.setText(BigDecimalUtil.bigdecimalToString(buyNowResp.getFund_amount()) + "元");
//                tvBankName.setText(buyNowResp.getBank_info());
//                tvPaySuccess.setText(buyNowResp.getSucc_time());
//                tvSureNumber.setText(buyNowResp.getConfirm_time());
//                tvQueryIncome.setText(buyNowResp.getIncome_time());
//            }
//        }
    }

    @Override
    public void initEvents() {
        headRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backDeal();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backDeal();
    }

    private void backDeal() {
        EventBus.getDefault().post(new ChangeTabEvent(Constant.MAIN_MY));
        startActivity(MainActivity.class);
        finish();
    }

}
