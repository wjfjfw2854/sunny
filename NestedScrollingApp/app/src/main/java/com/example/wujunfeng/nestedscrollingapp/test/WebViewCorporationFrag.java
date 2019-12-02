package com.example.wujunfeng.nestedscrollingapp.test;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.*;
import com.example.wujunfeng.nestedscrollingapp.R;
import com.example.wujunfeng.nestedscrollingapp.test.javascript.JavaScriptUs;
import com.example.wujunfeng.nestedscrollingapp.test.view.WebViewExt;

public class WebViewCorporationFrag extends Fragment {
    public static final String TITLE = "title";
    private View view;
    private WebViewExt webView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragcorporation_webview,container,false);
        webView = (WebViewExt) view.findViewById(R.id.web);
        JavaScriptUs javaScriptUs = new JavaScriptUs(webView);
        webView.addJavascriptInterface(javaScriptUs, JavaScriptUs.jsToJavaObj);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);//支持javascript
        settings.setUseWideViewPort(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);
        webView.setWebChromeClient(new MyWebChromeClient());
        webView.setWebViewClient(webView.new MyWebViewClient());
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAllowFileAccess(true);
        return view;
    }

    public static WebViewCorporationFrag newInstance(String title)
    {
        WebViewCorporationFrag webViewCorporationFrag = new WebViewCorporationFrag();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        webViewCorporationFrag.setArguments(bundle);
        return webViewCorporationFrag;
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.loadUrl("http://static.m.emoney.cn/html/emapp/stock/news/list/?stockid=601162_0_2&token=711E61935A2FE6F8CA99B5B037D27EAB&ar=10&css=night&pd=2&webAuthToken=waLV48LrucKFcw1IQpAQGeswihtvdfl67+pCuNNe8ZuaJIbA6KpPbOwL+koujaq/Lf1EnbssONNGrgzWaQ2kxuRv6ColM5U7F+nNqpvo4o/ieTzjNz49R7luqI5ws+76DLza6rHT6i1FewFW9V7hpD3JVfOuWnX7KByUPbeKcPg=&mv=8.0.0&emoneyToken=711E61935A2FE6F8CA99B5B037D27EAB&vd=8888");
//        webView.loadUrl("http://www.emoney.cn/dianjin/gw170630/index.html?ag_kwid=14639-11-884790d32ebb4df4.c47a021f7ae20807");
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                webView.scrollTo(0,400);
//            }
//        },1000);
    }

    public class MyWebChromeClient extends WebChromeClient {
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            //构建一个Builder来显示网页中的alert对话框
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("提示对话框");
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }

            });
            builder.setCancelable(false);
            builder.create();
            builder.show();
            return true;
        }

        public boolean onJsConfirm(WebView view, String url, String message, final JsResult result) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle("带选择的对话框");
            builder.setMessage(message);
            builder.setPositiveButton(android.R.string.ok, new AlertDialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.confirm();
                }

            });
            builder.setNeutralButton(android.R.string.cancel, new AlertDialog.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    result.cancel();
                }

            });
            builder.setCancelable(false);
            builder.create();
            builder.show();
            return true;
        }

        //设置网页加载的进度条
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);

//            if (newProgress < 100) {
//                webViewModel.progress.set(newProgress);
//            } else {
//                webViewModel.progress.set(-1);
//            }

        }

        //设置应用程序的标题
        public void onReceivedTitle(WebView view, String title) {
            if (!TextUtils.isEmpty(title)) {
//                webViewModel.title.set(title);
            }
            super.onReceivedTitle(view, title);
        }

        // For Android 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        }

        // For Android 3.0+
        public void openFileChooser(ValueCallback uploadMsg, String acceptType) {
        }

        // For Android 4.1
        public void openFileChooser(ValueCallback<Uri> uploadMsg,
                                    String acceptType, String capture) {
        }

        //For Android 5 & 5+
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            return true;
        }
    }
}
