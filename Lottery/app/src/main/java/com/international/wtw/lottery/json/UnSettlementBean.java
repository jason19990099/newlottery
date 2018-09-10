package com.international.wtw.lottery.json;

/**
 * Created by XIAOYAN on 2017/9/23.
 */

public class UnSettlementBean {

    private String dateTime;
    private String allMoney;
    private String allWinMoney;
    private String allNum;
    private String allCut;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getAllMoney() {
        return allMoney;
    }

    public void setAllMoney(String allMoney) {
        this.allMoney = allMoney;
    }

    public String getAllWinMoney() {
        return allWinMoney;
    }

    public void setAllWinMoney(String allWinMoney) {
        this.allWinMoney = allWinMoney;
    }

    public String getAllNum() {
        return allNum;
    }

    public void setAllNum(String allNum) {
        this.allNum = allNum;
    }

    public String getAllCut() {
        return allCut;
    }

    public void setAllCut(String allCut) {
        this.allCut = allCut;
    }

    public UnSettlementBean() {
        super();
    }

    public UnSettlementBean(String dateTime, String allMoney, String allWinMoney, String allNum, String allCut) {
        this.dateTime = dateTime;
        this.allMoney = allMoney;
        this.allWinMoney = allWinMoney;
        this.allNum = allNum;
        this.allCut = allCut;
    }

    @Override
    public String toString() {
        return "UnSettlementBean{" +
                "dateTime='" + dateTime + '\'' +
                ", allMoney='" + allMoney + '\'' +
                ", allWinMoney='" + allWinMoney + '\'' +
                ", allNum='" + allNum + '\'' +
                ", allCut='" + allCut + '\'' +
                '}';
    }
}
