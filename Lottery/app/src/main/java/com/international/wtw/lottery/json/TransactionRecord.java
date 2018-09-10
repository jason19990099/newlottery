package com.international.wtw.lottery.json;

import java.util.List;

/**
 * Created by user on 2017/7/6.
 */

public class TransactionRecord extends BaseModel {

    /**
     * page : {"allnmb":"2","page":1,"number":30}
     * res : [{"order_code":"ME_149924613323652","type_code":"0","pay_way":"2","addtime":"1499246133","money":"2.00","moneyf":"10000.00","bank_account":"3168794532956","bank_name":"测试","bank_address":"支付宝","bank_code":"中国工商银行","memo":"入款人:<br/>交易号:1234<br/>入款时间:17:15:33<br/>入款方式:支付宝<br>开户名:4345<br/>","recharge_offer":"0.00","status":"0","is_clear":"0","username":"test5"},{"order_code":"admin_1499153196","type_code":"0","pay_way":"0","addtime":"1499153196","money":"10000.00","moneyf":"0.00","bank_account":"","bank_name":"","bank_address":"","bank_code":"","memo":"","recharge_offer":"0.00","status":"1","is_clear":"1","username":"test5"}]
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

    public class PageBean {
        /**
         * allnmb : 2
         * page : 1
         * number : 30
         */

        private int allnmb;
        private int page;
        private int number;

        public int getAllnmb() {
            return allnmb;
        }

        public void setAllnmb(int allnmb) {
            this.allnmb = allnmb;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }
    }

    public class ResBean {
        /**
         * order_code : ME_149924613323652
         * type_code : 0
         * pay_way : 2
         * addtime : 1499246133
         * money : 2.00
         * moneyf : 10000.00
         * bank_account : 3168794532956
         * bank_name : 测试
         * bank_address : 支付宝
         * bank_code : 中国工商银行
         * memo : 入款人:<br/>交易号:1234<br/>入款时间:17:15:33<br/>入款方式:支付宝<br>开户名:4345<br/>
         * recharge_offer : 0.00
         * status : 0
         * is_clear : 0
         * username : test5
         */

        private String order_code;
        private String type_code;
        private String pay_way;
        private String addtime;
        private String money;
        private String moneyf;
        private String bank_account;
        private String bank_name;
        private String bank_address;
        private String bank_code;
        private String memo;
        private String recharge_offer;
        private String status;
        private String is_clear;
        private String username;
        private int transfer;
        private String context;


        public String getContext() {
            return context;
        }

        public void setContext(String context) {
            this.context = context;
        }

        public String getOrder_code() {
            return order_code;
        }

        public void setOrder_code(String order_code) {
            this.order_code = order_code;
        }

        public String getType_code() {
            return type_code;
        }

        public void setType_code(String type_code) {
            this.type_code = type_code;
        }

        public String getPay_way() {
            return pay_way;
        }

        public void setPay_way(String pay_way) {
            this.pay_way = pay_way;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getMoneyf() {
            return moneyf;
        }

        public void setMoneyf(String moneyf) {
            this.moneyf = moneyf;
        }

        public String getBank_account() {
            return bank_account;
        }

        public void setBank_account(String bank_account) {
            this.bank_account = bank_account;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBank_address() {
            return bank_address;
        }

        public void setBank_address(String bank_address) {
            this.bank_address = bank_address;
        }

        public String getBank_code() {
            return bank_code;
        }

        public void setBank_code(String bank_code) {
            this.bank_code = bank_code;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getRecharge_offer() {
            return recharge_offer;
        }

        public void setRecharge_offer(String recharge_offer) {
            this.recharge_offer = recharge_offer;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getIs_clear() {
            return is_clear;
        }

        public void setIs_clear(String is_clear) {
            this.is_clear = is_clear;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getTransfer() {
            return transfer;
        }

        public void setTransfer(int transfer) {
            this.transfer = transfer;
        }
    }
}
