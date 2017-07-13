package com.studio.smartbutler.utils;

import android.content.SharedPreferences;

import com.studio.smartbutler.application.BaseApplication;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.utils
 * file name: SharedUtils
 * creator: WindFromFarEast
 * created time: 2017/7/12 10:54
 * description: SharedPreferences封装类
 */

public class SharedUtils
{
    //文件名
    public static final String NAME = "config";

    //存储和获取功能
    public static void putString(String key,String value)
    {
        SharedPreferences sp= BaseApplication.getContext().getSharedPreferences(NAME, BaseApplication.getContext().MODE_PRIVATE);
        sp.edit().putString(key,value).commit();
    }

    public static String getString(String key,String defValue)
    {
        SharedPreferences sp= BaseApplication.getContext().getSharedPreferences(NAME, BaseApplication.getContext().MODE_PRIVATE);
        return sp.getString(key,defValue);
    }

    public static void putInt(String key,int value)
    {
        SharedPreferences sp= BaseApplication.getContext().getSharedPreferences(NAME, BaseApplication.getContext().MODE_PRIVATE);
        sp.edit().putInt(key,value).commit();
    }

    public static int getInt(String key,int defValue)
    {
        SharedPreferences sp= BaseApplication.getContext().getSharedPreferences(NAME, BaseApplication.getContext().MODE_PRIVATE);
        return sp.getInt(key,defValue);
    }

    public static void putBoolean(String key,Boolean value)
    {
        SharedPreferences sp= BaseApplication.getContext().getSharedPreferences(NAME, BaseApplication.getContext().MODE_PRIVATE);
        sp.edit().putBoolean(key,value).commit();
    }

    public static Boolean getBoolean(String key,Boolean defValue)
    {
        SharedPreferences sp= BaseApplication.getContext().getSharedPreferences(NAME, BaseApplication.getContext().MODE_PRIVATE);
        return sp.getBoolean(key,defValue);
    }

    //删除单个记录功能
    public static void delete(String key)
    {
        SharedPreferences sp= BaseApplication.getContext().getSharedPreferences(NAME, BaseApplication.getContext().MODE_PRIVATE);
        sp.edit().remove(key).commit();
    }

    //删除全部记录功能
    public static void deleteAll()
    {
        SharedPreferences sp= BaseApplication.getContext().getSharedPreferences(NAME, BaseApplication.getContext().MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
