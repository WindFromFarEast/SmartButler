package com.studio.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.studio.smartbutler.R;
import com.studio.smartbutler.utils.L;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.ui
 * file name: WebViewActivity
 * creator: WindFromFarEast
 * created time: 2017/7/28 15:21
 * description: 微信精选详情界面
 */

public class WebViewActivity extends BaseActivity
{
    private ProgressBar pb_loading;
    private WebView wechat_webView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        //初始化控件
        initView();
    }

    //初始化控件
    private void initView()
    {
        pb_loading= (ProgressBar) findViewById(R.id.pb_loading);
        //显示progressbar表示正在加载网页内容
        pb_loading.setVisibility(View.VISIBLE);
        wechat_webView= (WebView) findViewById(R.id.wechat_webView);

        //获取从微信精选Activity传递过来的值
        Intent intent=getIntent();
        final String url=intent.getStringExtra("url");
        L.i("跳转后url:"+url);
        String title=intent.getStringExtra("title");
        //改变页面的ActionBar标题
        getSupportActionBar().setTitle(title);
        //设置WebView的属性
        wechat_webView.loadUrl(url);
        wechat_webView.getSettings().setJavaScriptEnabled(true);//允许Js
        wechat_webView.setWebViewClient(new WebViewClient()//打开网页不调用系统浏览器,直接在本地WebView打开
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
            {
                view.loadUrl(url);
                return true;
            }
        });
        wechat_webView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                if (newProgress>=100)
                {
                    //加载页面完毕后,隐藏ProgressBar
                    pb_loading.setVisibility(View.GONE);
                }
            }
        });
    }
}
