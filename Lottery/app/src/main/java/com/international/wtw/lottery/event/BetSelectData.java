package com.international.wtw.lottery.event;

import com.international.wtw.lottery.newJason.BetData;

public class BetSelectData extends BaseEvent {
    private boolean isSelect;
    private BetData betData;
    private boolean isClearSelect;//是否清除选中数据

   public BetSelectData(boolean isSelect,BetData betData,boolean isClearSelect){
       this.isSelect=isSelect;
       this.betData=betData;
       this.isClearSelect=isClearSelect;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public BetData getBetData() {
        return betData;
    }

    public void setBetData(BetData betData) {
        this.betData = betData;
    }

    public boolean isClearSelect() {
        return isClearSelect;
    }

    public void setClearSelect(boolean clearSelect) {
        isClearSelect = clearSelect;
    }
}
