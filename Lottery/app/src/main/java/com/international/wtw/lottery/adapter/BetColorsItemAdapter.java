package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.base.ViewHolder;
import com.international.wtw.lottery.json.NewOddsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/7/10.
 */

public class BetColorsItemAdapter extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<NewOddsBean.ListBean> mDatas;
    private boolean isFeng;
    private boolean isColor;

    protected OnItemClickListener mOnItemClickListener;
    private List<Integer> heights;
    private RecyclerView mRecyclerView;
    private boolean refresh;

    public BetColorsItemAdapter(Context context, int layoutId, List<NewOddsBean.ListBean> list, RecyclerView recyclerView, boolean isColor, boolean feng, OnItemClickListener listener) {
        mContext = context;
        //mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = list;
        mRecyclerView = recyclerView;
        this.isColor = isColor;
        this.isFeng = feng;
        mOnItemClickListener = listener;
        getRandomHeight(3);
    }

    public void refresh() {
        refresh = true;
        notifyDataSetChanged();
    }

    public void refreshData(List<NewOddsBean.ListBean> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    public void refreshData(List<NewOddsBean.ListBean> datas, boolean feng) {
        this.mDatas = datas;
        this.isFeng = feng;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, null, parent, mLayoutId, -1);
        setListener(parent, viewHolder, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RecyclerView.LayoutManager manager = mRecyclerView.getLayoutManager();
        if (manager instanceof StaggeredGridLayoutManager) {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = heights.get((int) (Math.random() * 3));
            holder.itemView.setLayoutParams(params);
            holder.updatePosition(position);
        }
        convert(holder, mDatas.get(position));
    }

    public void convert(ViewHolder holder, NewOddsBean.ListBean bean) {
        holder.setViewBackgroud(R.id.ly_six_item, R.drawable.bg_normal_item);
        bean.setSelectedState(false);
        if (TextUtils.isEmpty(bean.getOdds()))
            holder.setViewVisible(R.id.tv_item_odds, false);
        //保险起见，添加下面两行代码
        holder.setViewBackgroud(R.id.tv_item_odds, R.drawable.bg_normal_bottom_item);
        holder.setTextColor(R.id.tv_item_odds, R.color.bet_text_odds_gray);
        if (isFeng) {
            holder.setText(R.id.tv_item_odds, "--");
        } else {
            holder.setText(R.id.tv_item_odds, bean.getOdds());
        }
        holder.setText(R.id.tv_item_name, bean.getName());
        if (!isColor) {
            holder.setTextColor(R.id.tv_item_name, R.color.black_08090b);
            return;
        }
        holder.setBlodText(R.id.tv_item_name);
        int num = Integer.valueOf(bean.getName());
        switch (num) {
            case 1:
            case 2:
            case 7:
            case 8:
            case 12:
            case 13:
            case 18:
            case 19:
            case 23:
            case 24:
            case 29:
            case 30:
            case 34:
            case 35:
            case 40:
            case 45:
            case 46:
                holder.setViewBackgroud(R.id.img_num_bg, R.mipmap.red);
                break;
            case 3:
            case 4:
            case 9:
            case 10:
            case 14:
            case 15:
            case 20:
            case 25:
            case 26:
            case 31:
            case 36:
            case 37:
            case 41:
            case 42:
            case 47:
            case 48:
                holder.setViewBackgroud(R.id.img_num_bg, R.mipmap.blue);
                break;
            default:
                holder.setViewBackgroud(R.id.img_num_bg, R.mipmap.green);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return null == mDatas ? 0 : mDatas.size();
    }

    private void getRandomHeight(int count) {//得到随机item的高度
        heights = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            heights.add((int) (200 + Math.random() * 400));
        }
    }

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getPosition(viewHolder);
                NewOddsBean.ListBean itemBean = mDatas.get(position);
                if (isFeng) {
                    viewHolder.setViewBackgroud(R.id.ly_six_item, R.drawable.bg_normal_item);
                } else {
                    boolean selected = itemBean.getSelectedState();
                    itemBean.setSelectedState(!selected);
                    if (selected) {
                        viewHolder.setViewBackgroud(R.id.ly_six_item, R.drawable.bg_normal_item);
                        if (!isColor) {
                            viewHolder.setTextColor(R.id.tv_item_name, R.color.bet_text_name_black);
                        } else {
                            viewHolder.setTextColor(R.id.tv_item_odds, R.color.white);
                        }
                        viewHolder.setTextColor(R.id.tv_item_odds, R.color.bet_text_odds_gray);
                        viewHolder.setViewBackgroud(R.id.tv_item_odds, R.drawable.bg_normal_bottom_item);
                    } else {
                        viewHolder.setViewBackgroud(R.id.ly_six_item, R.drawable.bg_selected_item);
                        if (!isColor)
                            viewHolder.setTextColor(R.id.tv_item_name, R.color.bet_color_blue);
                        viewHolder.setTextColor(R.id.tv_item_odds, R.color.white);
                        viewHolder.setViewBackgroud(R.id.tv_item_odds, R.drawable.bg_selected_bottom_item);
                    }
                    if (null != mOnItemClickListener)
                        mOnItemClickListener.onItemClick(parent, v, itemBean, position);
                }

            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(ViewGroup parent, View view, NewOddsBean.ListBean bean, int position);
    }
}
