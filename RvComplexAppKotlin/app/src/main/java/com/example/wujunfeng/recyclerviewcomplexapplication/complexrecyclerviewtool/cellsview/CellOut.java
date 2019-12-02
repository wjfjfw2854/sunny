package com.example.wujunfeng.recyclerviewcomplexapplication.complexrecyclerviewtool.cellsview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

public class CellOut extends FrameLayout {
    public CellOut(@NonNull Context context) {
        this(context,null);
    }

    public CellOut(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CellOut(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
