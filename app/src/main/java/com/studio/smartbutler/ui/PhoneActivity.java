package com.studio.smartbutler.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.studio.smartbutler.R;
import com.studio.smartbutler.utils.L;
import com.studio.smartbutler.utils.StaticClass;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.ui
 * file name: PhoneActivity
 * creator: WindFromFarEast
 * created time: 2017/7/24 11:36
 * description: 归属地查询页面
 */

public class PhoneActivity extends BaseActivity implements View.OnClickListener
{
    private EditText et_phone_number;
    private ImageView iv_company;
    private TextView tv_phone_info;
    private Button btn_number_one,btn_number_two,btn_number_three,btn_number_four,btn_number_five,btn_number_six,btn_number_seven;
    private Button btn_number_eight,btn_number_nine,btn_number_zero,btn_number_del,btn_number_query;
    //是否点击过查询按钮的标志位,默认为false即未点击过
    private boolean flag=false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        initView();
    }

    //初始化控件
    private void initView()
    {
        et_phone_number= (EditText) findViewById(R.id.et_phone_number);
        iv_company= (ImageView) findViewById(R.id.iv_company);
        tv_phone_info= (TextView) findViewById(R.id.tv_phone_info);
        btn_number_one= (Button) findViewById(R.id.btn_number_one);
        btn_number_two= (Button) findViewById(R.id.btn_number_two);
        btn_number_three= (Button) findViewById(R.id.btn_number_three);
        btn_number_four= (Button) findViewById(R.id.btn_number_four);
        btn_number_five= (Button) findViewById(R.id.btn_number_five);
        btn_number_six= (Button) findViewById(R.id.btn_number_six);
        btn_number_seven= (Button) findViewById(R.id.btn_number_seven);
        btn_number_eight= (Button) findViewById(R.id.btn_number_eight);
        btn_number_nine = (Button) findViewById(R.id.btn_number_nine);
        btn_number_zero= (Button) findViewById(R.id.btn_number_zero);
        btn_number_del= (Button) findViewById(R.id.btn_number_del);
        btn_number_query= (Button) findViewById(R.id.btn_number_query);

        //设置单点击监听器
        btn_number_one.setOnClickListener(this);
        btn_number_two.setOnClickListener(this);
        btn_number_three.setOnClickListener(this);
        btn_number_four.setOnClickListener(this);
        btn_number_five.setOnClickListener(this);
        btn_number_six.setOnClickListener(this);
        btn_number_seven.setOnClickListener(this);
        btn_number_eight.setOnClickListener(this);
        btn_number_nine.setOnClickListener(this);
        btn_number_zero.setOnClickListener(this);
        btn_number_del.setOnClickListener(this);
        btn_number_query.setOnClickListener(this);

        //设置长按监听器
        btn_number_del.setOnLongClickListener(new View.OnLongClickListener()
        {
            @Override
            public boolean onLongClick(View v)
            {
                //长按删除键清空已输入的手机号
                et_phone_number.setText("");
                return false;
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        String existedNumber=et_phone_number.getText().toString().trim();//用来保存点击按钮前输入栏中的手机号码
        switch (v.getId())
        {
            case R.id.btn_number_one:
            case R.id.btn_number_two:
            case R.id.btn_number_three:
            case R.id.btn_number_four:
            case R.id.btn_number_five:
            case R.id.btn_number_six:
            case R.id.btn_number_seven:
            case R.id.btn_number_eight:
            case R.id.btn_number_nine:
            case R.id.btn_number_zero:
            {
                if (flag==true)
                {
                    //点击过查询按钮后再输入新的手机号则清空原来的输入信息
                    existedNumber="";
                    et_phone_number.setText("");
                    et_phone_number.setSelection(0);
                    flag=false;
                }
                //点击数字按钮在手机号码输入栏末尾添加数字
                et_phone_number.setText(existedNumber+((Button)v).getText());
                et_phone_number.setSelection(existedNumber.length()+1);
                break;
            }
            case R.id.btn_number_del:
            {
                flag=false;//如果用户在点击了查询后再点击删除则说明他只打错了某些数字,那么不需要清空
                existedNumber=existedNumber.substring(0,existedNumber.length()-1);
                et_phone_number.setText(existedNumber);
                et_phone_number.setSelection(existedNumber.length());
                break;
            }
            case R.id.btn_number_query:
            {
                if (!TextUtils.isEmpty(existedNumber))
                {
                    //查询手机号码详细信息
                    getNumberInfo(existedNumber);
                    //改变标志位
                    flag=true;
                }
                else
                {
                    //提醒用户输入框不能为空
                    Toast.makeText(this,getString(R.string.input_no_empty),Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    //查询手机号码详细信息
    private void getNumberInfo(String phoneNumber)
    {
        String url="http://apis.juhe.cn/mobile/get?phone="+phoneNumber+"&key="+ StaticClass.PHONE_QUERY_KEY;
        //请求返回Json数据
        RxVolley.get(url, new HttpCallback()
        {
            @Override
            public void onSuccess(String response)
            {
                //返回成功则解析返回的Json数据
                parseJson(response);
            }
        });
    }

    //解析Json数据
    private void parseJson(String jsonText)
    {
        try
        {
            JSONObject jsonObject=new JSONObject(jsonText).getJSONObject("result");
            L.i("手机号码信息:"+jsonObject.toString());
            //所属省份、城市、运营商和邮编
            String province=jsonObject.getString("province");
            String city=jsonObject.getString("city");
            String company=jsonObject.getString("company");
            String zip=jsonObject.getString("zip");
            //显示手机号码详细信息
            showPhoneInfo(province,city,company,zip);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

    }

    //将手机号码详细信息利用TextView显示出来
    private void showPhoneInfo(String province, String city, String company, String zip)
    {
        //显示文字信息
        tv_phone_info.setText("省份："+province+"\n"+"城市(直辖市为空)："+city+"\n"+"运营商："+company+"\n"+"邮编：" +
                zip+"\n");
        //显示运营商LOGO
        if (company.equals(getString(R.string.china_mobile)))
        {
            //中国移动
            iv_company.setBackgroundResource(R.drawable.china_mobile);
        }
        else if (company.equals(getString(R.string.china_unicom)))
        {
            iv_company.setBackgroundResource(R.drawable.china_unicom);
        }
        else if (company.equals(getString(R.string.china_telecom)))
        {
            iv_company.setBackgroundResource(R.drawable.china_telecom);
        }
    }
}
