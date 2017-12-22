package com.zhsoft.fretting.ui.activity.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.BankCardResp;
import com.zhsoft.fretting.present.user.BankCardPresent;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/14 10:07
 * 描述：我的银行卡页面
 */

public class BankCardActivity extends XActivity<BankCardPresent> {
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

    /** 加载图片 */
    private ImageLoader imageLoader = ImageLoader.getInstance();
    private HttpLoadingDialog dialog;
    private BankCardResp bankCardResp;
    /** 用户编号 */
    private String userId;
    /** 登录标识 */
    private String token;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_bankcard;
    }

    @Override
    public BankCardPresent newP() {
        return new BankCardPresent();
    }

    @Override
    public void initData(Bundle bundle) {
        headTitle.setText("我的银行卡");
        dialog = new HttpLoadingDialog(context);
        //获取用户缓存的userid 和 token
        userId = App.getSharedPref().getString(Constant.USERID, "");
        token = App.getSharedPref().getString(Constant.TOKEN, "");

        //获取我的银行卡信息
        dialog.visible();
        getP().getBankCardInfo(token,userId);
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
                //先验证是否符合更换条件
                if ("1".equals(bankCardResp.getIsCanChange())) {
                    startActivity(BankCardChangeActivity.class);
                } else {
                    showToast("您的账户在持仓或者在途交易期间，不能更换银行卡");
                }

            }
        });

    }

    /**
     * 获得用户银行卡信息
     *
     * @param resp
     */
    public void showData(BankCardResp resp) {
        dialog.dismiss();
        bankCardResp = resp;
        imageLoader.displayImage(resp.getLogoUrl(), imageBanck);
        banckName.setText(resp.getBankName() + "（尾号" + resp.getLastNumber() + "）");
        tradeNumber.setText(resp.getDealAcccount());
    }

    /**
     * 获取用户银行卡信息失败
     */
    public void requestFail() {
        dialog.dismiss();
    }
}
