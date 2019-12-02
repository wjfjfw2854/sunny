package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.headscrollhorizontal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import com.example.wujunfeng.recyclerviewcomplexapplication.R;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.cellsview.CellOut;
import com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.cellsview.CellScroll;

public class HorizontalScrollHead extends LinearLayout {
    public HorizontalScrollHead(Context context) {
        this(context,null);
    }

    public HorizontalScrollHead(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HorizontalScrollHead(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context)
    {
        View rootView = LayoutInflater.from(context).inflate(R.layout.complexrecyclehead,null);
        CellScroll cellScrollHeadLeft = rootView.findViewById(R.id.cellScrollHeadLeft);
        CellOut celloutHead = rootView.findViewById(R.id.celloutHead);
        CellScroll cellScrollHeadRight = rootView.findViewById(R.id.cellScrollHeadRight);
    }
}
