package com.zhsoft.fretting.ui.activity.boot;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.event.ChangeTabEvent;
import com.zhsoft.fretting.ui.activity.MainActivity;
import com.zhsoft.fretting.ui.activity.user.LoginActivity;
import com.zhsoft.fretting.ui.widget.CustomDialog;
import com.zhsoft.fretting.ui.widget.TitleView;
import com.zhsoft.fretting.webjs.JSInterfaceClick;
import com.zhsoft.fretting.webjs.JSInterfaceUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 通用WebView
 * Created by sunny on 2017/4/25
 * 不需要token userid
 */

public class WebPublicActivity extends XActivity {

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
    public Object newP() {
        return null;
    }

    @Override
    public void initData(Bundle bundle) {

//        int title = bundle.getInt(Constant.WEB_TITLE);
        String link = bundle.getString(Constant.WEB_LINK);

//        titleView.setTitle(context, title);
//        headTitle.setText(title);
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

            @Override
            public void onReceivedSslError(WebView view,
                                           SslErrorHandler handler, SslError error) {
                // TODO Auto-generated method stub
                // handler.cancel();// Android默认的处理方式
                handler.proceed();// 接受所有网站的证书
                // handleMessage(Message msg);// 进行其他处理
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
     * JS
     *
     * @param jsInterfaceUtils
     */
    @SuppressLint("SetJavaScriptEnabled")
    @JavascriptInterface
    protected void setJSClick(JSInterfaceUtils jsInterfaceUtils) {
        // 设置js未登录触发事件
        jsInterfaceUtils.setJSInterfaceClick(new JSInterfaceClick() {

            public void toAppIndex() {
                baseToAppIndex();
            }
        });
    }

    /**
     * 返回首页
     */
    private void baseToAppIndex() {
        //TODO 返回首页
        EventBus.getDefault().post(new ChangeTabEvent(Constant.MAIN_INDEX));
        startActivity(MainActivity.class);
        finish();
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
}