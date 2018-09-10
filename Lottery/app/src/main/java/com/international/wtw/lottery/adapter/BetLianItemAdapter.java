package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.NewOddsBean;

import java.util.List;

/**
 * Created by wuya on 2017/6/16.
 */

public class BetLianItemAdapter extends BaseAdapter {

    private List<NewOddsBean.ListBean> list;
    private Context context;
    private ItemBettingCallback itemBettingCallback;
    private boolean isFeng;

    public BetLianItemAdapter(List<NewOddsBean.ListBean> list, Context context, boolean isFeng,ItemBettingCallback itemBettingCallback) {
        this.list = list;
        this.context = context;
        this.isFeng = isFeng;
        this.itemBettingCallback = itemBettingCallback;
    }

    @Override
    public int getCount() {
        return list.size();
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_bet_lian_lv, null);
            viewHolder.lmp_lv_item_tv_pm = (TextView) convertView.findViewById(R.id.lmp_lv_item_tv_pm);
            viewHolder.lmp_lv_item_tv_pl = (TextView) convertView.findViewById(R.id.lmp_lv_item_tv_pl);
            viewHolder.ly_item_bjpk10 = (RelativeLayout) convertView.findViewById(R.id.ly_item_bet);
            viewHolder.img_angle = (ImageView) convertView.findViewById(R.id.img_angle);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        NewOddsBean.ListBean itemBean = list.get(position);
        viewHolder.lmp_lv_item_tv_pm.setText(itemBean.getName());
        viewHolder.lmp_lv_item_tv_pl.setText(itemBean.getOdds());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean selected = itemBean.getSelectedState();
                itemBean.setSelectedState(!selected);
                if (isFeng){
                    viewHolder.ly_item_bjpk10.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_normal_item));
                   // viewHolder.img_angle.setVisibility(View.GONE);
                    return;
                }
                if (selected) {
                    viewHolder.ly_item_bjpk10.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_normal_item));
                   // viewHolder.img_angle.setVisibility(View.GONE);
                } else {
                    viewHolder.ly_item_bjpk10.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_selected_item));
                   // viewHolder.img_angle.setVisibility(View.VISIBLE);
                }
                itemBettingCallback.ItemClick();
            }
        });

        return convertView;
    }

    class ViewHolder {
        private TextView lmp_lv_item_tv_pm, lmp_lv_item_tv_pl;
        private RelativeLayout ly_item_bjpk10;
        private ImageView img_angle;
    }

    public interface ItemBettingCallback {
        void ItemClick();
    }

}
