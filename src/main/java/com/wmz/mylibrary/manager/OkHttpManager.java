package com.wmz.mylibrary.manager;

import android.util.Log;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by wmz on 26/5/16.
 */
public class OkHttpManager {
    private static OkHttpManager manager;
    private static OkHttpClient client = new OkHttpClient();

    public static void init() {
        client = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
//                .addInterceptor(new LoggerInterceptor("TAG"))
//                .cookieJar(cookieJar1)
//                .sslSocketFactory(HttpsUtils.getSslSocketFactory(null, null, null))
                .build();
    }

    public OkHttpManager(){
        if(client==null){
            client = new OkHttpClient();
        }
    }
    public synchronized static OkHttpManager getManager() {
        if (manager == null) {
            synchronized (OkHttpManager.class) {
                if (manager == null) {
                    manager = new OkHttpManager();
                }
            }
        }
        return manager;
    }

    public static OkHttpClient getClient() {
        return client;
    }

    public void setReadTimeout(long timeout) {
        client = client.newBuilder().readTimeout(timeout, TimeUnit.SECONDS).build();
    }

    /**
     * get
     * @param url
     * @return
     */
    public String execute(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        try {
            return client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * post
     * @param url
     * @param body
     * @return
     */
    public String execute(String url, RequestBody body) {
        Log.e("wmz","url="+url);
        Log.e("wmz","body="+body.toString());
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        try {
            return client.newCall(request).execute().body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void enqueue(String url, Callback responseCallback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(responseCallback);
    }

    public void enqueue(String url, RequestBody body, Callback responseCallback) {
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        client.newCall(request).enqueue(responseCallback);
    }
}
