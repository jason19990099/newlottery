package com.international.wtw.lottery.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.BetDialogAdapter;
import com.international.wtw.lottery.json.NewOddsBean;
import com.international.wtw.lottery.listener.OnSingleClickListener;
import com.international.wtw.lottery.utils.SizeUtils;

import java.util.List;

/**
 * Created by a_bin 2017/6/24.
 */

public class RecyclerViewDialog extends Dialog  {
    private TextView tv_number, tv_total;
    private Button btn_bet, btn_cancel;
    private View mContentView;
    private SureCallBack callBack;
    private Context mContext;
    private RecyclerView recyclerView;


    public RecyclerViewDialog(Context context, final List<NewOddsBean.ListBean> list, String money, SureCallBack callBack) {
        super(context, R.style.DialogTheme);
        setCancelable(false);
        mContext = context;
        this.callBack = callBack;
        mContentView = View.inflate(getContext(), R.layout.recycler_dialog2, null);
        setContentView(mContentView);

        tv_number = (TextView) mContentView.findViewById(R.id.tv_number);
        tv_total = (TextView) mContentView.findViewById(R.id.tv_total);
        int total = Integer.valueOf(money) * list.size();
        tv_number.setText(list.size() + "组");
        tv_total.setText(total + "元");
        btn_bet = (Button) mContentView.findViewById(R.id.btn_bet);
        btn_cancel = (Button) mContentView.findViewById(R.id.btn_cancel);
        recyclerView = (RecyclerView) mContentView.findViewById(R.id.recycle_bet);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        final BetDialogAdapter adapter = new BetDialogAdapter(mContext, R.layout.item_dialog, list, money, recyclerView);
        ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
        adapter.setOnItemClickListener(new BetDialogAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ViewGroup parent, View view, NewOddsBean.ListBean bean, int position) {
                //bean.setSelectedState(false);
                list.remove(bean);
                adapter.notifyItemRemoved(position);
                int total = Integer.valueOf(money) * list.size();
                tv_number.setText(list.size() + "组");
                tv_total.setText(total + "元");
                params.height = params.height - dip2px(mContext, 45);
                recyclerView.setLayoutParams(params);
                adapter.notifyDataSetChanged();
                if (list.size() == 0) {
                    if (callBack != null) {
                        callBack.onCancel();
                    }
                    dismiss();
                }
            }
        });

        recyclerView.setAdapter(adapter);
        btn_bet.setOnClickListener(new OnSingleClickListener() {
            @Override
            protected void onSingleClick(View v) {
                if (callBack != null) {
                    callBack.onSure();
                }
                dismiss();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (callBack != null) {
                    callBack.onCancel();
                }
                dismiss();
            }
        });

        params.width = getScreenWidth() - SizeUtils.dp2px(70);
        if (list.size() > 6) {
            params.height = getScreenHeight() / 2;
        }
        recyclerView.setLayoutParams(params);

    }

    public interface SureCallBack {
        void onSure();

        void onCancel();
    }

    public int getScreenHeight() {
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }


    public int getScreenWidth() {
        WindowManager manager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    public int dip2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5F);
    }
}
