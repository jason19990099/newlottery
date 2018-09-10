package com.international.wtw.lottery.adapter.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.HomeMsgBean;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/9/15.
 */

public class NewRecyclerViewBaseAdapter extends BaseAdapter {

    private Context mContext;
    private List<HomeMsgBean> mDatas;
    private int itemCount = 9;

    public NewRecyclerViewBaseAdapter(Context mContext, List<HomeMsgBean> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @Override
    public int getCount() {
//        if (mDatas.size() > 9) {
//            return itemCount;
//        } else {
//            return mDatas.size();
//        }
        return null==mDatas?0:mDatas.size();
    }

    public void addItemNum(int number)
    {
        itemCount = number;
    }

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

        viewHolder.tv_type_name.setText(mDatas.get(position).getType_name());
        /*String type_name = mDatas.get(position).getType_name();

        if (type_name.equals("北京赛车")) {
            viewHolder.img_type.setImageResource(R.mipmap.icon_item_pj_pk10);
        }
        if (type_name.equals("幸运飞艇")) {
            viewHolder.img_type.setImageResource(R.mipmap.icon_item_lucky_fly);
        }
        if (type_name.equals("广东快乐十分")) {
            viewHolder.img_type.setImageResource(R.mipmap.icon_item_gd_happy);
        }
        if (type_name.equals("重庆幸运农场")) {
            viewHolder.img_type.setImageResource(R.mipmap.icon_item_cj_lucky_lottery);
        }
        if (type_name.equals("重庆时时彩")) {
            viewHolder.img_type.setImageResource(R.mipmap.icon_item_cj_lottery);
        }
        if (type_name.equals("PC蛋蛋")) {
            viewHolder.img_type.setImageResource(R.mipmap.icon_item_pc_dd);
        }
        if (type_name.equals("香港六合彩")) {
            viewHolder.img_type.setImageResource(R.mipmap.icon_item_hk_mark_six);
        }
        if (type_name.equals("在线客服")) {
            viewHolder.img_type.setImageResource(R.mipmap.icon_item_kf);
        }
        if (type_name.equals("真人视讯")) {
            viewHolder.img_type.setImageResource(R.mipmap.icon_item_ag);
        }*/

        String type_name = mDatas.get(position).getType_name();
        if ("真人视讯".equals(type_name)) {
            viewHolder.img_hot.setVisibility(View.VISIBLE);
        } else {
            viewHolder.img_hot.setVisibility(View.GONE);
        }

        viewHolder.img_type.setImageResource(mDatas.get(position).getType_img());

        return view;
    }

    class ViewHolder {
        ImageView img_type;
        ImageView img_hot;
        TextView tv_type_name;
    }

}
