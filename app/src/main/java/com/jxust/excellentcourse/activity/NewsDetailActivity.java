package com.jxust.excellentcourse.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.jxust.excellentcourse.R;

/**
 * Created by Simon on 2017/5/27.
 */

public class NewsDetailActivity extends Activity {
    private WebView webView;
    private  String url;
    private TextView back;
    private TextView toolbarcenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        webView= (WebView) findViewById(R.id.wb_news);
        back= (TextView) findViewById(R.id.tv_toolbar_lefttext);
        toolbarcenter= (TextView) findViewById(R.id.tv_toolbar_centertext);
        toolbarcenter.setText("新闻详情");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent=getIntent();
        url=intent.getStringExtra("url");
        initWebView();
    }
    private void initWebView() {
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;//返回为true默认webview自带浏览器打开，false调用三方浏览器
            }
        });
    }
}
