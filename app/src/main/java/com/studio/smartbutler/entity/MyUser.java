package com.studio.smartbutler.entity;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.entity
 * file name: MyUser
 * creator: WindFromFarEast
 * created time: 2017/7/14 12:00
 * description: 用户类
 */

public class MyUser extends BmobUser
{
    private Boolean sex;
    private String desc;
    private Integer age;
    private String avatarUrl;

    public Boolean getSex()
    {
        return sex;
    }

    public void setSex(Boolean sex)
    {
        this.sex = sex;
    }

    public String getDesc()
    {
        return desc;
    }

    public void setDesc(String desc)
    {
        this.desc = desc;
    }

    public Integer getAge()
    {
        return age;
    }

    public void setAge(Integer age)
    {
        this.age = age;
    }

    public String getAvatarUrl()
    {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl)
    {
        this.avatarUrl = avatarUrl;
    }
}
