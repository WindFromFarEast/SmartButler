package com.studio.smartbutler.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.studio.smartbutler.R;
import com.studio.smartbutler.application.BaseApplication;
import com.studio.smartbutler.entity.ChattingText;


import java.util.List;

import static com.studio.smartbutler.utils.StaticClass.TYPE_LEFT_TEXT;
import static com.studio.smartbutler.utils.StaticClass.TYPE_RIGHT_TEXT;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.adapter
 * file name: ChattingAdapter
 * creator: WindFromFarEast
 * created time: 2017/7/26 11:38
 * description: 聊天ListView适配器
 */

public class ChattingAdapter extends BaseAdapter
{
    //数据源
    private List<ChattingText> mList;

    //适配器构造方法
    public ChattingAdapter(List<ChattingText> mList)
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
        //获取文本type
        int type=getItemViewType(position);
        ViewHolderLeftText viewHolderLeftText=null;
        ViewHolderRightText viewHolderRightText=null;
        //第一次加载
        if (convertView==null)
        {
            switch (type)
            {
                case TYPE_LEFT_TEXT:
                {
                    //文本Type类型为左边
                    viewHolderLeftText=new ViewHolderLeftText();
                    convertView= LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_text_left,null);
                    viewHolderLeftText.tv_left= (TextView) convertView.findViewById(R.id.tv_left);
                    //设置ViewHolder缓存
                    convertView.setTag(viewHolderLeftText);
                    break;
                }
                case TYPE_RIGHT_TEXT:
                {
                    //文本Type类型为右边
                    viewHolderRightText=new ViewHolderRightText();
                    convertView= LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_text_right,null);
                    viewHolderRightText.tv_right= (TextView) convertView.findViewById(R.id.tv_right);
                    //设置ViewHolder缓存
                    convertView.setTag(viewHolderRightText);
                    break;
                }
            }
        }
        else
        {
            switch (type)
            {
                case TYPE_LEFT_TEXT:
                {
                    //文本Type类型为左边
                    viewHolderLeftText= (ViewHolderLeftText) convertView.getTag();
                    break;
                }
                case TYPE_RIGHT_TEXT:
                {
                    //文本Type类型为右边
                    viewHolderRightText= (ViewHolderRightText) convertView.getTag();
                    break;
                }
            }
        }
        switch (type)
        {
            case TYPE_LEFT_TEXT:
            {
                //显示消息
                viewHolderLeftText.tv_left.setText(mList.get(position).getText().toString());
                break;
            }
            case TYPE_RIGHT_TEXT:
            {
                //显示消息
                viewHolderRightText.tv_right.setText(mList.get(position).getText().toString());
                break;
            }
        }
        return convertView;
    }


    //根据position获取item的type值
    @Override
    public int getItemViewType(int position)
    {
        ChattingText chattingText=mList.get(position);
        int type=chattingText.getType();
        return type;
    }

    //获取Layout样式数量
    @Override
    public int getViewTypeCount()
    {
        return 3;
    }

    class ViewHolderLeftText
    {
        private TextView tv_left;
    }

    class ViewHolderRightText
    {
        private TextView tv_right;
    }
}
