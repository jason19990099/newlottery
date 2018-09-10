package com.international.wtw.lottery.json;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.international.wtw.lottery.adapter.BetRecordAdapter;

import java.util.List;

/**
 * Created by XiaoXin on 2017/9/26.
 * 描述：
 */

public class BetRecordDetail extends BaseModel {

    /**
     * res : [{"win_money":-1,"no":"26748","detail":"总和小 @1.988","game_name":"广东快乐十分","money":1,"count":"1","game_code":"3","round":"20170922-77","rate":"1.988","time":"2017-09-22 21:42:00"}]
     * page : {"number":1,"allnmb":"20","page":1}
     */

    private PageBean page;
    private List<ResBean> res;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<ResBean> getRes() {
        return res;
    }

    public void setRes(List<ResBean> res) {
        this.res = res;
    }

    public static class PageBean {
        /**
         * number : 1
         * allnmb : 20
         * page : 1
         */

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
    }

    public static class ResBean implements MultiItemEntity {
        /**
         * win_money : -1
         * no : 26748
         * detail : 总和小 @1.988
         * game_name : 广东快乐十分
         * money : 1
         * count : 1
         * game_code : 3
         * round : 20170922-77
         * rate : 1.988
         * time : 2017-09-22 21:42:00
         */

        private String win_money;
        private String no;
        private String detail;
        private String game_name;
        private String money;
        private String count;
        private String game_code;
        private String round;
        private String rate;
        private String time;

        public String getWin_money() {
            return win_money;
        }

        public void setWin_money(String win_money) {
            this.win_money = win_money;
        }

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public String getGame_name() {
            return game_name;
        }

        public void setGame_name(String game_name) {
            this.game_name = game_name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getGame_code() {
            return game_code;
        }

        public void setGame_code(String game_code) {
            this.game_code = game_code;
        }

        public String getRound() {
            return round;
        }

        public void setRound(String round) {
            this.round = round;
        }

        public String getRate() {
            return rate;
        }

        public void setRate(String rate) {
            this.rate = rate;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        @Override
        public int getItemType() {
            return BetRecordAdapter.TYPE_LEVEL_1;
        }
    }
}
