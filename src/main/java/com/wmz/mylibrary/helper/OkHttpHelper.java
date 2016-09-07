package com.wmz.mylibrary.helper;

import android.os.Handler;
import android.os.Message;

import com.wmz.mylibrary.manager.ExecutorManager;
import com.wmz.mylibrary.manager.OkHttpManager;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.RequestBody;


public class OkHttpHelper{
    private OkHttpManager okHttpManager;
    private static OkHttpHelper instance;

    public static OkHttpHelper getInstance() {
        return instance;
    }

    private OkHttpHelper(){
        okHttpManager = OkHttpManager.getManager();
    }

    /**
     * 初始化
     */
    public static void init(){
        if(instance==null){
            instance = new OkHttpHelper();
        }
    }


    public void execute(final String url, final Handler handler,final int what){
        ExecutorManager.execute(new Runnable() {
            @Override
            public void run() {
                handler.sendMessage(Message.obtain(handler, what, execute(url)));
            }
        });
    }

    /**
     * 执行get请求
     * @param url
     * @param response
     */
    public void execute(final String url, final Response response){
        ExecutorManager.execute(new Runnable() {
            @Override
            public void run() {
                response.onResponse(execute(url));
            }
        });
    }


    /**
     * 执行get请求
     * @param url
     * @return
     */
    public String execute(final String url){
        return okHttpManager.execute(url);
    }

    /**
     * 执行post请求
     * @param url
     * @param body
     * @param response
     */
    public void execute(final String url, final RequestBody body,final Response response){
        ExecutorManager.execute(new Runnable() {
            @Override
            public void run() {
                response.onResponse(execute(url, body));
            }
        });
    }

    /**
     * 执行post请求
     * @param url
     * @param body
     * @return
     */
    public String execute(String url, RequestBody body){
        return okHttpManager.execute(url,body);
    }
    public void enqueue(String url, Callback responseCallback){
        okHttpManager.enqueue(url,responseCallback);
    }


    /**
     * 请求响应
     */
    public interface Response {
        void onResponse(String response);
    }

    /**
     * 请求体数据
     *
     * @param params
     * @return
     */
    public RequestBody getRequestBody(HashMap<String, String> params) {
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            builder.add(key, value);
        }
        return builder.build();
    }
}
