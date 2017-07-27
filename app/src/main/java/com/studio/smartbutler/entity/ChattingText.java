package com.studio.smartbutler.entity;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.entity
 * file name: ChattingText
 * creator: WindFromFarEast
 * created time: 2017/7/26 11:37
 * description: 聊天文本实体类
 */

public class ChattingText
{
    //区分文本在左边还是右边
    private int type;
    //文本内容
    private String text;

    public int getType()
    {
        return type;
    }

    public void setType(int type)
    {
        this.type = type;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }
}
