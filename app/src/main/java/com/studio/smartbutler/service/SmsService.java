package com.studio.smartbutler.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.IBinder;
import android.telephony.SmsMessage;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.studio.smartbutler.R;
import com.studio.smartbutler.utils.L;
import com.studio.smartbutler.utils.StaticClass;
import com.studio.smartbutler.view.DispatchLinearLayout;

//接受短信服务
public class SmsService extends Service
{
    //短信广播接收器
    private SmsReceiver smsReceiver;
    //短信发件人
    private String sender;
    //短信内容
    private String smsContent;
    //窗口管理器
    private WindowManager windowManager;
    //窗口布局参数
    private WindowManager.LayoutParams params;
    //窗口布局
    private DispatchLinearLayout windowView;
    //窗口布局控件
    private TextView tv_sms_sender;
    private TextView tv_sms_content;
    private Button btn_sms_send;
    //
    private DispatchLinearLayout.DispatchKeyEventListener dispatchKeyEventListener;

    @Override
    public void onCreate()
    {
        super.onCreate();
        L.i("服务创建");
        //初始化广播接收器
        init();
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        L.i("服务关闭");
        //注销广播接收器
        unregisterReceiver(smsReceiver);
    }

    private void init()
    {
        //完成广播接收器要注册的广播
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(StaticClass.SMS_ACTION);
        intentFilter.setPriority(Integer.MAX_VALUE);//设置优先级
        //为广播接收器注册广播
        smsReceiver= new SmsReceiver();
        registerReceiver(smsReceiver,intentFilter);
        L.i("广播接收器已注册");
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    //接受短信的广播接收器
    public class SmsReceiver extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            /**
             * 接收到注册的广播后执行的逻辑:
             * 1、获取短信内容和发信人
             * 2、弹出短信提示框
             */
            L.i("已接收到广播");
            //得到广播的action
            String action=intent.getAction();
            //判断接收到的广播的Action
            if (action.equals(StaticClass.SMS_ACTION))
            {
                //若是短信广播的Action则获取短信发信人和内容
                L.i("收到短信");
                //提取短信数据
                Object[] objects= (Object[]) intent.getExtras().get("pdus");
                for (Object object:objects)//遍历数组得到短信数据
                {
                    //把数组元素转换成短信对象
                    SmsMessage message=SmsMessage.createFromPdu((byte[]) object);
                    //发件人
                    sender=message.getOriginatingAddress();
                    //短信内容
                    smsContent=message.getMessageBody();
                    L.i("短信发件人:"+sender+"\n"+"短信内容:"+smsContent);
                    //显示短信提示窗口
                    showSmsWindow();
                }
            }
        }
    }

    //显示短信提示窗口
    private void showSmsWindow()
    {
        //获取WindowManager实例
        windowManager= (WindowManager) getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        //获取窗口布局参数实例
        params=new WindowManager.LayoutParams();
        //设置布局参数
        params.width=WindowManager.LayoutParams.MATCH_PARENT;//宽
        params.height=WindowManager.LayoutParams.MATCH_PARENT;//高
        //flags属性可以设置窗口的显示特性
        params.flags=WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH;
        //设置半透明格式
        params.format= PixelFormat.TRANSLUCENT;
        //设置窗口类型
        params.type=WindowManager.LayoutParams.TYPE_PHONE;
        //获取窗口布局实例
        windowView= (DispatchLinearLayout) View.inflate(this, R.layout.item_sms,null);
        //初始化布局控件
        initView();
        //显示短信提示窗口
        windowManager.addView(windowView,params);
        //为窗口布局添加分发事件监听器
        windowView.setDispatchKeyEventListener(new DispatchLinearLayout.DispatchKeyEventListener()
        {
            @Override
            public boolean handleKeyEvent(KeyEvent event)
            {
                //判断是否按返回键
                if (event.getKeyCode()==KeyEvent.KEYCODE_BACK)
                {
                    //按了返回键则关闭window
                    if (windowView.getParent()!=null)
                    {
                        windowManager.removeView(windowView);
                        return true;
                    }
                }
                return false;
            }
        });
    }

    //初始化布局控件
    private void initView()
    {
        tv_sms_sender= (TextView) windowView.findViewById(R.id.tv_sms_sender);
        tv_sms_content= (TextView) windowView.findViewById(R.id.tv_sms_content);
        btn_sms_send= (Button) windowView.findViewById(R.id.btn_sms_send);
        //将发信人和短信内容显示在窗口中
        tv_sms_sender.setText("发件人——"+sender);
        tv_sms_content.setText(smsContent);
        //为短信发送按钮设置监听器
        btn_sms_send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (v.getId()==R.id.btn_sms_send)
                {
                    //发送短信
                    sendSms();
                    //关闭窗口
                    windowManager.removeView(windowView);
                }
            }
        });
    }

    //发送短信
    private void sendSms()
    {
        Intent intent=new Intent(Intent.ACTION_SENDTO,Uri.parse("smsto:"+sender));
        //设置启动模式
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("sms_body","");
        //前往发送短信界面
        startActivity(intent);
    }

}
