package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/13 10:13
 * 描述：个人信息
 */

public class PersonInfoActivity extends XActivity {
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 设置按钮 */
    @BindView(R.id.head_right) Button headRight;
    /** 用户名 */
    @BindView(R.id.name) TextView name;
    /** 身份证号 */
    @BindView(R.id.identity) TextView identity;
    /** 账户编号 */
    @BindView(R.id.accountid) TextView accountid;
    /** 邮箱 */
    @BindView(R.id.email) EditText email;
    /** 编辑按钮 */
    @BindView(R.id.click_update) TextView clickUpdate;

    /** 用户编号 */
    String userId;
    /** 登录标识 */
    String token;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_personinfo;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        //获取用户缓存的userid 和 token
        userId = App.getSharedPref().getString(Constant.USERID, "");
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        //设置标题
        headTitle.setText("个人信息");
        headRight.setVisibility(View.VISIBLE);
        headRight.setText("编辑");
        if (!isNotEmpty(getText(email))) {
            clickUpdate.setVisibility(View.VISIBLE);
            clickUpdate.setText("去填写");
        }

    }

    @Override
    public void initEvents() {
        headRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("完成".equals(headRight.getText().toString())) {
                    //TODO 保存邮箱信息
                    showToast("保存该信息");
                    //然后设置为不可编辑状态
                    email.setFocusable(false);
                    email.setFocusableInTouchMode(false);
                    headRight.setText("编辑");
                    clickUpdate.setVisibility(View.GONE);
                } else if ("编辑".equals(headRight.getText().toString())) {
                    clickUpdate.setVisibility(View.VISIBLE);
                    clickUpdate.setText("修改");

                }

            }
        });

        clickUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headRight.setText("完成");
                email.setSelection(email.getText().toString().trim().length());//将光标移至文字末尾
                email.setFocusableInTouchMode(true);
                email.setFocusable(true);
                email.requestFocus();
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
