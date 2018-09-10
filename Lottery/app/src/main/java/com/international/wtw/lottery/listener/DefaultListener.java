package com.international.wtw.lottery.listener;

import android.util.Log;

import com.international.wtw.lottery.dialog.ClickableToast;


public class DefaultListener implements ClickableToast.OnClickListener {

    @Override
    public void onClick(ClickableToast toast) {
        Log.d(this.getClass().getSimpleName(), "取消");
    }
}