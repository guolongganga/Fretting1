package com.zhsoft.fretting.ui.activity.user;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.EventLog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.user.ImageResp;
import com.zhsoft.fretting.present.user.PhoneChangePresent;
import com.zhsoft.fretting.ui.widget.CountdownButton;
import com.zhsoft.fretting.utils.Base64ImageUtil;
import com.zhsoft.fretting.widget.ChenJingET;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/13 13:05
 * 描述：变更手机号 页面
 */

public class PhoneChangeActivity extends XActivity<PhoneChangePresent> {
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 手机号码 */
    @BindView(R.id.phone_number) EditText phoneNumber;
    /** 验证码 */
    @BindView(R.id.verify_code) EditText verifyCode;
    /** 获取验证码 */
    @BindView(R.id.get_verify_code) CountdownButton getVerifyCode;
    /** 保存按钮 */
    @BindView(R.id.btn_save) Button btnSave;

    /** 加载框 */
    private HttpLoadingDialog httpLoadingDialog;
    /** 图片验证码id */
    private String image_code_id;
    /** 登录标识 */
    private String token;
    /** 用户编号 */
    private String userId;

    //验证码pop
    PopupWindow mPopWindow;
    //关闭pop
    ImageView ivClose;
    //图片验证码
    ImageView ivCode;
    //刷新
    TextView tvRefresh;
    //输入验证码
    EditText etCode;

    @Override
    public int getLayoutId() {
        return R.layout.activity_user_phone_change;
    }

    @Override
    public PhoneChangePresent newP() {
        return new PhoneChangePresent();
    }

    @Override
    public void initData(Bundle bundle) {
        //解决键盘弹出遮挡不滚动问题
        ChenJingET.assistActivity(context);
        httpLoadingDialog = new HttpLoadingDialog(context);
        //设置标题
        headTitle.setText("变更手机号");
        //获取本地缓存的用户编号和登录标识
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        userId = App.getSharedPref().getString(Constant.USERID, "");

    }


    @Override
    public void initEvents() {
//        final String phone = ;
//        final String code = ;

        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(getText(phoneNumber))) {
                    showToast("手机号码不能为空");
                    return;
                }
                if (getText(phoneNumber).length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
//                //获取图片验证码
//                showImageCode(getText(phoneNumber));
                //获取短信验证码 不需要图片
                getP().getMessageCodeNoImage(getText(phoneNumber),token,userId);

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
                if (!isNotEmpty(getText(phoneNumber))) {
                    showToast("手机号码不能为空");
                    return;
                }
                if (getText(phoneNumber).length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (!isNotEmpty(getText(verifyCode))) {
                    showToast("验证码不能为空");
                    return;
                }
                getP().changePhone(getText(phoneNumber), getText(verifyCode), token, userId);

            }
        });

    }

    /**
     * 获取图片验证码
     */
    public void showImageCode(final String phone) {
        //设置contentView
        View contentView = LayoutInflater.from(context).inflate(R.layout.pop_show_image_code, null);
        mPopWindow = new PopupWindow(contentView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);
        mPopWindow.setContentView(contentView);
        mPopWindow.setFocusable(true);
        //外部是否可以点击
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
        mPopWindow.setOutsideTouchable(true);

        //解决popupwindow中弹出输入法被遮挡问题
        mPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        mPopWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);

        FrameLayout flContent = contentView.findViewById(R.id.fl_content);
        flContent.getBackground().setAlpha(150);

        ivClose = contentView.findViewById(R.id.iv_close);
        ivCode = contentView.findViewById(R.id.iv_code);
        tvRefresh = contentView.findViewById(R.id.tv_refresh);
        etCode = contentView.findViewById(R.id.et_code);

        //网络请求去获取图片
        getP().getImageCode();

        //关闭pop
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPopWindow.dismiss();
            }
        });

        //刷新 - 重新请求验证码
        tvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getP().getImageCode();
            }
        });

        //监听EditText的输入长度
        etCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String etImageCode = getText(etCode);
                int len = etImageCode.length();
                if (len == 4) {
                    //获取验证码
                    getP().getMessageCode(phone, etImageCode, image_code_id);
                }
            }
        });
        //显示PopupWindow
        mPopWindow.showAtLocation(getVerifyCode, Gravity.CENTER, 0, 0);
    }

    /**
     * 获取图片验证码成功
     *
     * @param data
     */
    public void getImageCodeSuccess(ImageResp data) {
        httpLoadingDialog.dismiss();
        //获取 图片Base64 字符串
        String strimage = data.getBase64Image();
        image_code_id = data.getImageCodeId();
        if (!TextUtils.isEmpty(strimage)) {
            //将Base64图片串转换成Bitmap
            Bitmap bitmap = Base64ImageUtil.base64ToBitmap(strimage);
            ivCode.setImageBitmap(bitmap);
        }
    }

    /**
     * 获取图片验证码失败
     */
    public void getImageCodeFail() {
        showToast("请求图片验证码失败...");
    }

    /**
     * 请求短信验证码成功
     */
    public void requestPhoneCodeSuccess(String data) {
//        messageCode = data.getMessageCode();
        //关闭pop，
        mPopWindow.dismiss();
        //开始倒计时
        getVerifyCode.start();
    }

    /**
     * 请求短信验证码失败
     */
    public void requestPhoneCodeFail() {
        //获取失败的原因
        getVerifyCode.cancel();
        showToast("后台那家伙说你输错验证码了...老铁");
    }

    /**
     * 修改手机号成功
     */
    public void disposeChangeResult() {
        httpLoadingDialog.dismiss();
        //更新本地缓存的手机号码
        App.getSharedPref().putString(Constant.USER_PHONE, getText(phoneNumber));
        //跳转回我的手机号码页面
        Intent intent = new Intent();
        intent.putExtra(Constant.CHANGE_PHONE, getText(phoneNumber));
        setResult(Constant.SUCCESS_BACK_PHONE, intent);
        finish();

    }

    /**
     * 修改手机号失败
     */
    public void requestFail() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 请求短信验证码成功 无图片
     */
    public void requestPhoneCodeNoImageSuccess(String data) {
        //开始倒计时
        getVerifyCode.start();
    }

    /**
     * 请求短信验证码失败 无图片
     */
    public void requestPhoneCodeNoImageFail() {
        //获取失败的原因
        getVerifyCode.cancel();
    }

}
