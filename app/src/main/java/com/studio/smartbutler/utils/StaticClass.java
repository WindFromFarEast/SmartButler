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
    //默认头像地址
    public static final String DEFAULT_AVATAR_URL="http://img4.imgtn.bdimg.com/it/u=2238386155,4137504811&fm=26&gp=0.jpg";
    //聚合数据物流信息ApiKey
    public static final String JUHE_API_KEY="fb038635c6dd2202a25d15941e4702f0";
    //聚合数据归属地ApiKey
    public static final String PHONE_QUERY_KEY="0a1ee393e1003dbb62ee7c0b8c370507";
    //聚合数据问答机器人ApiKey
    public static final String ROBOT_KEY="502f388b130fdc635dea5112c693116c";
    //聚合数据微信精选ApiKey
    public static final String WECHAT_KEY="ae9b11a5539c39113968e9908edd4490";
    //左边文本的type
    public static final int TYPE_LEFT_TEXT=1;
    //右边文本的type
    public static final int TYPE_RIGHT_TEXT=2;
    //科大讯飞TTS AppId
    public static final String TTS_APP_ID="597e9be2";
    //接受短信的Action
    public static final String SMS_ACTION="android.provider.Telephony.SMS_RECEIVED";
    //SMS权限申请请求码
    public static final int PERMISSION_CODE=1006;
    //WINDOW权限申请请求码
    public static final int PERMISSION_WINDOW_CODE=5463&0xffffff00;
    //最新版本配置信息地址
    public static final String LATEST_VERSION_INFO_URL="http://192.168.0.112:8080/xwx/version.json";
    //前往扫描二维码界面的请求码
    public static final int SCAN_QRCODE_REQUEST_CODE=0;
}
