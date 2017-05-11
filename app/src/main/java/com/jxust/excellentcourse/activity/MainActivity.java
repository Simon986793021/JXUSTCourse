package com.jxust.excellentcourse.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioGroup;

import com.jxust.excellentcourse.R;
import com.jxust.excellentcourse.fragment.CourseFragment;
import com.jxust.excellentcourse.fragment.HomePageFragment;
import com.jxust.excellentcourse.fragment.MyFragment;
import com.jxust.excellentcourse.fragment.ResourseFragment;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
    private  int curCursor;
    private RadioGroup radioGroup;
    private HomePageFragment homePageFragment=new HomePageFragment();
    private CourseFragment courseFragment=new CourseFragment();
    private ResourseFragment resourseFragment=new ResourseFragment();
    private MyFragment myFragment=new MyFragment();
    private List<Fragment> fragmentList= Arrays.asList(homePageFragment,courseFragment,resourseFragment, myFragment);
    private android.app.FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager=getFragmentManager();
        initFootBar();
    }
    private void initFootBar() {
        radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home:
                        curCursor = 0;
                        break;
                    case R.id.foot_bar_campus:
                        curCursor = 1;
                        break;
                    case R.id.foot_bar_discover:
                        curCursor = 2;
                        break;
                    case R.id.foot_bar_mime:
                        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                        startActivity(intent);
                        //curCursor = 3;

                        break;
                }
                addFragmentToStack(curCursor);
            }
        });

        addFragmentToStack(0);
    }

    private void addFragmentToStack(int cursor) {
        android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentList.get(cursor);
        if (!fragment.isAdded()) {
            fragmentTransaction.add(R.id.fl_fragment, fragment);
        }
        for (int i = 0; i < fragmentList.size(); i++) {
            Fragment f = fragmentList.get(i);
            if (i == cursor && f.isAdded()) {
                fragmentTransaction.show(f);
            } else if (f != null && f.isAdded() && f.isVisible()) {
                fragmentTransaction.hide(f);
            }
        }
        fragmentTransaction.commitAllowingStateLoss();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = fragmentList.get(curCursor);
        if (fragment != null){
            fragment.onActivityResult(requestCode,resultCode,data);
        }
    }
}
