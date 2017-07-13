package com.studio.smartbutler.utils;

import android.util.Log;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.utils
 * file name: L
 * creator: WindFromFarEast
 * created time: 2017/7/12 10:28
 * description: Log封装类
 */

public class L
{
    //是否允许打印的开关
    public static final boolean DEBUG = true;

    //TAG
    public static final String TAG = "SmartButler";

    //Log的五个等级 idwef
    public static void i(String text)
    {
        if (DEBUG)
        {
            Log.i(TAG,text);
        }
    }

    public static void d(String text)
    {
        if (DEBUG)
        {
            Log.d(TAG,text);
        }
    }

    public static void w(String text)
    {
        if (DEBUG)
        {
            Log.w(TAG,text);
        }
    }

    public static void e(String text)
    {
        if (DEBUG)
        {
            Log.e(TAG,text);

        }
    }
}
