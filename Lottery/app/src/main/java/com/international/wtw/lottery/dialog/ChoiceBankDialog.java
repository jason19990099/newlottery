package com.international.wtw.lottery.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.base.RecyclerViewBaseAdapter;
import com.international.wtw.lottery.adapter.base.ViewHolder;
import com.international.wtw.lottery.json.PayIn;

import java.util.List;

/**
 * Created by user on 2017/6/25.
 * 暂时不用
 */

public class ChoiceBankDialog extends Dialog {
    private RecyclerView recyclerView;
    private OnBankChoice bankChoice;

    public ChoiceBankDialog(Context context, List<PayIn.PayeeInfo> beanList, int themeResId) {
        super(context, themeResId);
        View contentView = View.inflate(getContext(), R.layout.dialog_choice_bank, null);
        setContentView(contentView);

        recyclerView = (RecyclerView) contentView.findViewById(R.id.recycle_bank);
        GridLayoutManager layoutManager = new GridLayoutManager(context, 6);

        recyclerView.setLayoutManager(layoutManager);
        RecyclerViewBaseAdapter<PayIn.PayeeInfo> adapter = new RecyclerViewBaseAdapter<PayIn.PayeeInfo>(context, R.layout.item_bank, beanList, recyclerView) {
            @Override
            public void convert(ViewHolder holder, PayIn.PayeeInfo bean, int position) {
                holder.setText(R.id.tv_draw_card_name, bean.getBank_name());
            }
        };
        adapter.setOnItemClickListener(new RecyclerViewBaseAdapter.OnItemClickListener<String>() {
            @Override
            public void onItemClick(ViewGroup parent, View view, String s, int position) {
                if (bankChoice!=null){
                    bankChoice.choiceBank(beanList.get(position));
                }
                dismiss();
            }
        });
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int span=2;
                if (beanList.size()==1) {
                    span= 6;
                }else if (beanList.size()==2){
                    span= 3;
                }
                return span;
            }
        });
        recyclerView.setAdapter(adapter);
    }
    public void setOnBankChoice(OnBankChoice choice){
        this.bankChoice = choice;
    }

    public interface OnBankChoice{
        void choiceBank(PayIn.PayeeInfo bean);
    }
}
