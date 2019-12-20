package gebilaolitou.ndkdemo.libtools.utils;

import android.util.Log;

/**
 * author  : wujunfeng
 * e-mail  : 421284553@qq.com
 * date    : 2019/12/19 16:14
 * version : 1.4.4
 */
public class DataElement {
    private long x;
    private long y;
    private long z;

    public long getX() {
        Log.e("wjf>>>>","getX(): " + x);
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        Log.e("wjf>>>>","getY(): " + y);
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    public long getZ() {
        Log.e("wjf>>>>","getZ(): " + z);
        return z;
    }

    public void setZ(long z) {
        this.z = z;
    }
}
