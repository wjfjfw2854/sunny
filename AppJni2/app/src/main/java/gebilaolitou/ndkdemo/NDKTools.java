package gebilaolitou.ndkdemo;

/**
 * author  : wujunfeng
 * e-mail  : 421284553@qq.com
 * date    : 2019/12/16 10:29
 * version : 1.4.4
 */
public class NDKTools {
    static {
        try {
            System.loadLibrary("ndkstatic");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static native String getStringFromNDK();
}
