package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.MainActivity;
import com.zhsoft.fretting.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/14 15:06
 * 描述：
 */

public class RegisterSuccessActivity extends XActivity {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.head_right) Button headRight;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.identity) TextView identity;
    @BindView(R.id.btn_finish) Button btnFinish;
    @BindView(R.id.risk_test) Button riskTest;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_register_success;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("基金开户");
        //TODO 获取用户名和身份证号

    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.class);
            }
        });
        riskTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO 跳转风险测评
                showToast("跳转风险测评");
            }
        });
    }

}
