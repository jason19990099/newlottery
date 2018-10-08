package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.newJason.PK10Rate;
import com.international.wtw.lottery.widget.NewGradview;

import java.util.List;


/**
 * PK10 兩面盤
 */
public class PK10adapter extends BaseAdapter {
    private Context context;
    private PK10Rate.DataBean.ListPlayGroupBean listPlayGroupBean;
    private LayoutInflater mInflater;

    public PK10adapter( Context context,PK10Rate.DataBean.ListPlayGroupBean listPlayGroupBean){
        this.context=context;
        this.listPlayGroupBean=listPlayGroupBean;
    }

    @Override
    public int getCount() {
        return null==listPlayGroupBean.getListPlay()?0:listPlayGroupBean.getListPlay().size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        mInflater = LayoutInflater.from(context);
        if (view == null) {
            view = mInflater.inflate(R.layout.pk10_item, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_name=(TextView) view.findViewById(R.id.tv_name);
            viewHolder.gridView= (NewGradview) view.findViewById(R.id.gradview);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.tv_name.setText(listPlayGroupBean.getListPlay().get(position).getName());
        List<PK10Rate.DataBean.ListPlayGroupBean.ListPlayBean.ListPlayRateBean>
                listPlayRate=listPlayGroupBean.getListPlay().get(position).getListPlayRate();
        BetItemAdapter2 adapter=new BetItemAdapter2(context,listPlayRate);
        viewHolder.gridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;
    }



    public class ViewHolder{
        TextView tv_name;
        NewGradview gridView;
    }

}
