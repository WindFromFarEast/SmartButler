package com.studio.smartbutler.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.studio.smartbutler.MainActivity;
import com.studio.smartbutler.R;
import com.studio.smartbutler.entity.MyUser;
import com.studio.smartbutler.utils.SharedUtils;
import com.tuyenmonkey.mkloader.MKLoader;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.ui
 * file name: LoginActivity
 * creator: WindFromFarEast
 * created time: 2017/7/14 10:44
 * description: 登录页
 */

public class LoginActivity extends AppCompatActivity
{
    private Button btn_register;
    private Button btn_login;
    private EditText et_user;
    private EditText et_password;
    private CheckBox checkBox_remember;
    private TextView tv_forget;
    private ImageView img_login;
    private MKLoader loader;

    private String username;
    private String password;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
    }

    private void initView()
    {
        btn_register= (Button) findViewById(R.id.btn_register);
        btn_login= (Button) findViewById(R.id.btn_login);
        et_user= (EditText) findViewById(R.id.et_user);
        et_password= (EditText) findViewById(R.id.et_password);
        checkBox_remember= (CheckBox) findViewById(R.id.checkBox_remember);
        tv_forget= (TextView) findViewById(R.id.tv_forget);
        img_login= (ImageView) findViewById(R.id.img_login);
        loader= (MKLoader) findViewById(R.id.MKLoader);

        if (SharedUtils.getBoolean("remember_password",false))
        {
            et_user.setText(SharedUtils.getString("username",""));
            et_password.setText(SharedUtils.getString("password",""));
            checkBox_remember.setChecked(true);
        }

        tv_forget.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                username=et_user.getText().toString().trim();
                password=et_password.getText().toString();

                if (TextUtils.isEmpty(username)|TextUtils.isEmpty(password))
                {
                    Toast.makeText(LoginActivity.this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //显示加载框
                    hideOrShowAllExceptLoader();

                    final MyUser user=new MyUser();
                    user.setUsername(username);
                    user.setPassword(password);
                    user.login(new SaveListener<MyUser>()
                    {
                        @Override
                        public void done(MyUser myUser, BmobException e)
                        {
                            //登陆成功
                            if (e==null)
                            {
                                hideOrShowAllExceptLoader();
                                //邮箱验证判断
                                if (user.getEmailVerified())
                                {
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    finish();
                                }
                                else
                                {
                                    Toast.makeText(LoginActivity.this,"邮箱未验证",Toast.LENGTH_SHORT).show();
                                }
                            }
                            else
                            {
                                Toast.makeText(LoginActivity.this,"登陆失败："+e.toString(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        SharedUtils.putBoolean("remember_password",checkBox_remember.isChecked());

        if (checkBox_remember.isChecked())
        {
            SharedUtils.putString("username",et_user.getText().toString().trim());
            SharedUtils.putString("password",et_password.getText().toString().trim());
        }
    }

    private void hideOrShowAllExceptLoader()
    {
        if (et_user.getVisibility()==View.VISIBLE)
        {
            et_user.setVisibility(View.GONE);
            et_password.setVisibility(View.GONE);
            checkBox_remember.setVisibility(View.GONE);
            btn_login.setVisibility(View.GONE);
            btn_register.setVisibility(View.GONE);
            img_login.setVisibility(View.GONE);
            tv_forget.setVisibility(View.GONE);
            loader.setVisibility(View.VISIBLE);
        }
        else if (et_user.getVisibility()==View.GONE)
        {
            et_user.setVisibility(View.VISIBLE);
            et_password.setVisibility(View.VISIBLE);
            checkBox_remember.setVisibility(View.VISIBLE);
            btn_login.setVisibility(View.VISIBLE);
            btn_register.setVisibility(View.VISIBLE);
            img_login.setVisibility(View.VISIBLE);
            tv_forget.setVisibility(View.VISIBLE);
            loader.setVisibility(View.GONE);
        }
    }
}
