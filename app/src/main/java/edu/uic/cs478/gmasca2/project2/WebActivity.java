package edu.uic.cs478.gmasca2.project2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebActivity extends Activity {

    public WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);

        webView.getSettings().setJavaScriptEnabled(true); //To play YouTube videos


        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError rerr) {
//                // Redirect to deprecated method, so you can use it in all SDK versions
//                onReceivedError(view, rerr.getErrorCode(), rerr.getDescription().toString(), req.getUrl().toString());
//            }
        });

        Intent intent = getIntent();
        String sv = intent.getStringExtra("song_video");

        webView.loadUrl(sv);

        setContentView(webView);

//        Intent intent = getIntent();

//        String s = intent.getStringExtra("song_video");
//        String video = intent.getStringExtra("video");



//        String frameVideo = "<html><body>Youtube video .. <br> <iframe width=\"560\" height=\"315\" src=\"https://www.youtube.com/embed/e-ORhEE9VVg\" frameborder=\"0\" allow=\"autoplay; encrypted-media\" allowfullscreen></iframe></body></html>";
//
//        webView.setWebViewClient(new WebViewClient(){
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                return false;
//            }
//        });
//        WebSettings webSettings = webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webView.loadData(frameVideo, "text/html", "utf-8");
    }
}

