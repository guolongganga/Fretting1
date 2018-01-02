package com.zhsoft.fretting.ui.activity.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.event.OpenAccountEvent;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.net.HttpContent;
import com.zhsoft.fretting.ui.activity.boot.WebPublicActivity;
import com.zhsoft.fretting.utils.RuntimeHelper;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 作者：sunnyzeng on 2017/12/12 19:50
 * 描述：设置页面
 */

public class SettingActivity extends XActivity {
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** 个人信息 */
    @BindView(R.id.personinfo) TextView personinfo;
    /** 手机号码 */
    @BindView(R.id.phone) TextView phone;
    /** 密码管理 */
    @BindView(R.id.password_manager) TextView passwordManager;
    /** 银行卡号 */
    @BindView(R.id.bankcard) TextView bankcard;
    /** 风险等级测评 */
    @BindView(R.id.risk_test) LinearLayout riskTest;
    /** 客服电话 */
    @BindView(R.id.call_agent) TextView callAgent;
    /** 关于我们 */
    @BindView(R.id.about_us) TextView aboutUs;
    /** 切换账户 */
    @BindView(R.id.change_user) TextView changeUser;
    /** 安全退出 */
    @BindView(R.id.exit) Button exit;

    /** 客服电话 */
    private static final String PHONE_NUMBER = "13717832879";

    @Override

    public int getLayoutId() {
        return R.layout.activity_user_setting;
    }

    @Override
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {
        initview();
    }

    private void initview() {
        headTitle.setText("设置");
    }

    @Override
    public void initEvents() {

        personinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PersonInfoActivity.class);
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PhoneActivity.class);
            }
        });
        passwordManager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ChangePwdActivity.class);
            }
        });
        bankcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(BankCardActivity.class);
            }
        });
        riskTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showToast("风险等级测评");
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.WEB_TITLE, R.string.user_risk_test);
                bundle.putString(Constant.WEB_LINK, "http://api.fushoushu.cn/inviteInfo/");
                startActivity(RiskTestWebViewAcvitity.class, bundle);
            }
        });
        callAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取文本框中的电话号码值
//            String number = phoneNumber.getText().toString();

                //掉用拨号权限 新建一个意图
                Intent intent = new Intent();
                //在把意图添加给操作系统时，操作系统会自动为intent添加类别，所以可省略
                intent.setAction(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + PHONE_NUMBER));
                //将意图添加给操作系统执行
                startActivity(intent);

            }
        });
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt(Constant.WEB_TITLE, R.string.user_about_us);
                bundle.putString(Constant.WEB_LINK, "https://www.baidu.com/?tn=96928074_hao_pg");
                startActivity(WebPublicActivity.class, bundle);
            }
        });
        changeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SwitchAccountActivity.class);
            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //清空缓存数据
                App.getSharedPref().putString(Constant.USERID, "");
                App.getSharedPref().putString(Constant.TOKEN, "");
                App.getSharedPref().putString(Constant.USER_PHONE, "");
                App.getSharedPref().putString(Constant.USER_CERTNO, "");
                App.getSharedPref().putString(Constant.IS_OPEN_ACCOUNT, "");
                EventBus.getDefault().post(new OpenAccountEvent());

                RuntimeHelper.getInstance().setLogin(false);

                //TODO 更新登录状态
                showToast("安全退出");
                finish();
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
