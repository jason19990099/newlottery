package com.international.wtw.lottery.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.OnlinePayChannel;

import java.util.List;

public class ChannelGridAdapter extends BaseAdapter {

    private Context mContext;
    private List<OnlinePayChannel> mChannels;

    public ChannelGridAdapter(Context context, List<OnlinePayChannel> channels) {
        mContext = context;
        mChannels = channels;
    }

    @Override
    public int getCount() {
        return mChannels == null ? 0 : mChannels.size();
    }

    @Override
    public Object getItem(int position) {
        return mChannels == null ? null : mChannels.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_pay_channel_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tvChannel = (TextView) convertView.findViewById(R.id.tv_pay_channel);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvChannel.setText(mChannels.get(position).channelName);
        return convertView;
    }

    class ViewHolder {
        TextView tvChannel;
    }
}
