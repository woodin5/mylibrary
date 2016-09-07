package com.wmz.mylibrary.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.wmz.mylibrary.manager.NetworkResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.util.Enumeration;

/**
 * HttpURLConnection网络执行
 * Created by wmz on 20/6/16.
 */
public class NetworkExecuter {


    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null;
    }

    public static boolean hasNet(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public static int getNetType(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        int type = manager.getActiveNetworkInfo().getType();
        return type;
    }


    /**
     * 连接网络
     * @param url
     * @return
     */
    public static HttpURLConnection getHttpURLConnection(String url) {
        HttpURLConnection httpurlconnection = null;

        try {
            httpurlconnection = (HttpURLConnection) new URL(url)
                    .openConnection();
            httpurlconnection
                    .setRequestProperty(
                            "User-Agent",
                            "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.106 Safari/535.2");
            return httpurlconnection;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * get连接网络
     * @param url
     * @param response
     * @return
     */
    public static InputStream getInputStreamyGet(
            String url, NetworkResponse response) {
        try {

            HttpURLConnection httpurlconnection = null;

            httpurlconnection = getHttpURLConnection(url);
            httpurlconnection.setDoInput(true);
            httpurlconnection.setRequestMethod("GET");
            httpurlconnection.setReadTimeout(60 * 1000);
            httpurlconnection.setConnectTimeout(60 * 1000);

            return httpurlconnection.getInputStream();

        } catch (Exception e) {
            response.onError(e.getMessage());
            return null;
        }
    }


    /**
     * post连接网络
     * @param url
     * @param params
     * @param response
     * @return
     */
    public static InputStream getInputStreamByPost(
            String url, String params, NetworkResponse response) {
        try {
            Log.d("wmz", "wmz:getInputStreamByPost-params=" + params);
            HttpURLConnection httpurlconnection = null;

            httpurlconnection = getHttpURLConnection(url);
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setDoInput(true);
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.getOutputStream().write(params.getBytes());
            httpurlconnection.setReadTimeout(60 * 1000);
            httpurlconnection.setConnectTimeout(60 * 1000);

            httpurlconnection.getOutputStream().flush();
            httpurlconnection.getOutputStream().close();
            return httpurlconnection.getInputStream();

        } catch (Exception e) {
            response.onError(e.getMessage());
            return null;
        }
    }

    /**
     * get请求网络
     * @param url
     * @param response
     */
    public static void executeByGet(String url, NetworkResponse response) {
        Log.d("wmz","wmz:executeByGet");
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader in = null;
            InputStream input = null;

            input = getInputStreamyGet(url, response);
            in = new BufferedReader(new InputStreamReader(input, "utf-8"));
            int len = 0;
            char buf[] = new char[4 * 1024];
            while ((len = in.read(buf)) != -1) {
                sb.append(new String(buf, 0, len));
            }
            in.close();
            response.onResponse(sb.toString());
        } catch (Exception e) {
            response.onError(e.getMessage());
        }

    }

    /**
     * post请求网络
     * @param url
     * @param params
     * @param response
     */
    public static void executeByPost(String url,
                                     String params, NetworkResponse response) {
        Log.d("wmz","wmz:executeByPost");
        StringBuffer sb = new StringBuffer();
        try {
            BufferedReader in = null;
            InputStream input = null;

            input = getInputStreamByPost(url, params, response);
            if (input == null) {
                return;
            }
            in = new BufferedReader(new InputStreamReader(input, "utf-8"));
            int len = 0;
            char buf[] = new char[4 * 1024];
            while ((len = in.read(buf)) != -1) {
                sb.append(new String(buf, 0, len));
            }
            in.close();
            response.onResponse(sb.toString());

        } catch (Exception e) {
            response.onError(e.getMessage());
        }
    }
}
