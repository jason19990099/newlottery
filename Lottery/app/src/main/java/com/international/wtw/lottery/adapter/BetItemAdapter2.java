package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.event.BetSelectData;
import com.international.wtw.lottery.newJason.BetDataModel;
import com.international.wtw.lottery.newJason.PK10RateModel;

import org.greenrobot.eventbus.EventBus;
import java.util.List;


public class BetItemAdapter2 extends BaseAdapter {
    private List<PK10RateModel.DataBean.ListPlayGroupBean.ListPlayBean.ListPlayRateBean> listPlayRate;
    private Context context;
    private boolean IsFeng;
    public  BetItemAdapter2(Context context, List<PK10RateModel.DataBean.ListPlayGroupBean.ListPlayBean.ListPlayRateBean> listPlayRate, boolean IsFeng){
        this.context=context;
        this.listPlayRate=listPlayRate;
        this.IsFeng=IsFeng;
    }

    @Override
    public int getCount() {
        return null==listPlayRate?0:listPlayRate.size();
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

        PK10RateModel.DataBean.ListPlayGroupBean.ListPlayBean.ListPlayRateBean  listPlayRateBean=listPlayRate.get(position);
        viewHolder.tv_item_name.setText(listPlayRateBean.getName());

        if (IsFeng) {
            viewHolder.tv_item_odds.setText("--");
            viewHolder.tv_item_odds.setTextColor(ContextCompat.getColor(context,R.color.bet_text_odds_gray));
            viewHolder.tv_item_name.setTextColor(ContextCompat.getColor(context,R.color.bet_text_name_black));
            viewHolder.ly_item_bjpk10.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_normal_item));
            viewHolder.tv_item_odds.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_normal_bottom_item));
        } else {
            if (null!=listPlayRateBean.getPlayRateValue())
            viewHolder.tv_item_odds.setText(String.valueOf(listPlayRateBean.getPlayRateValue().getValue()));
            boolean ischecked=listPlayRateBean.isSelect();
            if (ischecked) {
                viewHolder.tv_item_name.setTextColor(ContextCompat.getColor(context,R.color.bet_color_blue));
                viewHolder.tv_item_odds.setTextColor(ContextCompat.getColor(context,R.color.white));
                viewHolder.ly_item_bjpk10.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_selected_item));
                viewHolder.tv_item_odds.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_selected_bottom_item));
            } else {
                viewHolder.tv_item_odds.setTextColor(ContextCompat.getColor(context,R.color.bet_text_odds_gray));
                viewHolder.tv_item_name.setTextColor(ContextCompat.getColor(context,R.color.bet_text_name_black));
                viewHolder.ly_item_bjpk10.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_normal_item));
                viewHolder.tv_item_odds.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_normal_bottom_item));
            }
        }

        viewHolder.ly_item_bjpk10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (IsFeng) {
                    viewHolder.ly_item_bjpk10.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_normal_item));
                } else {
                    boolean selected = listPlayRateBean.isSelect();
                    listPlayRateBean.setSelect(!selected);
                    listPlayRate.get(position).setSelect(!selected);
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
                    BetDataModel betData=new BetDataModel();
                    betData.setPlayGroupCode(listPlayRateBean.getPlayGroupCode());
                    betData.setPlayCode(listPlayRateBean.getPlayCode());
                    betData.setPlayRateCode(listPlayRateBean.getCode());
                    betData.setPlayRateValueType(String.valueOf(listPlayRateBean.getPlayRateValueType()));
                    betData.setPlayRateValueId(listPlayRateBean.getPlayRateValue().getId());
                    betData.setPlayRateValue(String.valueOf(listPlayRateBean.getPlayRateValue().getValue()));
                    betData.setPlayname(listPlayRateBean.getPlayName());
                    betData.setBetitemname(listPlayRateBean.getName());

                    EventBus.getDefault().postSticky(new BetSelectData(!selected,betData,false));

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
