package com.example.wujunfeng.nestedscrollingapp.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.example.wujunfeng.nestedscrollingapp.R;
import com.example.wujunfeng.nestedscrollingapp.test.view.TextViewExtTest;

public class CompleteFragment extends Fragment {
    public static final String TITLE = "title";
    private String mTitle = "Defaut Value";
    private View view;
//    private WebView webView;
    private FrameLayout frameLayout;
    private TextViewExtTest textView;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mTitle = getArguments().getString(TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_complete, container, false);
//        webView = view.findViewById(R.id.webComplete);
        frameLayout = (FrameLayout)view.findViewById(R.id.frameOut);
        textView = (TextViewExtTest)view.findViewById(R.id.txtV);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
//        webView.loadUrl("https://www.baidu.com");
        String str = "";
        for(int i = 0;i < 200;i ++)
        {
            str += i + "改变不接受，接受不改变，当天空一片漆黑的时候相信神奇的力量会出现，昙花未曾一现" + i + "<====>\n\n";
        }
        textView.setText(str);
    }

    public static CompleteFragment newInstance(String title)
    {
        CompleteFragment completeFragment = new CompleteFragment();
        Bundle bundle = new Bundle();
        bundle.putString(TITLE, title);
        completeFragment.setArguments(bundle);
        return completeFragment;
    }
}
