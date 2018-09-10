package com.international.wtw.lottery.widget;

import android.content.Context;
import android.content.IntentFilter;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by A Bin on 2017/7/30.
 */

public class AutoFitRecycleView extends RecyclerView {
    public AutoFitRecycleView(Context context) {
        super(context);
    }

    public AutoFitRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoFitRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        super.onMeasure(widthSpec,heightSpec);
        //int maxWith = measureWithByChilds();
        //super.onMeasure(MeasureSpec.makeMeasureSpec(maxWith,MeasureSpec.EXACTLY),heightSpec);
    }

    private int measureWithByChilds(){
        int maxWith = 0;
        LayoutManager layoutManager = getLayoutManager();
        View view = null;
        for (int i =0;i<getAdapter().getItemCount();i++){
            //view = layoutManager.findViewByPosition(i);
            //view = this.getChildAt(i);
            view = layoutManager.getChildAt(i);
            view.measure(MeasureSpec.UNSPECIFIED,MeasureSpec.UNSPECIFIED);
            if (view.getMeasuredWidth()>maxWith){
                maxWith = view.getMeasuredWidth();
            }
        }
        return maxWith;
    }

    private int getDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
        View firstVisibleItem = this.getChildAt(0);
        int firstItemPosition = layoutManager.findFirstVisibleItemPosition();
        int itemCount = layoutManager.getItemCount();
        int recyclerviewHeight = this.getHeight();
        int itemHeight = firstVisibleItem.getHeight();
        int firstItemBottom = layoutManager.getDecoratedBottom(firstVisibleItem);
        return (itemCount - firstItemPosition - 1) * itemHeight - recyclerviewHeight + firstItemBottom; }
}
