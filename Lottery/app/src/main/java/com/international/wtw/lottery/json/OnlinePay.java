package com.international.wtw.lottery.json;

/**
 * Created by user on 2017/7/3.
 */

public class OnlinePay extends BaseModel {

    /**
     * PayID : {"农业银行":"农业银行","中国银行":"中国银行","招商银行":"招商银行","建设银行":"建设银行","交通银行":"交通银行","工商银行":"工商银行","渤海银行":"渤海银行","光大银行":"光大银行","兴业银行":"兴业银行","民生银行":"民生银行","中信银行":"中信银行","广发银行":"广发银行","华夏银行":"华夏银行","平安银行":"平安银行","邮政储蓄银行":"邮政储蓄银行","浦发银行":"浦发银行","北京农商":"北京农商","上海银行":"上海银行","银联支付":"银联支付"}
     * payurl : http://119.47.83.129/pay.php/payapi
     * paytype : 19
     * pay_id : 91
     * recharge_offer : {"id":"91","account_company":"19","business_code":"80061033","business_pwd":"FE8CA8A5B6D3186EF29D03C548D0F91E","url":"119.47.83.129","baofooid":"必付网银收款 0.6%","level":"3,4,6,7,8,9,10","status":"1","recharge_offer":"0.00","pay_type":"0","notify_url":"119.47.83.129"}
     * userid : 26645
     */
    //{"payurl":"http:\/\/pay.alcp33.com\/pay.php\/recharge","paytype":"4","pay_id":"69","userid":"7602"}
    private PayIDBean PayID;
    private String payurl;
    private String paytype;
    private String pay_id;
    private RechargeOfferBean recharge_offer;
    private String userid;

    public PayIDBean getPayID() {
        return PayID;
    }

    public void setPayID(PayIDBean PayID) {
        this.PayID = PayID;
    }

    public String getPayurl() {
        return payurl;
    }

