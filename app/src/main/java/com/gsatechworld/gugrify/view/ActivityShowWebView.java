package com.gsatechworld.gugrify.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.gsatechworld.gugrify.R;

public class ActivityShowWebView extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener{

    private WebView w;
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

        w = findViewById(R.id.webView);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        progressBar = findViewById(R.id.webPBar);
        swipe.setOnRefreshListener(this);
        currentURL = getIntent().getStringExtra("url");
        w.loadUrl(currentURL);
        w.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                swipe.setRefreshing(false);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String urlNewString) {
                return true;
            }
        });
    }

    private void ReLoadWebView(String currentURL) {
        w.loadUrl(currentURL);
    }

    @Override
    public void onRefresh() {
        swipe.setRefreshing(true);
        progressBar.setVisibility(View.VISIBLE);
        ReLoadWebView(currentURL);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
