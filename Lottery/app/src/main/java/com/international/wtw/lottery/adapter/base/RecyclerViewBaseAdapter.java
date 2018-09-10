package com.international.wtw.lottery.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.international.wtw.lottery.utils.ClickUtill;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/6/4.
 */

public abstract class RecyclerViewBaseAdapter<T> extends RecyclerView.Adapter<ViewHolder> {
    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    protected OnItemClickListener mOnItemClickListener;
    private List<Integer> heights;
    private RecyclerView mRecyclerView;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = -3;

    public RecyclerViewBaseAdapter(Context context, int layoutId, List<T> datas, RecyclerView recyclerView) {
        mContext = context;
        //mInflater = LayoutInflater.from(context);
        //mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayoutId = layoutId;
        mDatas = datas;
        mRecyclerView = recyclerView;
        getRandomHeight(3);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<T> list){
        mDatas = list;
        notifyDataSetChanged();
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
            convert(holder, mDatas.get(position),position);
        }
    }

    public abstract void convert(ViewHolder holder, T t,int position);

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

    protected void setListener(final ViewGroup parent, final ViewHolder viewHolder, int viewType){
        viewHolder.getConvertView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ClickUtill.filter())
                    return;
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