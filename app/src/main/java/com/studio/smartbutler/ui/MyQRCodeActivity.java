package com.studio.smartbutler.ui;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.studio.smartbutler.R;
import com.xys.libzxing.zxing.encoding.EncodingUtils;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.ui
 * file name: MyQRCodeActivity
 * creator: WindFromFarEast
 * created time: 2017/8/5 11:19
 * description: 我的二维码页面
 */

public class MyQRCodeActivity extends BaseActivity
{
    //用来显示我的二维码
    private ImageView iv_my_qrcode;
    //二维码显示的内容
    private EditText et_my_qrcode_content;
    //提交二维码内容
    private Button btn_qrcode_ok;
    //屏幕宽度
    private int width;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_qrcode);
        //初始化控件
        initView();
    }

    private void initView()
    {
        //获取控件实例
        iv_my_qrcode= (ImageView) findViewById(R.id.iv_my_qrcode);
        et_my_qrcode_content= (EditText) findViewById(R.id.et_my_qrcode_content);
        btn_qrcode_ok= (Button) findViewById(R.id.btn_qrcode_ok);
        //为提交按钮设置监听器
        btn_qrcode_ok.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String qrcode_content;
                //获取输入的内容
                qrcode_content=et_my_qrcode_content.getText().toString();
                //判断内容是否为空
                if (!TextUtils.isEmpty(qrcode_content))
                {
                    //不为空则生成二维码
                    showMyQRCode(qrcode_content);
                }
                else
                {
                    //为空则提示用户
                    Toast.makeText(MyQRCodeActivity.this,getString(R.string.input_no_empty), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //生成并显示我的二维码
    private void showMyQRCode(String content)
    {
        //获取屏幕宽度
        width=getWindowWidth();
        //根据字符串生成二维码图片并显示在界面上，第二个参数为图片的大小(width*height)
        Bitmap qrCodeBitmap = EncodingUtils.createQRCode(content, width/2, width/2, null);
        iv_my_qrcode.setImageBitmap(qrCodeBitmap);
    }

    //获取屏幕宽度(在Activity里可以这么用)
    private int getWindowWidth()
    {
        Resources resources=getResources();
        width=resources.getDisplayMetrics().widthPixels;
        return width;
    }
}
