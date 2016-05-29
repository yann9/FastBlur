package cc.yann.android.jniblur;


import android.graphics.Bitmap;

/**
 * @author Yann Chou
 * @email zhouyanbin1029@gmail.com
 * @create 2016-05-28 17:01
 */

public class JNIBlur {

    static {
        System.loadLibrary("jniblur");
    }

    public static Bitmap blur(Bitmap bitmap, int radius) {
        Bitmap result = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        if (nativeBlur(result, radius) == 0) {
            return result;
        } else {
            if (result != bitmap) {
                result.recycle();
            }
            return null;
        }
    }

    private static native int nativeBlur(Bitmap bitmap, int radius);

}
