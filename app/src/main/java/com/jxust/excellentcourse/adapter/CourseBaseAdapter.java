package com.jxust.excellentcourse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVObject;
import com.bumptech.glide.Glide;
import com.jxust.excellentcourse.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Simon on 2017/5/25.
 */

public class CourseBaseAdapter extends BaseAdapter {
    private List<AVObject> list=new ArrayList<>();
    private Context mcontext;

    public CourseBaseAdapter(List<AVObject> list,Context context)
    {
        this.list=list;
        mcontext=context;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder==null)
        {

            convertView= LayoutInflater.from(mcontext).inflate(R.layout.item_listview_course,null,false);
            viewHolder=new ViewHolder();
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.iv_title);
            viewHolder.title= (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.content= (TextView) convertView.findViewById(R.id.tv_content);
            viewHolder.likeperson= (TextView) convertView.findViewById(R.id.tv_likeperson);
            viewHolder.viewperson= (TextView) convertView.findViewById(R.id.tv_viewperson);
            convertView.setTag(viewHolder);
            }
        else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
            viewHolder.title.setText(list.get(position).getString("title"));
            viewHolder.content.setText(list.get(position).getString("content"));
            String likeperson=list.get(position).get("likeperson").toString();
        if (likeperson!=null)
        {
            viewHolder.likeperson.setText("收藏人数："+likeperson);
        }
        else {
            viewHolder.likeperson.setText("0");
        }
        String viewperson=list.get(position).get("viewperson").toString();
        if (viewperson!=null)
        {
            viewHolder.viewperson.setText("浏览人数："+viewperson);
        }
        else {
            viewHolder.viewperson.setText("0");
        }
        String url=list.get(position).get("coursePicUrl").toString();
        Glide.with(mcontext).load(url)
                .error(R.drawable.error)
                .into(viewHolder.imageView);//Glide 加载图片
        return convertView;
    }
    private  class ViewHolder{
        private ImageView imageView;
        private TextView title;
        private TextView content;
        private TextView likeperson;
        private TextView viewperson;
    }
}
