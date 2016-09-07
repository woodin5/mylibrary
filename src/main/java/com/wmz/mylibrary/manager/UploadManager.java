package com.wmz.mylibrary.manager;

import android.os.Handler;

import com.wmz.mylibrary.utils.FormFile;
import com.wmz.mylibrary.utils.HandlerUtils;
import com.wmz.mylibrary.utils.UploadUtil;
import com.wmz.mylibrary.utils.UploadUtils;

import java.util.HashMap;
import java.util.Map;


public class UploadManager {

    public static void upload(final String uploadUrl, final String filename, final Handler handler, final int what) {
        ExecutorManager.execute(new Runnable() {

            @Override
            public void run() {
                String response = UploadUtils.uploadFile(uploadUrl, filename);
                HandlerUtils.sendHandle(handler, response, what);
            }
        });
    }

    public static void upload(final String uploadUrl, final String filename, final Handler handler, final HashMap<String, String> params, final int what) {
        ExecutorManager.execute(new Runnable() {

            @Override
            public void run() {
                String response = UploadUtils.uploadFile(uploadUrl, filename, params);
                HandlerUtils.sendHandle(handler, response, what);
            }
        });
    }

    public static void upload(final String uploadUrl, final String filename, final NetworkResponse response) {
        ExecutorManager.execute(new Runnable() {

            @Override
            public void run() {
                String content = UploadUtils.uploadFile(uploadUrl, filename);
                response.onResponse(content);
            }
        });
    }

    /**
     *
     * @param fileName
     * @param requestURL
     * @param formParams
     * @param response
     */
    public static void upload(final String requestURL,
                              final String fileName,
                              final Map<String, String> formParams,
                              final NetworkResponse response) {
        final FormFile formFiles =new FormFile(fileName, "image",
                "image/jpeg");
        ExecutorManager.execute(new Runnable() {

            @Override
            public void run() {
                String content = UploadUtil.post(null, requestURL, formParams, formFiles);
                response.onResponse(content);
            }
        });
    }
}
