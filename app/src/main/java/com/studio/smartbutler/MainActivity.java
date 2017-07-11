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
import com.studio.smartbutler.ui.SettingActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FloatingActionButton fab_setting;
    private List<String> mTabTitles;
    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //去掉ActionBar的阴影
        getSupportActionBar().setElevation(0);
        initData();
        initView();
    }

    //数据初始化
    private void initData()
    {
        mTabTitles=new ArrayList<>();
        mTabTitles.add(this.getString(R.string.tab_butler));
        mTabTitles.add(this.getString(R.string.tab_wechat));
        mTabTitles.add(this.getString(R.string.tab_girl));
        mTabTitles.add(this.getString(R.string.tab_user));

        mFragments=new ArrayList<>();
        mFragments.add(new ButlerFragment());
        mFragments.add(new WechatFragment());
        mFragments.add(new GirlFragment());
        mFragments.add(new UserFragment());
    }

    //初始化View
    private void initView()
    {
        mTabLayout= (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager= (ViewPager) findViewById(R.id.mViewPager);
        fab_setting= (FloatingActionButton) findViewById(R.id.fab_setting);

        //首先隐藏悬浮按钮
        fab_setting.setVisibility(View.GONE);

        //预加载ViewPager
        mViewPager.setOffscreenPageLimit(mFragments.size());

        //设置ViewPager的适配器
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

        fab_setting.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
            }
        });

        //监听ViewPager的滑动事件，控制悬浮按钮的隐藏与显示
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
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
    }
}
