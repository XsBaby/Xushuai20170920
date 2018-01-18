package com.bway.xushuai.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bway.xushuai.R;
import com.bway.xushuai.bean.MainBean;

import java.util.List;

/**
 * date:2017/9/20
 * author:徐帅(acer)
 * funcation: RecyclerView的适配器
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder> {

    private List<MainBean.TopStoriesBean> list;
    private Context context;

    public MainAdapter(List<MainBean.TopStoriesBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //绑定视图
        View view = View.inflate(context, R.layout.item, null);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        //请求图片并赋值
        Glide.with(context).load(list.get(position).getImage()).into(holder.image);
        holder.title.setText(list.get(position).getTitle());

        //设置监听事件
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClickListener(position, v);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView title;

        public MyViewHolder(View itemView) {
            super(itemView);
            //查找控件
            image = (ImageView) itemView.findViewById(R.id.image);
            title = (TextView) itemView.findViewById(R.id.title);
        }
    }

    //添加自定义监听事件
    public interface OnItemClickListener {
        void onItemClickListener(int position, View view);

        void onItemLongClickListener(int position, View view);
    }

    public OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}