package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.PopupMenuBean;

import java.util.List;

/**
 * Created by XIAOYAN on 2018/2/13.
 */

public class PopupMenuAdapter extends BaseAdapter {

    private Context context;
    private List<PopupMenuBean> list;

    public PopupMenuAdapter(Context context, List<PopupMenuBean> list) {
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
        if(view==null){
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.popup_menu_item, null);
            viewHolder.tv_lottery_name = (TextView) view.findViewById(R.id.tv_lottery_name);
            viewHolder.ll_popupmenu = (RelativeLayout) view.findViewById(R.id.ll_popupmenu);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_lottery_name.setText(list.get(position).getName());
//        viewHolder.ll_popupmenu.getBackground().setAlpha(240);

        return view;
    }

    class ViewHolder{
        TextView tv_lottery_name;
        RelativeLayout ll_popupmenu;
    }

}
