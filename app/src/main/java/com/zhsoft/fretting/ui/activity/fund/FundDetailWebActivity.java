package com.zhsoft.fretting.ui.activity.fund;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.model.fund.BuyFundResp;
import com.zhsoft.fretting.model.fund.NewestFundResp;
import com.zhsoft.fretting.net.Api;
import com.zhsoft.fretting.net.HttpContent;
import com.zhsoft.fretting.present.fund.FundDetailPresent;
import com.zhsoft.fretting.ui.activity.user.LoginActivity;
import com.zhsoft.fretting.ui.activity.user.PersonInfoActivity;
import com.zhsoft.fretting.ui.activity.user.RegisterSecondActivity;
import com.zhsoft.fretting.ui.activity.user.RiskTestWebViewAcvitity;
import com.zhsoft.fretting.ui.widget.CustomDialog;
import com.zhsoft.fretting.utils.RuntimeHelper;
import com.zhsoft.fretting.webjs.JSInterfaceClick;
import com.zhsoft.fretting.webjs.JSInterfaceUtils;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 通用WebView
 * Created by sunny on 2017/4/25
 */

public class FundDetailWebActivity extends XActivity<FundDetailPresent> {

    //页面标题
//    @BindView(R.id.title_view)
//    TitleView titleView;
    /** 返回按钮 */
    @BindView(R.id.head_back) ImageButton headBack;
    /** 标题 */
    @BindView(R.id.head_title) TextView headTitle;
    /** WebView */
    @BindView(R.id.my_web)
    WebView mWeb;
    /** 进度条 */
    @BindView(R.id.pb)
    ProgressBar pb;
    private CustomDialog loginDialog;
    /** 用户登录标识 */
    private String token;
    /** 用户编号 */
    private String userId;
    /** 基金对象 */
    private NewestFundResp fundResp;

    /** 去开户 弹出框 */
    private CustomDialog openAccountDialog;
    /** 去补全个人信息 弹出框 */
    private CustomDialog personInfoDialog;
    /** 去开户 去风险测评 */
    private CustomDialog riskTestDialog;
    /** 风险等级 弹出框 */
    private CustomDialog validateDialog;

    @Override
    public void onResume() {
        super.onResume();
        if (mWeb != null) {
            mWeb.onResume();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mWeb != null) {
            mWeb.onPause();
        }
    }


    @Override
    public int getLayoutId() {
        return R.layout.activity_web_public;
    }

    @Override
    public FundDetailPresent newP() {
        return new FundDetailPresent();
    }

    @Override
    public void initData(Bundle bundle) {

        int title = bundle.getInt(Constant.WEB_TITLE);
        String link = bundle.getString(Constant.WEB_LINK);
//        fundResp = (NewestFundResp) bundle.getParcelable(Constant.FUND_RESP_OBJECT);
//        titleView.setTitle(context, title);
        headTitle.setText(title);
        pb.setMax(100);

        WebSettings webSettings = mWeb.getSettings();
        // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);

        // 设置WebViewClient，保证新的链接地址不打开系统的浏览器窗口
        mWeb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                /**
                 *  如果紧跟着
                 *  webView.loadUrl(file:///android_asset/index.html);
                 *  调用Js中的方法是不起作用的，必须页面加载完成才可以
                 */
                //调用js中的函数：showInfoFromJava(msg)
//                mWeb.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        mWeb.loadUrl("javascript:callJS('" + "123456" + "')");
//                    }
//                });
            }
        });
        // 设置WebView支持运行普通的Javascript
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        //加载视频需要 支持插件
        webSettings.setPluginState(WebSettings.PluginState.ON);
        //将图片调整到适合WebView的大小  无效
        webSettings.setUseWideViewPort(true);
        // android 5.0以上默认不支持Mixed Content
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(mWeb.getSettings().MIXED_CONTENT_ALWAYS_ALLOW);
        }
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        // 缩放至屏幕的大小
        webSettings.setLoadWithOverviewMode(true);

        // 通过addJavascriptInterface()将Java对象映射到JS对象
        //参数1：Javascript对象名
        //参数2：Java对象名
        // webview设置JS
        JSInterfaceUtils jsInterfaceUtils = new JSInterfaceUtils(this, mWeb);
        //jsInterfaceUtils类对象映射到js的connObj对象
        mWeb.addJavascriptInterface(jsInterfaceUtils, JSInterfaceUtils.JS_ID);
        setJSClick(jsInterfaceUtils);

        mWeb.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

        // 设置WebChromeClient，以支持运行特殊的Javascript
        mWeb.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                pb.setProgress(newProgress);
                if (newProgress == 100) {
                    pb.setVisibility(View.GONE);
                }
                super.onProgressChanged(view, newProgress);
            }

            //设置标题
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                XLog.d("WebPublicActivity", "TITLE=" + title);
                headTitle.setText(title);
            }
        });

        mWeb.loadUrl(link);

        // 加载JS代码
        // 格式规定为:file:///android_asset/文件名.html
