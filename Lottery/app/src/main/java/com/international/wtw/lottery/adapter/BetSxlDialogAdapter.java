package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.base.ViewHolder;
import com.international.wtw.lottery.json.NewOddsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/7/3.
 */

public class BetSxlDialogAdapter extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<NewOddsBean.ListBean> mDatas;
    protected LayoutInflater mInflater;

    protected OnItemClickListener mOnItemClickListener;
    private List<Integer> heights;
    private RecyclerView mRecyclerView;
    private String money;

    public BetSxlDialogAdapter(Context context, int layoutId, List<NewOddsBean.ListBean> datas, String money, RecyclerView recyclerView) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
        mRecyclerView = recyclerView;
        this.money = money;
        getRandomHeight(3);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, null, parent, mLayoutId, -1);
        //setListener(parent, viewHolder, viewType);
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

    public void convert(ViewHolder holder, NewOddsBean.ListBean item) {
        if (item.getName() != null)
            holder.setText(R.id.tv_sx_name,item.getName());
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

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        viewHolder.getConvertView().findViewById(R.id.img_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    mOnItemClickListener.onItemClick(parent, v, mDatas.get(position), position);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(ViewGroup parent, View view, NewOddsBean.ListBean bean, int position);
    }
}
