
package com.rafiant.iq;
import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceError;
import android.graphics.Bitmap;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.webkit.ValueCallback;
public class MainActivity extends Activity {
    private WebView webView;
    private ProgressBar progressBar;
    private LinearLayout errorLayout;
    private static final String URL = "https://rafiantiq.com/app.html";
    private ValueCallback<Uri[]> filePathCallback;
    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout root = new LinearLayout(this); root.setOrientation(LinearLayout.VERTICAL); root.setBackgroundColor(0xFF000000);
        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal); root.addView(progressBar, new LinearLayout.LayoutParams(-1, 8));
        errorLayout = new LinearLayout(this); errorLayout.setOrientation(LinearLayout.VERTICAL); errorLayout.setVisibility(View.GONE); errorLayout.setPadding(40,200,40,40);
        TextView errorText = new TextView(this); errorText.setText("No connection. Your saved offers work offline."); errorText.setTextColor(0xFFFFFFFF); errorLayout.addView(errorText); root.addView(errorLayout);
        webView = new WebView(this); root.addView(webView, new LinearLayout.LayoutParams(-1, -1, 1f)); setContentView(root);
        WebSettings ws = webView.getSettings(); ws.setJavaScriptEnabled(true); ws.setDomStorageEnabled(true); ws.setDatabaseEnabled(true);
        webView.setWebViewClient(new WebViewClient(){
            public void onPageStarted(WebView v, String u, Bitmap f){ progressBar.setVisibility(View.VISIBLE); errorLayout.setVisibility(View.GONE); }
            public void onPageFinished(WebView v, String u){ progressBar.setVisibility(View.GONE); }
            public void onReceivedError(WebView v, WebResourceRequest r, WebResourceError e){ if(r.isForMainFrame()){ errorLayout.setVisibility(View.VISIBLE); } }
            public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest r){ String url=r.getUrl().toString(); if(url.contains("rafiantiq.com")) return false; return false; }
        });
        webView.setWebChromeClient(new WebChromeClient(){ public void onProgressChanged(WebView v, int p){ progressBar.setProgress(p); } });
        if(savedInstanceState!=null) webView.restoreState(savedInstanceState); else webView.loadUrl(URL);
    }
    public boolean onKeyDown(int k, KeyEvent e){ if(k==KeyEvent.KEYCODE_BACK && webView.canGoBack()){ webView.goBack(); return true; } return super.onKeyDown(k,e); }
}
