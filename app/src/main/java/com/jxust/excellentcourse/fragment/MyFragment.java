package com.jxust.excellentcourse.fragment;


import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.jxust.excellentcourse.R;
import com.jxust.excellentcourse.Utils.Utils;
import com.jxust.excellentcourse.activity.MainActivity;
import com.jxust.excellentcourse.activity.MyInfoActivity;
import com.jxust.excellentcourse.base.BaseFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ListView listView;

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        listView = (ListView) view.findViewById(R.id.lv_fragment_my);
        loadData();
        listView.setOnItemClickListener(this);
        return view;
    }

    private void loadData() {
        SimpleAdapter adapter = new SimpleAdapter(mactivity, getData(), R.layout.item_fragment_my, new String[]{"left", "center", "right"}, new int[]{R.id.im_listview_left, R.id.tv_listview_center, R.id.im_listview_right});
        listView.setAdapter(adapter);
    }

    public List<? extends Map<String, ?>> getData() {
        List<Map<String, Object>> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("left", R.drawable.myinfo);
        map.put("center", "我的资料");
        map.put("right", R.drawable.gogogo);
        list.add(map);

        map = new HashMap<>();
        map.put("left", R.drawable.loginout);
        map.put("center", "退出登录");
        map.put("right", R.drawable.gogogo);
        list.add(map);

        return list;

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                if (Utils.isNetworkAvailable(mactivity))
                {
                    Intent intent=new Intent(mactivity,MyInfoActivity.class);
                    startActivity(intent);
                }
                else {
                    Utils.showNoNetToast(mactivity);
                }
                break;
            case 1:
                if (Utils.isNetworkAvailable(getActivity())) {
                    quitDialog();
                } else {
                    Utils.showToast(getActivity(), "请检查网络");
                }
                break;
            default:
                break;
        }
    }

    /*
             退出登录  对话框
              */
    private void quitDialog() {
        final AlertDialog quitDialog = new AlertDialog.Builder(getActivity()).create();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_loginout, null, false);
        quitDialog.show();
                /*
                 *  直接从xml设置dialog不能铺满整个宽度 ，通过以下代码设置
                 */
        Window window = quitDialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.FILL_PARENT;
        window.setAttributes(layoutParams);

        quitDialog.setContentView(view);
        quitDialog.getWindow().setGravity(Gravity.BOTTOM);

        TextView logoutTextView = (TextView) view.findViewById(R.id.tv_logout);
        TextView cancelTextView = (TextView) view.findViewById(R.id.tv_cancel);
        logoutTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitDialog.cancel();
                AVUser.getCurrentUser().logOut();
                startActivity(new Intent(mactivity,MainActivity.class));
            }
        });
        cancelTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quitDialog.cancel();
            }
        });
    }
}