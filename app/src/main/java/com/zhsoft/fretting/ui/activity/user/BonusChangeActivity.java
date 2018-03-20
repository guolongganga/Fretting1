package com.zhsoft.fretting.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.event.RefreshBonusEvent;
import com.zhsoft.fretting.ui.widget.ChenJingET;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2018/1/23 17:32
 * 描述：分红方式
 */

public class BonusChangeActivity extends XActivity {
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
    @BindView(R.id.tv_fund_name) TextView tvFundName;
    @BindView(R.id.tv_fund_code) TextView tvFundCode;
    @BindView(R.id.rb_again) RadioButton rbAgain;
    @BindView(R.id.rb_carsh) RadioButton rbCarsh;
    @BindView(R.id.password) EditText password;
    @BindView(R.id.btn_save) Button btnSave;
    private String chooseStyle;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_bonus_change;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        //解决键盘弹出遮挡不滚动问题
        ChenJingET.assistActivity(context);
        headTitle.setText("分红方式变更");
        //默认选中第一个
        rbAgain.setChecked(true);

    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(getText(password))) {
                    showToast("交易密码不能为空");
                    return;
                }
                if (rbAgain.isChecked()) {
                    chooseStyle = "红利再投资";
                } else {
                    chooseStyle = "现金分红";
                }
                showToast("保存，分红方式为：" + chooseStyle);
                //跳转到修改成功界面，通知分红方式改变
                EventBus.getDefault().post(new RefreshBonusEvent(chooseStyle));
                startActivity(BonusChangeSuccessActivity.class);
                finish();
            }
        });
    }

}
