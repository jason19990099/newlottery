package com.international.wtw.lottery.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.manager.PaymentActivity;
import com.international.wtw.lottery.newJason.PaymentMethodModel;
import com.squareup.picasso.Picasso;
import java.util.List;

public class PaywayAdapter extends BaseAdapter {
    private Context context;
    private List<PaymentMethodModel.DataBean> data;
    private LayoutInflater mInflater;

    public PaywayAdapter(Context context,List<PaymentMethodModel.DataBean> data){
        this.context=context;
        this.data=data;
    }

    @Override
    public int getCount() {
        return null==data?0:data.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder;
        mInflater = LayoutInflater.from(context);//写在这里结局了动画还没加载完点击其他地方导致的bug？等待填充数据的时间验证
        if (view == null) {
            view = mInflater.inflate(R.layout.adapter_payways, null);
            viewHolder = new ViewHolder();
            viewHolder.iv_pay_type = (ImageView) view.findViewById(R.id.iv_pay_type);
            viewHolder.tv_pay_method = (TextView) view.findViewById(R.id.tv_pay_method);
            viewHolder.rl_all = (RelativeLayout) view.findViewById(R.id.rl_all);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.tv_pay_method.setText(data.get(position).getName());

        Picasso.with(context)
                .load(data.get(position).getImageName())
                .into(viewHolder.iv_pay_type);

        viewHolder.rl_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (data.get(position).getListPaymentModePaymentMethod().size()==0){
                    Toast.makeText(context,"请选择其他支付方式。",Toast.LENGTH_SHORT).show();
                }else{
                    Intent  intent=new Intent(context,PaymentActivity.class);
                    intent.putExtra("paydata", data.get(position));
                    context.startActivity(intent);
                }

            }
        });
        return view;
    }

    class ViewHolder {
        TextView tv_pay_method;
        ImageView iv_pay_type;
        RelativeLayout rl_all;
    }
}
