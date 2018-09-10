package com.international.wtw.lottery.json;
/**
 * Created by Administrator on 2017/10/4.
 */

public class AgAccountBalance extends BaseModel {

    /**
     * msg : 2006
     * info : success
     * balance : {"userBalance":"120.00","agBalance":"30.00"}
     */


    private BalanceBean balance;

    public BalanceBean getBalance() {
        return balance;
    }

    public void setBalance(BalanceBean balance) {
        this.balance = balance;
    }

    public static class BalanceBean {
        /**
         * userBalance : 120.00
         * agBalance : 30.00
         */

        private Double userBalance;
        private Double agBalance;

        public Double getUserBalance() {
            return userBalance;
        }

        public void setUserBalance(Double userBalance) {
            this.userBalance = userBalance;
        }

        public Double getAgBalance() {
            return agBalance;
        }

        public void setAgBalance(Double agBalance) {
            this.agBalance = agBalance;
        }
    }
}
