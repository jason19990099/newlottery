package com.international.wtw.lottery.adapter.base;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.squareup.picasso.Picasso;

/**
 * Created by user on 2017/6/4.
 */

public class ViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> mViews;
    private int mPosition;
    private View mConvertView;
    private Context mContext;
    private int mLayoutId;

    public ViewHolder(Context context, View itemView, ViewGroup parent, int position) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mPosition = position;
        mViews = new SparseArray<View>();
        mConvertView.setTag(this);
    }

    public static ViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
            ViewHolder holder = new ViewHolder(context, itemView, parent, position);
            holder.mLayoutId = layoutId;
            return holder;
        } else {
            ViewHolder holder = (ViewHolder) convertView.getTag();
            holder.mPosition = position;
            return holder;
        }
    }

    public View getConvertView() {
        return mConvertView;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public ViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setText(int viewId, String text) {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int colorId) {
        TextView tv = getView(viewId);
        tv.setTextColor(ContextCompat.getColor(mContext, colorId));
        return this;
    }

    public ViewHolder setTextSize(int viewId, int size) {
        TextView tv = getView(viewId);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
        return this;
    }

    public ViewHolder setBlodText(int viewId){
        TextView tv = getView(viewId);
        tv.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));//加粗
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId) {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolder setDrawableLeft(int viewId,int resId){
        TextView textView = getView(viewId);
        Drawable drawable = ContextCompat.getDrawable(mContext,resId);
        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
        textView.setCompoundDrawables(drawable,null,null,null);
        return this;
    }

    public ViewHolder setImageResource (Context context,int viewId, String url){
        ImageView view = getView(viewId);
        if (!url.contains("http"))
            url = "http://" + url;
        Picasso.with(context).load(url).into(view);
        return this;
    }

    public ViewHolder showTime(long midTime, int viweId) {
        while (midTime > 0){
            midTime--;
            long hh = midTime / 60 / 60 % 60;
            long mm = midTime / 60 % 60;
            long ss = midTime % 60;
            ((TextView)getView(viweId)).setText(hh+":"+mm+":"+ss);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    public void setViewVisible(int viewId,Boolean visible){
        View view = getView(viewId);
        view.setVisibility(visible?View.VISIBLE:View.GONE);
    }

    public void setImageVisible(int viewId, boolean visible) {
        ImageView view = getView(viewId);
        if (visible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }

    public void setViewBackgroud(int viewId, int resId) {
        View view = getView(viewId);
        view.setBackground(ContextCompat.getDrawable(mContext, resId));
    }

    public void updatePosition(int position) {
        mPosition = position;
    }


}