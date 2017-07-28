package com.studio.smartbutler.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.studio.smartbutler.R;
import com.studio.smartbutler.adapter.WechatAdapter;
import com.studio.smartbutler.entity.WechatNews;
import com.studio.smartbutler.ui.WebViewActivity;
import com.studio.smartbutler.utils.L;
import com.studio.smartbutler.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.fragment
 * file name: WechatFragment
 * creator: WindFromFarEast
 * created time: 2017/7/11 12:02
 * description: 智能管家
 */

public class WechatFragment extends Fragment
{
    //ListView
    private ListView wechat_mList;
    //Adapter
    private WechatAdapter adapter;
    //Adapter数据源
    private List<WechatNews> wechatNewsList=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_wechat,null);
        //初始化控件
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        wechat_mList= (ListView) view.findViewById(R.id.wechat_mList);
        //取消ListView的下划线
        wechat_mList.setDivider(null);
        //利用聚合Api获取微信精选存放到数据源中
        String url="http://v.juhe.cn/weixin/query?key="+ StaticClass.WECHAT_KEY;
        //初始化适配器,为ListView绑定适配器
        adapter=new WechatAdapter(wechatNewsList);
        wechat_mList.setAdapter(adapter);
        RxVolley.get(url, new HttpCallback()
        {
            @Override
            public void onSuccess(String response)
            {
                //成功获得新闻Json数据
                L.i("微信精选:"+response.toString());
                //解析Json数据,完成数据源
                parseJson(response);
                //通知适配器刷新数据源
                adapter.notifyDataSetChanged();
            }
        });
        //为ListView设置监听器
        wechat_mList.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            //点击对应的微信精选跳转到WebView显示详情内容
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                WechatNews wechatNews=wechatNewsList.get(position);
                Intent intent=new Intent(getActivity(),WebViewActivity.class);
                intent.putExtra("title",wechatNews.getTitle().toString());
                intent.putExtra("url",wechatNews.getUrl().toString());
                startActivity(intent);
            }
        });
    }

    //解析Json数据
    private void parseJson(String response)
    {
        try
        {
            JSONArray jsonArray=new JSONObject(response).getJSONObject("result").getJSONArray("list");
            //完成数据源的填充
            for (int i=0;i<jsonArray.length();i++)
            {
                String title,imgUrl,source,url;
                JSONObject jsonObject= (JSONObject) jsonArray.get(i);
                title=jsonObject.getString("title");
                source=jsonObject.getString("source");
                url=jsonObject.getString("url");
                imgUrl=jsonObject.getString("firstImg");
                WechatNews wechatNews=new WechatNews();
                wechatNews.setImaURL(imgUrl);
                wechatNews.setSource(source);
                wechatNews.setTitle(title);
                wechatNews.setUrl(url);
                wechatNewsList.add(wechatNews);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
