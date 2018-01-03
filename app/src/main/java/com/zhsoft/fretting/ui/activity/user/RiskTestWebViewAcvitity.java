package com.zhsoft.fretting.ui.activity.user;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.widget.TitleView;

import org.apache.http.util.EncodingUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.log.XLog;
import cn.droidlover.xdroidmvp.mvp.XActivity;
import cn.droidlover.xdroidmvp.utils.EncryptDecrypt;

/**
 * 作者：sunnyzeng on 2018/1/2 16:46
 * 描述：
 */

public class RiskTestWebViewAcvitity extends XActivity {

    //页面标题
    @BindView(R.id.title_view)
    TitleView titleView;
    @BindView(R.id.my_web)
    WebView mWeb;
    @BindView(R.id.pb)
    ProgressBar pb;

    String token;
    String userId;

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

        int title = bundle.getInt(Constant.WEB_TITLE);
        String link = bundle.getString(Constant.WEB_LINK);
        token = App.getSharedPref().getString(Constant.TOKEN, "");
        userId = App.getSharedPref().getString(Constant.USERID, "");

        titleView.setTitle(context, title);
        pb.setMax(100);

        // 设置可以支持缩放
        mWeb.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWeb.getSettings().setBuiltInZoomControls(true);

        // 设置WebViewClient，保证新的链接地址不打开系统的浏览器窗口
//        mWeb.setWebViewClient(new WebViewClient());
        mWeb.setWebViewClient(new WebViewClient() {
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
        mWeb.getSettings().setJavaScriptEnabled(true);
        //加载视频需要
        mWeb.getSettings().setPluginState(WebSettings.PluginState.ON);// 支持插件
        mWeb.getSettings().setUseWideViewPort(true); //将图片调整到适合WebView的大小  无效

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWeb.getSettings().setMixedContentMode(mWeb.getSettings().MIXED_CONTENT_ALWAYS_ALLOW);
        }

        mWeb.getSettings().setLoadsImagesAutomatically(true);  //支持自动加载图片
        mWeb.getSettings().setLoadWithOverviewMode(true); // 缩放至屏幕的大小

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

        });
        //添加header
        String ua = mWeb.getSettings().getUserAgentString();
        mWeb.getSettings().setUserAgentString(ua.replace("appType", "Android"));

//        CommonReqData reqData = new CommonReqData();
//        reqData.setToken(token);
//        reqData.setUserId(userId);
//
//        Gson gson = new Gson();
//        String strData = gson.toJson(reqData);
        //加密请求数据
//        String encrypt = EncryptDecrypt.encryptByAES(strData);
//        Map<String, String> map = new HashMap<>();
//        map.put("reqData", encrypt);
//        XLog.e("qqq", "data = " + strData);
//        XLog.e("qqq", "params = " + gson.toJson(map));
//        XLog.e("qqq", "url = " + link);
//        mWeb.postUrl(link, EncodingUtils.getBytes(gson.toJson(map), "UTF-8"));

        CommonReqData reqData = new CommonReqData();
        reqData.setToken("343e2aa09def8de29bd9d2fc38a57ede");
        reqData.setUserId("7");
        Gson gson = new Gson();
        String strData = gson.toJson(reqData);
        XLog.e("qqq", "params = " + strData);
        mWeb.postUrl("http://api.fushoushu.cn/inviteInfo/", strData.getBytes());


    }

    @Override
    public void initEvents() {
    }
}
