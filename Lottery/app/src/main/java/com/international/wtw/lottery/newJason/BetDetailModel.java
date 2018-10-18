package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

import java.util.List;

public class BetDetailModel extends BaseModel {


    /**
     * data : [{"GameCode":"bjscpk10","GameName":"北京赛车(PK10)","Count":20,"Amount":208,"RebateAmount":0,"WinLoseAmount":14.66},{"GameCode":"msft","GameName":"秒速飞艇","Count":3,"Amount":30,"RebateAmount":0,"WinLoseAmount":9.7},{"GameCode":"mssc","GameName":"秒速赛车","Count":3,"Amount":30,"RebateAmount":0,"WinLoseAmount":-10.1}]
     * timestamp : 1539838833
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
         * GameCode : bjscpk10
         * GameName : 北京赛车(PK10)
         * Count : 20
         * Amount : 208
         * RebateAmount : 0
         * WinLoseAmount : 14.66
         */

        private String GameCode;
        private String GameName;
        private int Count;
        private int Amount;
        private int RebateAmount;
        private double WinLoseAmount;

        public String getGameCode() {
            return GameCode;
        }

        public void setGameCode(String GameCode) {
            this.GameCode = GameCode;
        }

        public String getGameName() {
            return GameName;
        }

        public void setGameName(String GameName) {
            this.GameName = GameName;
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
