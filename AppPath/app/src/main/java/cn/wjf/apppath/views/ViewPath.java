package cn.wjf.apppath.views;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class ViewPath extends View {

    private float progress;
    private Paint paint;

    private ValueAnimator valueAnimator;
    private PathMeasure pathMeasureCycle;
    private Path path;

    private PathMeasure pathMeasureLine;
    private Path pathLine;

    public ViewPath(Context context) {
        this(context,null);
    }

    public ViewPath(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ViewPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ViewPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context)
    {
        setLayerType(LAYER_TYPE_SOFTWARE,null);//取消硬件加速
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(0xffff5555);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
        paint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed)
        {
            int width = right - left;
            int height = bottom - top;
            Path pathL = new Path();
            pathL.moveTo(10,160);
            int spanX = 30;//(width - 10) / 10;
            int spanY = 40;//(bottom - 160) / 10;
//            for(int i = 0;i < 6;i ++) {
//                int x = 10 + spanX * i + 10;
//                int y = 160 + spanY * i + 10;
//                pathL.lineTo(x,y);
//            }
            pathL.lineTo(30,50);
            pathL.lineTo(80,170);
            pathL.lineTo(300,450);
            pathL.lineTo(width,200);
            pathMeasureLine = new PathMeasure(pathL,false);
            pathLine = new Path();

            Path pathCycle = new Path();
            pathCycle.addCircle(140,140,100,Path.Direction.CW);

            pathMeasureCycle = new PathMeasure(pathCycle,false);
            path = new Path();

            AccelerateDecelerateInterpolator adInterPolator = new AccelerateDecelerateInterpolator();
            valueAnimator = ValueAnimator.ofFloat(0,1);
            valueAnimator.setInterpolator(adInterPolator);
            valueAnimator.setDuration(1000);
            valueAnimator.setRepeatMode(ValueAnimator.RESTART);
            valueAnimator.setRepeatCount(100);
            valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    progress = (float)animation.getAnimatedValue();
                    invalidate();
                }
            });
            valueAnimator.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        float stop = pathMeasureCycle.getLength() * progress;
        pathMeasureCycle.getSegment(0,stop,path,true);
        canvas.drawPath(path,paint);

        if(pathLine != null)
        {
            pathLine.reset();
            float stopLine = pathMeasureLine.getLength() * progress;
            if(pathMeasureLine != null) {
                pathMeasureLine.getSegment(0, stopLine, pathLine, true);
            }
            paint.setColor(0xff5555ff);
            canvas.drawPath(pathLine,paint);
        }
    }
}
