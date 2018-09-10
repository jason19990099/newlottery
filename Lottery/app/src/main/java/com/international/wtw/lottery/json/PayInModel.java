package com.international.wtw.lottery.json;

import java.util.List;

/**
 * Created by XiaoXin on 2017/10/9.
 * 描述：
 */
public class PayInModel extends BaseModel {

    /**
     * online_bankU : ["http://pay.sz10015.cn/pay.php/recharge?userid=62578&pay_id=205&isShowBank=1","https://pay.alcp33.com/pay.php/recharge?userid=62578&pay_id=107&isShowBank=0"]
     * online_wechatU : ["http://pay.sz10015.cn/pay.php/recharge?userid=62578&pay_id=204","https://pay.alcp33.com/pay.php/recharge?userid=62578&pay_id=106"]
     * online_cftU : ["https://pay.alcp33.com/pay.php/recharge?userid=62578&pay_id=202"]
     * online_bank : ["205","107"]
     * online_alipay : []
     * online_wechat : ["204","106"]
     * online_cft : ["202"]
     * online_quickpay : []
     * bankpay_array : [{"id":"91","bank_name":"农业银行","bank_user":"永安市辉隆达贸易有限公司","bank_addres":"中国农业银行","bank_account":"13810201040012019","level":"0,10","status":"1","bank_image_url":"","bank_image_url2":"","url_type":"1","recharge_offer":"0.00"}]
     * alipay_array : [{"id":"96","bank_name":"支付宝","bank_user":"铭卢灯具经营部","bank_addres":"若无法支付请联系客服","bank_account":"若无法支付请联系客服","level":"0,10","status":"1","bank_image_url":"http://ockag92kg.bkt.clouddn.com/hxr.jpg","bank_image_url2":"","url_type":"1","recharge_offer":"0.00"}]
     * cft_array : []
     * wechat_array : [{"id":"86","bank_name":"微信","bank_user":"永安市辉隆达贸易有限公司","bank_addres":"提交订单备注商户单号后四位","bank_account":"转账请备注会员账号","level":"0","status":"1","bank_image_url":"http://ockag92kg.bkt.clouddn.com/hld-pingan.png","bank_image_url2":"","url_type":"1","recharge_offer":"0.00"}]
     * quickpay_array : []
     * linedown : {"bankpay_array":[{"id":"91","bank_name":"农业银行","bank_user":"永安市辉隆达贸易有限公司","bank_addres":"中国农业银行","bank_account":"13810201040012019","level":"0,10","status":"1","bank_image_url":"","bank_image_url2":"","url_type":"1","recharge_offer":"0.00"}],"alipay_array":[{"id":"96","bank_name":"支付宝","bank_user":"铭卢灯具经营部","bank_addres":"若无法支付请联系客服","bank_account":"若无法支付请联系客服","level":"0,10","status":"1","bank_image_url":"http://ockag92kg.bkt.clouddn.com/hxr.jpg","bank_image_url2":"","url_type":"1","recharge_offer":"0.00"}],"cft_array":[],"wechat_array":[{"id":"86","bank_name":"微信","bank_user":"永安市辉隆达贸易有限公司","bank_addres":"提交订单备注商户单号后四位","bank_account":"转账请备注会员账号","level":"0","status":"1","bank_image_url":"http://ockag92kg.bkt.clouddn.com/hld-pingan.png","bank_image_url2":"","url_type":"1","recharge_offer":"0.00"}],"quickpay_array":[]}
     * moneylimit : {"bankmin":"50","bankmax":"49999","wechatmin":"10","wechatmax":"4999","alipaymin":"500","alipaymax":"5000","cftpaymin":"50","cftpaymax":"3000","quickpaymin":"10","quickpaymax":"5000","linedownmin":"50"}
     */

    public MoneyLimitBean moneylimit;
    public List<String> online_bankU;
    public List<String> online_alipayU;
    public List<String> online_wechatU;
    public List<String> online_cftU;
    public List<String> online_quickpayU;
    public List<String> online_bank;
    public List<String> online_alipay;
    public List<String> online_wechat;
    public List<String> online_cft;
    public List<String> online_quickpay;
    public List<OfflinePayModel> bankpay_array;
    public List<OfflinePayModel> alipay_array;
    public List<OfflinePayModel> cft_array;
    public List<OfflinePayModel> wechat_array;
    public List<OfflinePayModel> quickpay_array;

    public static class MoneyLimitBean {
        /**
         * bankmin : 50
         * bankmax : 49999
         * wechatmin : 10
         * wechatmax : 4999
         * alipaymin : 500
         * alipaymax : 5000
         * cftpaymin : 50
         * cftpaymax : 3000
         * quickpaymin : 10
         * quickpaymax : 5000
         * linedownmin : 50
         */

        public int bankmin;
        public int bankmax;
        public int wechatmin;
        public int wechatmax;
        public int alipaymin;
        public int alipaymax;
        public int cftpaymin;
        public int cftpaymax;
        public int quickpaymin;
        public int quickpaymax;
        public int linedownmin;
    }
}
