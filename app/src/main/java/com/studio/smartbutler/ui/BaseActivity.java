package com.studio.smartbutler.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.ui
 * file name: BaseActivity
 * creator: WindFromFarEast
 * created time: 2017/7/11 10:49
 * description: Activity基类,用来设置统一的方法,统一的接口,统一的界面等
 */

public class BaseActivity extends AppCompatActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //显示ActionBar上的返回键
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //设置菜单栏的监听，如返回键等
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            //监听返回键
            case android.R.id.home:
            {
                finish();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
