package com.international.wtw.lottery.dialog.easypopup;

import android.content.Context;
import android.view.View;

/**
 * Created by Yuan on 2017/10/5.
 * 描述：
 */

public abstract class BaseCustomPopup extends EasyPopup {
    private static final String TAG = "BaseCustomPopup";

    protected BaseCustomPopup(Context context) {
        super(context);
    }

    @Override
    public void onPopupWindowCreated() {
        super.onPopupWindowCreated();
        //执行设置PopupWindow属性也可以通过Builder中设置
        //setContentView(x,x,x);
        //...
        initAttributes();
    }

    @Override
    public void onPopupWindowViewCreated(View contentView) {
        initViews(contentView);
    }

    @Override
    public void onPopupWindowDismiss() {

    }

    /**
     * 可以在此方法中设置PopupWindow需要的属性
     */
    protected abstract void initAttributes();

    /**
     * 初始化view {@see getView()}
     *
     * @param view
     */
    protected abstract void initViews(View view);

}
