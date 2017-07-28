package com.studio.smartbutler.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.studio.smartbutler.R;
import com.studio.smartbutler.application.BaseApplication;
import com.studio.smartbutler.entity.WechatNews;
import com.studio.smartbutler.utils.L;
import com.studio.smartbutler.utils.PicassoUtils;

import java.util.List;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.adapter
 * file name: WechatAdapter
 * creator: WindFromFarEast
 * created time: 2017/7/28 11:08
 * description: 微信精选ListView适配器
 */

public class WechatAdapter extends BaseAdapter
{
    //数据源
    private List<WechatNews> wechatNewsList;

    //适配器构造方法
    public WechatAdapter(List<WechatNews> wechatNewsList)
    {
        this.wechatNewsList=wechatNewsList;
    }

    @Override
    public int getCount()
    {
        return wechatNewsList.size();
    }

    @Override
    public Object getItem(int position)
    {
        return wechatNewsList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder viewHolder=null;
        if (convertView==null)
        {
            //第一次初始化
            viewHolder=new ViewHolder();
            convertView= LayoutInflater.from(BaseApplication.getContext()).inflate(R.layout.item_wechat,null);
            //初始化控件
            viewHolder.tv_wechat_source= (TextView) convertView.findViewById(R.id.tv_wechat_source);
            viewHolder.tv_wechat_title= (TextView) convertView.findViewById(R.id.tv_wechat_title);
            viewHolder.iv_wechat_cover= (ImageView) convertView.findViewById(R.id.iv_wechat_cover);
            //设置缓存
            convertView.setTag(viewHolder);
        }
        else
        {
            //获取缓存ViewHolder
            viewHolder= (ViewHolder) convertView.getTag();
        }
        //设置控件内容
        viewHolder.tv_wechat_source.setText(wechatNewsList.get(position).getSource().toString());
        viewHolder.tv_wechat_title.setText(wechatNewsList.get(position).getTitle().toString());
        L.i("错误的url:"+wechatNewsList.get(position).getImaURL().toString());
        //由于某些图片url可能为空,因此为了让所有图片加载成功需要判断空值
        if (!TextUtils.isEmpty(wechatNewsList.get(position).getImaURL()))
        {
            //Picasso加载图片
            PicassoUtils.loadImgWithSize(wechatNewsList.get(position).getImaURL(),viewHolder.iv_wechat_cover,250,250);
        }
        else
        {

        }
        return convertView;
    }

    class ViewHolder
    {
        private TextView tv_wechat_title;
        private TextView tv_wechat_source;
        private ImageView iv_wechat_cover;
    }
}
