package com.studio.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.studio.smartbutler.MainActivity;
import com.studio.smartbutler.R;
import com.studio.smartbutler.utils.SharedUtils;
import com.studio.smartbutler.utils.StaticClass;
import com.studio.smartbutler.utils.UtilTools;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.ui
 * file name: SplashActivity
 * creator: WindFromFarEast
 * created time: 2017/7/12 12:59
 * description: 闪屏页
 */

public class SplashActivity extends AppCompatActivity
{
    /**
     * 闪屏页逻辑
     * 1、延时2000ms
     * 2、判断APP是否第一次运行
     * 3、自定义字体
     * 4、Activity全屏主题
     */
    private TextView tv_splash;

    private Handler handler=new Handler()
    {
        @Override
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                case StaticClass.HANDLER_SPLASH:
                {
                    //判断是否第一次启动App,是就跳转到引导页,不是就跳转到主页
                    if (isFirst())
                    {
                        startActivity(new Intent(SplashActivity.this, GuideActivity.class));
                    }
                    else
                    {
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                    }
                    finish();
                    break;
                }
            }
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
    }

    //初始化View
    private void initView()
    {
        //延迟3000ms后发送消息
        handler.sendEmptyMessageDelayed(StaticClass.HANDLER_SPLASH,3000);

        tv_splash= (TextView) findViewById(R.id.tv_splash);

        //设置字体
        UtilTools.setTypeface(tv_splash);
    }

    //判断App是否第一次运行
    private boolean isFirst()
    {
        Boolean isFirst=SharedUtils.getBoolean(StaticClass.FIRST_RUN,true);
        if (isFirst)
        {
            SharedUtils.putBoolean(StaticClass.FIRST_RUN,false);
            return true;
        }
        else
        {
            return false;
        }
    }
}
