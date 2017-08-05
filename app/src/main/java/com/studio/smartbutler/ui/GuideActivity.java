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
    private ViewPager mViewPager;
    private List<View> mList= new ArrayList<>();
    private View view1,view2,view3;
    private ImageView point1,point2,point3;
    private Button btn_start,btn_leap;
    private TextView tv_guide_one,tv_guide_two,tv_guide_three;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        initView();
    }

    private void initView()
    {
        mViewPager= (ViewPager) findViewById(R.id.mViewPager);

        point1= (ImageView) findViewById(R.id.point1);
        point2= (ImageView) findViewById(R.id.point2);
        point3= (ImageView) findViewById(R.id.point3);

        tv_guide_one= (TextView) findViewById(R.id.tv_guide_one);
        tv_guide_two= (TextView) findViewById(R.id.tv_guide_two);
        tv_guide_three= (TextView) findViewById(R.id.tv_guide_three);

        setPointImg(0);

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

        LayoutInflater inflater=LayoutInflater.from(this);
        view1= inflater.inflate(R.layout.guide_one,null);
        view2= inflater.inflate(R.layout.guide_two,null);
        view3= inflater.inflate(R.layout.guide_three,null);

        tv_guide_one= (TextView) view1.findViewById(R.id.tv_guide_one);
        tv_guide_two= (TextView) view2.findViewById(R.id.tv_guide_two);
        tv_guide_three= (TextView) view3.findViewById(R.id.tv_guide_three);

        //修改引导页文字
        UtilTools.setTypeface(tv_guide_one);
        UtilTools.setTypeface(tv_guide_two);
        UtilTools.setTypeface(tv_guide_three);

        mList.add(view1);
        mList.add(view2);
        mList.add(view3);

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

    //设置小圆点图片
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
