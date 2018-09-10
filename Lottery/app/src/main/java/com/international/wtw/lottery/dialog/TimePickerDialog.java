package com.international.wtw.lottery.dialog;

import android.text.TextUtils;
import android.view.View;
import android.widget.TimePicker;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.dialog.nice.ViewHolder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Abin on 2017/9/14.
 * 描述：
 */

public class TimePickerDialog extends BaseNiceDialog implements TimePicker.OnTimeChangedListener, View.OnClickListener {

    private TimeChangeListener mTimeChangeListener;
    private SimpleDateFormat mFormat = new SimpleDateFormat("HH:mm");
    private String timeStr;

    public static TimePickerDialog newInstance() {
        return new TimePickerDialog();
    }

    public TimePickerDialog setTimeChangeListener(TimeChangeListener listener) {
        mTimeChangeListener = listener;
        return this;
    }

    @Override
    public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
        timeStr = String.format(Locale.CHINESE, "%02d:%02d", hourOfDay, minute);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_to_now:
                if (mTimeChangeListener != null) {
                    mTimeChangeListener.onTimeChanged(mFormat.format(Calendar.getInstance().getTime()));
                }
                break;
            case R.id.tv_confirm:
                if (mTimeChangeListener != null && !TextUtils.isEmpty(timeStr)) {
                    mTimeChangeListener.onTimeChanged(timeStr);
                }
                break;
        }
        dismiss();
    }

    @Override
    public int intLayoutId() {
        return R.layout.layout_dialog_time_picker;
    }

    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        holder.setOnClickListener(R.id.tv_to_now, this);
        holder.setOnClickListener(R.id.tv_confirm, this);

        TimePicker timePicker = holder.getView(R.id.timePicker);
        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(this);
    }

    public interface TimeChangeListener {
        void onTimeChanged(String timeString);
    }
}
