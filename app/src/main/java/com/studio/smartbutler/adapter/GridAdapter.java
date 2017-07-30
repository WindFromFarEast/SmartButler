package com.studio.smartbutler.adapter;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.studio.smartbutler.R;
import com.studio.smartbutler.application.BaseApplication;
import com.studio.smartbutler.entity.Girl;
import com.studio.smartbutler.utils.PicassoUtils;

import java.util.List;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.adapter
 * file name: GridAdapter
 * creator: WindFromFarEast
 * created time: 2017/7/30 11:47
 * description: 美女社区GridView适配器
 */

public class GridAdapter extends BaseAdapter
{
    //数据源
    private List<Girl> mList;
    //
    private WindowManager windowManager;
    //屏幕宽度
    private int width;
    //屏幕高度
    private int height;

    //构造方法
    public GridAdapter(List<Girl> mList)
    {
        this.mList=mList;
        //获取屏幕宽度
        windowManager= (WindowManager) BaseApplication.getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display=windowManager.getDefaultDisplay();
        Point size=new Point();
        display.getSize(size);
        width=size.x;
        height=size.y;
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder = null;
        if (convertView == null)
        {
            //第一次初始化
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_girl, null);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        //获取美女图片地址
        String imgUrl=mList.get(position).getImgUrl();
        //利用Picasso加载图片
        PicassoUtils.loadImgWithSize(imgUrl,viewHolder.imageView,width/2,height/5);
        return convertView;
    }

    //缓存控件
    class ViewHolder
    {
        private ImageView imageView;
    }
}
