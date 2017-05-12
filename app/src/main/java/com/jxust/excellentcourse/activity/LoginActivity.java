package com.jxust.excellentcourse.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.jxust.excellentcourse.R;
import com.jxust.excellentcourse.Utils.Utils;
import com.jxust.excellentcourse.view.ClearableEditText;

/**
 * Created by Simon on 2017/5/11.
 */

public class LoginActivity extends Activity implements View.OnClickListener{
    private TextView toolbarcenter;
    private TextView back;
    private TextView register;
    private ClearableEditText usernameedittext;
    private ClearableEditText passwordedittext;
    private Button button;
    private String username;
    private String password;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbarcenter= (TextView) findViewById(R.id.tv_toolbar_centertext);
        back= (TextView) findViewById(R.id.tv_toolbar_lefttext);
        register= (TextView) findViewById(R.id.tv_register);
        usernameedittext= (ClearableEditText) findViewById(R.id.et_username_login);
        passwordedittext= (ClearableEditText) findViewById(R.id.et_password_login);
        toolbarcenter.setText("登录");
        button= (Button) findViewById(R.id.bt_login);
        button.setOnClickListener(this);
        back.setOnClickListener(this);
        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_toolbar_lefttext:
                finish();
                break;
            case R.id.tv_register:
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_login:
                if (Utils.isNetworkAvailable(LoginActivity.this))
                {
                    startLogin();
                }
                else {
                    Utils.showNoNetToast(LoginActivity.this);
                }
            default:
                break;
        }
    }

    private void startLogin() {
        username=usernameedittext.getText().toString().trim();
        password=passwordedittext.getText().toString().trim();
        if (TextUtils.isEmpty(username)||TextUtils.isEmpty(password))
        {
            Utils.showToast(LoginActivity.this,"用户名和密码不能为空");
        }
        else
        {
            final Dialog dialog=Utils.showSpinnerDialog(LoginActivity.this,"正在登录");
            AVUser.loginByMobilePhoneNumberInBackground(username, password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser user, AVException e) {
                    dialog.dismiss();
                    if (e == null) {
                        //登陆成功
                        Utils.showToast(LoginActivity.this,"登录成功");
                        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    } else {
                        Utils.showToast(LoginActivity.this,e.getMessage());

                    }
                }
            });
        }
        }
    }

