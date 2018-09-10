package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.lottery.PK10Activity;
import com.international.wtw.lottery.json.NewOddsBean;
import java.util.List;


public class BetItemAdapter extends BaseAdapter {

    private List<NewOddsBean.ListBean> list;
    private Context context;
    private ItemBettingCallback itemBettingCallback;
    private boolean IsFeng;

    public BetItemAdapter(List<NewOddsBean.ListBean> list, Context context,
                          ItemBettingCallback itemBettingCallback, boolean IsFeng) {
        this.list = list;
        this.context = context;
        this.itemBettingCallback = itemBettingCallback;
        this.IsFeng = IsFeng;
    }


    @Override
    public int getCount() {
        return null==list?0:list.size();
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
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_bet_lv, null);
            viewHolder.tv_item_name = (TextView) view.findViewById(R.id.lmp_lv_item_tv_pm);
            viewHolder.tv_item_odds = (TextView) view.findViewById(R.id.lmp_lv_item_tv_pl);
            viewHolder.ly_item_bjpk10 = (LinearLayout) view.findViewById(R.id.ly_item_bet);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        NewOddsBean.ListBean itemBean = list.get(position);
        viewHolder.tv_item_name.setText(itemBean.getName());

        if (IsFeng) {
            viewHolder.tv_item_odds.setText("--");
        } else {
            viewHolder.tv_item_odds.setText(itemBean.getOdds());
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IsFeng) {
                    viewHolder.ly_item_bjpk10.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_normal_item));
                } else {
                    boolean selected = itemBean.getSelectedState();
                    itemBean.setSelectedState(!selected);
                    if (selected) {
                        viewHolder.tv_item_odds.setTextColor(ContextCompat.getColor(context,R.color.bet_text_odds_gray));
                        viewHolder.tv_item_name.setTextColor(ContextCompat.getColor(context,R.color.bet_text_name_black));
                        viewHolder.ly_item_bjpk10.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_normal_item));
                        viewHolder.tv_item_odds.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_normal_bottom_item));
                    } else {
                        viewHolder.tv_item_name.setTextColor(ContextCompat.getColor(context,R.color.bet_color_blue));
                        viewHolder.tv_item_odds.setTextColor(ContextCompat.getColor(context,R.color.white));
                        viewHolder.ly_item_bjpk10.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_selected_item));
                        viewHolder.tv_item_odds.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_selected_bottom_item));
                    }
                    itemBettingCallback.ItemClick();

                }
            }
        });

        return view;
    }



    class ViewHolder {
        private TextView tv_item_name, tv_item_odds;
        private LinearLayout ly_item_bjpk10;
    }

    public interface ItemBettingCallback {
        void ItemClick();
    }


}
