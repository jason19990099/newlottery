package com.international.wtw.lottery.dialog;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.dialog.nice.BaseNiceDialog;
import com.international.wtw.lottery.dialog.nice.ViewHolder;

/**
 * Created by XiaoXin on 2018/1/25.
 * 描述：这个Dialog要向Toast一样自动消失
 */

public class ToastDialog extends BaseNiceDialog {

    public static final String ARG_MSG = "arg_msg";
    public static final String ARG_AUTO_DISMISS = "arg_auto_dismiss";
    public static final int DEFAULT_DURATION = 2000;
    public static final int TOAST_LEVEL_SUCCESS = 0x01;
    public static final int TOAST_LEVEL_WARNING = 0x02;
    public static final int TOAST_LEVEL_ERROR = 0x03;

    private int mDuration = DEFAULT_DURATION;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private String mMessage;
    private static int toastLevel;
    private OnDismissListener mDismissListener;

    public static ToastDialog makeText(String msg, int toastLevel, boolean autoDismiss) {
        Bundle bundle = new Bundle();
        bundle.putString(ARG_MSG, msg);
        ToastDialog.toastLevel = toastLevel;
        bundle.putBoolean(ARG_AUTO_DISMISS, autoDismiss);
        ToastDialog toast = new ToastDialog();
        toast.setArguments(bundle);
        return toast;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mMessage = bundle.getString(ARG_MSG);
        boolean autoDismiss = bundle.getBoolean(ARG_AUTO_DISMISS);
        if (autoDismiss) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dismissAllowingStateLoss();
                }
            }, mDuration);
        }
    }

    @Override
    public int intLayoutId() {
        switch (toastLevel) {
            case TOAST_LEVEL_SUCCESS:
                return R.layout.dialog_x_toast_success;
            case TOAST_LEVEL_WARNING:
            default:
                return R.layout.dialog_x_toast_warning;
        }
    }

    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        holder.setText(R.id.tv_message, mMessage);
        switch (toastLevel) {
            case TOAST_LEVEL_SUCCESS:
                holder.setBackgroundResource(R.id.iv_icon, R.drawable.ic_dialog_success);
                break;
            case TOAST_LEVEL_WARNING:
            default:
                holder.setBackgroundResource(R.id.iv_icon, R.drawable.ic_dialog_warning);
                holder.setOnClickListener(R.id.btn_ok, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dismissAllowingStateLoss();
                    }
                });
        }
    }

    public ToastDialog setDismissListener(OnDismissListener dismissListener) {
        mDismissListener = dismissListener;
        return this;
    }

    public OnDismissListener set() {
        return mDismissListener;
    }

    public static ToastDialog success(String msg) {
        return makeText(msg, TOAST_LEVEL_SUCCESS, true);
    }

    public static ToastDialog success(String msg, boolean autoDismiss) {
        return makeText(msg, TOAST_LEVEL_SUCCESS, autoDismiss);
    }

    public static ToastDialog warning(String msg) {
        return makeText(msg, TOAST_LEVEL_WARNING, true);
    }

    public static ToastDialog warning(String msg, boolean autoDismiss) {
        return makeText(msg, TOAST_LEVEL_WARNING, autoDismiss);
    }

    public static ToastDialog error(String msg) {
        return makeText(msg, TOAST_LEVEL_ERROR, true);
    }

    public static ToastDialog error(String msg, boolean autoDismiss) {
        return makeText(msg, TOAST_LEVEL_ERROR, autoDismiss);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        mHandler.removeCallbacksAndMessages(null);
        if (mDismissListener != null) {
            mDismissListener.onDismiss(this);
        }
    }

    public ToastDialog setDuration(int duration) {
        mDuration = duration;
        return this;
    }

    /**
     * 在主线程中显示~
     */
    public void show(final FragmentManager manager) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.showDialog(manager);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    ToastDialog.super.showDialog(manager);
                }
            });
        }
    }

    public interface OnDismissListener {

        void onDismiss(ToastDialog dialog);
    }
}
