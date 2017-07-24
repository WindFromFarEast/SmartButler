package com.studio.smartbutler.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.studio.smartbutler.R;
import com.studio.smartbutler.adapter.CourierAdapter;
import com.studio.smartbutler.entity.Courier;
import com.studio.smartbutler.utils.L;
import com.studio.smartbutler.utils.StaticClass;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.ui
 * file name: CourierActivity
 * creator: WindFromFarEast
 * created time: 2017/7/22 13:35
 * description: 物流查询页
 */

public class CourierActivity extends BaseActivity implements View.OnClickListener
{
    private EditText et_company;
    private EditText et_courier_number;
    private Button btn_query;
    private ListView lv_timeline;
    //聚合Api返回的Json结果
    private String response=null;
    //ListView的数据源
    private List<Courier> mList=new ArrayList<>();
    //ListView适配器
    private CourierAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courier);
        initView();
    }

    //初始化控件
    private void initView()
    {
        et_company= (EditText) findViewById(R.id.et_company);
        et_courier_number= (EditText) findViewById(R.id.et_courier_number);
        btn_query= (Button) findViewById(R.id.btn_query);
        lv_timeline= (ListView) findViewById(R.id.lv_timeline);

        //设置监听器
        btn_query.setOnClickListener(this);
    }

    //监听事件
    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_query:
            {
                //点击查询按钮查询物流信息
                queryCourier();
                break;
            }
        }
    }

    //查询物流信息
    private void queryCourier()
    {
        //获取输入的物流公司代号和快递单号
        String company=et_company.getText().toString().trim();
        String number=et_courier_number.getText().toString().trim();

        if (!TextUtils.isEmpty(company)&!TextUtils.isEmpty(number))
        {
            //输入框不为空则查询物流信息
            RxVolley.get("http://v.juhe.cn/exp/index?key=" + StaticClass.JUHE_API_KEY + "&com=" + company + "&no=" + number,
                    new HttpCallback()
                    {
                        @Override
                        public void onSuccess(String jsonText)
                        {
                            //成功返回数据
                            response=jsonText;
                            L.i("物流Json信息:"+response);
                            //解析返回的Json物流数据
                            parseJson(response);
                        }
                    });
        }
        else
        {
            //输入框为空则弹出吐司提醒用户
            Toast.makeText(this,this.getString(R.string.input_no_empty),Toast.LENGTH_SHORT).show();
        }
    }

    //解析返回的Json物流信息,完成ListView数据源
    private void parseJson(String response)
    {
        try
        {
            JSONObject jsonObject=new JSONObject(response);
            JSONObject jsonResult=jsonObject.getJSONObject("result");
            L.i("jsonResult"+jsonResult.toString());
            JSONArray jsonArray=jsonResult.getJSONArray("list");
            for (int i=0;i<jsonArray.length();i++)
            {
                Courier courier = new Courier();
                JSONObject tempJsonObject= (JSONObject) jsonArray.get(i);
                courier.setRemark(tempJsonObject.getString("remark"));
                courier.setDatatime(tempJsonObject.getString("datetime"));
                mList.add(courier);
            }

            //为ListView添加适配器
            adapter=new CourierAdapter(mList);
            lv_timeline.setAdapter(adapter);
            //去掉分割线
            lv_timeline.setDivider(null);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }
}
