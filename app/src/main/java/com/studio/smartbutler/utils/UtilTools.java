package com.studio.smartbutler.utils;

import android.graphics.Typeface;
import android.widget.TextView;

import com.studio.smartbutler.application.BaseApplication;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.utils
 * file name: UtilTools
 * creator: WindFromFarEast
 * created time: 2017/7/11 10:57
 * description: 常用工具类
 */

public class UtilTools
{
    //设置字体
    public static void setTypeface(TextView textView)
    {
        Typeface typeface=Typeface.createFromAsset(BaseApplication.getContext().getAssets(),"fonts/FONT.TTF");
        textView.setTypeface(typeface);
    }
}
