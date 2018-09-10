package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.SummaryDetailsResBean;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/9/20.
 */

public class ExpandableListViewChildAdapter extends BaseAdapter {

    private Context context;
    private List<SummaryDetailsResBean> list;

    public ExpandableListViewChildAdapter(Context context, List<SummaryDetailsResBean> list) {
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
        if(view ==null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.channel_gridview_item,null);
            viewHolder.tv_zdh = (TextView) view.findViewById(R.id.tv_zdh);
            viewHolder.tv_yx_zl = (TextView) view.findViewById(R.id.tv_yx_zl);
            viewHolder.tv_yx_wf = (TextView) view.findViewById(R.id.tv_yx_wf);
            viewHolder.tv_yxwf = (TextView) view.findViewById(R.id.tv_yxwf);
            viewHolder.tv_xzsj = (TextView) view.findViewById(R.id.tv_xzsj);
            viewHolder.tv_xzje = (TextView) view.findViewById(R.id.tv_xzje);
            viewHolder.tv_kyje = (TextView) view.findViewById(R.id.tv_kyje);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_zdh.setText(list.get(position).getNo());
        viewHolder.tv_yx_zl.setText(list.get(position).getGame_name());
        viewHolder.tv_yx_wf.setText("第"+list.get(position).getRound()+"期");
        viewHolder.tv_yxwf.setText(list.get(position).getDetail());
        viewHolder.tv_xzsj.setText(list.get(position).getTime());
        viewHolder.tv_xzje.setText(list.get(position).getMoney()+"");
        viewHolder.tv_kyje.setText(list.get(position).getWin_money()+"");

        return view;
    }

    class ViewHolder{
        TextView tv_zdh,tv_yx_zl,tv_yx_wf,tv_yxwf,tv_xzsj,tv_xzje,tv_kyje;
    }

}
