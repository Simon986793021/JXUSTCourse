package com.jxust.excellentcourse.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.jxust.excellentcourse.R;
import com.jxust.excellentcourse.Utils.Utils;
import com.jxust.excellentcourse.adapter.CourseBaseAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Simon on 2017/5/14.
 */

public class CourseListActivity extends Activity {
    private ListView courseshow;
    private TextView back;
    private TextView toolbarcenter;
    private String academy;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_list);
        courseshow= (ListView) findViewById(R.id.lv_activity_course);
        back= (TextView) findViewById(R.id.tv_toolbar_lefttext);
        toolbarcenter= (TextView) findViewById(R.id.tv_toolbar_centertext);
        toolbarcenter.setText("精品课程");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        academy=getIntent().getStringExtra("academy");
        Utils.loadCourseFromAvo(courseshow,academy,CourseListActivity.this);
        courseshow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AVQuery<AVObject> query = new AVQuery<>("Course");
                query.whereEqualTo("type",academy);
                query.orderByAscending("createdAt");
                query.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        if (list!=null)
                        {
                            Intent intent=new Intent(CourseListActivity.this,CourseDetailActivity.class);
                            AVObject course=list.get(position);
                            String viewPerson=course.get("viewperson").toString();
                            Log.i("<<<",viewPerson);
                            int viewcount=Integer.parseInt(viewPerson);
                            viewcount++;
                            course.put("viewperson",viewcount+"");
                            course.saveInBackground();
                            Log.i(">>>>",course.toString());
                            Bundle bundle=new Bundle();
                            bundle.putParcelable("course",course);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }


}
