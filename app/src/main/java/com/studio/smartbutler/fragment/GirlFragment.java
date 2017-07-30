package com.studio.smartbutler.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.studio.smartbutler.R;
import com.studio.smartbutler.adapter.GridAdapter;
import com.studio.smartbutler.entity.Girl;
import com.studio.smartbutler.utils.L;
import com.studio.smartbutler.utils.PicassoUtils;
import com.studio.smartbutler.view.CustomDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.fragment
 * file name: GirlFragment
 * creator: WindFromFarEast
 * created time: 2017/7/11 11:58
 * description: 美女社区
 */

public class GirlFragment extends Fragment
{
    private GridView girl_gridView;
    private List<Girl> mList=new ArrayList<>();
    private GridAdapter adapter;
    //自定义Dialog
    private CustomDialog dialog;
    //预览图片
    private ImageView iv_dialog;
    //PhotoView
    PhotoViewAttacher attacher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_girl,null);
        //初始化控件
        initView(view);
        return view;
    }

    private void initView(View view)
    {
        girl_gridView= (GridView) view.findViewById(R.id.girl_gridView);
        //初始化自定义Dialog并实例化Dialog中的ImageView
        dialog=new CustomDialog(getActivity(), LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT,
                R.layout.dialog_girl,R.style.Theme_Dialog, Gravity.CENTER,R.style.pop_anim_style);
        iv_dialog= (ImageView) dialog.findViewById(R.id.iv_dialog);
        //填充美女数据源
        addGirlToList();
        //完成适配器初始化
        adapter=new GridAdapter(mList);
        //为GridView添加适配器
        girl_gridView.setAdapter(adapter);
        //为GridView添加点击事件,点击后出现可缩放的图片
        girl_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //加载图片
                PicassoUtils.loadImg(mList.get(position).getImgUrl(),iv_dialog);
                //利用PhotoView缩放图片
                attacher=new PhotoViewAttacher(iv_dialog);
                attacher.update();
                //自定义Dialog显示预览图片
                dialog.show();
            }
        });
    }

    //填充数据源
    private void addGirlToList()
    {
        String girlApiUrl=null;
        //将'福利'两字转码
        String welfare = null;
        try
        {
            //Gank升級 需要转码
            welfare = URLEncoder.encode(getString(R.string.text_welfare), "UTF-8");
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        //利用接口获取Json数据
        girlApiUrl="http://gank.io/api/search/query/listview/category/" + welfare + "/count/50/page/1";
        L.i("美女接口:"+girlApiUrl);
        RxVolley.get(girlApiUrl, new HttpCallback()
        {
            @Override
            public void onSuccess(String response)
            {
                parseJson(response);
                adapter.notifyDataSetChanged();
            }
        });
    }

    //解析Json数据,完成数据源填充
    private void parseJson(String response)
    {
        try
        {
            JSONArray jsonArray=new JSONObject(response).getJSONArray("results");
            for (int i=0;i<jsonArray.length();i++)
            {
                String imgUrl=null;
                JSONObject jsonObject= (JSONObject) jsonArray.get(i);
                if (jsonObject!=null)
                {
                    Girl girl=new Girl();
                    imgUrl = jsonObject.getString("url");
                    L.i("美女图片的地址:"+imgUrl);
                    girl.setImgUrl(imgUrl);
                    mList.add(girl);
                }
                else
                {
                    L.i("JSONObject为null");
                }
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
