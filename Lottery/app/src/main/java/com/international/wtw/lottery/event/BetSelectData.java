package com.international.wtw.lottery.event;

import com.international.wtw.lottery.newJason.BetData;

public class BetSelectData extends BaseEvent {
    private boolean isSelect;
    private BetData betData;

   public BetSelectData(boolean isSelect,BetData betData){
       this.isSelect=isSelect;
       this.betData=betData;
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
}
