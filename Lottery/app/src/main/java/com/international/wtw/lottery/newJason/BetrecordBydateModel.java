package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

import java.util.List;

public class BetrecordBydateModel extends BaseModel {


    /**
     * data : [{"BetDate":"2018-10-11","Count":21,"Amount":347,"RebateAmount":0,"WinLoseAmount":142.43},{"BetDate":"2018-10-12","Count":26,"Amount":268,"RebateAmount":0,"WinLoseAmount":14.26},{"BetDate":"2018-10-15","Count":9,"Amount":90,"RebateAmount":0,"WinLoseAmount":-10.4},{"BetDate":"2018-10-16","Count":19,"Amount":190,"RebateAmount":0,"WinLoseAmount":-90.5},{"BetDate":"2018-10-17","Count":22,"Amount":220,"RebateAmount":0,"WinLoseAmount":18.8}]
     * timestamp : 1539760769
     */

    private int timestamp;
    private List<DataBean> data;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * BetDate : 2018-10-11
         * Count : 21
         * Amount : 347
         * RebateAmount : 0
         * WinLoseAmount : 142.43
         */

        private String BetDate;
        private int Count;
        private int Amount;
        private int RebateAmount;
        private double WinLoseAmount;

        public String getBetDate() {
            return BetDate;
        }

        public void setBetDate(String BetDate) {
            this.BetDate = BetDate;
        }

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public int getAmount() {
            return Amount;
        }

        public void setAmount(int Amount) {
            this.Amount = Amount;
        }

        public int getRebateAmount() {
            return RebateAmount;
        }

        public void setRebateAmount(int RebateAmount) {
            this.RebateAmount = RebateAmount;
        }

        public double getWinLoseAmount() {
            return WinLoseAmount;
        }

        public void setWinLoseAmount(double WinLoseAmount) {
            this.WinLoseAmount = WinLoseAmount;
        }
    }
}
