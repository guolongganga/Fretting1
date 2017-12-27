package com.zhsoft.fretting.ui.activity.user;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.inputmethodservice.Keyboard;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.LoginResp;
import com.zhsoft.fretting.model.user.ImageResp;
import com.zhsoft.fretting.model.user.PhoneCodeResp;
import com.zhsoft.fretting.present.user.RegisterFirstPresent;
import com.zhsoft.fretting.ui.activity.MainActivity;
import com.zhsoft.fretting.ui.widget.CountdownButton;
import com.zhsoft.fretting.ui.widget.CustomDialog;
import com.zhsoft.fretting.ui.widget.PopShow;
import com.zhsoft.fretting.utils.Base64ImageUtil;
import com.zhsoft.fretting.utils.RuntimeHelper;
import com.zhsoft.fretting.widget.ChenJingET;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.dialog.httploadingdialog.HttpLoadingDialog;
import cn.droidlover.xdroidmvp.imageloader.ILFactory;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/11 17:35
 * 描述：基金开户 第一步界面
 */

public class RegisterFirstActivity extends XActivity<RegisterFirstPresent> {
//    private static final String PHONE = "phone";
    /**
     * 返回按钮
     */
    @BindView(R.id.head_back)
    ImageButton headBack;
    /**
     * 标题
     */
    @BindView(R.id.head_title)
    TextView headTitle;
    /**
     * 获取验证码
     */
    @BindView(R.id.get_verify_code)
    CountdownButton getVerifyCode;
    /**
     * 手机号码
     */
    @BindView(R.id.phone_number)
    EditText phoneNumber;
    /**
     * 密码
     */
    @BindView(R.id.password)
    EditText password;
    /**
     * 确认密码
     */
    @BindView(R.id.password_again)
    EditText passwordAgain;
    /**
     * 短信验证码
     */
    @BindView(R.id.msg_code)
    EditText msgCode;
    /**
     * 选择已有账户
     */
    @BindView(R.id.to_login)
    TextView toLogin;
    /**
     * 接收不到验证码
     */
    @BindView(R.id.message_fail)
    TextView messageFail;
    /**
     * 下一步
     */

    @BindView(R.id.btn_next)
    Button btnNext;
    /** 图片验证码 */
//    @BindView(R.id.image_code) EditText imageCode;
    /** 图片验证码显示 */
//    @BindView(R.id.image) ImageView image;
    /**
     * 加载圈
     */
    private HttpLoadingDialog httpLoadingDialog;
    /** 图片加载 */
    private ImageLoader imageLoader = ImageLoader.getInstance();
    /**
     * 图片验证码id
     */
    private String image_code_id;
    /** 短信验证码 */
    private String messageCode;

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

    private CustomDialog customDialog;


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
//        httpLoadingDialog.visible();
//        getP().getImageCode();
//        imageLoader.displayImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1513685250527&di=94488903fc6fca64b390715ecf2d6244&imgtype=0&src=http%3A%2F%2Freso2.yiihuu.com%2F1331436-z.jpg", image);

    }

    @Override
    public void initEvents() {

        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(MainActivity.class);
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

                if (customDialog == null) {
                    customDialog = new CustomDialog
                            .Builder(context)
                            .setTitle("无法收到短信验证码")
                            .setMessage(R.string.user_register_first_no_message_code_hint)
                            .setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    customDialog.dismiss();
                                }
                            }).create();
                }
                customDialog.show();
            }
        });

//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //图片验证码接口
//                httpLoadingDialog.visible();
//                getP().getImageCode();
//            }
//        });

        getVerifyCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = phoneNumber.getText().toString();
                String pwd = getText(password);
                String pwdagain = getText(passwordAgain);

                //表单验证通过才弹出pop
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
                    return;
                }
                if (pwd.length() < 8) {
                    showToast("登录密码为8-16位数字、字母、特殊字符等");
                    return;
                }
                if (!isNotEmpty(pwdagain)) {
                    showToast("确认登录密码不能为空");
                    return;
                }
                if (!pwd.equals(pwdagain)) {
                    showToast("两次密码不一致");
                    return;
                }
//                if (!isNotEmpty(imgcode)) {
//                    showToast("图片验证码不能为空");
//                    return;
//                }

                //获取图片验证码
                //getP().getMessageCode(phone, imgcode, image_code_id);
                showImageCode(phone);
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = getText(phoneNumber);
                String pwd = getText(password);
                String pwdagain = getText(passwordAgain);

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
                    return;
                }
                if (pwd.length() < 8) {
                    showToast("登录密码为8-16位数字、字母、特殊字符等");
                    return;
                }
                if (!isNotEmpty(pwdagain)) {
                    showToast("确认登录密码不能为空");
                    return;
                }
                if (!pwd.equals(pwdagain)) {
                    showToast("两次密码不一致");
                    return;
                }
//                if (!isNotEmpty(imgcode)) {
//                    showToast("图片验证码不能为空");
//                    return;
//                }
                if (!isNotEmpty(code)) {
                    //注册第一步
                    showToast("验证码不能为空");
                    return;
                }
//                if(!code.equals(messageCode)){
//                    showToast("短信验证码不正确");
//                }
                //注册接口 短信验证码
                httpLoadingDialog.visible("加载中...");
                httpLoadingDialog.setCanceledOnKeyBack();
                getP().register(phone, pwd, code);

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
        App.getSharedPref().putString(Constant.USER_PHONE, getText(phoneNumber));
        App.getSharedPref().putString(Constant.IS_OPEN_ACCOUNT, model.getIsOpenAccount());

        //全局变量设置为登录状态
        RuntimeHelper.getInstance().setLogin(true);

        Bundle bundle = new Bundle();
        bundle.putString(Constant.PHONE, getText(phoneNumber));
        startActivity(RegisterSecondActivity.class, bundle);

        finish();
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

        //获取验证码成功就显示
//        imageLoader.displayImage("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1514031909051&di=8ed9e18b1cc42840143e19f0f2bc8976&imgtype=0&src=http%3A%2F%2Freso2.yiihuu.com%2F1331436-z.jpg", ivCode);

//        ILFactory.getLoader().loadNet(ivCode, "", null);


    }

    /**
     * 获取图片验证码成功失败
     */
    public void getImageCodeFail() {
        showToast("请求图片验证码失败...");
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

}
