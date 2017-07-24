package com.studio.smartbutler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.studio.smartbutler.R;
import com.studio.smartbutler.application.BaseApplication;
import com.studio.smartbutler.entity.Courier;

import java.util.List;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.adapter
 * file name: CourierAdapter
 * creator: WindFromFarEast
 * created time: 2017/7/23 11:41
 * description: 物流页面ListView适配器
 */

public class CourierAdapter extends BaseAdapter
{
    //数据源
    private List<Courier> mList;

    public CourierAdapter(List<Courier> mList)
    {
        this.mList=mList;
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
        ViewHolder viewHolder;
        if (convertView == null)
        {
            //第一次加载
            viewHolder=new ViewHolder();
            convertView = LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.layout_courier_item, null);
            viewHolder.tv_datatime= (TextView) convertView.findViewById(R.id.tv_datatime);
            viewHolder.tv_remark= (TextView) convertView.findViewById(R.id.tv_remark);
            //设置缓存
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        //显示物流信息
        viewHolder.tv_datatime.setText(mList.get(position).getDatatime().toString());
        viewHolder.tv_remark.setText(mList.get(position).getRemark().toString());
        return convertView;
    }

    class ViewHolder
    {
        private TextView tv_datatime;
        private TextView tv_remark;
    }
}
