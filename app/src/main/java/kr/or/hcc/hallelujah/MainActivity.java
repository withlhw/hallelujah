package kr.or.hcc.hallelujah;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

public class MainActivity extends Activity {
    private ImageView mImageView;
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mImageView = (ImageView)findViewById(R.id.intro_image);
        mWebView = (WebView)findViewById(R.id.activity_main_webview);

        mImageView.setVisibility(View.VISIBLE);
        mWebView.setVisibility(View.INVISIBLE);

        new Handler().postDelayed(new Runnable(){
            public void run() {
            mImageView.setVisibility(View.GONE);
            mWebView.setVisibility(View.VISIBLE);
            }
        }, 3000);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("rtsp")) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                    return true;
                }

                view.loadUrl(url);
                return false;
            }
        });

        mWebView.loadUrl("http://m.hcc.or.kr/");
    }

    @Override
    protected void onPause() {
        super.onPause();

        mWebView.onPause();
        mWebView.pauseTimers();
    }

    @Override
    protected void onResume() {
        super.onResume();

        mWebView.onResume();
        mWebView.resumeTimers();
    }

    @Override
    public void onBackPressed() {
        if (mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