//        mWeb.loadUrl("file:///android_asset/javascript.html");

    }

    /**
     * 事件
     */
    @Override
    public void initEvents() {
        headBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    /**
     * JS
     *
     * @param jsInterfaceUtils
     */
    @SuppressLint("SetJavaScriptEnabled")
    @JavascriptInterface
    protected void setJSClick(JSInterfaceUtils jsInterfaceUtils) {
        // 设置js未登录触发事件
        jsInterfaceUtils.setJSInterfaceClick(new JSInterfaceClick() {

            @Override
            public void toLogin() {
                baseToLogin();
            }

            @Override
            public void toBuy() {
                baseToBuy();
            }
        });
    }

    private void baseToLogin() {
        showToast("调用了Android代码");
        if (loginDialog == null) {
            loginDialog = new CustomDialog
                    .Builder(context)
                    .setMessage(R.string.user_common_no_login)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            loginDialog.dismiss();
                            //跳转回登录界面
                            Bundle bundle = new Bundle();
                            bundle.putString(Constant.SKIP_SIGN, Constant.WEB_ACTIVITY);
                            startActivity(LoginActivity.class,bundle);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            loginDialog.dismiss();
                        }
                    }).create();
        }
        loginDialog.show();
    }

    private void baseToBuy() {
        if (RuntimeHelper.getInstance().isLogin()) {
//            token = App.getSharedPref().getString(Constant.TOKEN, "");
//            userId = App.getSharedPref().getString(Constant.USERID, "");
            token = "8d9f2d6690904d569c1b27133d692db1";
            userId = "0f4ddf4852e644598d7ade9edc433e87";
            String fund_code = "050001";

            getP().buyFund(token, userId, fund_code);
        } else {
            //跳转回登录界面
            Bundle bundle = new Bundle();
            bundle.putString(Constant.SKIP_SIGN, Constant.WEB_ACTIVITY);
            startActivity(LoginActivity.class,bundle);
        }
    }

    /**
     * 网页能返回上一级页面
     */
    @Override
    public void onBackPressed() {

        if (null != mWeb && mWeb.canGoBack()) {
            mWeb.goBack();// 返回前一个页面
        } else {
            super.onBackPressed();
        }
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        try {
            if (mWeb != null) {
                mWeb.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
                mWeb.clearHistory();

                ((ViewGroup) mWeb.getParent()).removeView(mWeb);
                mWeb.destroy();
                mWeb = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    /**
     * 购买（是否符合购买资格）失败
     */
    public void requestBuyFundFail() {
    }


    /**
     * 购买（是否符合购买资格）成功 跳转购买页面
     */
    public void requestBuyFundSuccess(final BuyFundResp resp) {
        if (Constant.TO_OPEN_ACCOUNT.equals(resp.getCanBuy())) {
            //弹出弹出框 去开户
            if (openAccountDialog == null) {
                openAccountDialog = new CustomDialog.Builder(context)
                        .setMessage("您还没有开户，现在去开户！")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                openAccountDialog.dismiss();
                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                openAccountDialog.dismiss();
                                startActivity(RegisterSecondActivity.class);
                            }
                        }).create();
            }
            openAccountDialog.show();
        } else if (Constant.TO_PERSON_INFO.equals(resp.getCanBuy())) {
            //弹出弹出框 去补全个人信息
            if (personInfoDialog == null) {
                personInfoDialog = new CustomDialog.Builder(context)
                        .setMessage("购买基金前请先完善您的个人信息！")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                personInfoDialog.dismiss();
                            }
                        }).setPositiveButton("去完成", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                personInfoDialog.dismiss();
                                startActivity(PersonInfoActivity.class);
                            }
                        }).create();
            }
            personInfoDialog.show();
        } else if (Constant.TO_RISK_TEST.equals(resp.getCanBuy())) {
            //弹出弹出框 去风险测评
            if (riskTestDialog == null) {
                riskTestDialog = new CustomDialog.Builder(context)
                        .setMessage("购买基金前请先完成风险等级测评！")
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                riskTestDialog.dismiss();
                            }
                        }).setPositiveButton("去完成", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                riskTestDialog.dismiss();
                                Bundle bundle = new Bundle();
                                bundle.putInt(Constant.WEB_TITLE, R.string.user_risk_test);
                                bundle.putString(Constant.WEB_LINK, Api.API_BASE_URL + HttpContent.risk_question);
                                startActivity(RiskTestWebViewAcvitity.class, bundle);
                            }
                        }).create();
            }
            riskTestDialog.show();

        } else if (Constant.TO_VALIDATE.equals(resp.getCanBuy())) {
            //弹出风险等级框
            if (validateDialog == null) {
                validateDialog = new CustomDialog.Builder(context)
                        .setMessage(resp.getFundRisk())
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                validateDialog.dismiss();
                            }
                        }).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                validateDialog.dismiss();
                                //去购买
                                Bundle bundle = new Bundle();
                                bundle.putParcelable(Constant.BUY_FUND_OBJECT, resp);
                                startActivity(BuyActivity.class, bundle);
                            }
                        }).create();
            }
            validateDialog.show();
        } else {
            //去购买
            Bundle bundle = new Bundle();
            bundle.putParcelable(Constant.BUY_FUND_OBJECT, resp);
            startActivity(BuyActivity.class, bundle);
        }

    }
}