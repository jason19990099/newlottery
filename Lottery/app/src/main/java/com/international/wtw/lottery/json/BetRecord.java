package com.international.wtw.lottery.json;

import android.support.annotation.NonNull;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.international.wtw.lottery.adapter.BetRecordAdapter;

import java.util.List;

/**
 * Created by XiaoXin on 2017/9/26.
 * 描述：
 */

public class BetRecord extends BaseModel {

    /**
     * msg : 2006
     * info : success
     * summary : {"unSettlement":[{"dateTime":"2017-09-21","allMoney":"3.00","allWinMoney":"145.50","allNum":"3","allCut":"0.0"}],"hasClose":[{"dateTime":"2017-09-22","allNum":"20","allTotal":"20.00","allWinMoney":"-2.05","allCut":"0.05"}]}
     */

    public String info;
    public SummaryBean summary;

    public static class SummaryBean {

        public List<RecordItem> unSettlement;
        public List<RecordItem> hasClose;

        public static class RecordItem extends AbstractExpandableItem<BetRecordDetail.ResBean> implements MultiItemEntity, Comparable<RecordItem> {
            /**
             * dateTime : 2017-09-21
             * allMoney : 3.00
             * allWinMoney : 145.50
             * allNum : 3
             * allCut : 0.0
             */

            public int type;//0-未结 1-已结
            public String dateTime;
            public String allMoney;
            public String allWinMoney;
            public int allNum;
            public String allCut;


            @Override
            public int compareTo(@NonNull RecordItem o) {
                return o.dateTime.compareTo(this.dateTime);
            }

            @Override
            public int getLevel() {
                return BetRecordAdapter.TYPE_LEVEL_0;
            }

            @Override
            public int getItemType() {
                return 0;
            }
        }
    }
}
