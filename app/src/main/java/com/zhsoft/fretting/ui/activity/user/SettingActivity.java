package com.zhsoft.fretting.ui.activity.user;

import android.content.Intent;
import android.net.Uri;
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

    private static final String PHONE_NUMBER = "13717832879";

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
                startActivity(PhoneActivity.class);
            }
        });
        passwordManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ChangePwdActivity.class);
            }
        });
        bankcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(BankCardActivity.class);
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
                //获取文本框中的电话号码值
//            String number = phoneNumber.getText().toString();

                //掉用拨号权限 新建一个意图
                Intent intent = new Intent();
                //在把意图添加给操作系统时，操作系统会自动为intent添加类别，所以可省略
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + PHONE_NUMBER));
                //将意图添加给操作系统执行
                startActivity(intent);

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
