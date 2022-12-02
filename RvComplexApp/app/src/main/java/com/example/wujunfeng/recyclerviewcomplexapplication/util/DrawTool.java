package com.example.wujunfeng.recyclerviewcomplexapplication.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.TypedValue;

public class DrawTool {
    public static final int LEFT = 0x100000;
    public static final int RIGHT = 0x010000;
    public static final int CENTER_HOR = 0x001000;
    public static final int TOP = 0x000001;
    public static final int BOTTOM = 0x000010;
    public static final int CENTER_VER = 0x000100;
    public static final int VER_MASK = 0x000111;
    public static final int HOR_MASX = 0x111000;
    public static final int CENTER = CENTER_VER | CENTER_HOR;


    public static void drawRectText(Canvas c, String text, Paint paint, RectF rect, int gravity, boolean isAutoScale) {
        if (TextUtils.isEmpty(text)) return;
        Paint.FontMetricsInt fontMetricsInt = paint.getFontMetricsInt();
        float x;
        float textLen = paint.measureText(text);
        float temp = paint.getTextSize();
        if (textLen > rect.width() && isAutoScale) {
            paint.setTextSize(rect.width() / textLen * temp);
            textLen = paint.measureText(text);
        }
        switch (gravity & HOR_MASX) {
            case RIGHT:
                x = (int) (rect.right - textLen);
                break;
            case CENTER_HOR:
                x = rect.centerX() - textLen / 2f;
                break;
            case LEFT:
            default:
                x = rect.left;
        }

        float baseline;
        switch (gravity & VER_MASK) {
            case TOP:
                baseline = rect.top + (-fontMetricsInt.ascent);
                break;
            case BOTTOM:
                baseline = rect.bottom;
                break;
            case CENTER_VER:
            default:
                baseline = rect.centerY() - (fontMetricsInt.bottom + fontMetricsInt.top) / 2f;
                break;

        }
        c.drawText(text, x, baseline, paint);
        paint.setTextSize(temp);
    }

    public static void drawRectImage(Context context, Canvas c, int res,RectF rect, float width,float height) {
        Drawable flag = context.getResources().getDrawable(res);
        flag.setBounds((int) (rect.centerX() - width / 2), (int) (rect.centerY() - height / 2), (int) (rect.centerX() + width / 2), (int) (rect.centerY() + height / 2));
        flag.draw(c);
    }

    public static void drawBg(Canvas c,Paint p, RectF rect) {
        c.drawRect(rect,p);
    }

    public static int dp2Px(Context context, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics());
    }

    public static int sp2px(Context context, float spValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spValue, context.getResources().getDisplayMetrics());
    }
}
