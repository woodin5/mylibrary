package com.wmz.mylibrary.utils;

import android.graphics.Bitmap;
import android.view.View;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by wmz on 20/7/16.
 */
public class ImageUtils {

    /**
     * 截屏
     * @return
     */
    public static String getScreenShot(int width, int height, View view) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss", Locale.US);
        String fname = "/sdcard/" + sdf.format(new Date()) + ".png";

        //生成相同大小的图片
        Bitmap temBitmap = Bitmap.createBitmap( width, height, Bitmap.Config.ARGB_8888 );
        //设置缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //从缓存中获取当前屏幕的图片

//        View view = homePage;
//        view.setDrawingCacheEnabled(true);
        //  view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();

        if (bitmap != null) {
            System.out.println("bitmap got !");
            try {
                FileOutputStream out = new FileOutputStream(fname);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("bitmapis NULL !");
        }
        return fname;
    }
}
