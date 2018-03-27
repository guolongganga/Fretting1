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
import com.zhsoft.fretting.model.fund.FundStatusResp;
import com.zhsoft.fretting.model.user.StepResp;
import com.zhsoft.fretting.ui.activity.MainActivity;
import com.zhsoft.fretting.ui.widget.ChenJingET;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    @BindView(R.id.font_pay_success) TextView fontPaySuccess;
    @BindView(R.id.font_sure_number) TextView fontSureNumber;
    @BindView(R.id.font_query_income) TextView fontQueryIncome;
    @BindView(R.id.line_one) View lineOne;
    @BindView(R.id.line_two) View lineTwo;
    /** 购买成功传递过来的数据 */
    private FundStatusResp statusResp;

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
        headBack.setVisibility(View.GONE);
        headTitle.setText("卖出详情");
        headRight.setText("完成");
        headRight.setVisibility(View.VISIBLE);
        if (bundle != null) {
            statusResp = (FundStatusResp) bundle.getParcelable(Constant.ACTIVITY_OBJECT);
            if (statusResp != null) {
                tvFundName.setText(statusResp.getFundName());
                tvFundAmount.setText(statusResp.getShares());
                tvBankName.setText("回款到  " + statusResp.getBankCardPageEntity().getBankName() + "  （" + statusResp.getBankCardPageEntity().getBankNoTail() + "）");

                //进度
                ArrayList<StepResp> stepList = statusResp.getStepList();

                //第一步
                if ("1".equals(stepList.get(0).getIscpl())) {
                    //选中
                    ivPaySuccess.setImageResource(R.drawable.icon_progress_choose);
                } else {
                    //未选中
                    ivPaySuccess.setImageResource(R.drawable.icon_progress_unchoose);
                }
                fontPaySuccess.setText(stepList.get(0).getName());
                tvPaySuccess.setText(stepList.get(0).getTime());

                //第二步
                if ("1".equals(stepList.get(1).getIscpl())) {
                    //选中
                    ivSureNumber.setImageResource(R.drawable.icon_progress_choose);
                    lineOne.setBackgroundColor(getResources().getColor(R.color.color_DC6F5A));
                }else{
                    ivSureNumber.setImageResource(R.drawable.icon_progress_unchoose);
                    lineOne.setBackgroundColor(getResources().getColor(R.color.color_D8D8D8));
                }
                fontSureNumber.setText(stepList.get(1).getName());
                tvSureNumber.setText(stepList.get(1).getTime());

                //第三步
                if ("1".equals(stepList.get(2).getIscpl())) {
                    //选中
                    lineTwo.setBackgroundColor(getResources().getColor(R.color.color_DC6F5A));
                    ivQueryIncome.setImageResource(R.drawable.icon_progress_choose);
                }else{
                    lineTwo.setBackgroundColor(getResources().getColor(R.color.color_D8D8D8));
                    ivQueryIncome.setImageResource(R.drawable.icon_progress_unchoose);
                }
                fontQueryIncome.setText(stepList.get(2).getName());
                tvQueryIncome.setText(stepList.get(2).getTime());
            }
        }
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
