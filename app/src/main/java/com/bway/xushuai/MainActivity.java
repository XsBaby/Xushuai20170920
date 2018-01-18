package com.bway.xushuai;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bway.xushuai.adapter.MainAdapter;
import com.bway.xushuai.bean.MainBean;
import com.bway.xushuai.net.OkHttp;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

public class MainActivity extends AppCompatActivity {

    private RecyclerView reCyclerView;
    private String url = "http://news-at.zhihu.com/api/4/news/latest";
    private List<MainBean.TopStoriesBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //查找控件
        initView();

        //添加数据
        initData();
    }

    private void initView() {
        reCyclerView = (RecyclerView) findViewById(R.id.reCyclerView);
    }

    private void initData() {
        //获得布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(this);
        reCyclerView.setLayoutManager(manager);

        list = new ArrayList<>();
        //网络请求数据
        OkHttp.getAsync(url, new OkHttp.DataCallBack() {
            @Override
            public void requestFailure(Request request, IOException e) {

            }

            @Override
            public void requestSuccess(String result) throws Exception {
                //使用Gson解析
                Gson gson = new Gson();
                MainBean bean = gson.fromJson(result, MainBean.class);
                //添加到list集合中
                list.addAll(bean.getTop_stories());
                System.out.println("result = " + result);

                //实例化适配器
                MainAdapter adapter = new MainAdapter(list, MainActivity.this);
                //给RecyclerView添加适配器
                reCyclerView.setAdapter(adapter);
                //刷新适配器
                adapter.notifyDataSetChanged();

                //给item设置条目监听事件
                adapter.setOnItemClickListener(new MainAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClickListener(int position, View view) {
                        //吐司新闻标题
                        Toast.makeText(MainActivity.this, list.get(position).getTitle(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onItemLongClickListener(int position, View view) {

                    }
                });
            }
        });
    }
}