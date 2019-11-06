package com.thebhakti;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Document;

import java.io.IOException;
import java.util.List;

/**
 * Created by Yeshveer on 9/18/2018.
 */

public class LiveWebview extends AppCompatActivity{
    WebView webView;
    private TextView txtTitle;
    private TextView txtDesc;
    String title1,desc1,urlLoad;



    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liveweb_view);
        webView = (WebView) findViewById(R.id.webView);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        txtDesc = (TextView) findViewById(R.id.txtDesc);


        // basic webview settings
        webView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadsImagesAutomatically(true);
        // External Settings
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
webView.getSettings().getDomStorageEnabled();

        webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        webView.getSettings().setMediaPlaybackRequiresUserGesture(true);
        webView.getSettings().getTextZoom();
        webView.getSettings().getBuiltInZoomControls();
        webView.getSettings().getAllowUniversalAccessFromFileURLs();
        webView.getSettings().getAllowFileAccess();
        webView.getSettings().getCacheMode();
        webView.getSettings().getBlockNetworkLoads();
        webView.getSettings().getAllowContentAccess();
        webView.getSettings().getBlockNetworkImage();
        {
            // Receive Intent
            // Get value of Intent from another Activity
            title1 = getIntent().getStringExtra("title");
            desc1 = getIntent().getStringExtra("desc");
            urlLoad = getIntent().getStringExtra("url");
        }



       txtTitle.setText(title1);
        txtDesc.setText(desc1);
        // webCromeClient
        webView.setWebChromeClient(new WebChromeClient());
        /*webView.setWebViewClient(new WebViewClient());*/
        MyBrowser myBrowser=new MyBrowser();
        webView.setWebViewClient(myBrowser);




        // Check package is installed or not
        boolean isAppInstalled = appInstalledOrNot("com.android.browser");
        if(urlLoad.equals("https://www.sai.org.in/en/sai-video-popup")) {
            webView.loadUrl(urlLoad);
        }
        else
        {

        /*    Intent ii = new Intent();
            ComponentName comp = new ComponentName("com.android.browser", "com.android.browser.BrowserActivity");
            ii.setComponent(comp);
            ii.setAction("android.intent.action.VIEW");
            ii.addCategory("android.intent.category.BROWSABLE");
            ii.setData(Uri.parse(urlLoad));
            startActivity(ii);
            finish();*/
           Intent intent1=new Intent(Intent.ACTION_VIEW,Uri.parse(urlLoad));
           startActivity(intent1);
           finish();

        }
        /*if(isAppInstalled) {
            //This intent will help you to launch if the package is already installed

            if(urlLoad.equals("https://www.sai.org.in/en/sai-video-popup")) {
                webView.loadUrl(urlLoad);
            }
            else
            {

                Intent ii = new Intent();
                ComponentName comp = new ComponentName("com.android.browser", "com.android.browser.BrowserActivity");
                ii.setComponent(comp);
                ii.setAction("android.intent.action.VIEW");
                ii.addCategory("android.intent.category.BROWSABLE");
                ii.setData(Uri.parse(urlLoad));
                startActivity(ii);
                finish();
            }

        }
        else {
            // Do whatever we want to do if application not installed
            // For example, Redirect to play store

            if(urlLoad.equals("https://www.sai.org.in/en/sai-video-popup")) {
                webView.loadUrl(urlLoad);
            }
            else
            {

                Intent ii = new Intent();
                ComponentName comp = new ComponentName("com.android.chrome", "com.android.chrome.BrowserActivity");
                ii.setComponent(comp);
                ii.setAction("android.intent.action.VIEW");
                ii.addCategory("android.intent.category.BROWSABLE");
                ii.setData(Uri.parse(urlLoad));
                startActivity(ii);
                finish();
            }
        }*/


     /*   // open a native browser
        Intent intent4 = new Intent();
        ComponentName comp = new ComponentName("com.google.android.browser","com.google.android.browser.BrowserActivity");
        intent4.setComponent(comp);
        intent4.setAction("android.intent.action.VIEW");
        intent4.addCategory("android.intent.category.BROWSABLE");
        Uri uri = Uri.parse(urlLoad);
        intent4.setData(uri);
        try
        {
            startActivity(intent4);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }*/

        /*Intent intent3 = new Intent(Intent.ACTION_VIEW);
        intent3.setData(Uri.parse(urlLoad));
        PackageManager packageManager = getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent3, 0);

        for (ResolveInfo resolveInfo : list) {

            if(resolveInfo.isDefault)
            {
                //default browser
            }
        }*/


        //set webclient
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

                /*pd.show();*/
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
               /* pd.dismiss();*/
                /*pb1.setVisibility(0);*/
            }
        });
        // use of JS and HTML
     /*   String data,mimeType,encoding;
        data = "<!DOCTYPE html>";
        data += "<head><title>Hello World</title></head>";
        data += "<body>Welcome to the WebView</body>";
        data += "</html>";
// args: data, mimeType, encoding
        *//*webView.loadData(data, "text/html", "UTF-8");*//*
        String js = "alert('Alert from Java');";
        webView.loadUrl("JavaScript:" + js);*/








    }// onCreate closer

    private boolean appInstalledOrNot(String s) {
        PackageManager pm = getPackageManager();
        try {
            String uri="com.android.browser";
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
        }

        return false;

    }

    static class MyBrowser extends WebViewClient {
    }
}
