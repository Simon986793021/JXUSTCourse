package com.jxust.excellentcourse.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;
import com.avos.avoscloud.okhttp.internal.Util;
import com.jxust.excellentcourse.R;
import com.jxust.excellentcourse.Utils.Utils;
import com.jxust.excellentcourse.view.ClearableEditText;
import com.jxust.excellentcourse.view.PasswordInputView;

import org.json.JSONException;

/**
 * Created by Simon on 2017/5/11.
 */

public class RegisterActivity extends Activity implements View.OnClickListener{
    private TextView back;
    private TextView toolbarcenter;
    private Button button;
    private ClearableEditText nicket;
    private ClearableEditText phoneet;
    private ClearableEditText passwordet;
    private String nickName;
    private String phoneNum;
    private String passWord;
    private AVUser user=new AVUser();
    private boolean isNext=false;
    private LinearLayout ll;
    private PasswordInputView verificationCode;
    private boolean isFinal=false;
    private Spinner sp;
    private String[] mItems = {"信息", "外语", "经管", "建筑", "材料", "冶金", "机械", "电气","文法","资环"};
    private String hobby;
    private Dialog dialog;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.avtivity_register);
        back= (TextView) findViewById(R.id.tv_toolbar_lefttext);
        toolbarcenter= (TextView) findViewById(R.id.tv_toolbar_centertext);
        nicket= (ClearableEditText) findViewById(R.id.et_nickname_register);
        phoneet= (ClearableEditText) findViewById(R.id.et_username_register);
        passwordet= (ClearableEditText) findViewById(R.id.et_password_register);
        ll= (LinearLayout) findViewById(R.id.ll_registerInfo);
        sp= (Spinner) findViewById(R.id.sp_select_hobby);
        verificationCode= (PasswordInputView) findViewById(R.id.et_verification_code);
        toolbarcenter.setText("注册");
        button= (Button) findViewById(R.id.bt_register);
        back.setOnClickListener(this);
        button.setOnClickListener(this);
    }
    /*判断相关输入是否为空*/
    public boolean isEmpty(String nickName, String phoneNumber, String password) {
        if (TextUtils.isEmpty(nickName)) {
            Utils.showToast(RegisterActivity.this,"昵称不能为空");
            return true;
        }
        else if (TextUtils.isEmpty(phoneNumber)||!Utils.checkCellphone(phoneNumber)) {
            Utils.showToast(getApplicationContext(),"手机号码有误");
            //showShortToast(this, "手机号码不能为空");
            return true;
        }
        else if (TextUtils.isEmpty(password)) {
            Utils.showToast(getApplicationContext(),"密码不能为空");
            //  showShortToast(this, "密码不能为空");
            return true;
        }
        else
        {
            return false;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.tv_toolbar_lefttext:
                finish();
                break;
            case R.id.bt_register:
                if (Utils.isNetworkAvailable(RegisterActivity.this))
                {
                    getRegisInfo();
                    if (!isEmpty(nickName,phoneNum,passWord))
                    {
                        user.setUsername(nickName);
                        user.setPassword(passWord);
                        user.setMobilePhoneNumber(phoneNum);
                        registerAction(nickName,passWord,phoneNum);
                    }
                }
                else {
                    Utils.showNoNetToast(RegisterActivity.this);
                }


                break;
            default:
                break;
        }
    }
    /*注册按钮点击*/
    private void registerAction(String nNickName, String pWord, String pNumber) {
        if (isFinal) {
            //在这里提交个人信息到服务器
            dialog=Utils.showSpinnerDialog(RegisterActivity.this,"正在提交");
            if (TextUtils.isEmpty(hobby)) {
               Utils.showToast(RegisterActivity.this,"请选择学院");
            }
            else {
                user.put("academy", hobby);
                user.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        if (e == null) {
                            dialog.dismiss();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                        } else {
                            Utils.showToast(RegisterActivity.this, "提交出错了，稍后试试吧~");
                            //  showShortToast(RegisterActivity.this, "提交出错了，稍后试试吧~");
                        }
                    }
                });
            }

            return;
        }
        if (isNext) {
            String vCode = verificationCode.getText().toString();
            verifiyCode(vCode);
            showSpinner();
            return;
        }
        if (phoneNum == null || phoneNum.equals("")) {
            Utils.showToast(RegisterActivity.this, "手机号码不能为空");
            return;
        } else if (!Utils.checkCellphone(pNumber)) {
           Utils.showToast (RegisterActivity.this, "请输入正确的手机号码");
            return;
        }
        user.setUsername(nNickName);
        user.setPassword(pWord);
        user.setMobilePhoneNumber(phoneNum);
        getVerifyCode(user);
    }

    private void showSpinner() {

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mItems);
        sp.setAdapter(adapter);

    }


    private void verifiyCode(String vCode) {
        if (vCode == null || vCode.equals("")) {
            Utils.showToast(RegisterActivity.this,"验证码不能为空");
            return;
        }
        else {
            dialog=Utils.showSpinnerDialog(RegisterActivity.this,"正在验证");
            AVUser.verifyMobilePhoneInBackground(vCode, new AVMobilePhoneVerifyCallback() {
                @Override
                public void done(AVException e) {
                    if (e == null) {
                        //验证成功,进入下一步
                        dialog.dismiss();
                        isFinal = true;
                        verificationCode.setVisibility(View.GONE);
                        sp.setVisibility(View.VISIBLE);
                        button.setText("完成");
                    } else {
                        Utils.showToast(getApplicationContext(),"验证码错误，请重试");
                    }
                }
            });
        }

    }

    /*注册未验证账户,获取验证码*/
    private void getVerifyCode(AVUser user) {
        dialog=Utils.showSpinnerDialog(RegisterActivity.this,"正在获取验证码");
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    dialog.dismiss();
                    isNext = true;
                    nicket.setVisibility(View.GONE);
                    phoneet.setVisibility(View.GONE);
                    passwordet.setVisibility(View.GONE);
                    ll.setVisibility(View.GONE);
                    verificationCode.setVisibility(View.VISIBLE);
                    button.setText("验证");
                } else {
                        String msg = e.getMessage();
                        Utils.showToast(RegisterActivity.this,"注册失败:"+msg);
                    Log.i(">>>>>>",msg);
                        // showShortToast(RegisterActivity.this, "注册未验证账户失败:" + msg);
                }
            }
        });
    }
    private void getRegisInfo() {
        nickName=nicket.getText().toString().trim();
        phoneNum=phoneet.getText().toString().trim();
        passWord=passwordet.getText().toString().toString();
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                hobby=mItems[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
