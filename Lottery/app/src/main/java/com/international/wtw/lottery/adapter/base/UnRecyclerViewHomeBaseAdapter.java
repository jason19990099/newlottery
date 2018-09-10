package com.international.wtw.lottery.adapter.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.HomeMsgBean;
import com.international.wtw.lottery.json.HomeResultMsgBean;
import com.international.wtw.lottery.utils.CountDown;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2017/6/4.
 */

public class UnRecyclerViewHomeBaseAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context mContext;
    private List<HomeMsgBean> mDatas;
    private OnItemClickListener mOnItemClickListener;
    private List<Integer> heights;
    private RecyclerView mRecyclerView;
    private static final int TYPE_NORMAL = 0;
    private static final int TYPE_FOOTER = -3;

    public UnRecyclerViewHomeBaseAdapter(Context context, List<HomeMsgBean> datas, RecyclerView recyclerView) {
        mContext = context;
        mDatas = datas;
        mRecyclerView = recyclerView;
        getRandomHeight(3);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public void setData(List<HomeMsgBean> list) {
        mDatas = list;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder viewHolder = ViewHolder.get(mContext, null, parent, R.layout.item_mainfragment_gridview_un, -1);
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

    public void convert(ViewHolder holder, HomeMsgBean bean) {

        holder.setText(R.id.tv_type_name, bean.getType_name());
        String type_name = bean.getType_name();
        if (type_name.equals("北京赛车")) {
            holder.setImageResource(R.id.img_type, R.mipmap.icon_item_pj_pk10);
        }
        if (type_name.equals("幸运飞艇")) {
            holder.setImageResource(R.id.img_type, R.mipmap.icon_item_lucky_fly);
        }
        if (type_name.equals("广东快乐十分")) {
            holder.setImageResource(R.id.img_type, R.mipmap.icon_item_gd_happy);
        }
        if (type_name.equals("重庆幸运农场")) {
            holder.setImageResource(R.id.img_type, R.mipmap.icon_item_cj_lucky_lottery);
        }
        if (type_name.equals("重庆时时彩")) {
            holder.setImageResource(R.id.img_type, R.mipmap.icon_item_cj_lottery);
        }
        if (type_name.equals("PC蛋蛋")) {
            holder.setImageResource(R.id.img_type, R.mipmap.icon_item_pc_dd);
        }
        if (type_name.equals("香港六合彩")) {
            holder.setImageResource(R.id.img_type, R.mipmap.icon_item_hk_mark_six);
        }
        if (type_name.equals("在线客服")) {
            holder.setImageResource(R.id.img_type, R.mipmap.icon_item_kf);
        }
        if (type_name.equals("试玩")) {
            holder.setImageResource(R.id.img_type, R.mipmap.icon_item_ag);
        }
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