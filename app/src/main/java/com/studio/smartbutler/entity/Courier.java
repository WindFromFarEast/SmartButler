package com.studio.smartbutler.entity;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.entity
 * file name: Courier
 * creator: WindFromFarEast
 * created time: 2017/7/23 11:37
 * description: 物流实体类
 */

public class Courier
{
    //日期时间
    private String datatime;
    //物流信息详情
    private String remark;

    public String getDatatime()
    {
        return datatime;
    }

    public void setDatatime(String datatime)
    {
        this.datatime = datatime;
    }

    public String getRemark()
    {
        return remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }
}
