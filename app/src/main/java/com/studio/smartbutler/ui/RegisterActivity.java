package com.studio.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.studio.smartbutler.R;
import com.studio.smartbutler.entity.MyUser;
import com.studio.smartbutler.utils.L;
import com.studio.smartbutler.utils.StaticClass;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.ui
 * file name: RegisterActivity
 * creator: WindFromFarEast
 * created time: 2017/7/14 10:57
 * description: 注册页
 */

public class RegisterActivity extends BaseActivity
{
    private EditText et_user;
    private EditText et_age;
    private EditText et_desc;
    private EditText et_password1;
    private EditText et_password2;
    private EditText et_email;
    private RadioGroup mRadioGroup;
    private Button btn_register;
    private Boolean sex = true;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView()
    {
        et_user = (EditText) findViewById(R.id.et_user);
        et_age = (EditText) findViewById(R.id.et_age);
        et_desc = (EditText) findViewById(R.id.et_desc);
        et_password1 = (EditText) findViewById(R.id.et_password1);
        et_password2 = (EditText) findViewById(R.id.et_password2);
        et_email = (EditText) findViewById(R.id.et_email);
        mRadioGroup = (RadioGroup) findViewById(R.id.mRadioGroup);
        btn_register = (Button) findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String username = et_user.getText().toString().trim();
                String password1 = et_password1.getText().toString();
                String password2 = et_password2.getText().toString();
                String age = et_age.getText().toString().trim();
                String email = et_email.getText().toString().trim();
                String desc = et_desc.getText().toString();

                if (TextUtils.isEmpty(username) | TextUtils.isEmpty(password1) | TextUtils.isEmpty(password2) | TextUtils.isEmpty(age)
                        | TextUtils.isEmpty(email))
                {
                    Toast.makeText(RegisterActivity.this, "输入框不能为空!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if (!password1.equals(password2))
                    {
                        Toast.makeText(RegisterActivity.this, "两次输入的密码不一致!", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        if (TextUtils.isEmpty(desc))
                        {
                            desc = "这个人很懒，什么都没有留下";
                        }
                        //注册,更多用法查看Bmob开发文档
                        MyUser myUser = new MyUser();
                        myUser.setUsername(username);
                        myUser.setPassword(password1);
                        myUser.setAge(Integer.parseInt(age));
                        myUser.setDesc(desc);
                        myUser.setEmail(email);
                        myUser.setSex(sex);
                        myUser.setAvatarUrl(StaticClass.DEFAULT_AVATAR_URL);
                        myUser.signUp(new SaveListener<MyUser>()
                        {
                            @Override
                            public void done(MyUser myUser, BmobException e)
                            {
                                if (e == null)
                                {
                                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(RegisterActivity.this, "注册失败:" + e.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }
            }
        });

        mRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId)
            {
                if (checkedId == R.id.rb_man)
                {
                    sex = true;
                }
                else if (checkedId == R.id.rb_woman)
                {
                    sex = false;
                }
            }
        });
    }
}
