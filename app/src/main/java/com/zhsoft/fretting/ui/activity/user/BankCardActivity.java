package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhsoft.fretting.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/14 10:07
 * 描述：我的银行卡页面
 */

public class BankCardActivity extends XActivity {
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 银行图标 */
    @BindView(R.id.image_banck) ImageView imageBanck;
    /** 银行名称 */
    @BindView(R.id.banck_name) TextView banckName;
    /** 交易账号 */
    @BindView(R.id.trade_number) TextView tradeNumber;
    /** 变更银行卡 */
    @BindView(R.id.btn_change) Button btnChange;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_bankcard;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("我的银行卡");
    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(BankCardChangeActivity.class);
            }
        });

    }


}
