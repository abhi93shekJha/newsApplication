package com.gsatechworld.gugrify.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gsatechworld.gugrify.R;

public class ActivityShowWebView extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private WebView webView;
    private SwipeRefreshLayout swipe;
    private String currentURL = "";
    private ProgressBar progressBar;
    private Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setCustomView(R.layout.custom_actionbar);
            View view = getSupportActionBar().getCustomView();
        }

        webView = findViewById(R.id.webView);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        progressBar = findViewById(R.id.webPBar);
        swipe.setOnRefreshListener(this);
        currentURL = getIntent().getStringExtra("url");
        Log.d("loading url is", currentURL);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setWebViewClient(new WebViewClient(){

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressBar.setVisibility(View.VISIBLE);
                view.loadUrl(url);
                return true;
            }
            @Override
            public void onPageFinished(WebView view, final String url) {
                progressBar.setVisibility(View.GONE);
                swipe.setRefreshing(false);
            }
        });

        webView.loadUrl(currentURL);
    }


    @Override
    public void onRefresh() {
        swipe.setRefreshing(true);
        progressBar.setVisibility(View.VISIBLE);
        webView.reload();
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
