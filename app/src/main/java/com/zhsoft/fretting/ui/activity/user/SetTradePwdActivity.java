package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.ui.widget.PayPwdEditText;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/12 18:44
 * 描述：
 */

public class SetTradePwdActivity extends XActivity {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.ppe_pwd) PayPwdEditText ppePwd;
    @BindView(R.id.ppe_pwd_again) PayPwdEditText ppePwdAgain;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_set_tradepwd;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        headBack.setVisibility(View.VISIBLE);
        headTitle.setText("基金开户");
        ppePwd.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.color999999, R.color.color999999, 20);
        ppePwdAgain.initStyle(R.drawable.edit_num_bg, 6, 0.33f, R.color.color999999, R.color.color999999, 20);

    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ppePwd.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                Toast.makeText(SetTradePwdActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });
        ppePwdAgain.setOnTextFinishListener(new PayPwdEditText.OnTextFinishListener() {
            @Override
            public void onFinish(String str) {//密码输入完后的回调
                Toast.makeText(SetTradePwdActivity.this, str, Toast.LENGTH_SHORT).show();
            }
        });


    }

}
