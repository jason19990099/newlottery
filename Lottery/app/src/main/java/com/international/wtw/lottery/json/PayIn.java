package com.international.wtw.lottery.json;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuya on 2017/8/8.
 */

public class PayIn extends BaseModel {

    /**
     * online_bank : ["107","104","98","91","68","64"]
     * online_alipay : ["106","95","94","93","88"]
     * online_wechat : ["102","97","90","86","80","79","78","66"]
     * online_cft : ["105"]
     * online_quickpay : []
     * bankpay_array : [{"id":"1","bank_name":"中国工商银行","bank_user":"李湛洋","bank_addres":"福建分行","bank_account":"1407702501104191879","level":"0","status":"1","bank_image_url":"http://omg2jdwey.bkt.clouddn.com/%E4%B8%8D%E6%8E%A5%E5%8F%97ATM.jpg","bank_image_url2":"","url_type":"1","recharge_offer":"0.00"}]
     * alipay_array : []
     * cft_array : []
     * wechat_array : [{"id":"3","bank_name":"微信","bank_user":"刘启帆5931","bank_addres":"温馨提示：请务必以商户单号的后四位提交入款单！","bank_account":"温馨提示：请务必以实时备注的昵称提交入款单！","level":"0","status":"1","bank_image_url":"http://omg2jdwey.bkt.clouddn.com/%E9%80%9A%E8%81%94%E6%94%B6%E6%AC%BE.jpg","bank_image_url2":"","url_type":"1","recharge_offer":"0.00"}]
     * quickpay_array : []
     * linedown : {"bankpay_array":[{"id":"1","bank_name":"中国工商银行","bank_user":"李湛洋","bank_addres":"福建分行","bank_account":"1407702501104191879","level":"0","status":"1","bank_image_url":"http://omg2jdwey.bkt.clouddn.com/%E4%B8%8D%E6%8E%A5%E5%8F%97ATM.jpg","bank_image_url2":"","url_type":"1","recharge_offer":"0.00"}],"alipay_array":[],"cft_array":[],"wechat_array":[{"id":"3","bank_name":"微信","bank_user":"刘启帆5931","bank_addres":"温馨提示：请务必以商户单号的后四位提交入款单！","bank_account":"温馨提示：请务必以实时备注的昵称提交入款单！","level":"0","status":"1","bank_image_url":"http://omg2jdwey.bkt.clouddn.com/%E9%80%9A%E8%81%94%E6%94%B6%E6%AC%BE.jpg","bank_image_url2":"","url_type":"1","recharge_offer":"0.00"}],"quickpay_array":[]}
     * moneylimit : {"bankmin":"10","bankmax":"1000000","wechatmin":"10","wechatmax":"5000","alipaymin":"10","alipaymax":"3000","linedownmin":"10"}
     */
    private LinedownBean linedown;
    private MoneylimitBean moneylimit;
    private ArrayList<String> online_bankU;
    private ArrayList<String> online_alipayU;
    private ArrayList<String> online_wechatU;
    private ArrayList<String> online_cftU;
    private ArrayList<String> online_bank;
    private ArrayList<String> online_alipay;
    private ArrayList<String> online_wechat;
    private ArrayList<String> online_cft;
    private ArrayList<String> online_quickpay;
    private List<PayeeInfo> bankpay_array;
    private List<PayeeInfo> alipay_array;
    private List<PayeeInfo> cft_array;
    private List<PayeeInfo> wechat_array;
    private List<PayeeInfo> quickpay_array;

    public LinedownBean getLinedown() {
        return linedown;
    }

    public void setLinedown(LinedownBean linedown) {
        this.linedown = linedown;
    }

    public MoneylimitBean getMoneylimit() {
        return moneylimit;
    }

    public void setMoneylimit(MoneylimitBean moneylimit) {
        this.moneylimit = moneylimit;
    }

    public ArrayList<String> getOnline_bankU() {
        return online_bankU;
    }

    public void setOnline_bankU(ArrayList<String> online_bankU) {
        this.online_bankU = online_bankU;
    }

