package com.studio.smartbutler.application;

import android.app.Application;
import android.content.Context;

import com.studio.smartbutler.utils.StaticClass;
import com.tencent.bugly.crashreport.CrashReport;

import cn.bmob.v3.Bmob;

/**
 * project name:SmartButler
 * package name:com.studio.smartbutler.application
 * file name:BaseApplication
 * creator:WindFromFarEast
 * created time:2017/7/11 10:23
 * description:Application基类，用于实现全局获取Context的功能
 */

public class BaseApplication extends Application
{
    public static Context mContext;

    @Override
    public void onCreate()
    {
        super.onCreate();
        //初始化Bugly
        CrashReport.initCrashReport(getApplicationContext(), StaticClass.BUGLY_APP_ID, true);
        //初始化Bmob
        Bmob.initialize(this, StaticClass.BMOB_APP_ID);
        //初始化上下文
        mContext=getApplicationContext();
    }

    public static Context getContext()
    {
        return mContext;
    }
}
