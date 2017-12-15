package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhsoft.fretting.R;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/13 11:53
 * 描述：我的手机号页面
 */

public class PhoneActivity extends XActivity {
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 图片 */
    @BindView(R.id.img_sign) ImageView imgSign;
    /** 电话号码 */
    @BindView(R.id.phone_number) TextView phoneNumber;
    /** 更改电话号码 */
    @BindView(R.id.btn_change) Button btnChange;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_phone;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("变更手机号");

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
                startActivity(PhoneChangeActivity.class);
            }
        });
    }


}
