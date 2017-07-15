package com.studio.smartbutler.ui;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.studio.smartbutler.R;
import com.studio.smartbutler.entity.MyUser;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * project name: SmartButler
 * package name: com.studio.smartbutler.ui
 * file name: ForgetPasswordActivity
 * creator: WindFromFarEast
 * created time: 2017/7/15 11:54
 * description: 忘记密码页
 */

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener
{
    private EditText et_now;
    private EditText et_new1;
    private EditText et_new2;
    private EditText et_email;
    private Button btn_change_password;
    private Button btn_send_email;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        initView();
    }

    private void initView()
    {
        et_email= (EditText) findViewById(R.id.et_email);
        btn_change_password= (Button) findViewById(R.id.btn_change_password);
        btn_send_email= (Button) findViewById(R.id.btn_send_email);

        btn_change_password.setOnClickListener(this);
        btn_send_email.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId())
        {
            case R.id.btn_change_password:
            {
                Toast.makeText(ForgetPasswordActivity.this,"该功能只有在登陆后才能使用!",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_send_email:
            {
                String email=et_email.getText().toString().trim();
                if (TextUtils.isEmpty(email))
                {
                    Toast.makeText(ForgetPasswordActivity.this,"输入框不能为空",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    MyUser.resetPasswordByEmail(email, new UpdateListener()
                    {
                        @Override
                        public void done(BmobException e)
                        {
                            if (e==null)
                            {
                                Toast.makeText(ForgetPasswordActivity.this,"邮件已发送",Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                            {
                                Toast.makeText(ForgetPasswordActivity.this,"邮件发送失败",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    finish();
                }
                break;
            }
        }
    }
}
