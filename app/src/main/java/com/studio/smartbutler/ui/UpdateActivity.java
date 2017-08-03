package com.studio.smartbutler.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.kymjs.rxvolley.RxVolley;
import com.kymjs.rxvolley.client.HttpCallback;
import com.kymjs.rxvolley.client.ProgressListener;
import com.kymjs.rxvolley.http.VolleyError;
import com.kymjs.rxvolley.toolbox.FileUtils;
import com.studio.smartbutler.R;
import com.studio.smartbutler.utils.L;

import java.io.File;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.ui
 * file name: UpdateActivity
 * creator: WindFromFarEast
 * created time: 2017/8/3 13:07
 * description: 版本更新界面
 */

public class UpdateActivity extends BaseActivity
{
    //正在下载
    public static final int DOWNLOAD_LOADING=1002;
    //下载进度条
    private NumberProgressBar npb_update;
    //下载进度文字条
    private TextView tv_update_download;
    //文件下载路径
    private String filePath;
    //文件下载地址
    private String latestDownloadUrl;
    //异步消息处理
    private Handler handler;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        //初始化控件
        initView();
    }

    private void initView()
    {
        npb_update= (NumberProgressBar) findViewById(R.id.npb_update);
        tv_update_download= (TextView) findViewById(R.id.tv_update_download);
        //设置下载进度条的最大值
        npb_update.setMax(100);
        //为了在主线程中进行UI操作
        handler=new Handler()
        {
            @Override
            public void handleMessage(Message msg)
            {
                switch (msg.what)
                {
                    case DOWNLOAD_LOADING://下载过程中更新UI
                    {
                        long downloadedSize,size;
                        int percent;//下载百分比
                        //获取到Message中传过来的文件大小和已下载大小的值并显示在进度条中
                        Bundle bundle=msg.getData();
                        downloadedSize=bundle.getLong("downloaded");
                        size=bundle.getLong("size");
                        percent=(int) ((((float)downloadedSize)/((float)size))*100);
                        //设置进度条的进度和文字进度
                        npb_update.setProgress(percent);
                        tv_update_download.setText(downloadedSize+"/"+size);
                        break;
                    }
                }
            }
        };
        //获取最新下载地址
        latestDownloadUrl=getIntent().getStringExtra("downloadUrl");
        //根据时间确定下载路径及文件名
        filePath= FileUtils.getSDCardPath()+"/"+System.currentTimeMillis()+".apk";
        //开始下载
        RxVolley.download(filePath, latestDownloadUrl, new ProgressListener()
        {
            @Override
            public void onProgress(long transferredBytes, long totalSize)
            {
                if (transferredBytes<totalSize)
                {
                    //尚未下载完成,则进度条不断增加
                    Message message=new Message();
                    message.what=DOWNLOAD_LOADING;
                    //将文件大小通过bundle传到Handler的handleMessage方法中
                    Bundle bundle=new Bundle();
                    bundle.putLong("downloaded",transferredBytes);
                    bundle.putLong("size",totalSize);
                    message.setData(bundle);
                    //发送消息处理UI
                    handler.sendMessage(message);
                }
            }
        }, new HttpCallback()
        {
            @Override
            public void onSuccess(String t)
            {
                //下载成功则直接进入Apk安装界面
                installApk();
            }

            @Override
            public void onFailure(VolleyError error)
            {
                //下载失败提示用户
                Toast.makeText(UpdateActivity.this,getString(R.string.download_fail),Toast.LENGTH_SHORT).show();
            }
        });
    }

    //安装最新版的Apk
    private void installApk()
    {
        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(new File(filePath)),"application/vnd.android.package-archive");
        startActivity(intent);
        //进入安装Apk界面后直接关闭更新界面
        finish();
    }
}
