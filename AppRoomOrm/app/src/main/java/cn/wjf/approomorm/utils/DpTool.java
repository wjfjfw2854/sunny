package cn.wjf.approomorm.utils;

import android.content.Context;
import android.util.TypedValue;

public class DpTool {
    public static int dp2px(Context context,int dp)
    {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics()) + 0.5f);
    }

}
