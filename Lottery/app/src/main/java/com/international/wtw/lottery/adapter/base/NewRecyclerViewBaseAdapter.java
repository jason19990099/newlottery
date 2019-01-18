package com.international.wtw.lottery.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.newJason.LotteryinfoModel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class NewRecyclerViewBaseAdapter extends BaseAdapter {

    private Context mContext;
    private List<LotteryinfoModel> mDatas;

    public NewRecyclerViewBaseAdapter(Context mContext, List<LotteryinfoModel> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() { return null==mDatas?0:mDatas.size(); }
    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_mainfragment_gridview_un, null);
            viewHolder.img_type = (ImageView) view.findViewById(R.id.img_type);
            viewHolder.img_hot = (ImageView) view.findViewById(R.id.img_hot);
            viewHolder.tv_type_name = (TextView) view.findViewById(R.id.tv_type_name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.tv_type_name.setText(mDatas.get(position).getName());
        Picasso.with(mContext)
                .load(mDatas.get(position).getImageUrl())
                .into(viewHolder.img_type);


        return view;
    }

    class ViewHolder {
        ImageView img_type;
        ImageView img_hot;
        TextView tv_type_name;
    }

}
