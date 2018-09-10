package com.international.wtw.lottery.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.utils.CountDown;
import com.international.wtw.lottery.json.HomeResultMsgBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/6/4.
 */

public class RecyclerViewHomeBaseAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private List<HomeResultMsgBean> mDatas;
    private OnItemClickListener mOnItemClickListener;
    private List<Integer> heights;
    private RecyclerView mRecyclerView;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = -3;

    public RecyclerViewHomeBaseAdapter(Context context, List<HomeResultMsgBean> datas, RecyclerView recyclerView) {
        mContext = context;
        mDatas = datas;
        mRecyclerView = recyclerView;
        getRandomHeight(3);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<HomeResultMsgBean> list) {
        mDatas = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, null, parent, R.layout.item_mainfragment_gridview2, -1);
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
        if (mDatas.size() > 0 && position < mDatas.size()) {
            convert(holder, mDatas.get(position));
        }
    }

    public void convert(ViewHolder holder, HomeResultMsgBean bean) {
        holder.setText(R.id.textTitle, bean.getGame_name());

        if (null!=bean.getIsOpen()&&Integer.valueOf(bean.getIsOpen()) == 0) {
            holder.setText(R.id.tv_nper, "未开盘");
        }
        if (null!=bean.getIsOpen()&&Integer.valueOf(bean.getIsOpen())== 1) {
            holder.setText(R.id.tv_nper, "第" + bean.getRound() + "期");
        }

        holder.setText(R.id.tv_lottery_number, bean.getResult());

        TextView textView = holder.getView(R.id.textTime);
        Long times;
        try {
            times = Long.valueOf(bean.getTime()) - Long.valueOf(bean.getServerTime());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            textView.setText("00:00");
            return;
        }
        new CountDown(textView, times);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private void getRandomHeight(int count) {//得到随机item的高度
        heights = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            heights.add((int) (200 + Math.random() * 400));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= mDatas.size()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_NORMAL;
        }
    }

    private int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    mOnItemClickListener.onItemClick(parent, v, mDatas.get(position), position);
                }
            }
        });
    }


    public interface OnItemClickListener<T> {
        void onItemClick(ViewGroup parent, View view, T t, int position);
    }
}