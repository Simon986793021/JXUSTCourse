package com.jxust.excellentcourse.fragment;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jxust.excellentcourse.R;
import com.jxust.excellentcourse.Utils.Utils;
import com.jxust.excellentcourse.base.BaseFragment;
import com.panxw.android.imageindicator.ImageIndicatorView;

/**
 * @author Simon
 * @created 2017/2/14
 */
public class CourseFragment extends BaseFragment {
    private ImageIndicatorView imageIndicatorView;
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_homepage,container,false);
        imageIndicatorView= (ImageIndicatorView) view.findViewById(R.id.indicate_view);
        Utils.loadImage(imageIndicatorView);
        return view;
    }

}
