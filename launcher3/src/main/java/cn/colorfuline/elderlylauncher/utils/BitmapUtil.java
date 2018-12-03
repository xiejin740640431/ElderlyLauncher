package cn.colorfuline.elderlylauncher.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2017/2/14.
 */

public class BitmapUtil {
    public static final String TAG = BitmapUtil.class.getSimpleName();

    /**
     * Compresses the bitmap to a byte array for serialization.
     */
    public static byte[] flattenBitmap(Bitmap bitmap) {
        // Try go guesstimate how much space the icon will take when serialized
        // to avoid unnecessary allocations/copies during the write.
        int size = bitmap.getWidth() * bitmap.getHeight() * 4;
        ByteArrayOutputStream out = new ByteArrayOutputStream(size);
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {
            Log.w(TAG, "Could not write bitmap");
            return null;
        }
    }

    public static Bitmap createBitmap(byte[] data){
        BitmapFactory.Options options = new BitmapFactory.Options();
        // Always prefer RGB_565 config for low res. If the bitmap has transparency, it will
        // automatically be loaded as ALPHA_8888.
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }

    /**
     * 
     * @param drawable
     * @return
     */
    public static Bitmap drawableToBitamp(Drawable drawable) {
        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable) drawable).getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        System.out.println("Drawable转Bitmap");
        Bitmap.Config config =
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                        : Bitmap.Config.RGB_565;
        Bitmap bitmap = Bitmap.createBitmap(w, h, config);
        //注意，下面三行代码要用到，否在在View或者surfaceview里的canvas.drawBitmap会看不到图
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 保存到本地
     *
     * @param bitmap
     */
    public static void saveBitmap(Bitmap bitmap, String savePath,
                                  boolean isRecycle) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        File file = new File(savePath);
        try {
            file.createNewFile();
            BufferedOutputStream os = new BufferedOutputStream(
                    new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            os.write(baos.toByteArray());
            os.flush();
            os.close();
        } catch (IOException e) {
            Log.d("BITMAP", e.getMessage());
        } finally {
            if (isRecycle && bitmap != null) {
                bitmap.recycle();
            }
        }
    }
}
