package com.studio.smartbutler.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.smartbutler.R;
import com.studio.smartbutler.utils.UtilTools;

import java.util.ArrayList;
import java.util.List;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.ui
 * file name: GuideActivity
 * creator: WindFromFarEast
 * created time: 2017/7/12 13:22
 * description: 引导页
 */

public class GuideActivity extends AppCompatActivity
{
    //ViewPager，用来实现引导页三个图片之间的滑动
    private ViewPager mViewPager;
    //ViewPager适配器数据源
    private List<View> mList= new ArrayList<>();
    //用来显示图片的三个View
    private View view1,view2,view3;
    //下方滑动标记的点
    private ImageView point1,point2,point3;
    //进入主页图标和跳过引导页图标
    private Button btn_start,btn_leap;
    //显示图片下方文字
    private TextView tv_guide_one,tv_guide_two,tv_guide_three;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        //初始化控件
        initView();
    }

    private void initView()
    {
        //获取各种控件实例
        mViewPager= (ViewPager) findViewById(R.id.mViewPager);
        point1= (ImageView) findViewById(R.id.point1);
        point2= (ImageView) findViewById(R.id.point2);
        point3= (ImageView) findViewById(R.id.point3);
        tv_guide_one= (TextView) findViewById(R.id.tv_guide_one);
        tv_guide_two= (TextView) findViewById(R.id.tv_guide_two);
        tv_guide_three= (TextView) findViewById(R.id.tv_guide_three);
        //由于初进入是显示引导页第一页，因此下方三个点的第一个点需要为亮色
        setPointImg(0);
        //获取跳过按钮实例，并设置监听事件
        btn_leap= (Button) findViewById(R.id.btn_leap);
        btn_leap.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(GuideActivity.this,LoginActivity.class));
                //进入主页后关闭引导页
                finish();
            }
        });
        //实例化三个View
        LayoutInflater inflater=LayoutInflater.from(this);
        view1= inflater.inflate(R.layout.guide_one,null);
        view2= inflater.inflate(R.layout.guide_two,null);
        view3= inflater.inflate(R.layout.guide_three,null);
        //获取三个文字控件的实例化
        tv_guide_one= (TextView) view1.findViewById(R.id.tv_guide_one);
        tv_guide_two= (TextView) view2.findViewById(R.id.tv_guide_two);
        tv_guide_three= (TextView) view3.findViewById(R.id.tv_guide_three);
        //修改引导页文字风格
        UtilTools.setTypeface(tv_guide_one);
        UtilTools.setTypeface(tv_guide_two);
        UtilTools.setTypeface(tv_guide_three);
        //将View加入ViewPager适配器的数据源集合
        mList.add(view1);
        mList.add(view2);
        mList.add(view3);
        //为ViewPager设置适配器
        mViewPager.setAdapter(new PagerAdapter()
        {
            @Override
            public int getCount()
            {
                return mList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object)
            {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position)
            {
                container.addView(mList.get(position));
                return mList.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object)
            {
                container.removeView(mList.get(position));
            }
        });
        //监听ViewPager滑动事件
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {
                //滑动到第几页就将下方小圆点设置为相应的点数
                setPointImg(position);
                hideOrShowLeap(position);
            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });

        //获取进入主页按钮的实例，由于这个按钮属于View3，因此一定要放在View3实例化后，并且要用view3的实例对象来获取按钮的实例
        btn_start= (Button) view3.findViewById(R.id.btn_start);
        //设置进入主页按钮的监听器
        btn_start.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                //进入主页后关闭引导页
                finish();
            }
        });
    }

    //设置下方小圆点图片
    private void setPointImg(int position)
    {
        if (position==0)
        {
            point1.setBackgroundResource(R.drawable.point_on);
            point2.setBackgroundResource(R.drawable.point_off);
            point3.setBackgroundResource(R.drawable.point_off);
        }
        if (position==1)
        {
            point1.setBackgroundResource(R.drawable.point_off);
            point2.setBackgroundResource(R.drawable.point_on);
            point3.setBackgroundResource(R.drawable.point_off);
        }
        if (position==2)
        {
            point1.setBackgroundResource(R.drawable.point_off);
            point2.setBackgroundResource(R.drawable.point_off);
            point3.setBackgroundResource(R.drawable.point_on);
        }
    }

    //显示或隐藏跳过按钮
    private void hideOrShowLeap(int position)
    {
        if (position==0)
        {
            btn_leap.setVisibility(View.VISIBLE);
        }
        if (position==1)
        {
            btn_leap.setVisibility(View.VISIBLE);
        }
        if (position==2)
        {
            btn_leap.setVisibility(View.GONE);
        }
    }
}
