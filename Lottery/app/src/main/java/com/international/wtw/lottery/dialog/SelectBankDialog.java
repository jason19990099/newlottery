package com.international.wtw.lottery.dialog;
import android.view.View;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.base.app.BaseApplication;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.dialog.nice.ViewHolder;
import com.weigan.loopview.LoopView;
import java.util.Arrays;
import java.util.List;

/**
 * 描述：绑定银行卡弹窗
 */

public class SelectBankDialog extends BaseNiceDialog implements View.OnClickListener {

    private List<String> mBankList;
    private LoopView mWheelView;
    private BankSelectListener mListener;
    public static SelectBankDialog newInstance() {
        return new SelectBankDialog();
    }


    public SelectBankDialog setListener(BankSelectListener listener) {
        mListener = listener;
        return this;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_complete:
                int item = mWheelView.getSelectedItem();
                if (mListener != null) {
                    mListener.onBankSelect(mBankList.get(item));
                }
                dismiss();
                break;
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }



    @Override
    public int intLayoutId() {
        return R.layout.popup_layout_select_bank;
    }

    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        holder.setOnClickListener(R.id.tv_complete, this);
        holder.setOnClickListener(R.id.tv_cancel, this);
        mWheelView = holder.getView(R.id.wheel_bank);
        //获取银行名称列表
        String[] bankNames = BaseApplication.getAppContext().getResources().getStringArray(R.array.bank_name_items);
        mBankList = Arrays.asList(bankNames);
        mWheelView.setItems(mBankList);
        mWheelView.setNotLoop();
        mWheelView.setInitPosition(0);
    }

    public interface BankSelectListener {
        void onBankSelect(String bank);
    }
}
