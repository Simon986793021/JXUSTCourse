package com.jxust.excellentcourse.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jxust.excellentcourse.R;
import com.jxust.excellentcourse.base.BaseFragment;

/**
 * @author Simon
 * @created 2017/2/14
 */
public class ResourseFragment extends BaseFragment {

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_resourse,container,false);
        return view;
    }

}
