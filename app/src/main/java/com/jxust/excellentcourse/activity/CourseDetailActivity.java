package com.jxust.excellentcourse.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.bumptech.glide.Glide;
import com.jxust.excellentcourse.R;
import com.jxust.excellentcourse.Utils.Utils;

/**
 * Created by Simon on 2017/5/14.
 */

public class CourseDetailActivity  extends Activity{
    private TextView back;
    private TextView toolbarcenter;
    private ImageView imageview;
    private TextView content;
    private Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        back= (TextView) findViewById(R.id.tv_toolbar_lefttext);
        imageview= (ImageView) findViewById(R.id.iv_course_detail_pic);
        content= (TextView) findViewById(R.id.tv_course_detail);
        button= (Button) findViewById(R.id.bt_like);
        toolbarcenter= (TextView) findViewById(R.id.tv_toolbar_centertext);
        toolbarcenter.setText("课程详情");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showToast(CourseDetailActivity.this,"收藏成功");
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        loadData();
    }

    private void loadData() {
        AVObject course=getIntent().getParcelableExtra("course");
        Log.i(">>>>",course.toString());
        Glide.with(CourseDetailActivity.this).load(course.getString("coursePicUrl").toString())
                .override(600,300)
                .into(imageview);

        content.setText(course.getString("detail"));
    }
}
