package com.wmz.mylibrary.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.StatFs;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import java.io.File;

/**
 * Created by wmz on 12/7/16.
 */
public class DeviceUtil {
    public DeviceUtil() {
    }

    public static String getVersion(Context context) {
        String verName = "1.0.0";
        String packageName = context.getPackageName();
        Log.e("DeviceUtil==>>", "packageName:" + packageName);

        try {
            verName = context.getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException var4) {
            verName = "1.0.0";
            var4.printStackTrace();
        }

        return verName;
    }

    public static String getStoragePath() {
        String sdCardPath = "";
        if("mounted".equals(Environment.getExternalStorageState())) {
            sdCardPath = Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            sdCardPath = Environment.getRootDirectory().getAbsolutePath();
        }

        return sdCardPath;
    }

    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static float getStorageAvailSize() {
        float value = 0.0F;
        String state = Environment.getExternalStorageState();
        if("mounted".equals(state)) {
            File sdcardDir = Environment.getExternalStorageDirectory();
            StatFs sf = new StatFs(sdcardDir.getPath());
            long blockSize = (long)sf.getBlockSize();
            long blockCount = (long)sf.getBlockCount();
            long availCount = (long)sf.getAvailableBlocks();
            Log.d("", "block大小:" + blockSize + ",block数目:" + blockCount + ",总大小:" + blockSize * blockCount / 1024L + "KB");
            Log.d("", "可用的block数目：:" + availCount + ",剩余空间:" + availCount * blockSize / 1024L + "KB");
            value = (float)(availCount * blockSize / 1024L / 1024L);
        }

        return value;
    }
}
