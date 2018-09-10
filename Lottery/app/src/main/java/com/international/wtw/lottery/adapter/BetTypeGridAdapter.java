package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.TypeTitle;

import java.util.List;

/**
 * Created by XiaoXin on 2017/9/19.
 * 描述：
 */

public class BetTypeGridAdapter extends BaseAdapter {

    private Context context;
    private List<TypeTitle>  gameTypes;

    public BetTypeGridAdapter(Context context) {
        this.context = context;
    }

    public void setGameTypes(List<TypeTitle> gameTypes) {
        this.gameTypes = gameTypes;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return gameTypes == null ? 0 : gameTypes.size();
    }

    @Override
    public Object getItem(int position) {
        return gameTypes == null ? null : gameTypes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return gameTypes == null ? 0 : gameTypes.get(position).getPosition();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_game_type_title, null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.tv_game_type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setTag(gameTypes.get(position).getPosition());
        viewHolder.textView.setText(gameTypes.get(position).getTitle());
        return convertView;
    }

    class ViewHolder {
        TextView textView;
    }
}