    public ArrayList<String> getOnline_alipayU() {
        return online_alipayU;
    }

    public void setOnline_alipayU(ArrayList<String> online_alipayU) {
        this.online_alipayU = online_alipayU;
    }

    public ArrayList<String> getOnline_wechatU() {
        return online_wechatU;
    }

    public void setOnline_wechatU(ArrayList<String> online_wechatU) {
        this.online_wechatU = online_wechatU;
    }

    public ArrayList<String> getOnline_cftU() {
        return online_cftU;
    }

    public void setOnline_cftU(ArrayList<String> online_cftU) {
        this.online_cftU = online_cftU;
    }

    public ArrayList<String> getOnline_bank() {
        return online_bank;
    }

    public void setOnline_bank(ArrayList<String> online_bank) {
        this.online_bank = online_bank;
    }

    public ArrayList<String> getOnline_alipay() {
        return online_alipay;
    }

    public void setOnline_alipay(ArrayList<String> online_alipay) {
        this.online_alipay = online_alipay;
    }

    public ArrayList<String> getOnline_wechat() {
        return online_wechat;
    }

    public void setOnline_wechat(ArrayList<String> online_wechat) {
        this.online_wechat = online_wechat;
    }

    public ArrayList<String> getOnline_cft() {
        return online_cft;
    }

    public void setOnline_cft(ArrayList<String> online_cft) {
        this.online_cft = online_cft;
    }

    public ArrayList<String> getOnline_quickpay() {
        return online_quickpay;
    }

    public void setOnline_quickpay(ArrayList<String> online_quickpay) {
        this.online_quickpay = online_quickpay;
    }

    public List<PayeeInfo> getBankpay_array() {
        return bankpay_array;
    }

    public void setBankpay_array(List<PayeeInfo> bankpay_array) {
        this.bankpay_array = bankpay_array;
    }

    public List<PayeeInfo> getAlipay_array() {
        return alipay_array;
    }

    public void setAlipay_array(List<PayeeInfo> alipay_array) {
        this.alipay_array = alipay_array;
    }

    public List<PayeeInfo> getCft_array() {
        return cft_array;
    }

    public void setCft_array(List<PayeeInfo> cft_array) {
        this.cft_array = cft_array;
    }

    public List<PayeeInfo> getWechat_array() {
        return wechat_array;
    }

    public void setWechat_array(List<PayeeInfo> wechat_array) {
        this.wechat_array = wechat_array;
    }

    public List<PayeeInfo> getQuickpay_array() {
        return quickpay_array;
    }

    public void setQuickpay_array(List<PayeeInfo> quickpay_array) {
        this.quickpay_array = quickpay_array;
    }

    public class LinedownBean implements Serializable {
        private List<PayeeInfo> bankpay_array;
        private List<PayeeInfo> alipay_array;
        private List<PayeeInfo> cft_array;
        private List<PayeeInfo> wechat_array;
        private List<PayeeInfo> quickpay_array;

        public List<PayeeInfo> getBankpay_array() {
            return bankpay_array;
        }

        public void setBankpay_array(List<PayeeInfo> bankpay_array) {
            this.bankpay_array = bankpay_array;
        }

        public List<PayeeInfo> getAlipay_array() {
            return alipay_array;
        }

        public void setAlipay_array(List<PayeeInfo> alipay_array) {
            this.alipay_array = alipay_array;
        }

        public List<PayeeInfo> getCft_array() {
            return cft_array;
        }

        public void setCft_array(List<PayeeInfo> cft_array) {
            this.cft_array = cft_array;
        }

        public List<PayeeInfo> getWechat_array() {
            return wechat_array;
        }

        public void setWechat_array(List<PayeeInfo> wechat_array) {
            this.wechat_array = wechat_array;
        }

        public List<PayeeInfo> getQuickpay_array() {
            return quickpay_array;
        }

        public void setQuickpay_array(List<PayeeInfo> quickpay_array) {
            this.quickpay_array = quickpay_array;
        }
    }

