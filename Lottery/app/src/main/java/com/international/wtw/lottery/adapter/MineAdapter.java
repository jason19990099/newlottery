package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.MineBean;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/8/14.
 */

public class MineAdapter extends BaseAdapter {

    private Context context;
    private List<MineBean> list;

    public MineAdapter(Context context, List<MineBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
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
            view = LayoutInflater.from(context).inflate(R.layout.fragment_mine_item, null);
            viewHolder.mine_item_img_type = (ImageView) view.findViewById(R.id.mine_item_img_type);
            viewHolder.mine_item_tv_type = (TextView) view.findViewById(R.id.mine_item_tv_type);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String type_name = list.get(position).getType_name();
        viewHolder.mine_item_tv_type.setText(type_name);
        if (type_name.equals("个人资料")) {
            viewHolder.mine_item_img_type.setImageResource(R.mipmap.mine_person);
        }
        if (type_name.equals("登录密码")) {
            viewHolder.mine_item_img_type.setImageResource(R.mipmap.mine_denglumima);
        }
        if (type_name.equals("信息中心")) {
            viewHolder.mine_item_img_type.setImageResource(R.mipmap.mine_xinxizhongxin);
        }
        if (type_name.equals("资金管理")) {
            viewHolder.mine_item_img_type.setImageResource(R.mipmap.mine_zijijnguanli);
        }
        if (type_name.equals("取款密码")) {
            viewHolder.mine_item_img_type.setImageResource(R.mipmap.mine_qukuanmima);
        }
        if (type_name.equals("银行账号")) {
            viewHolder.mine_item_img_type.setImageResource(R.mipmap.mine_yinhangzhanghao);
        }
        if (type_name.equals("今日已结")) {
            viewHolder.mine_item_img_type.setImageResource(R.mipmap.mine_jinriyijie);
        }
        if (type_name.equals("下注记录")) {
            viewHolder.mine_item_img_type.setImageResource(R.mipmap.mine_xiazhujilu);
        }
        if (type_name.equals("客服")) {
            viewHolder.mine_item_img_type.setImageResource(R.mipmap.mine_kefu);
        }

        return view;
    }

    class ViewHolder {
        ImageView mine_item_img_type;
        TextView mine_item_tv_type;
    }
}
