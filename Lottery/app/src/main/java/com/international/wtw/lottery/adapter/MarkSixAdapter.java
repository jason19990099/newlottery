package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.base.ViewHolder;
import com.international.wtw.lottery.json.NewOddsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a bin on 2017/6/21.
 */

public class MarkSixAdapter extends RecyclerView.Adapter<ViewHolder>{
    private Context mContext;
    private int mLayoutId;
    private List<NewOddsBean.ListBean> mDatas;
    private LayoutInflater mInflater;
    private int selectedPosition = -1;
    private OnItemClickListener mOnItemClickListener;
    private List<Integer> heights;
    private RecyclerView mRecyclerView;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = -3;

    public MarkSixAdapter(Context context, int layoutId, List<NewOddsBean.ListBean> datas, RecyclerView recyclerView) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;
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
        if (viewType == TYPE_NORMAL) {
            ViewHolder viewHolder = ViewHolder.get(mContext, null, parent, mLayoutId, -1);
            setListener(parent, viewHolder, viewType);
            return viewHolder;
        } else {
            //View itemView = LayoutInflater.from(mContext).inflate(R.layout.listview_footer, parent, false);
            //ViewHolder holder = new ViewHolder(mContext, itemView, parent, -1);
            return null;
        }
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
            holder.setImageVisible(R.id.img_mark,mDatas.get(position).getSelectedState());
        }

        //if (selectedPosition == position && !mDatas.get(position).isSelected()) {
        if (selectedPosition == position ||(selectedPosition==-1&&position==0)) {
            mDatas.get(position).setSelectedState(true);
            holder.getView(R.id.fy_type).setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_selected_type));
            holder.getView(R.id.img_mark).setVisibility(View.VISIBLE);
        } else {
            mDatas.get(position).setSelectedState(false);
            holder.getView(R.id.fy_type).setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_normal_item));
            if (selectedPosition==-1)
                return;
            holder.getView(R.id.img_mark).setVisibility(View.GONE);
        }
    }

    private void convert(ViewHolder holder, NewOddsBean.ListBean listBean){
        holder.setText(R.id.tv_type_name,listBean.getName());
        holder.setImageVisible(R.id.img_mark,listBean.getSelectedState());
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

    private void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType){
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    int position = getPosition(viewHolder);
                    selectedPosition = getPosition(viewHolder);
                    if (position==-1) //又是频繁切换过快position会为-1
                        position=0;
                    mOnItemClickListener.onItemClick(parent, v, mDatas.get(position), position);
                }
            }
        });
    }

    public interface OnItemClickListener {
        void onItemClick(ViewGroup parent, View view, NewOddsBean.ListBean listBean, int position);
    }
}
