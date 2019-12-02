package com.example.wujunfeng.nestedscrollingapp.test.javascript;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import com.example.wujunfeng.nestedscrollingapp.test.view.WebViewExt;
import org.json.JSONException;
import org.json.JSONObject;

public class JavaScriptUs {
    //js绑定对象名
    public static final String jsToJavaObj = "goods";
    private WebViewExt webView;

    public JavaScriptUs(WebViewExt webView) {
        this.webView = webView;
    }

    @JavascriptInterface
    public synchronized String getAppInfo()
    {

//        {"userId":"BN5d4eaec2-5548-47cb-b3c4-444ca5ec1b96EN",
//                "guid":"BN5d4eaec2-5548-47cb-b3c4-444ca5ec1b96EN",
//                "systemVersion":27,
//                "pd":"2",
//                "mv":"8.0.0",
//                "ar":"10",
//                "vd":"8888",
//                "loginType":0,
//                "doubleType":0,
//                "isPhoneNum":false,
//                "webAuthToken":"A8Gh%2BKJElR8O48gdn2b22qkav3OuGlL7RdbF%2BMpOwbrVzyYVOfCLJDQrN7OhscDgCipqCXMBSQ%2BuNjMUHUDKWYOigoFehaTF%2FCTyI3fiJ1Wlaa7Dm3uz%2BpaNeGCQyJ%2FrSkAF7wj%2BKf9vvNUAGhtksciIVTncW2m28YV0%2FtzHicQ%3D",
//                "right":"l2"}

        JSONObject object = new JSONObject();
        try {
            object.put("userId","BN5d4eaec2-5548-47cb-b3c4-444ca5ec1b96EN");
            object.put("guid","BN5d4eaec2-5548-47cb-b3c4-444ca5ec1b96EN");
            object.put("systemVersion",27);
            object.put("pd","2");
            object.put("mv","8.0.0");
            object.put("ar",10);
            object.put("vd","8888");
            object.put("loginType",0);
            object.put("doubleType",0);
            object.put("isPhoneNum",false);
            object.put("webAuthToken","A8Gh%2BKJElR8O48gdn2b22qkav3OuGlL7RdbF%2BMpOwbrVzyYVOfCLJDQrN7OhscDgCipqCXMBSQ%2BuNjMUHUDKWYOigoFehaTF%2FCTyI3fiJ1Wlaa7Dm3uz%2BpaNeGCQyJ%2FrSkAF7wj%2BKf9vvNUAGhtksciIVTncW2m28YV0%2FtzHicQ%3D");
            object.put("right","l2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    @JavascriptInterface
    public void onHeightChange(String jsonStr) {
        try {
            JSONObject json = new JSONObject(jsonStr);
            final int height = json.optInt("height", 0);

//            if (height <= 0) return;
//            webView.post(new Runnable() {
//                @Override
//                public void run() {
//                    int newHeight = dip2px(webView.getContext(), height);
//                    if (webView.getHeight() != newHeight) {
//                        ViewGroup.LayoutParams lp = webView.getLayoutParams();
//                        lp.height = newHeight;
//                        webView.setLayoutParams(lp);
//                    }
//                }
//            });
        }
        catch (Exception e)
        {

        }
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
