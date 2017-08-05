package com.studio.smartbutler.ui;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.Setting;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.studio.smartbutler.R;
import com.studio.smartbutler.service.SmsService;
import com.studio.smartbutler.utils.L;
import com.studio.smartbutler.utils.PermissionUtils;
import com.studio.smartbutler.utils.SharedUtils;
import com.studio.smartbutler.utils.StaticClass;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import org.json.JSONException;
import org.json.JSONObject;


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
    //检查版本更新
    private LinearLayout ll_check_version;
    //版本名显示
    private TextView tv_version_name;
    //二维码扫描项
    private LinearLayout ll_scan_qrcode;
    //生成我的二维码项
    private LinearLayout ll_my_qrcode;
    //当前版本号
    private int currentVersionCode;
    //当前版本名
    private String currentVersionName;
    //最新版本号
    private int latestVerisonCode;
    private String latestVersionName;
    //最新版本下载地址
    private String latestDownloadUrl;

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
        ll_check_version= (LinearLayout) findViewById(R.id.ll_check_version);
        tv_version_name= (TextView) findViewById(R.id.tv_version_name);
        ll_scan_qrcode= (LinearLayout) findViewById(R.id.ll_scan_qrcode);
        ll_my_qrcode= (LinearLayout) findViewById(R.id.ll_my_qrcode);
        //获取当前版本信息显示在界面上
        showCurrentVersionInfo();
        //为各种开关设置监听事件
        switch_voice.setOnClickListener(this);
        switch_sms.setOnClickListener(this);
        ll_check_version.setOnClickListener(this);
        ll_scan_qrcode.setOnClickListener(this);
        ll_my_qrcode.setOnClickListener(this);
        //进入界面后读取之前保存的开关状态
        switch_voice.setChecked(SharedUtils.getBoolean("voice_key",false));
        switch_sms.setChecked(SharedUtils.getBoolean("sms_key",false));
    }

    //显示当前版本号
    private void showCurrentVersionInfo()
    {
        try
        {
            PackageManager packageManager=getPackageManager();
            PackageInfo info=packageManager.getPackageInfo(getPackageName(),0);
            currentVersionCode=info.versionCode;
            currentVersionName=info.versionName;
            tv_version_name.setText("当前版本："+currentVersionName);

        }
        catch (PackageManager.NameNotFoundException e)
        {
            e.printStackTrace();
        }
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
            case R.id.ll_check_version:
            {
                //先进行运行时权限判断,获取写内存的权限
                if(PermissionUtils.requestPermission(SettingActivity.this,StaticClass.WRITE_EXTERNAL_STORAGE_CODE
                        ,Manifest.permission.WRITE_EXTERNAL_STORAGE)==0)
                {
                    //如果申请过读写内存的权限则检测最新版本
                    searchLatestVersion();
                }
                break;
            }
            case R.id.ll_scan_qrcode:
            {
                Intent openCameraIntent=new Intent(this,CaptureActivity.class);
                startActivityForResult(openCameraIntent,StaticClass.SCAN_QRCODE_REQUEST_CODE);
                break;
            }
            case R.id.ll_my_qrcode:
            {
                Intent intent=new Intent(this,MyQRCodeActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    private void searchLatestVersion()
    {
        //检测是否为最新版本
        RxVolley.get(StaticClass.LATEST_VERSION_INFO_URL, new HttpCallback()
        {
            @Override
            public void onSuccess(String response)
            {
                //成功拿到最新版本信息后解析Json
                parseJson(response);
                //判断当前版本是否为最新版本
                if (latestVerisonCode==currentVersionCode)
                {
                    //是最新版本则弹出提示
                    Toast.makeText(SettingActivity.this,getString(R.string.is_latest_version),Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //不是最新版本则弹出Dialog询问用户是否更新到最新版本
                    showVersionDialog();
                }
            }
        });
    }

    //解析服务器上最新版本Json信息,获得最新版本的版本号和版本名以及下载地址
    private void parseJson(String response)
    {
        try
        {
            JSONObject jsonObject=new JSONObject(response);
            latestVerisonCode=jsonObject.getInt("versionCode");
            latestVersionName=jsonObject.getString("versionName");
            latestDownloadUrl=jsonObject.getString("url");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    //询问用户是否更新到最新版本的Dialog
    private void showVersionDialog()
    {
        AlertDialog.Builder builder=new AlertDialog.Builder(SettingActivity.this);
        builder.setTitle(getString(R.string.new_version_existed));
        builder.setMessage(getString(R.string.update_or_not));
        builder.setPositiveButton(getString(R.string.update_dialog_positive), new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                //用户点击更新按钮则跳转到更新界面
                Intent intent=new Intent(SettingActivity.this,UpdateActivity.class);
                intent.putExtra("downloadUrl",latestDownloadUrl);
                startActivity(intent);
            }
        });
        builder.setNegativeButton(getString(R.string.update_dialog_nagetive),null);
        //显示Dialog
        builder.create().show();
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
                case StaticClass.WRITE_EXTERNAL_STORAGE_CODE:
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
                    searchLatestVersion();
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
                        if (PermissionUtils.requestPermission(SettingActivity.this,StaticClass.PERMISSION_CODE
                                ,Manifest.permission.RECEIVE_SMS) == 0)
                        {
                            //申请过SMS的权限,直接启动服务
                            startService(new Intent(SettingActivity.this, SmsService.class));
                        }
                    }
                }
                break;
            }
            case StaticClass.SCAN_QRCODE_REQUEST_CODE:
            {
                if (resultCode==RESULT_OK)
                {
                    //扫描二维码成功
                    Bundle bundle = data.getExtras();
                    String scanResult = bundle.getString("result");//获取扫描二维码得到的内容
                    L.i("二维码结果:"+scanResult);
                }
            }
        }
    }
}
