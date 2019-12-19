package gebilaolitou.ndkdemo.libtools;

import java.util.ArrayList;
import gebilaolitou.ndkdemo.libtools.utils.DataElement;

/**
 * author  : wujunfeng
 * e-mail  : 421284553@qq.com
 * date    : 2019/12/17 16:44
 * version : 1.4.4
 */
public class ToolUtil {
    static {
        try {
            System.loadLibrary("jnitools");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static native String or(String x1,String x2);
    public static native long calcXyz(ArrayList<DataElement> arys);
}
