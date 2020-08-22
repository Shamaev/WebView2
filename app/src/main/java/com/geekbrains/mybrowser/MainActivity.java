package com.geekbrains.mybrowser;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private String url;

    public static final String APP_PREFERENCES = "mysettings";
    public static final String APP_PREFERENCES_NAME = "urlName";

    SharedPreferences mSettings;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSettings = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        editor = mSettings.edit();
        if(mSettings.contains(APP_PREFERENCES_NAME)) {
            url = mSettings.getString(APP_PREFERENCES_NAME, "https://yandex.ru");
        } else {
            url = "https://yandex.ru";
        }

        webView = findViewById(R.id.webView);

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl(url);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());

                editor.putString(APP_PREFERENCES_NAME, view.getUrl());
                editor.apply();

                Log.i("sdfsdfsffg", request.getUrl().toString());

                if (request.getUrl().toString().contains("maps.yandex.ru")) {
                    Uri uri = Uri.parse("yandexmaps://maps.yandex.ru/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    PackageManager packageManager = getPackageManager();
                    List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
                    boolean isIntentSafe = activities.size() > 0;
                    if (isIntentSafe) {
                        startActivity(intent);
                    } else {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse("https://maps.yandex.ru/"));
                        startActivity(intent);
                    }
                    return true;
                } else if (request.getUrl().toString().contains("yandex.ru/pogoda")) {
                    Uri uri = Uri.parse("https://pogoda.yandex.com/");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    PackageManager packageManager = getPackageManager();
                    List<ResolveInfo> activities = packageManager.queryIntentActivities(intent, 0);
                    boolean isIntentSafe = activities.size() > 0;
                    if (isIntentSafe) {
                        startActivity(intent);
                    } else {
                        intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(uri);
                        startActivity(intent);
                    }
                    return true;
                } else {
                    return false;
                }
            }
        });

        CookieManager.getInstance().setAcceptCookie(true);
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().setAcceptThirdPartyCookies(webView, true);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    public void onBackPressed() {

//        if (webView.canGoBack()) {
//            webView.goBack();
//        } else {
//
//            if (mSettings.getString(APP_PREFERENCES_NAME, "https://yandex.ru").equals("https://yandex.ru")) {
//                FragmentManager manager = getSupportFragmentManager();
//                Dialog myDialogFragment = new Dialog();
//                FragmentTransaction transaction = manager.beginTransaction();
//                myDialogFragment.show(transaction, "dialog");
//            } else {
//                editor.clear();
//                editor.commit();
//                webView.loadUrl("https://yandex.ru");
//            }
//        }

        boolean homeCheck = mSettings.getString(APP_PREFERENCES_NAME, "https://yandex.ru")
                        .equals("https://yandex.ru");

        if (homeCheck) {
            FragmentManager manager = getSupportFragmentManager();
            Dialog myDialogFragment = new Dialog();
            FragmentTransaction transaction = manager.beginTransaction();
            myDialogFragment.show(transaction, "dialog");
        } else {
            if (webView.canGoBack() && !homeCheck) {
                webView.goBack();
            } else {
                editor.clear();
                editor.commit();
                webView.loadUrl("https://yandex.ru");
            }
        }
    }
}