    public class PayeeInfo implements Serializable {
        /**
         * id : 1
         * bank_name : 中国工商银行
         * bank_user : 李湛洋
         * bank_addres : 福建分行
         * bank_account : 1407702501104191879
         * level : 0
         * status : 1
         * bank_image_url : http://omg2jdwey.bkt.clouddn.com/%E4%B8%8D%E6%8E%A5%E5%8F%97ATM.jpg
         * bank_image_url2 :
         * url_type : 1
         * recharge_offer : 0.00
         */

        private int payType;//自己新增一个参数, 用于区分支付方式:银行卡 1, 支付宝 2, 微信 3, 财付通 4, 快捷支付 5.

        private String id;
        private String bank_name;
        private String bank_user;
        private String bank_addres;
        private String bank_account;
        private String level;
        private String status;
        private String bank_image_url;
        private String bank_image_url2;
        private String url_type;
        private String recharge_offer;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBank_name() {
            return bank_name;
        }

        public void setBank_name(String bank_name) {
            this.bank_name = bank_name;
        }

        public String getBank_user() {
            return bank_user;
        }

        public void setBank_user(String bank_user) {
            this.bank_user = bank_user;
        }

        public String getBank_addres() {
            return bank_addres;
        }

        public void setBank_addres(String bank_addres) {
            this.bank_addres = bank_addres;
        }

        public String getBank_account() {
            return bank_account;
        }

        public void setBank_account(String bank_account) {
            this.bank_account = bank_account;
        }

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getBank_image_url() {
            return bank_image_url;
        }

        public void setBank_image_url(String bank_image_url) {
            this.bank_image_url = bank_image_url;
        }

        public String getBank_image_url2() {
            return bank_image_url2;
        }

        public void setBank_image_url2(String bank_image_url2) {
            this.bank_image_url2 = bank_image_url2;
        }

        public String getUrl_type() {
            return url_type;
        }

        public void setUrl_type(String url_type) {
            this.url_type = url_type;
        }

        public String getRecharge_offer() {
            return recharge_offer;
        }

        public void setRecharge_offer(String recharge_offer) {
            this.recharge_offer = recharge_offer;
        }
    }

    public static class MoneylimitBean implements Serializable {
        /**
         * bankmin : 10
         * bankmax : 1000000
         * wechatmin : 10
         * wechatmax : 5000
         * alipaymin : 10
         * alipaymax : 3000
         * linedownmin : 10
         */

        private String bankmin;
        private String bankmax;
        private String wechatmin;
        private String wechatmax;
        private String alipaymin;
        private String alipaymax;
        private String cftpaymin;
        private String cftpaymax;
        private String quickpaymin;
        private String quickpaymax;
        private String linedownmin;

        public String getBankmin() {
            return bankmin;
        }

        public void setBankmin(String bankmin) {
            this.bankmin = bankmin;
        }

        public String getBankmax() {
            return bankmax;
        }

        public void setBankmax(String bankmax) {
            this.bankmax = bankmax;
        }

        public String getWechatmin() {
            return wechatmin;
        }

        public void setWechatmin(String wechatmin) {
            this.wechatmin = wechatmin;
        }

        public String getWechatmax() {
            return wechatmax;
        }

        public void setWechatmax(String wechatmax) {
            this.wechatmax = wechatmax;
        }

        public String getAlipaymin() {
            return alipaymin;
        }

        public void setAlipaymin(String alipaymin) {
            this.alipaymin = alipaymin;
        }

        public String getAlipaymax() {
            return alipaymax;
        }

        public void setAlipaymax(String alipaymax) {
            this.alipaymax = alipaymax;
        }

        public String getLinedownmin() {
            return linedownmin;
        }

        public String getCftpaymin() {
            return cftpaymin;
        }

        public String getCftpaymax() {
            return cftpaymax;
        }

        public String getQuickpaymin() {
            return quickpaymin;
        }

        public String getQuickpaymax() {
            return quickpaymax;
        }

        public void setLinedownmin(String linedownmin) {
            this.linedownmin = linedownmin;
        }
    }
}
