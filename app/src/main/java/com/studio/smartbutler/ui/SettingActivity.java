package com.studio.smartbutler.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;

import com.studio.smartbutler.R;
import com.studio.smartbutler.utils.SharedUtils;

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
    private Switch switch_voice;

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
        //为语音播放开关设置监听事件
        switch_voice.setOnClickListener(this);
        //将语音播放开关状态设置为保存的状态,默认为关闭状态
        switch_voice.setChecked(SharedUtils.getBoolean("voice_key",false));
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.switch_voice:
            {
                //改变开关状态
                switch_voice.setSelected(!switch_voice.isSelected());
                //保存开关状态
                SharedUtils.putBoolean("voice_key",switch_voice.isChecked());
                break;
            }
        }
    }
}
