package com.international.wtw.lottery.dialog;

import android.content.Context;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.international.wtw.lottery.R;
import com.international.wtw.lottery.dialog.easypopup.BaseCustomPopup;
import com.international.wtw.lottery.dialog.easypopup.HorizontalGravity;
import com.international.wtw.lottery.dialog.easypopup.VerticalGravity;

public class ValidatePop extends BaseCustomPopup {

    private TextView mTextView;
    private View mAnchor;

    public ValidatePop(Context context, View anchor) {
        super(context);
        mAnchor = anchor;
    }

    @Override
    protected void initAttributes() {
        setContentView(R.layout.pop_input_error_msg, -2, -2);
        setFocusAndOutsideEnable(true)
                .setOutsideTouchable(false)
                .setFocusable(false)
                .setBackgroundDimEnable(false);
    }

    @Override
    public void onPopupWindowCreated() {
        super.onPopupWindowCreated();
        getPopupWindow().setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
    }

    @Override
    protected void initViews(View view) {
        mTextView = getView(R.id.tv_msg);
    }

    @Override
    public ValidatePop createPopup() {
        return super.createPopup();
    }

    public void showMsg(String msg) {
        if (mTextView != null) {
            mTextView.setText(msg);
        }
        showAtAnchorView(mAnchor, VerticalGravity.ABOVE, HorizontalGravity.ALIGN_LEFT);
    }

    public void show(){
        showAtAnchorView(mAnchor, VerticalGravity.ABOVE, HorizontalGravity.ALIGN_LEFT);
    }
}
