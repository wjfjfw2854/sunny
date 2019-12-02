package com.example.wujunfeng.nestedscrollingapp.test;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.wujunfeng.nestedscrollingapp.R;
import com.example.wujunfeng.nestedscrollingapp.test.view.WebViewExt;

public class WebViewFrag extends Fragment {

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
        view = inflater.inflate(R.layout.frag_web,container,false);
        webView = (WebViewExt) view.findViewById(R.id.web);
        return view;
    }

    public static WebViewFrag newInstance(String title)
    {
        WebViewFrag webviewFrag = new WebViewFrag();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        webviewFrag.setArguments(bundle);
        return webviewFrag;
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.loadUrl("http://www.emoney.cn/dianjin/gw170630/index.html?ag_kwid=14639-11-884790d32ebb4df4.c47a021f7ae20807");
    }
}
