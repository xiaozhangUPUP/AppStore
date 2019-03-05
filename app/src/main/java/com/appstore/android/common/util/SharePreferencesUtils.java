package com.appstore.android.common.util;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


import com.appstore.android.MyApplication;

import javax.inject.Inject;

public class SharePreferencesUtils {


    /**
     * 保存String参数
     *
     * @param key
     * @param value
     */
    public static void saveString(String key, String value) {
        SharedPreferences sharedPreferences =
                MyApplication.getContext().getSharedPreferences("appstore", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 获取各项String配置参数
     *
     * @param key
     * @return
     */
    public static String getStringByKey(String key) {
        SharedPreferences sharedPreferences =
                MyApplication.getContext().getSharedPreferences("appstore", Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    /**
     * 保存boolean类型参数
     *
     * @param key
     * @param value
     */
    public static void saveBoolean(String key, boolean value) {
        SharedPreferences sharedPreferences =
                MyApplication.getContext().getSharedPreferences("adts", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    /**
     * 获取boolean类型参数
     *
     * @param key
     * @return
     */
    public static boolean getBooleanByKey(String key) {
        SharedPreferences sharedPreferences =
                MyApplication.getContext().getSharedPreferences("adts", Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }


    /**
     * 删除某个key的值
     *
     * @param key
     * @return
     */
    public static void deleteByKey(String key) {
        SharedPreferences sharedPreferences =
                MyApplication.getContext().getSharedPreferences("appstore", Context.MODE_PRIVATE);
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

}
