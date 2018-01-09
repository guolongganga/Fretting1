package com.zhsoft.fretting.ui.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.event.ChangeBankCardEvent;
import com.zhsoft.fretting.model.user.BankCardResp;
import com.zhsoft.fretting.present.user.BankCardPresent;
import com.zhsoft.fretting.utils.Base64ImageUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    private String isChange = "1";

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

        //注册eventbus
        EventBus.getDefault().register(this);

        //获取我的银行卡信息
        dialog.visible();
        getP().getBankCardInfo(token, userId);
    }

    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backDeal();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //验证是否符合更换条件
                dialog.visible();
                getP().changeBankCardCheck(token, userId);
            }
        });

    }

    /**
     * 获得用户银行卡信息
     *
     * @param resp
     */
    public void showBankCardData(BankCardResp resp) {
        dialog.dismiss();
        bankCardResp = resp;
        //获取 图片Base64 字符串
        String strimage = resp.getBankLogo();
        if (!TextUtils.isEmpty(strimage)) {
            //将Base64图片串转换成Bitmap
            Bitmap bitmap = Base64ImageUtil.base64ToBitmap(strimage);
            imageBanck.setImageBitmap(bitmap);
        }
        banckName.setText(resp.getBankName() + "（尾号" + resp.getBankNoTail() + "）");
        tradeNumber.setText(resp.getTa_acco());
    }


    /**
     * 获取用户银行卡信息失败
     */
    public void requestFail() {
        dialog.dismiss();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    /**
     * 修改银行卡事件
     *
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.POSTING)
    public void changeBankEvent(ChangeBankCardEvent event) {
        //重新获取我的银行卡信息
        dialog.visible();
        //修改了银行卡
        isChange = "0";
        getP().getBankCardInfo(token, userId);
        showToast("修改啦");
    }

    /**
     * 是否能修改银行卡
     *
     * @param data
     */
    public void isCanChange(BankCardResp data) {
        dialog.dismiss();
        if ("0".equals(data.getIsCanChangeBankNo())) {
            startActivity(BankCardChangeActivity.class);
        } else {
            showToast("您的账户在持仓或者在途交易期间，不能更换银行卡");
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backDeal();
    }

    /**
     * 返回键处理
     */
    private void backDeal() {
        Intent intent = new Intent();
        intent.putExtra(Constant.CHANGE_BANK, isChange);
        setResult(Constant.SUCCESS_BACK_BANK, intent);
        finish();
    }

}
