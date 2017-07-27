package com.studio.smartbutler.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.studio.smartbutler.R;
import com.studio.smartbutler.adapter.ChattingAdapter;
import com.studio.smartbutler.entity.ChattingText;
import com.studio.smartbutler.utils.L;
import com.studio.smartbutler.utils.StaticClass;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.fragment
 * file name: ButlerFragment
 * creator: WindFromFarEast
 * created time: 2017/7/11 11:51
 * description: 智能管家页面
 */

public class ButlerFragment extends Fragment implements View.OnClickListener
{
    private ListView lv_chatting;
    //ListView数据源
    private List<ChattingText> mList=new ArrayList<>();
    //ListView适配器
    private ChattingAdapter adapter;
    private Button btn_chatting_send;
    private EditText et_chatting_text;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_butler,container,false);
        initView(view);//初始化view
        return view;
    }

    //初始化View
    private void initView(View view)
    {
        lv_chatting= (ListView) view.findViewById(R.id.lv_chatting);
        btn_chatting_send= (Button) view.findViewById(R.id.btn_chatting_send);
        et_chatting_text= (EditText) view.findViewById(R.id.et_chatting_text);

        //适配器初始化以及与ListView的绑定
        adapter=new ChattingAdapter(mList);
        lv_chatting.setAdapter(adapter);
        //取消ListView的分割线
        lv_chatting.setDivider(null);

        btn_chatting_send.setOnClickListener(this);
        et_chatting_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_chatting_send:
            {
                //获取输入框内容
                String content=et_chatting_text.getText().toString();
                //判断是否为空并且长度是否大于30
                if (!TextUtils.isEmpty(content))
                {
                    if (content.length()<=30)
                    {
                        //发送
                        showUserResponse(content);
                        //清空输入框
                        et_chatting_text.setText("");
                        //通过聚合Api请求机器人回答
                        String url="http://op.juhe.cn/robot/index?info="+content+"&key="+StaticClass.ROBOT_KEY;
                        RxVolley.get(url, new HttpCallback()
                        {
                            @Override
                            public void onSuccess(String response)
                            {
                                //结果返回成功
                                L.i("机器人回答Json:"+response);
                                showButlerResponse(getTextForJson(response).toString());
                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(getActivity(),getActivity().getString(R.string.content_text_limit),Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(getActivity(),getActivity().getString(R.string.input_no_empty),Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    //将Json解析成文本
    private String getTextForJson(String response)
    {
        String text=null;
        try
        {
            JSONObject jsonObject=new JSONObject(response).getJSONObject("result");
            text=jsonObject.getString("text");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return text;
    }

    private void showUserResponse(String text)
    {
        ChattingText chattingText=new ChattingText();
        chattingText.setText(text);
        chattingText.setType(StaticClass.TYPE_RIGHT_TEXT);
        mList.add(chattingText);
        //更新适配器
        adapter.notifyDataSetChanged();
        //下滑到最底部
        lv_chatting.setSelection(lv_chatting.getBottom());
    }

    private void showButlerResponse(String text)
    {
        ChattingText chattingText=new ChattingText();
        chattingText.setText(text);
        chattingText.setType(StaticClass.TYPE_LEFT_TEXT);
        mList.add(chattingText);
        //通知adapter刷新
        adapter.notifyDataSetChanged();
        //下滑到最底部
        lv_chatting.setSelection(lv_chatting.getBottom());
    }
}
