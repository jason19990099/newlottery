package com.international.wtw.lottery.json;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Steven on 2017/8/25.
 */

public class BettingHistoryBean implements Serializable {


    private List<TwoWeekHistoryBean> two_week_history;

    public List<TwoWeekHistoryBean> getTwo_week_history() {
        return two_week_history;
    }

    public void setTwo_week_history(List<TwoWeekHistoryBean> two_week_history) {
        this.two_week_history = two_week_history;
    }

    public static class TwoWeekHistoryBean {
        /**
         * betTime : 2017-08-25
         * allNum : 6
         * allMoney : 60.00
         * allWin : 53.89
         * allCut : 0.00
         * allTrueWin : 53.89
         */

        private String betTime;
        private String allNum;
        private String allMoney;
        private String allWin;
        private String allCut;
        private String allTrueWin;

        public String getBetTime() {
            return betTime;
        }

        public void setBetTime(String betTime) {
            this.betTime = betTime;
        }

        public String getAllNum() {
            return allNum;
        }

        public void setAllNum(String allNum) {
            this.allNum = allNum;
        }

        public String getAllMoney() {
            return allMoney;
        }

        public void setAllMoney(String allMoney) {
            this.allMoney = allMoney;
        }

        public String getAllWin() {
            return allWin;
        }

        public void setAllWin(String allWin) {
            this.allWin = allWin;
        }

        public String getAllCut() {
            return allCut;
        }

        public void setAllCut(String allCut) {
            this.allCut = allCut;
        }

        public String getAllTrueWin() {
            return allTrueWin;
        }

        public void setAllTrueWin(String allTrueWin) {
            this.allTrueWin = allTrueWin;
        }
    }
}
