package com.zhsoft.fretting.ui.activity.user;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.LoginResp;
import com.zhsoft.fretting.model.user.ImageResp;
import com.zhsoft.fretting.present.user.RegisterFirstPresent;
import com.zhsoft.fretting.ui.widget.CountdownButton;
import com.zhsoft.fretting.utils.Base64ImageUtil;
import com.zhsoft.fretting.utils.RuntimeHelper;
import com.zhsoft.fretting.widget.ChenJingET;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/11 17:35
 * 描述：基金开户 第一步界面
 */

public class RegisterFirstActivity extends XActivity<RegisterFirstPresent> {
    private static final String PHONE = "phone";
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 获取验证码 */
    @BindView(R.id.get_verify_code) CountdownButton getVerifyCode;
    /** 手机号码 */
    @BindView(R.id.phone_number) EditText phoneNumber;
    /** 密码 */
    @BindView(R.id.password) EditText password;
    /** 确认密码 */
    @BindView(R.id.password_again) EditText passwordAgain;
    /** 短信验证码 */
    @BindView(R.id.msg_code) EditText msgCode;
    /** 选择已有账户 */
    @BindView(R.id.to_login) TextView toLogin;
    /** 接收不到验证码 */
    @BindView(R.id.message_fail) TextView messageFail;
    /** 下一步 */
    @BindView(R.id.btn_next) Button btnNext;
    /** 图片验证码 */
    @BindView(R.id.image_code) EditText imageCode;
    /** 图片验证码显示 */
    @BindView(R.id.image) ImageView image;
    /** 加载圈 */
    private HttpLoadingDialog httpLoadingDialog;
    /** 图片加载 */
//    private ImageLoader imageLoader = ImageLoader.getInstance();
    /** 图片验证码id */
    private String image_code_id;


    @Override
    public int getLayoutId() {
        return R.layout.activity_user_register_first;
    }

    @Override
    public RegisterFirstPresent newP() {
        return new RegisterFirstPresent();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //解决键盘弹出遮挡不滚动问题
        ChenJingET.assistActivity(context);
        httpLoadingDialog = new HttpLoadingDialog(context);
        //设置标题
        headTitle.setText("基金开户");

        //访问图片验证码请求接口
        httpLoadingDialog.visible();
        getP().getImageCode();
//        imageLoader.displayImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513685250527&di=94488903fc6fca64b390715ecf2d6244&imgtype=0&src=http%3A%2F%2Freso2.yiihuu.com%2F1331436-z.jpg", image);

    }

    @Override
    public void initEvents() {

        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(LoginActivity.class);
            }
        });

        messageFail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("收不到短信验证码");
            }
        });

        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                httpLoadingDialog.visible();
                getP().getImageCode();
            }
        });

        getVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phoneNumber.getText().toString();
                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(phone)) {
                    showToast("手机号码不能为空");
                    return;
                }
                if (phone.length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (!isNotEmpty(getText(imageCode))) {
                    showToast("图片验证码不能为空");
                    return;
                }

                getVerifyCode.start();

            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = getText(phoneNumber);
                String pwd = getText(password);
                String pwdagain = getText(passwordAgain);
                String imgcode = getText(imageCode);
                String code = getText(msgCode);
                //表单验证
                if (noNetWork()) {
                    showNetWorkError();
                    return;
                }
                if (!isNotEmpty(phone)) {
                    showToast("手机号码不能为空");
                    return;
                }
                if (phone.length() < 11) {
                    showToast("请输入正确的手机号码");
                    return;
                }
                if (!isNotEmpty(pwd)) {
                    showToast("登录密码不能为空");
                }
                if (!isNotEmpty(pwd)) {
                    showToast("登录密码不能为空");
                }
                if (pwd.length() < 8) {
                    showToast("登录密码为8-16位数字、字母、特殊字符等");
                    return;
                }
                if (!isNotEmpty(pwdagain)) {
                    showToast("登录密码不能为空");
                }
                if (pwdagain.length() < 8) {
                    showToast("登录密码为8-16位数字、字母、特殊字符等");
                    return;
                }
                if (!isNotEmpty(imgcode)) {
                    showToast("图片验证码不能为空");
                    return;
                }
                if (!isNotEmpty(code)) {
                    //注册第一步
                    showToast("验证码不能为空");
                    return;
                }
                //TODO 下一步
                httpLoadingDialog.visible("加载中...");
                getP().register(phone, pwd, imgcode, image_code_id);

//                startActivity(RegisterSecondActivity.class);//有接口就去掉

            }
        });
    }

    public void requestFail() {
        httpLoadingDialog.dismiss();
    }

    /**
     * 注册成功
     *
     * @param model
     */
    public void commitSuccess(LoginResp model) {
        httpLoadingDialog.dismiss();
        showToast(model.getToken());

        //缓存用户userId,token,username,is_open_account
        App.getSharedPref().putString(Constant.USERID, model.getUserId());
        App.getSharedPref().putString(Constant.TOKEN, model.getToken());
        App.getSharedPref().putString(Constant.USER_NAME, getText(phoneNumber));
        App.getSharedPref().putString(Constant.IS_OPEN_ACCOUNT, model.getIsOpenAccount());

        //全局变量设置为登录状态
        RuntimeHelper.getInstance().setLogin(true);

        Bundle bundle = new Bundle();
        bundle.putString(PHONE, getText(phoneNumber));
        startActivity(RegisterSecondActivity.class, bundle);

        finish();
    }

    /**
     * 获取图片验证码
     *
     * @param data
     */
    public void getImageCode(ImageResp data) {
        httpLoadingDialog.dismiss();
        //获取 图片Base64 字符串
        String strimage = data.getBase64Image();
        image_code_id = data.getImageCodeId();
        if (!TextUtils.isEmpty(strimage)) {
            //将Base64图片串转换成Bitmap
            Bitmap bitmap = Base64ImageUtil.base64ToBitmap(strimage);
            image.setImageBitmap(bitmap);
        }
    }

    public void requestMessageFail() {
        httpLoadingDialog.dismiss();
    }
}
