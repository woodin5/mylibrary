package com.wmz.mylibrary.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by wmz on 18/6/16.
 */
public class StringUtils {
    /**
     * 输入流转换成字符串
     *
     * @param is
     * @return
     */
    public static String inputstreamConvertToString(InputStream is) {
        StringBuffer buffer = new StringBuffer();
        byte[] date = new byte[1024];
        int len = 0;
        try {
            while ((len = is.read(date)) != -1) {
                buffer.append(new String(date, 0, len));
            }
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * 字符串转化输入流
     *
     * @param content
     * @return
     */
    public static InputStream stringConvertToInputStream(String content) {
        if (content != null && !content.trim().equals("")) {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(content.getBytes());
                return bais;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }

    /**
     * bytes[]转换成Hex字符串,可用于URL转换，IP地址转换
     *
     * @param bytes
     * @return
     */
    public static String bytesToHexString(byte[] bytes) {
        // http://stackoverflow.com/questions/332079
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    public static String map2ParamsGet(Map<String, String> map) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.append("&");
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
        }
        builder.deleteCharAt(0);
        builder.insert(0,"?");
        return builder.toString();
    }

    public static String map2ParamsPost(Map<String, String> map) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.append("&");
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue());
        }
        builder.deleteCharAt(0);
        return builder.toString();
    }
}
