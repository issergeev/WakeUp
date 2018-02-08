package com.cogwheelSoft.androiddevelop.wakeup;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class AboutApp extends AppCompatActivity{

    private WebView MyWeb;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.about_app);

        MyWeb = (WebView) findViewById(R.id.MyWebView);
        MyWeb.getSettings().setJavaScriptEnabled(true);
        MyWeb.setWebViewClient(new MyWebClient());
        MyWeb.loadUrl("https://play.google.com/store/apps/details?id=com.cogwheelSoft.androiddevelop.wakeup");
    }

    private class MyWebClient extends WebViewClient {
        public boolean OverrideURL(WebView myWebview, String URL){
            myWebview.loadUrl(URL);
            return true;
        }
    }

    public void onBackPressed(){
        if(MyWeb.canGoBack()){
            MyWeb.goBack();
        }else {
            startActivity(new Intent(AboutApp.this, Settings_Preferences.class));
            overridePendingTransition(R.anim.settings_anim_reverce, R.anim.options_anim_reverce);
            finish();
        }
    }
}