package com.studio.smartbutler.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.studio.smartbutler.R;
import com.studio.smartbutler.service.SmsService;
import com.studio.smartbutler.utils.L;
import com.studio.smartbutler.utils.PermissionUtils;
import com.studio.smartbutler.utils.SharedUtils;
import com.studio.smartbutler.utils.StaticClass;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.ui
 * file name: SettingActivity
 * creator: WindFromFarEast
 * created time: 2017/7/11 13:55
 * description: 设置
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener
{
    //语音播报开关
    private Switch switch_voice;
    //短信提醒开关
    private Switch switch_sms;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        initView();//初始化控件
    }

    private void initView()
    {
        switch_voice= (Switch) findViewById(R.id.switch_voice);
        switch_sms= (Switch) findViewById(R.id.switch_sms);
        //为各种开关设置监听事件
        switch_voice.setOnClickListener(this);
        switch_sms.setOnClickListener(this);
        //进入界面后读取之前保存的开关状态
        switch_voice.setChecked(SharedUtils.getBoolean("voice_key",false));
        switch_sms.setChecked(SharedUtils.getBoolean("sms_key",false));
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.switch_voice://语音播报开关
            {
                //改变开关状态
                switch_voice.setSelected(!switch_voice.isSelected());
                //保存开关状态
                SharedUtils.putBoolean("voice_key",switch_voice.isChecked());
                break;
            }
            case R.id.switch_sms://短信提醒开关
            {
                //改变开关状态
                switch_sms.setSelected(!switch_sms.isSelected());
                //保存开关状态
                SharedUtils.putBoolean("sms_key",switch_sms.isChecked());
                //检测开关状态，决定接受短信的服务是否开启
                if (switch_sms.isChecked())
                {
                    //在运行时权限申请前先进行Window相关权限的设置
                    if (Build.VERSION.SDK_INT>=23)
                    {
                        //若没有申请Window相关权限
                        if (!Settings.canDrawOverlays(this))
                        {
                            Intent intent=new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                            startActivityForResult(intent,StaticClass.PERMISSION_WINDOW_CODE);
                        }
                        else
                        {
                            startService(new Intent(SettingActivity.this,SmsService.class));
                        }
                    }
                    else
                    {
                        Toast.makeText(SettingActivity.this,"本应用只适配Android6.0以上",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    stopService(new Intent(SettingActivity.this,SmsService.class));
                }
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        //判断用户是否接受了权限
        if (grantResults.length>0)
        {
            switch (requestCode)
            {
                case StaticClass.PERMISSION_CODE:
                {
                    for (int result:grantResults)
                    {
                        if (result!=PackageManager.PERMISSION_GRANTED)
                        {
                            Toast.makeText(SettingActivity.this,getApplicationContext().getString(R.string.deny_permission),
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    startService(new Intent(SettingActivity.this,SmsService.class));
                    break;
                }
            }
        }
        else
        {
            Toast.makeText(SettingActivity.this,getApplicationContext().getString(R.string.deny_permission),Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        L.i("进入Window权限回调");
        switch (requestCode)
        {
            case StaticClass.PERMISSION_WINDOW_CODE:
            {
                if (Build.VERSION.SDK_INT>=23)
                {
                    if (Settings.canDrawOverlays(this))
                    {
                        L.i("用户同意了Window权限");
                        //由于SMS是危险权限,需要进行运行时权限申请
                        if (PermissionUtils.requestPermission(SettingActivity.this, Manifest.permission.RECEIVE_SMS) == 0)
                        {
                            //申请过SMS的权限,直接启动服务
                            startService(new Intent(SettingActivity.this, SmsService.class));
                        }
                    }
                }
                break;
            }
        }
    }
}
