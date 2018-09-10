package com.international.wtw.lottery.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.json.TransactionRecord;
import com.international.wtw.lottery.utils.DateUtil;

/**
 *  交易记录的弹窗
 */
public class RecordDetailDialog extends Dialog implements View.OnClickListener {
    private TextView tv_record_no, tv_record_time, tv_record_style, tv_record_status, tv_record_amount, tv_record_bz;
    private Button btn_confirm;
    private View mContentView;
    private ImageView img_close;

    public RecordDetailDialog(Context context, TransactionRecord.ResBean resBean) {
        super(context, R.style.DialogTheme);
        mContentView = View.inflate(getContext(), R.layout.dialog_record_detail, null);
        setContentView(mContentView);
        tv_record_no = (TextView) mContentView.findViewById(R.id.tv_record_no);
        tv_record_time = (TextView) mContentView.findViewById(R.id.tv_record_time);
        tv_record_style = (TextView) mContentView.findViewById(R.id.tv_record_style);
        tv_record_status = (TextView) mContentView.findViewById(R.id.tv_record_status);
        tv_record_amount = (TextView) mContentView.findViewById(R.id.tv_record_amount);
        tv_record_bz = (TextView) mContentView.findViewById(R.id.tv_record_bz);
        btn_confirm = (Button) mContentView.findViewById(R.id.btn_recharge);
        img_close = (ImageView) mContentView.findViewById(R.id.img_close);
        btn_confirm.setOnClickListener(this);
        img_close.setOnClickListener(this);

        tv_record_no.setText(resBean.getOrder_code().trim());
        tv_record_time.setText(DateUtil.convertDateTime(resBean.getAddtime()));
        switch (resBean.getType_code()) {
            case "0":
                tv_record_style.setText(context.getResources().getString(R.string.cun_ru));
                break;
            case "1":
                tv_record_style.setText(context.getResources().getString(R.string.qu_chu));
                break;

        }

        if ("0".equals(resBean.getStatus())) {
            tv_record_status.setText(context.getResources().getString(R.string.untreated));
            tv_record_status.setTextColor(context.getResources().getColor(R.color.bet_color_blue));
        } else if ("1".equals(resBean.getStatus())) {
            if ("0".equals(resBean.getIs_clear())) {
                tv_record_status.setText(context.getResources().getString(R.string.fail));
                tv_record_status.setTextColor(context.getResources().getColor(R.color.red));
            } else if ("1".equals(resBean.getIs_clear())) {
                tv_record_status.setText(context.getResources().getString(R.string.success));
                tv_record_status.setTextColor(context.getResources().getColor(R.color.gray));
            }
        }
        tv_record_amount.setText(resBean.getMoney());
        if (null != resBean.getMemo() && !TextUtils.isEmpty(resBean.getMemo())) {
            tv_record_bz.setVisibility(View.VISIBLE);
            tv_record_bz.setText(resBean.getMemo().replace("<br>", ""));
        }
        if (null != resBean.getContext() && !TextUtils.isEmpty(resBean.getContext())) {
            tv_record_bz.setVisibility(View.VISIBLE);
            tv_record_bz.setText(resBean.getContext().replace("<br>", ""));
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_recharge:
                dismiss();
                break;
            case R.id.img_close:
                dismiss();
                break;
        }
    }
}
