package com.international.wtw.lottery.json;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by XiaoXin on 2017/10/9.
 * 描述：
 */

public class MultiPayInItem implements MultiItemEntity {

    public static final int TYPE_TITLE = 0;
    public static final int TYPE_ONLINE_PAY = 1;
    public static final int TYPE_OFFLINE_PAY = 2;

    public int itemType;

    @Override
    public int getItemType() {
        return itemType;
    }
}
