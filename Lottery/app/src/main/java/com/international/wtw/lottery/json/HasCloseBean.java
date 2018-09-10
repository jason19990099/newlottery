package com.international.wtw.lottery.json;

/**
 * Created by XIAOYAN on 2017/9/23.
 */

public class HasCloseBean {

    private String dateTime;
    private String allNum;
    private String allTotal;
    private String allWinMoney;
    private String allCut;

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getAllNum() {
        return allNum;
    }

    public void setAllNum(String allNum) {
        this.allNum = allNum;
    }

    public String getAllTotal() {
        return allTotal;
    }

    public void setAllTotal(String allTotal) {
        this.allTotal = allTotal;
    }

    public String getAllWinMoney() {
        return allWinMoney;
    }

    public void setAllWinMoney(String allWinMoney) {
        this.allWinMoney = allWinMoney;
    }

    public String getAllCut() {
        return allCut;
    }

    public void setAllCut(String allCut) {
        this.allCut = allCut;
    }

    public HasCloseBean() {
        super();
    }

    public HasCloseBean(String dateTime, String allNum, String allTotal, String allWinMoney, String allCut) {
        this.dateTime = dateTime;
        this.allNum = allNum;
        this.allTotal = allTotal;
        this.allWinMoney = allWinMoney;
        this.allCut = allCut;
    }

    @Override
    public String toString() {
        return "HasCloseBean{" +
                "dateTime='" + dateTime + '\'' +
                ", allNum='" + allNum + '\'' +
                ", allTotal='" + allTotal + '\'' +
                ", allWinMoney='" + allWinMoney + '\'' +
                ", allCut='" + allCut + '\'' +
                '}';
    }
}
