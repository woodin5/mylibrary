package com.wmz.mylibrary.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by wmz on 20/6/16.
 */
public class SharedPreferencesUtils {

    /**
     * 保存Set集合key value
     * @param mContext
     * @param name
     * @param key
     * @param value
     */
    public static void putString(Context mContext, String name,String key,Set<String> value) {
        SharedPreferences spf = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.putStringSet(key,value);
        editor.commit();
    }
    /**
     * 保存map集合key,value
     * @param mContext
     * @param name
     * @param map
     */
    public static void putString(Context mContext, String name, Map<String, String> map) {
        SharedPreferences spf = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        for(Map.Entry<String,String> entry:map.entrySet()){
            editor.putString(entry.getKey(),entry.getValue());
        }
        editor.commit();
    }

    /**
     * 保存单个key,value
     * @param mContext
     * @param name
     * @param key
     * @param value
     */
    public static void putString(Context mContext,String name,String key,String value){
        SharedPreferences spf = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        spf.edit().putString(key,value).commit();
    }

    /**
     * 保存单个key,value
     * @param mContext
     * @param name
     * @param key
     * @param value
     */
    public static void putBoolean(Context mContext,String name,String key,Boolean value){
        SharedPreferences spf = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        spf.edit().putBoolean(key, value).commit();
    }
    /**
     * 获取Set<String>集合的值
     * @param mContext
     * @param name
     * @param key
     * @return
     */
    public static Set<String> getStrings(Context mContext, String name, String key){
        SharedPreferences spf = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return spf.getStringSet(key,new HashSet<String>());
    }
    /**
     * 获取String值
     * @param mContext
     * @param name
     * @param key
     * @return
     */
    public static String getString(Context mContext, String name, String key) {
        SharedPreferences spf = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return spf.getString(key, "");
    }

    /**
     * 获取Boolean值
     * @param mContext
     * @param name
     * @param key
     * @return
     */
    public static Boolean getBoolean(Context mContext, String name, String key, boolean defValue) {
        SharedPreferences spf = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return spf.getBoolean(key, defValue);
    }
    /**
     * 清空name文件里所有数据
     * @param mContext
     * @param name
     */
    public static void clear(Context mContext, String name) {
        SharedPreferences spf = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        spf.edit().clear().commit();
    }

    /**
     * 删除key值
     * @param mContext
     * @param name
     * @param key
     */
    public static void remove(Context mContext, String name, String key) {
        SharedPreferences spf = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        spf.edit().remove(key).commit();
    }

    /**
     * 是否存在key值
     * @param mContext
     * @param name
     * @param key
     * @return
     */
    public static boolean hasKey(Context mContext, String name, String key) {
        SharedPreferences sp = mContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sp.contains(key);
    }
}
