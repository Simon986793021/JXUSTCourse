package com.jxust.excellentcourse.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jxust.excellentcourse.R;
import com.jxust.excellentcourse.activity.NewsListActivity;
import com.jxust.excellentcourse.base.BaseFragment;

/**
 * @author Simon
 * @created 2017/2/14
 */
public class ResourseFragment extends BaseFragment implements View.OnClickListener{
    private LinearLayout top;
    private LinearLayout entertainment;
    private LinearLayout science;
    private LinearLayout sport;
    private LinearLayout international;
    private LinearLayout fashion;
    private Intent  intent;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_resourse,container,false);
        top= (LinearLayout) view.findViewById(R.id.ll_top);
        entertainment= (LinearLayout) view.findViewById(R.id.ll_yule);
        science= (LinearLayout) view.findViewById(R.id.ll_keji);
        sport= (LinearLayout) view.findViewById(R.id.ll_tiyu);
        international= (LinearLayout) view.findViewById(R.id.ll_guoji);
        fashion= (LinearLayout) view.findViewById(R.id.ll_shishang);
        top.setOnClickListener(this);
        entertainment.setOnClickListener(this);
        science.setOnClickListener(this);
        sport.setOnClickListener(this);
        international.setOnClickListener(this);
        fashion.setOnClickListener(this);
        intent=new Intent(mactivity,NewsListActivity.class);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.ll_top:

                intent.putExtra("type","top");
                startActivity(intent);
                break;
            case R.id.ll_yule:

                intent.putExtra("type","yule");
                startActivity(intent);
                break;
            case R.id.ll_keji:
                intent.putExtra("type","keji");
                startActivity(intent);
                break;
            case R.id.ll_tiyu:
                intent.putExtra("type","tiyu");
                startActivity(intent);
                break;
            case R.id.ll_guoji:
                intent.putExtra("type","guoji");
                startActivity(intent);
                break;
            case R.id.ll_shishang:
                intent.putExtra("type","shishang");
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
