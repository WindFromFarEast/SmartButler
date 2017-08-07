package com.studio.smartbutler;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.studio.smartbutler.fragment.ButlerFragment;
import com.studio.smartbutler.fragment.GirlFragment;
import com.studio.smartbutler.fragment.UserFragment;
import com.studio.smartbutler.fragment.WechatFragment;
import com.studio.smartbutler.service.SmsService;
import com.studio.smartbutler.ui.SettingActivity;
import com.studio.smartbutler.utils.SharedUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.utils
 * file name: MainActivity
 * creator: WindFromFarEast
 * created time: 2017/7/11 10:57
 * description: App主界面，包含四个Fragment：智能管家、微信精选、美女社区和个人中心
 */

public class MainActivity extends AppCompatActivity
{
    //用来显示四个Fragment的标签
    private TabLayout mTabLayout;
    //ViewPager，包含四个Fragment，用来实现四个Fragment之间滑动
    private ViewPager mViewPager;
    //悬浮设置按钮
    private FloatingActionButton fab_setting;
    //存储Fragment标签名称，ViewPager适配器的数据源之一
    private List<String> mTabTitles;
    //存储Fragment，ViewPager适配器的数据源之一
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //去掉ActionBar的阴影
        getSupportActionBar().setElevation(0);
        //初始化数据
        initData();
        initView();
    }

    //数据初始化
    private void initData()
    {
        //添加Fragment名称
        mTabTitles=new ArrayList<>();
        mTabTitles.add(this.getString(R.string.tab_butler));
        mTabTitles.add(this.getString(R.string.tab_wechat));
        mTabTitles.add(this.getString(R.string.tab_girl));
        mTabTitles.add(this.getString(R.string.tab_user));

        //添加四个Fragment进集合
        mFragments=new ArrayList<>();
        mFragments.add(new ButlerFragment());
        mFragments.add(new WechatFragment());
        mFragments.add(new GirlFragment());
        mFragments.add(new UserFragment());
    }

    //初始化View
    private void initView()
    {
        //获取界面上各种控件实例
        mTabLayout= (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager= (ViewPager) findViewById(R.id.mViewPager);
        fab_setting= (FloatingActionButton) findViewById(R.id.fab_setting);
        //首先隐藏悬浮按钮
        fab_setting.setVisibility(View.GONE);
        //预加载ViewPager页数
        mViewPager.setOffscreenPageLimit(mFragments.size());
        //设置ViewPager的适配器，由于是与Fragment的搭配因此使用FragmentPagerAdapter
        mViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
        {
            //返回选中的item
            @Override
            public Fragment getItem(int position)
            {
                return mFragments.get(position);
            }

            //返回item的个数
            @Override
            public int getCount()
            {
                return mFragments.size();
            }

            //设置item标题
            @Override
            public CharSequence getPageTitle(int position)
            {
                return mTabTitles.get(position);
            }
        });
        //绑定TabLayout和ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
        //为悬浮设置按钮设置监听监听器
        fab_setting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
            }
        });
        //监听ViewPager的滑动事件，控制悬浮设置按钮的隐藏与显示
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                //在第一页就隐藏设置按钮
                if (position==0)
                {
                    fab_setting.setVisibility(View.GONE);
                }
                else
                {
                    fab_setting.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

        //若是短信提醒功能是开启状态则直接开启短信提醒服务
        if (SharedUtils.getBoolean("sms_key",false))
        {
            startService(new Intent(MainActivity.this,SmsService.class));
        }
    }
}
