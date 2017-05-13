package com.jxust.excellentcourse.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.jxust.excellentcourse.R;
import com.jxust.excellentcourse.Utils.Utils;

/**
 * Created by Simon on 2017/5/12.
 */

public class MyInfoActivity extends Activity{
    private TextView back;
    private TextView toolbarcenter;
    private TextView usernametextview;
    private TextView liketextview;
    private TextView phonenumtextview;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);
        usernametextview= (TextView) findViewById(R.id.tv_username);
        liketextview= (TextView) findViewById(R.id.tv_like);
        phonenumtextview= (TextView) findViewById(R.id.tv_phonenum);
        back= (TextView) findViewById(R.id.tv_toolbar_lefttext);
        toolbarcenter= (TextView) findViewById(R.id.tv_toolbar_centertext);
        toolbarcenter.setText("我的资料");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        if (Utils.isNetworkAvailable(MyInfoActivity.this))
        {
            loadData();
        }
        else {
            Utils.showNoNetToast(MyInfoActivity.this);
        }
    }

    private void loadData() {
        AVUser user=AVUser.getCurrentUser();
        String username=user.getUsername();
        String phonenum=user.getMobilePhoneNumber();
        String like=user.get("academy").toString();
        if (!TextUtils.isEmpty(username))
        {
        usernametextview.setText(username);
         }
         if (!TextUtils.isEmpty(phonenum))
         {
             phonenumtextview.setText(phonenum);
         }
         if (!TextUtils.isEmpty(like))
         {
             liketextview.setText(like);
         }

    }
}
