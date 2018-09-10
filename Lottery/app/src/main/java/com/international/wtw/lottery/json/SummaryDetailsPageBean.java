package com.international.wtw.lottery.json;

/**
 * Created by XIAOYAN on 2017/9/23.
 */

public class SummaryDetailsPageBean {

    private int number;
    private String allnmb;
    private int page;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAllnmb() {
        return allnmb;
    }

    public void setAllnmb(String allnmb) {
        this.allnmb = allnmb;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public SummaryDetailsPageBean() {
        super();
    }

    public SummaryDetailsPageBean(int number, String allnmb, int page) {
        this.number = number;
        this.allnmb = allnmb;
        this.page = page;
    }

    @Override
    public String toString() {
        return "SummaryDetailsPageBean{" +
                "number=" + number +
                ", allnmb='" + allnmb + '\'' +
                ", page=" + page +
                '}';
    }
}
