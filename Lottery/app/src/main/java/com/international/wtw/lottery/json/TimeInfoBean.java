package com.international.wtw.lottery.json;

import java.util.ArrayList;

/**
 * Created by wuya on 2017/6/13.
 */

public class TimeInfoBean extends BaseModel {

    /**
     * next : {"round":"627290","endtime":1499335320,"closetime":1499335290,"isclose":false,"timestap":1499335148}
     * last : {"round":"627289","endtime":"1499335020","number":["7","4","2","3","10","1","8","6","5","9"]}
     */

    private NextBean next;
    private LastBean last;
    private double lcurrency;
    private String token;

    public NextBean getNext() {
        return next;
    }

    public void setNext(NextBean next) {
        this.next = next;
    }

    public LastBean getLast() {
        return last;
    }

    public void setLast(LastBean last) {
        this.last = last;
    }

    public double getLcurrency() {
        return lcurrency;
    }

    public void setLcurrency(double lcurrency) {
        this.lcurrency = lcurrency;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static class NextBean {
        /**
         * round : 627290
         * endtime : 1499335320
         * closetime : 1499335290
         * timestap : 1499335148
         */

        private String round;
        private String endtime;
        private String closetime;
        private String timestap;
        private Boolean isclose;

        public Boolean isclose() {
            return isclose;
        }

        public void setIsclose(Boolean isclose) {
            this.isclose = isclose;
        }

        public String getRound() {
            return round;
        }

        public void setRound(String round) {
            this.round = round;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getClosetime() {
            return closetime;
        }

        public void setClosetime(String closetime) {
            this.closetime = closetime;
        }

        public String getTimestap() {
            return timestap;
        }

        public void setTimestap(String timestap) {
            this.timestap = timestap;
        }
    }

    public static class LastBean {
        /**
         * round : 627289
         * endtime : 1499335020
         * number : ["7","4","2","3","10","1","8","6","5","9"]
         */

        private String round;
        private String endtime;
        private ArrayList<String> number;
        private ArrayList<String> zodiac;

        public String getRound() {
            return round;
        }

        public void setRound(String round) {
            this.round = round;
        }

        public String getEndtime() {
            return endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public ArrayList<String> getNumber() {
            return number;
        }

        public void setNumber(ArrayList<String> number) {
            this.number = number;
        }

        public ArrayList<String> getZodiac() {
            return zodiac;
        }

        public void setZodiac(ArrayList<String> zodiac) {
            this.zodiac = zodiac;
        }
    }
}
