package com.international.wtw.lottery.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.utils.DateUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/3/28.
 */
public class DatePickDialog extends Dialog implements OnClickListener, OnDateChangedListener {

    private View view;
    private DatePicker picker;
    private TextView tv_pick_title;
    private TextView tv_to_tody;
    private TextView tv_set;
    private String dateTime;
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy.MM.dd");
    private DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
    private String firstDateString;
    private DateChangeListener listener;
    private int changeTime = 0;
    private FragmentActivity mActivity;

    public interface DateChangeListener {
        void refreshDate(String dateString);
    }

    public DatePickDialog(FragmentActivity context) {
        super(context, R.style.dialog_dk_style);
    }

    public DatePickDialog(FragmentActivity context, int theme) {
        super(context, theme);
    }

    public DatePickDialog(FragmentActivity context, String firstDate, DateChangeListener listener) {
        super(context, R.style.dialog_dk_style);
        mActivity = context;
        firstDateString = firstDate;
        this.listener = listener;
    }

    protected void onCreate(Bundle savedInstanceState) {
        view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_datel_layout, null);
        setContentView(view);
        tv_pick_title = (TextView) findViewById(R.id.picker_title);
        tv_to_tody = (TextView) findViewById(R.id.tv_to_today);
        tv_set = (TextView) findViewById(R.id.tv_set);
        picker = (DatePicker) findViewById(R.id.date_picker);
        tv_pick_title.setText(getDateAndWeek(firstDateString));
        tv_to_tody.setOnClickListener(this);
        tv_set.setOnClickListener(this);
        init(picker, firstDateString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_to_today:
                listener.refreshDate(sdf1.format(Calendar.getInstance().getTime()));
                //                listener.refreshDate(DateUtil.getYear()+"."+(DateUtil.getMonth()<10?"0"+DateUtil.getMonth():DateUtil.getMonth())+"."+
                //                        (DateUtil.getCurrentMonthDay()<10?"0"+DateUtil.getCurrentMonthDay():DateUtil.getCurrentMonthDay()));
                dismiss();
                break;
            case R.id.tv_set:
                Calendar calendar = Calendar.getInstance();
                if ((picker.getYear() > DateUtil.getYear()) || ((picker.getYear() == DateUtil.getYear()) && (picker.getMonth() >= DateUtil.getMonth()))) {
                    ToastDialog.error("选择日期不能超过当前月").show(mActivity.getSupportFragmentManager());
                    return;
                }
                calendar.set(picker.getYear(), picker.getMonth(), picker.getDayOfMonth());
                dateTime = sdf1.format(calendar.getTime());
                //                int cmonth=calendar.get(Calendar.MONTH)+1;
                //                dateTime = calendar.get(Calendar.YEAR)+"."+cmonth+"."+calendar.get(Calendar.DAY_OF_MONTH);
                listener.refreshDate(dateTime);
                dismiss();
                Log.d("calender", "tv_set onClick:" + dateTime);
                break;
        }
    }

    @Override
    public void onDateChanged(DatePicker picker, int year, int monthOfYear, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(picker.getYear(), picker.getMonth(), picker.getDayOfMonth());
        changeTime++;
        if (changeTime == 1) {
            tv_pick_title.setText(getDateAndWeek(firstDateString));
        } else {
            tv_pick_title.setText(sdf1.format(calendar.getTime()));
        }
    }

    public void init(DatePicker datePicker) {
        Calendar calendar = Calendar.getInstance();
        datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);
    }

    public void init(DatePicker datePicker, String dateString) {
        Calendar calendar = Calendar.getInstance();
        try {
            Date date = sdf2.parse(dateString);
            calendar.setTime(date);
        } catch (Exception e) {

        } finally {
            datePicker.init(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), this);
        }
    }

    public String getDateAndWeek(String dateString) {
        String currentDate = "", currentWeek = "";
        Date date = null;
        Calendar calendar = Calendar.getInstance();
        try {
            date = sdf1.parse(dateString);
            calendar.setTime(date);

        } catch (Exception e) {

        }
        currentDate = sdf1.format(date);
        return currentDate;
    }

}
