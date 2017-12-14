package com.zhsoft.fretting.ui.activity.boot;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;


import com.zhsoft.fretting.R;
import com.zhsoft.fretting.constant.Constant;
import com.zhsoft.fretting.ui.widget.TitleView;

import butterknife.BindView;
import cn.droidlover.xdroidmvp.mvp.XActivity;

/**
 * 通用WebView
 * Created by sunny on 2017/4/25.implements OnChatActivityListener
 */

public class WebPublicActivity extends XActivity {

    //页面标题
    @BindView(R.id.title_view)
    TitleView titleView;
    @BindView(R.id.my_web)
    WebView mWeb;
    @BindView(R.id.pb)
    ProgressBar pb;

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

        titleView.setTitle(context, title);
        pb.setMax(100);

        // 设置可以支持缩放
        mWeb.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWeb.getSettings().setBuiltInZoomControls(true);

        // 设置WebViewClient，保证新的链接地址不打开系统的浏览器窗口
        mWeb.setWebViewClient(new WebViewClient());
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

        mWeb.loadUrl(link);
    }

    @Override
    public void initEvents() {
    }
}