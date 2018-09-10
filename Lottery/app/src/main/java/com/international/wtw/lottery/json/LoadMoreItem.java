package com.international.wtw.lottery.json;

import com.international.wtw.lottery.adapter.BetRecordAdapter;

/**
 * Created by XiaoXin on 2017/9/26.
 * 描述：
 */

public class LoadMoreItem extends BetRecordDetail.ResBean {

    public static final int STATUS_LOADING = 0;
    public static final int STATUS_FAIL = 1;
    public static final int STATUS_END = 2;
    public static final int STATUS_HAS_MORE = 3;

    private int status;
    private int pageIndex = 1;

    public LoadMoreItem(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    @Override
    public int getItemType() {
        return BetRecordAdapter.TYPE_LEVEL_1_1;
    }


}
