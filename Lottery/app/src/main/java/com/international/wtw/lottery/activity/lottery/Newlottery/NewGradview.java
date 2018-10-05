package com.international.wtw.lottery.activity.lottery.Newlottery;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 *  新的gradciew  不然gradview在listvirerew包裹下只显示一行
 */
public class NewGradview extends GridView {
    public NewGradview(Context context) {
        super(context);
    }

    public NewGradview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NewGradview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public NewGradview(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);	   }
}
