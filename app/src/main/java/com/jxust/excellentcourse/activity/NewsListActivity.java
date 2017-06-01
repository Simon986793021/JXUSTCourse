package com.jxust.excellentcourse.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jxust.excellentcourse.R;
import com.jxust.excellentcourse.Utils.Utils;
import com.jxust.excellentcourse.adapter.NewsListBaseAdapter;
import com.jxust.excellentcourse.model.News;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Simon on 2017/5/27.
 */

public class NewsListActivity extends Activity{
    private TextView back;
    private TextView toolbarcenter;
    private OkHttpClient client=new OkHttpClient();
    private ListView listview;
    private List<News.Second.Third> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        back= (TextView) findViewById(R.id.tv_toolbar_lefttext);
        toolbarcenter= (TextView) findViewById(R.id.tv_toolbar_centertext);
        toolbarcenter.setText("新闻");
        listview= (ListView) findViewById(R.id.lv_news_list);
        if (Utils.isNetworkAvailable(NewsListActivity.this))
        {
            loadNewsList();
        }
        else {
            Utils.showNoNetToast(NewsListActivity.this);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent=new Intent(NewsListActivity.this,NewsDetailActivity.class);
                    String url=list.get(position).url;
                    intent.putExtra("url",url);
                    startActivity(intent);
            }
        });
    }

    private void loadNewsList() {
        Intent intent=getIntent();
        String type=intent.getStringExtra("type");
        loadData(type);

    }
    private void loadData(String type) {
        String url="http://v.juhe.cn/toutiao/index?type="+type+"&key=65d4c89f2460e131bd8b288f3f70bff6";
        Log.i(">>>>>",url);
        final Gson gson =new Gson();
        final Request request=new Request.Builder()
                .get()
                .url(url)
                .build();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Response response;
                try {
                    response=client.newCall(request).execute();
                    if (response.isSuccessful())
                    {
                        String content=response.body().string();
                        Log.i(">>>>>",content);
                        News news=gson.fromJson(content,News.class);

                        final String reason=news.reason;

                        News.Second second=news.result;
                        list=second.data;
                                runOnUiThread(new Runnable() {
                                        @Override
                                         public void run() {
                                       listview.setAdapter(new NewsListBaseAdapter(list,NewsListActivity.this));
                                    }
                                });

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
