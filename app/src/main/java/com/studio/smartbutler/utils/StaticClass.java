package com.studio.smartbutler.utils;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.utils
 * file name: StaticClass
 * creator: WindFromFarEast
 * created time: 2017/7/11 10:57
 * description: 数据/常量
 */

public class StaticClass
{
    //闪屏页延时
    public static final int HANDLER_SPLASH = 1001;
    //判断App是否第一次运行
    public static final String FIRST_RUN="first_run";
    //腾讯Bugly需要的AppId
    public static final String BUGLY_APP_ID="97f5a37836";
    //Bomb Key
    public static final String BMOB_APP_ID="9ad1efaaa3fa7be4abab2a7496fab8b8";
    //拍照
    public static final int TAKE_PHOTO=1002;
    //相册选择照片
    public static final int CHOOSE_PHOTO=1003;
    //裁剪图片
    public static final int CUT_PHOTO=1004;
    //运行时权限处理——读写SD卡请求码
    public static final int WRITE_EXTERNAL_STORAGE_CODE=1005;
    //media类型Uri的Authority
    public static final String MEDIA_URI_AUTHORITY="com.android.providers.media.documents";
    //downloads类型Uri的包名
    public static final String DOWNLOADS_URI_AUTHORITY="com.android.providers.downloads.documents";
    //downloads类型Uri的路径
    public static final String DOWNLOADS_URI_PATH="content://downloads/public_downloads";
    //默认头像地址
    public static final String DEFAULT_AVATAR_URL="http://img4.imgtn.bdimg.com/it/u=2238386155,4137504811&fm=26&gp=0.jpg";
}
