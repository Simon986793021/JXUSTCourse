package com.jxust.excellentcourse.fragment;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.jxust.excellentcourse.R;
import com.jxust.excellentcourse.Utils.Utils;
import com.jxust.excellentcourse.activity.CourseDetailActivity;
import com.jxust.excellentcourse.activity.CourseListActivity;
import com.jxust.excellentcourse.base.BaseFragment;
import com.panxw.android.imageindicator.ImageIndicatorView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author  by Simon
 * @created on 2017/2/14
 */
public class HomePageFragment extends BaseFragment {
    private ImageIndicatorView imageIndicatorView;
    private GridView gridView;
    private ListView listview;
    private String[] mItems = {"信息", "外语", "经管", "建筑", "材料", "冶金", "机械", "电气","文法","资环"};
    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_homepage,container,false);
        imageIndicatorView= (ImageIndicatorView) view.findViewById(R.id.indicate_view);
        Utils.loadImage(imageIndicatorView);
        listview= (ListView) view.findViewById(R.id.lv_fragment_course);
        gridView= (GridView) view.findViewById(R.id.gv_academy);
        loadGridView();
        if (Utils.isNetworkAvailable(mactivity))
        {
            loadListView();
            onClick();
        }
        else {
            Utils.showNoNetToast(mactivity);
        }
        return view;
    }

    private void onClick() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {

                AVQuery<AVObject> query = new AVQuery<>("Course");
                query.whereEqualTo("type","推荐");
                query.orderByAscending("createdAt");
                query.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        if (list!=null)
                        {
                            Intent intent=new Intent(mactivity,CourseDetailActivity.class);
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
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String academy=mItems[position];
                Intent intent1=new Intent(mactivity,CourseListActivity.class);
                intent1.putExtra("academy",academy);
                startActivity(intent1);
            }
        });
    }

    private void loadListView() {
       Utils.loadCourseFromAvo(listview,"推荐",mactivity);
    }


    private void loadGridView() {
        SimpleAdapter adapter=new SimpleAdapter(mactivity,getData(),R.layout.item_gridview_academy,new String[]{"img","text"},new int[]{R.id.iv_academy,R.id.tv_academy});
        gridView.setAdapter(adapter);
    }

    private List<? extends Map<String,?>> getData() {
        List list=new ArrayList();

        Map<String,Object> map=new HashMap<>();
        map.put("img",R.drawable.xinxi);
        map.put("text","信息");
        list.add(map);

        map=new HashMap<>();
        map.put("img",R.drawable.waiyu);
        map.put("text","外语");
        list.add(map);

        map=new HashMap<>();
        map.put("img",R.drawable.jingguan);
        map.put("text","经管");
        list.add(map);

        map=new HashMap<>();
        map.put("img",R.drawable.jianzhu);
        map.put("text","建筑");
        list.add(map);

        map=new HashMap<>();
        map.put("img",R.drawable.cailiao);
        map.put("text","材料");
        list.add(map);

        map=new HashMap<>();
        map.put("img",R.drawable.yejin);
        map.put("text","冶金");
        list.add(map);

        map=new HashMap<>();
        map.put("img",R.drawable.jixie);
        map.put("text","机械");
        list.add(map);

        map=new HashMap<>();
        map.put("img",R.drawable.dianqi);
        map.put("text","电气");
        list.add(map);

        map=new HashMap<>();
        map.put("img",R.drawable.wenfa);
        map.put("text","文法");
        list.add(map);

        map=new HashMap<>();
        map.put("img",R.drawable.zihuan);
        map.put("text","资环");
        list.add(map);

        return list;
    }

}
