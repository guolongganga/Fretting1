package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.R;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/12 19:50
 * 描述：
 */

public class SettingActivity extends XActivity {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.head_right) Button headRight;
    @BindView(R.id.personinfo) TextView personinfo;
    @BindView(R.id.phone) TextView phone;
    @BindView(R.id.password_manager) TextView passwordManager;
    @BindView(R.id.bankcard) TextView bankcard;
    @BindView(R.id.icon_arrow) ImageView iconArrow;
    @BindView(R.id.risk_test) LinearLayout riskTest;
    @BindView(R.id.call_agent) TextView callAgent;
    @BindView(R.id.about_us) TextView aboutUs;
    @BindView(R.id.change_user) TextView changeUser;
    @BindView(R.id.exit) Button exit;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_setting;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        initview();
    }

    private void initview() {
        headBack.setVisibility(View.VISIBLE);
        headTitle.setText("设置");
    }

    @Override
    public void initEvents() {

        personinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PersonInfoActivity.class);
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("手机号码");
            }
        });
        passwordManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("密码管理");
            }
        });
        bankcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("银行卡号");
            }
        });
        riskTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("风险等级测评");
            }
        });
        callAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("客服电话");
            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("关于我们");
            }
        });
        changeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("切换账户");
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("安全退出");
            }
        });
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
}
