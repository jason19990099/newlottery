package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.mine.MsgDetailActivity;
import com.international.wtw.lottery.json.MessageBean;
import com.international.wtw.lottery.utils.MyTextView;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/12/5.
 */

public class MyMessageAdapter extends BaseAdapter {

    private Context context;
    private List<MessageBean.MsgListBean> list;

    public MyMessageAdapter(Context context, List<MessageBean.MsgListBean> list) {
        this.context = context;
        this.list = list;
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
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.my_message_item, null);
            viewHolder.tv_new = (TextView) view.findViewById(R.id.tv_new);
            viewHolder.tv_time = (TextView) view.findViewById(R.id.tv_time_msg);
            viewHolder.tv_context = (MyTextView) view.findViewById(R.id.tv_context_msg);
            viewHolder.ll_content = (LinearLayout) view.findViewById(R.id.ll_content_msg);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        if (list.get(position).getIsRead() == 0) {
            viewHolder.tv_new.setVisibility(View.VISIBLE);
        }

        viewHolder.tv_time.setText(list.get(position).getAddtime());
        viewHolder.tv_context.setText(list.get(position).getComment());
        viewHolder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MsgDetailActivity.class);
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("time", list.get(position).getAddtime());
                intent.putExtra("text", list.get(position).getComment());
                context.startActivity(intent);
            }
        });

        return view;
    }

    class ViewHolder {
        TextView tv_time, tv_new;
        com.international.wtw.lottery.utils.MyTextView tv_context;
        LinearLayout ll_content;
    }
}
