package com.zhsoft.fretting.ui.activity.fund;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.event.ChangeTabEvent;
import com.zhsoft.fretting.ui.activity.MainActivity;
import com.zhsoft.fretting.ui.widget.ChenJingET;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2018/1/9 10:57
 * 描述：定投页面
 */

public class InvestSuccessActivity extends XActivity {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.tv_fund_name) TextView tvFundName;
    @BindView(R.id.tv_fund_code) TextView tvFundCode;
    @BindView(R.id.tv_week) TextView tvWeek;
    @BindView(R.id.tv_day) TextView tvDay;
    @BindView(R.id.tv_fund_amount) TextView tvFundAmount;
    @BindView(R.id.tv_bank_name) TextView tvBankName;
    @BindView(R.id.tv_last_number) TextView tvLastNumber;
    @BindView(R.id.tv_day_week) TextView tvDayWeek;
    @BindView(R.id.sure) Button sure;

    @Override
    public int getLayoutId() {
        return R.layout.activity_fund_invest_success;
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
        headTitle.setText("定投详情");
    }

    @Override
    public void initEvents() {

        sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EventBus.getDefault().post(new ChangeTabEvent(Constant.MAIN_MY));
                startActivity(MainActivity.class);
                finish();
            }
        });
    }


}
