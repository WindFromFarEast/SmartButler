package com.studio.smartbutler.entity;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.entity
 * file name: WechatNews
 * creator: WindFromFarEast
 * created time: 2017/7/28 11:07
 * description: 微信精选新闻实体类
 */

public class WechatNews
{
    //新闻标题
    private String title;
    //新闻来源
    private String source;
    //新闻地址
    private String url;
    //新闻封面
    private String imaURL;

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getSource()
    {
        return source;
    }

    public void setSource(String source)
    {
        this.source = source;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getImaURL()
    {
        return imaURL;
    }

    public void setImaURL(String imaURL)
    {
        this.imaURL = imaURL;
    }
}
