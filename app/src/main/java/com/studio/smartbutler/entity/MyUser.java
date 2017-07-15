package com.studio.smartbutler.entity;

import cn.bmob.v3.BmobUser;

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
}
