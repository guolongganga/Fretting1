package com.zhsoft.fretting.ui.activity.user;

import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.zhsoft.fretting.App;
import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.params.CommonReqData;
import com.zhsoft.fretting.ui.widget.TitleView;

import org.apache.http.entity.StringEntity;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

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
//    @BindView(R.id.title_view)
//    TitleView titleView;
    @BindView(R.id.head_back) ImageButton headBack;
    @BindView(R.id.head_title) TextView headTitle;
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

//        titleView.setTitle(context, title);
        headTitle.setText(title);
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
        link = link + "?token=" + token + "&userId=" + userId;
        XLog.e("qqq", link);
//        mWeb.loadDataWithBaseURL(link, null, "text/html", "UTF-8", null);
        mWeb.loadUrl(link);

        //添加token userid
//        CommonReqData reqData = new CommonReqData();
//        reqData.setToken(token);
//        reqData.setUserId(userId);
//
//        Gson gson = new Gson();
//        String strData = gson.toJson(reqData);
//        //加密请求数据
//        String encrypt = EncryptDecrypt.encryptByAES(strData);
//        Map<String, String> map = new HashMap<>();
//        map.put("reqData", encrypt);
//        String params = gson.toJson(map);
//        XLog.e("qqq", "data = " + strData);
//        XLog.e("qqq", "params = " + params);
//        XLog.e("qqq", "url = " + link);
//        mWeb.postUrl(link, params.getBytes());

//        mWeb.loadUrl(link + reqData.toString());

//        StringEntity se = null;
//        try
//        {
//            se = new StringEntity(reqData.toString(),"UTF-8");
//            se.setContentType("application/json");
//            byte[] array = EntityUtils.toByteArray(se);
//            mWeb.postUrl(link, array);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        HashMap<String,String> map = new HashMap();
//        map.put("token","7a9a9ef0bfda9a7a0907e21dd913baf0");
//        map.put("uid","28");
//        Gson gson = new Gson();
//        String strData = gson.toJson(map);
//        XLog.e("qqq", "params = " + strData);
//        mWeb.postUrl("http://api.fushoushu.cn/inviteInfo/", strData.getBytes());

//        CommonReqData reqData = new CommonReqData();
//        reqData.setToken(token);
//        reqData.setUserId(userId);
////
//        Gson gson = new Gson();
//        String strData = gson.toJson(reqData);
//        Map<String, String> map = new HashMap<>();
//        map.put("reqData", strData);
//        String params = gson.toJson(map);

//        StringEntity se = null;
//        try
//        {
//        XLog.e("qqq", "strData = " + strData);
//            se = new StringEntity(strData,"UTF-8");
//            se.setContentType("application/json");
//            byte[] array = EntityUtils.toByteArray(se);
//            mWeb.postUrl(link, array);
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        XLog.e("qqq", "strData = " + strData);
//        XLog.e("qqq", "url = " + link);
//
//        mWeb.postUrl(link, strData.getBytes());


    }

    /**
     * 点击事件
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
     * 网页能返回上一级
     */
    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        if (null != mWeb && mWeb.canGoBack()) {
//            mWeb.goBack();// 返回前一个页面
//        } else {
//            super.onBackPressed();
//        }
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
