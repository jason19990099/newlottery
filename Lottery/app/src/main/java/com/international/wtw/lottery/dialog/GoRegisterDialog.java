package com.international.wtw.lottery.dialog;

import android.content.Intent;
import android.view.View;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.activity.login.RegisterActivity;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.dialog.nice.ViewHolder;

public class GoRegisterDialog extends BaseNiceDialog implements View.OnClickListener {

    @Override
    public int intLayoutId() {
        return R.layout.dialog_2_register;
    }

    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        holder.setOnClickListener(R.id.tv_to_register, this);
        holder.setOnClickListener(R.id.tv_close, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_to_register:
                getActivity().startActivity(new Intent(getActivity(), RegisterActivity.class));
                break;
            case R.id.tv_close:
                dismiss();
                break;
        }
    }
}
