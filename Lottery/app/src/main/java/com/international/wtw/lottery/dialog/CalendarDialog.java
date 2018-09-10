package com.international.wtw.lottery.dialog;

import android.support.annotation.NonNull;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.dialog.nice.ViewHolder;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarDialog extends BaseNiceDialog implements View.OnClickListener, OnDateSelectedListener {

    private MaterialCalendarView mCalendarView;
    private TextView mTvTitle;
    private static DateSelectedListener mDateSelectedListener;

    public static CalendarDialog newInstance(DateSelectedListener dateSelectedListener) {
        mDateSelectedListener = dateSelectedListener;
        return new CalendarDialog();
    }

    @Override
    public int intLayoutId() {
        return R.layout.layout_dialog_calendar;
    }

    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        mTvTitle = holder.getView(R.id.tv_title);
        Calendar calendar = Calendar.getInstance(Locale.CHINESE);
        String date = DateUtils.formatDateTime(getContext(), calendar.getTimeInMillis(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_WEEKDAY);
        mTvTitle.setText(date);

        mCalendarView = holder.getView(R.id.calendar_view);
        mCalendarView.setSelectedDate(calendar);
        mCalendarView.setWeekDayFormatter(new ArrayWeekDayFormatter(new String[]{"日", "一", "二", "三", "四", "五", "六"}));
        MaterialCalendarView.StateBuilder edit = mCalendarView.state().edit();
        edit.setMaximumDate(calendar);
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        edit.setMinimumDate(calendar)
                .commit();

        mCalendarView.setOnDateChangedListener(this);
        holder.setOnClickListener(R.id.tv_cancel, this);
        holder.setOnClickListener(R.id.tv_confirm, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
            case R.id.tv_confirm:
                if (mDateSelectedListener != null) {
                    mDateSelectedListener.onDateSelected(mCalendarView.getSelectedDate().getDate());
                }
                dismiss();
                break;
        }
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        String title = DateUtils.formatDateTime(getContext(), date.getDate().getTime(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_WEEKDAY);
        mTvTitle.setText(title);
    }

    public interface DateSelectedListener {
        void onDateSelected(Date date);
    }
}