    public void setPayurl(String payurl) {
        this.payurl = payurl;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getPay_id() {
        return pay_id;
    }

    public void setPay_id(String pay_id) {
        this.pay_id = pay_id;
    }

    public RechargeOfferBean getRecharge_offer() {
        return recharge_offer;
    }

    public void setRecharge_offer(RechargeOfferBean recharge_offer) {
        this.recharge_offer = recharge_offer;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public static class PayIDBean {
        /**
         * 农业银行 : 农业银行
         * 中国银行 : 中国银行
         * 招商银行 : 招商银行
         * 建设银行 : 建设银行
         * 交通银行 : 交通银行
         * 工商银行 : 工商银行
         * 渤海银行 : 渤海银行
         * 光大银行 : 光大银行
         * 兴业银行 : 兴业银行
         * 民生银行 : 民生银行
         * 中信银行 : 中信银行
         * 广发银行 : 广发银行
         * 华夏银行 : 华夏银行
         * 平安银行 : 平安银行
         * 邮政储蓄银行 : 邮政储蓄银行
         * 浦发银行 : 浦发银行
         * 北京农商 : 北京农商
         * 上海银行 : 上海银行
         * 银联支付 : 银联支付
         */

        private String 农业银行;
        private String 中国银行;
        private String 招商银行;
        private String 建设银行;
        private String 交通银行;
        private String 工商银行;
        private String 渤海银行;
        private String 光大银行;
        private String 兴业银行;
        private String 民生银行;
        private String 中信银行;
        private String 广发银行;
        private String 华夏银行;
        private String 平安银行;
        private String 邮政储蓄银行;
        private String 浦发银行;
        private String 北京农商;
        private String 上海银行;
        private String 银联支付;

        public String get农业银行() {
            return 农业银行;
        }

        public void set农业银行(String 农业银行) {
            this.农业银行 = 农业银行;
        }

        public String get中国银行() {
            return 中国银行;
        }

        public void set中国银行(String 中国银行) {
            this.中国银行 = 中国银行;
        }

        public String get招商银行() {
            return 招商银行;
        }

        public void set招商银行(String 招商银行) {
            this.招商银行 = 招商银行;
        }

        public String get建设银行() {
            return 建设银行;
        }

        public void set建设银行(String 建设银行) {
            this.建设银行 = 建设银行;
        }

        public String get交通银行() {
            return 交通银行;
        }

        public void set交通银行(String 交通银行) {
            this.交通银行 = 交通银行;
        }

        public String get工商银行() {
            return 工商银行;
        }

        public void set工商银行(String 工商银行) {
            this.工商银行 = 工商银行;
        }

        public String get渤海银行() {
            return 渤海银行;
        }

        public void set渤海银行(String 渤海银行) {
            this.渤海银行 = 渤海银行;
        }

        public String get光大银行() {
            return 光大银行;
        }

        public void set光大银行(String 光大银行) {
            this.光大银行 = 光大银行;
        }

        public String get兴业银行() {
            return 兴业银行;
        }

        public void set兴业银行(String 兴业银行) {
            this.兴业银行 = 兴业银行;
        }

        public String get民生银行() {
            return 民生银行;
        }

        public void set民生银行(String 民生银行) {
            this.民生银行 = 民生银行;
        }

        public String get中信银行() {
            return 中信银行;
        }

        public void set中信银行(String 中信银行) {
            this.中信银行 = 中信银行;
        }

        public String get广发银行() {
            return 广发银行;
        }

        public void set广发银行(String 广发银行) {
            this.广发银行 = 广发银行;
        }

        public String get华夏银行() {
            return 华夏银行;
        }

        public void set华夏银行(String 华夏银行) {
            this.华夏银行 = 华夏银行;
        }

        public String get平安银行() {
            return 平安银行;
        }

        public void set平安银行(String 平安银行) {
            this.平安银行 = 平安银行;
        }

        public String get邮政储蓄银行() {
            return 邮政储蓄银行;
        }

        public void set邮政储蓄银行(String 邮政储蓄银行) {
            this.邮政储蓄银行 = 邮政储蓄银行;
        }

        public String get浦发银行() {
            return 浦发银行;
        }

        public void set浦发银行(String 浦发银行) {
            this.浦发银行 = 浦发银行;
        }

        public String get北京农商() {
            return 北京农商;
        }

        public void set北京农商(String 北京农商) {
            this.北京农商 = 北京农商;
        }

        public String get上海银行() {
            return 上海银行;
        }

        public void set上海银行(String 上海银行) {
            this.上海银行 = 上海银行;
        }

        public String get银联支付() {
            return 银联支付;
        }

        public void set银联支付(String 银联支付) {
            this.银联支付 = 银联支付;
        }
    }

    public static class RechargeOfferBean {
        /**
         * id : 91
         * account_company : 19
         * business_code : 80061033
         * business_pwd : FE8CA8A5B6D3186EF29D03C548D0F91E
         * url : 119.47.83.129
         * baofooid : 必付网银收款 0.6%
         * level : 3,4,6,7,8,9,10
         * status : 1
         * recharge_offer : 0.00
         * pay_type : 0
         * notify_url : 119.47.83.129
         */

        private String id;
        private String account_company;
        private String business_code;
        private String business_pwd;
        private String url;
        private String baofooid;
        private String level;
        private String status;
        private String recharge_offer;
        private String pay_type;
        private String notify_url;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAccount_company() {
            return account_company;
        }

        public void setAccount_company(String account_company) {
            this.account_company = account_company;
        }

        public String getBusiness_code() {
            return business_code;
        }

        public void setBusiness_code(String business_code) {
            this.business_code = business_code;
        }

        public String getBusiness_pwd() {
            return business_pwd;
        }

        public void setBusiness_pwd(String business_pwd) {
            this.business_pwd = business_pwd;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBaofooid() {
            return baofooid;
        }

        public void setBaofooid(String baofooid) {
            this.baofooid = baofooid;
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

        public String getRecharge_offer() {
            return recharge_offer;
        }

        public void setRecharge_offer(String recharge_offer) {
            this.recharge_offer = recharge_offer;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getNotify_url() {
            return notify_url;
        }

        public void setNotify_url(String notify_url) {
            this.notify_url = notify_url;
        }
    }
}
