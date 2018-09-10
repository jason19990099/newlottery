package com.international.wtw.lottery.adapter;
import android.content.Context;
import android.support.v4.content.ContextCompat;
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
 * Created by user on 2017/6/21.
 */

public class LianMaAdapter extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<NewOddsBean.ListBean> mDatas;
    protected LayoutInflater mInflater;
    private int selectedPosition = -1;

    protected OnItemClickListener mOnItemClickListener;
    private List<Integer> heights;
    private RecyclerView mRecyclerView;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = -3;

    public LianMaAdapter(Context context, int layoutId, List<NewOddsBean.ListBean> list, RecyclerView recyclerView) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = list;
        mRecyclerView = recyclerView;
        getRandomHeight(3);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
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
        if (mDatas.size() > 0 && position < mDatas.size()) {
            convert(holder, mDatas.get(position));
            holder.setImageVisible(R.id.img_mark, mDatas.get(position).getSelectedState());
        }

        //if (selectedPosition == position && !mDatas.get(position).isSelected()) {
        if (selectedPosition == position || (selectedPosition == -1 && position == 0)) {
            mDatas.get(position).setSelectedState(true);
            holder.getView(R.id.fy_type).setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_selected_type));
            holder.getView(R.id.img_mark).setVisibility(View.VISIBLE);
        } else {
            mDatas.get(position).setSelectedState(false);
            holder.getView(R.id.fy_type).setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_normal_item));
            if (selectedPosition == -1)
                return;
            holder.getView(R.id.img_mark).setVisibility(View.GONE);
        }

    }

    public void convert(ViewHolder holder, NewOddsBean.ListBean bean) {
        holder.setText(R.id.tv_type_name, bean.getName());
        holder.setText(R.id.tv_type_code, bean.getOdds());
        holder.setImageVisible(R.id.img_mark, bean.getSelectedState());
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

    protected int getPosition(RecyclerView.ViewHolder viewHolder) {
        return viewHolder.getAdapterPosition();
    }

    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType) {
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    selectedPosition = getPosition(viewHolder);
                    mOnItemClickListener.onItemClick(parent, v, mDatas.get(position), position);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(ViewGroup parent, View view, NewOddsBean.ListBean bean, int position);
    }
}
