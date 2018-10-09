package com.international.wtw.lottery.event;

public class BetGo extends BaseEvent {
    private String betMoney;
    private String expectNo;
    public BetGo(String expectNo,String betMoney){
        this.expectNo=expectNo;
        this.betMoney=betMoney;
    }

    public String getBetMoney() {
        return betMoney;
    }

    public void setBetMoney(String betMoney) {
        this.betMoney = betMoney;
    }

    public String getExpectNo() {
        return expectNo;
    }

    public void setExpectNo(String expectNo) {
        this.expectNo = expectNo;
    }
}
