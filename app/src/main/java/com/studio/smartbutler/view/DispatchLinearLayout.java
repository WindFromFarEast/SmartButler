package com.studio.smartbutler.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.widget.LinearLayout;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.view
 * file name: DispatchLinearLayout
 * creator: WindFromFarEast
 * created time: 2017/8/2 12:26
 * description: 为了完成事件分发（监听返回键关闭Window）的布局
 */

public class DispatchLinearLayout extends LinearLayout
{
    private DispatchKeyEventListener dispatchKeyEventListener;

    //重写三种构造方法
    public DispatchLinearLayout(Context context)
    {
        super(context);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs)
    {
        super(context, attrs);
    }

    public DispatchLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    //getter和setter方法
    public DispatchKeyEventListener getDispatchKeyEventListener()
    {
        return dispatchKeyEventListener;
    }
    public void setDispatchKeyEventListener(DispatchKeyEventListener dispatchKeyEventListener)
    {
        this.dispatchKeyEventListener = dispatchKeyEventListener;
    }

    //监听分发事件的接口
    public interface DispatchKeyEventListener
    {
        //处理KeyEvent的回调方法
        boolean handleKeyEvent(KeyEvent event);
    }

    //分发KeyEvent的回调方法
    @Override
    public boolean dispatchKeyEvent(KeyEvent event)
    {
        if (dispatchKeyEventListener!=null)
        {
            //接口不为空说明要处理分发事件
            return dispatchKeyEventListener.handleKeyEvent(event);
        }
        return super.dispatchKeyEvent(event);
    }

}
