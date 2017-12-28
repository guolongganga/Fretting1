package com.zhsoft.fretting.ui.activity.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.PhoneResp;
import com.zhsoft.fretting.present.user.PhonePresent;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/13 11:53
 * 描述：我的手机号页面
 */

public class PhoneActivity extends XActivity<PhonePresent> {
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

    private HttpLoadingDialog httpLoadingDialog;

    private String token;
    private String userId;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_phone;
    }

    @Override
    public PhonePresent newP() {
        return new PhonePresent();
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("变更手机号");
        httpLoadingDialog = new HttpLoadingDialog(context);
        //获得本地缓存
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        userId = App.getSharedPref().getString(Constant.USERID, "");

        //访问用户手机号信息
        httpLoadingDialog.visible();
        getP().getPhoneInfo(token, userId);
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
                startActivity(PhoneChangeActivity.class, Constant.CHANGE_PHONE_ACTIVITY);
            }
        });
    }

    /**
     * 用户手机号码信息访问成功
     */
    public void requestSuccess(PhoneResp resp) {
        httpLoadingDialog.dismiss();
        if ("yidong".equals(resp.getCarrieroperator())) {
            imgSign.setImageResource(R.mipmap.yidong_logo);
        } else if ("liantong".equals(resp.getCarrieroperator())) {
            imgSign.setImageResource(R.mipmap.lianton_logo);
        } else if ("dianxin".equals(resp.getCarrieroperator())) {
            imgSign.setImageResource(R.mipmap.dianxin_logo);
        }
        phoneNumber.setText("现注册手机号为" + resp.getPhone());
    }

    /***
     * 用户手机号码信息访问失败
     */
    public void requestFail() {
        httpLoadingDialog.dismiss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //修改手机号码成功之后返回到此页面并刷新数据
        if (requestCode == Constant.CHANGE_PHONE_ACTIVITY && resultCode == Constant.SUCCESS_BACK_PHONE) {
            String phone = data.getStringExtra(Constant.CHANGE_PHONE);
            phoneNumber.setText("现注册手机号为" + phone);
            httpLoadingDialog.visible();
            getP().getPhoneInfo(token, userId);
        }
    }
}
