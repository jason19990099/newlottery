package com.international.wtw.lottery.json;

public class MoneyInfo extends BaseModel {

    /**
     * money : 107138.99
     * username : hugo111
     * bank_name : 工商银行
     * bank_code : 987654321987654321
     * bank_address : 测试
     * user_phonen :
     * user_name : 测试1
     * user_balance : 107138.99
     * user_win_lose : 0
     * user_withdrawal : null
     */

    private double money;
    private String username;
    private String bank_name;
    private String bank_code;
    private String bank_address;
    private String user_phonen;
    private String user_name;
    private double user_balance;
    private double user_win_lose;
    private String user_withdrawal;

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getBank_code() {
        return bank_code;
    }

    public void setBank_code(String bank_code) {
        this.bank_code = bank_code;
    }

    public String getBank_address() {
        return bank_address;
    }

    public void setBank_address(String bank_address) {
        this.bank_address = bank_address;
    }

    public String getUser_phonen() {
        return user_phonen;
    }

    public void setUser_phonen(String user_phonen) {
        this.user_phonen = user_phonen;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public double getUser_balance() {
        return user_balance;
    }

    public void setUser_balance(double user_balance) {
        this.user_balance = user_balance;
    }

    public double getUser_win_lose() {
        return user_win_lose;
    }

    public void setUser_win_lose(double user_win_lose) {
        this.user_win_lose = user_win_lose;
    }

    public String getUser_withdrawal() {
        return user_withdrawal;
    }

    public void setUser_withdrawal(String user_withdrawal) {
        this.user_withdrawal = user_withdrawal;
    }
}
