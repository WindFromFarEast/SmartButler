package com.studio.smartbutler.view;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;
import android.view.WindowManager;


/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.view
 * file name: CustomDialog
 * creator: WindFromFarEast
 * created time: 2017/7/30 14:38
 * description: 自定义Dialog
 */

public class CustomDialog extends Dialog
{
    //构造方法
    public CustomDialog(Context context,int width,int height,int layout,int style,int gravity,int anim)
    {
        super(context, style);
        //设置属性
        setContentView(layout);//布局
        Window window=getWindow();
        WindowManager.LayoutParams layoutParams =window.getAttributes();
        layoutParams.width=width;//布局宽
        layoutParams.height=height;//布局高
        layoutParams.gravity=gravity;//布局位置
        window.setAttributes(layoutParams);
        window.setWindowAnimations(anim);//动画
    }
}
