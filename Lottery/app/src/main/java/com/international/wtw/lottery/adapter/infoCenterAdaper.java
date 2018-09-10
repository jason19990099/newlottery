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
import com.international.wtw.lottery.activity.first.InfoDetailActivity;
import com.international.wtw.lottery.json.LunbotuBean;
import com.international.wtw.lottery.utils.TImeUtil;

import java.util.List;

/**
 * Created by 18sTEVEN on 2017/9/22.
 */

public class infoCenterAdaper extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<LunbotuBean.AnnouncementsBean> datas;
    private Context context;

    public infoCenterAdaper(Context context, List<LunbotuBean.AnnouncementsBean> datas) {
        this.datas = datas;
        this.context = context;
    }

    @Override
    public int getCount() {
        return null == datas ? 0 : datas.size();
    }

    @Override
    public Object getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        mInflater = LayoutInflater.from(context);//写在这里结局了动画还没加载完点击其他地方导致的bug？等待填充数据的时间验证
        if (view == null) {
            view = mInflater.inflate(R.layout.adapter_info, null);
            viewHolder = new ViewHolder();
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        //时间戳转化为Sting或Date
        String time = TImeUtil.getDateStringByTimeSTamp(datas.get(position).getTime(), "yyyy-MM-dd ");

        viewHolder.tv_time = (TextView) view.findViewById(R.id.tv_time);
        viewHolder.tv_context = (com.international.wtw.lottery.utils.MyTextView) view.findViewById(R.id.tv_context);
        viewHolder.ll_content = (LinearLayout) view.findViewById(R.id.ll_content);
        viewHolder.tv_time.setText(time);
        viewHolder.tv_context.setText(datas.get(position).getContent());
        viewHolder.ll_content.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, InfoDetailActivity.class);
                intent.putExtra("time", time);
                intent.putExtra("text", datas.get(position).getContent());
                context.startActivity(intent);
            }
        });
        return view;
    }

    class ViewHolder {
        TextView tv_time;
        com.international.wtw.lottery.utils.MyTextView tv_context;
        LinearLayout ll_content;
    }


}
