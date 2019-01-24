package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

import java.io.Serializable;
import java.util.List;

public class PaymentMethodModel extends BaseModel {


    /**
     * data : [{"ListPaymentModePaymentMethod":[{"PaymentMethodId":0,"PaymentMethod":{"MaxAmount":10000,"Sort":0,"UserDesc":null,"Code":"Zongbao","ListPaymentMethodBank":[{"BankId":0,"PaymentBank":{"Code":"zhongguoyinhang","Name":"中国银行","IsDisable":false,"Id":1},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"gongshangyinhang","Name":"工商银行","IsDisable":false,"Id":2},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"jiansheyinhang","Name":"建设银行","IsDisable":false,"Id":3},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"nongyeyinhang","Name":"农业银行","IsDisable":false,"Id":4},"PaymentMethodId":0}],"AccountName":null,"AllowSourceType":null,"AccountDesc":null,"BankCode":null,"Name":"众宝网银","BankUrl":null,"AccountNum":"88999917","Id":1046,"MinAmount":10,"AccountAddress":null,"QrcodeUrl":"http://img.shyqyl.com","IsJump":true},"PaymentModeId":0}],"ImageName":"http://img.shyqyl.com/Content/UploadFile/Image/logo/BankQuick.81a1801.png","Code":"BankOnline","IsOnline":true,"ShortName":null,"Name":"网银支付","Id":1},{"ListPaymentModePaymentMethod":[{"PaymentMethodId":0,"PaymentMethod":{"MaxAmount":100000,"Sort":0,"UserDesc":null,"Code":"BankTransfer","ListPaymentMethodBank":[{"BankId":0,"PaymentBank":{"Code":"zhongguoyinhang","Name":"中国银行","IsDisable":false,"Id":1},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"gongshangyinhang","Name":"工商银行","IsDisable":false,"Id":2},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"jiansheyinhang","Name":"建设银行","IsDisable":false,"Id":3},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"nongyeyinhang","Name":"农业银行","IsDisable":false,"Id":4},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"zhaoshangyinhang","Name":"招商银行","IsDisable":false,"Id":5},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"jiaotongyinhang","Name":"交通银行","IsDisable":false,"Id":6},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"guangfayinhang","Name":"广发银行","IsDisable":false,"Id":13},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"pufayinhang","Name":"浦发银行","IsDisable":false,"Id":18},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"hangzouyinhang","Name":"杭州银行","IsDisable":false,"Id":19},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"guangdayinhang","Name":"光大银行","IsDisable":false,"Id":20},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"shanghaiyinhang","Name":"上海银行","IsDisable":false,"Id":25},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"minshengyinhang","Name":"民生银行","IsDisable":false,"Id":7},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"pinganyinhang","Name":"平安银行","IsDisable":false,"Id":8},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"youzhengyinhang","Name":"邮政银行","IsDisable":false,"Id":9},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"beijingyinhang","Name":"北京银行","IsDisable":false,"Id":10},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"xingyeyinhang","Name":"兴业银行","IsDisable":false,"Id":11},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"zhongxinyinhang","Name":"中信银行","IsDisable":false,"Id":12},"PaymentMethodId":0}],"AccountName":"张胜","AllowSourceType":null,"AccountDesc":null,"BankCode":"jiansheyinhang","Name":"建设银行","BankUrl":"http://img.shyqyl.com","AccountNum":"6217000730022696963","Id":1038,"MinAmount":10,"AccountAddress":"沈阳于洪支行","QrcodeUrl":"http://img.shyqyl.com","IsJump":false},"PaymentModeId":0},{"PaymentMethodId":0,"PaymentMethod":{"MaxAmount":100000,"Sort":0,"UserDesc":null,"Code":"BankTransfer","ListPaymentMethodBank":[{"BankId":0,"PaymentBank":{"Code":"zhongguoyinhang","Name":"中国银行","IsDisable":false,"Id":1},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"gongshangyinhang","Name":"工商银行","IsDisable":false,"Id":2},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"jiansheyinhang","Name":"建设银行","IsDisable":false,"Id":3},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"nongyeyinhang","Name":"农业银行","IsDisable":false,"Id":4},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"zhaoshangyinhang","Name":"招商银行","IsDisable":false,"Id":5},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"jiaotongyinhang","Name":"交通银行","IsDisable":false,"Id":6},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"guangfayinhang","Name":"广发银行","IsDisable":false,"Id":13},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"pufayinhang","Name":"浦发银行","IsDisable":false,"Id":18},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"hangzouyinhang","Name":"杭州银行","IsDisable":false,"Id":19},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"guangdayinhang","Name":"光大银行","IsDisable":false,"Id":20},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"shanghaiyinhang","Name":"上海银行","IsDisable":false,"Id":25},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"minshengyinhang","Name":"民生银行","IsDisable":false,"Id":7},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"pinganyinhang","Name":"平安银行","IsDisable":false,"Id":8},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"youzhengyinhang","Name":"邮政银行","IsDisable":false,"Id":9},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"beijingyinhang","Name":"北京银行","IsDisable":false,"Id":10},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"xingyeyinhang","Name":"兴业银行","IsDisable":false,"Id":11},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"zhongxinyinhang","Name":"中信银行","IsDisable":false,"Id":12},"PaymentMethodId":0}],"AccountName":"锦州颜兵装修装饰工程有限公司","AllowSourceType":null,"AccountDesc":null,"BankCode":"guangdayinhang","Name":"光大银行","BankUrl":"http://img.shyqyl.com","AccountNum":"52320188000077801","Id":1040,"MinAmount":10,"AccountAddress":"锦州分行 ","QrcodeUrl":"http://img.shyqyl.com","IsJump":false},"PaymentModeId":0},{"PaymentMethodId":0,"PaymentMethod":{"MaxAmount":100000,"Sort":0,"UserDesc":null,"Code":"BankTransfer","ListPaymentMethodBank":[{"BankId":0,"PaymentBank":{"Code":"zhongguoyinhang","Name":"中国银行","IsDisable":false,"Id":1},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"gongshangyinhang","Name":"工商银行","IsDisable":false,"Id":2},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"jiansheyinhang","Name":"建设银行","IsDisable":false,"Id":3},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"nongyeyinhang","Name":"农业银行","IsDisable":false,"Id":4},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"zhaoshangyinhang","Name":"招商银行","IsDisable":false,"Id":5},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"jiaotongyinhang","Name":"交通银行","IsDisable":false,"Id":6},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"guangfayinhang","Name":"广发银行","IsDisable":false,"Id":13},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"pufayinhang","Name":"浦发银行","IsDisable":false,"Id":18},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"hangzouyinhang","Name":"杭州银行","IsDisable":false,"Id":19},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"guangdayinhang","Name":"光大银行","IsDisable":false,"Id":20},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"shanghaiyinhang","Name":"上海银行","IsDisable":false,"Id":25},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"minshengyinhang","Name":"民生银行","IsDisable":false,"Id":7},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"pinganyinhang","Name":"平安银行","IsDisable":false,"Id":8},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"youzhengyinhang","Name":"邮政银行","IsDisable":false,"Id":9},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"beijingyinhang","Name":"北京银行","IsDisable":false,"Id":10},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"xingyeyinhang","Name":"兴业银行","IsDisable":false,"Id":11},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"zhongxinyinhang","Name":"中信银行","IsDisable":false,"Id":12},"PaymentMethodId":0}],"AccountName":"王丹","AllowSourceType":null,"AccountDesc":null,"BankCode":"shundenSh","Name":"顺德农商银行行","BankUrl":"http://img.shyqyl.com","AccountNum":"6216286606000086467     ","Id":1043,"MinAmount":10,"AccountAddress":"高明顺银村镇银行","QrcodeUrl":"http://img.shyqyl.com","IsJump":false},"PaymentModeId":0}],"ImageName":"http://img.shyqyl.com/Content/UploadFile/Image/logo/BankTransfer.2c5e0af.png","Code":"BankTransfer","IsOnline":false,"ShortName":null,"Name":"银行卡转账","Id":2},{"ListPaymentModePaymentMethod":[{"PaymentMethodId":0,"PaymentMethod":{"MaxAmount":1000,"Sort":0,"UserDesc":"前台备注","Code":"Xiaoma","ListPaymentMethodBank":[],"AccountName":null,"AllowSourceType":null,"AccountDesc":null,"BankCode":null,"Name":"蚂蚁支付宝扫码(H5)","BankUrl":null,"AccountNum":null,"Id":1049,"MinAmount":10,"AccountAddress":null,"QrcodeUrl":"http://img.shyqyl.com","IsJump":true},"PaymentModeId":0},{"PaymentMethodId":0,"PaymentMethod":{"MaxAmount":10000,"Sort":0,"UserDesc":null,"Code":"Xiaoma","ListPaymentMethodBank":[],"AccountName":null,"AllowSourceType":null,"AccountDesc":null,"BankCode":null,"Name":"蚂蚁支付宝扫码","BankUrl":null,"AccountNum":"180778742","Id":1048,"MinAmount":10,"AccountAddress":null,"QrcodeUrl":"http://img.shyqyl.com","IsJump":true},"PaymentModeId":0}],"ImageName":"http://img.shyqyl.com/Content/UploadFile/Image/logo/Alipay.b780b91.png","Code":"Alipay","IsOnline":true,"ShortName":null,"Name":"支付宝支付","Id":3},{"ListPaymentModePaymentMethod":[{"PaymentMethodId":0,"PaymentMethod":{"MaxAmount":10000,"Sort":0,"UserDesc":null,"Code":"Zongbao","ListPaymentMethodBank":[],"AccountName":null,"AllowSourceType":null,"AccountDesc":null,"BankCode":null,"Name":"众宝微信扫码","BankUrl":null,"AccountNum":"88999917","Id":1045,"MinAmount":10,"AccountAddress":null,"QrcodeUrl":"http://img.shyqyl.com","IsJump":true},"PaymentModeId":0}],"ImageName":"http://img.shyqyl.com/Content/UploadFile/Image/logo/WechatPay.ab7328c.png","Code":"WechatPay","IsOnline":true,"ShortName":null,"Name":"微信支付","Id":4},{"ListPaymentModePaymentMethod":[{"PaymentMethodId":0,"PaymentMethod":{"MaxAmount":10000,"Sort":0,"UserDesc":null,"Code":"AlipayTransfer","ListPaymentMethodBank":[],"AccountName":"张胜","AllowSourceType":null,"AccountDesc":null,"BankCode":null,"Name":"张胜","BankUrl":null,"AccountNum":"6217000730022696963","Id":1037,"MinAmount":10,"AccountAddress":null,"QrcodeUrl":"http://img.shyqyl.com/Content/UploadFile/Image/2019-01/190103161450009677.png","IsJump":false},"PaymentModeId":0},{"PaymentMethodId":0,"PaymentMethod":{"MaxAmount":10000,"Sort":0,"UserDesc":"支付宝转支付宝账号，方便快捷","Code":"AlipayTransfer","ListPaymentMethodBank":[],"AccountName":"魏文杰 ","AllowSourceType":null,"AccountDesc":null,"BankCode":null,"Name":"魏文杰 ","BankUrl":null,"AccountNum":"13019856017","Id":1039,"MinAmount":10,"AccountAddress":null,"QrcodeUrl":"http://img.shyqyl.com/Content/UploadFile/Image/2019-01/190104125955237723.png","IsJump":false},"PaymentModeId":0},{"PaymentMethodId":0,"PaymentMethod":{"MaxAmount":10000,"Sort":1,"UserDesc":null,"Code":"AlipayTransfer","ListPaymentMethodBank":[],"AccountName":"王丹","AllowSourceType":null,"AccountDesc":null,"BankCode":null,"Name":"王丹","BankUrl":null,"AccountNum":"6216286606000086467     ","Id":1041,"MinAmount":10,"AccountAddress":null,"QrcodeUrl":"http://img.shyqyl.com/Content/UploadFile/Image/2019-01/190104201319322043.png","IsJump":false},"PaymentModeId":0}],"ImageName":"http://img.shyqyl.com/Content/UploadFile/Image/logo/Alipay.b780b91.png","Code":"AlipayTransfer","IsOnline":false,"ShortName":null,"Name":"支付宝转账","Id":6},{"ListPaymentModePaymentMethod":[{"PaymentMethodId":0,"PaymentMethod":{"MaxAmount":20000,"Sort":0,"UserDesc":null,"Code":"WechatTransfer","ListPaymentMethodBank":[],"AccountName":"张胜","AllowSourceType":null,"AccountDesc":null,"BankCode":null,"Name":"张胜","BankUrl":null,"AccountNum":"6217000730022696963","Id":1036,"MinAmount":10,"AccountAddress":null,"QrcodeUrl":"http://img.shyqyl.com/Content/UploadFile/Image/2019-01/190103161229822675.png","IsJump":false},"PaymentModeId":0},{"PaymentMethodId":0,"PaymentMethod":{"MaxAmount":20000,"Sort":0,"UserDesc":null,"Code":"WechatTransfer","ListPaymentMethodBank":[],"AccountName":"王丹","AllowSourceType":null,"AccountDesc":null,"BankCode":null,"Name":"王丹","BankUrl":null,"AccountNum":"6216286606000086467     ","Id":1042,"MinAmount":10,"AccountAddress":null,"QrcodeUrl":"http://img.shyqyl.com/Content/UploadFile/Image/2019-01/190104201424481044.png","IsJump":false},"PaymentModeId":0}],"ImageName":"http://img.shyqyl.com/Content/UploadFile/Image/logo/WechatPay.ab7328c.png","Code":"WechatTransfer","IsOnline":false,"ShortName":null,"Name":"微信转账","Id":7},{"ListPaymentModePaymentMethod":[{"PaymentMethodId":0,"PaymentMethod":{"MaxAmount":10000,"Sort":1,"UserDesc":"下载云闪付（原银联钱包）APP，扫码支付成功自动到账，无需提交存款信息 方便快捷","Code":"Xinyifu","ListPaymentMethodBank":[],"AccountName":null,"AllowSourceType":null,"AccountDesc":null,"BankCode":null,"Name":"银联扫码1","BankUrl":null,"AccountNum":"100161810288497009","Id":1023,"MinAmount":10,"AccountAddress":null,"QrcodeUrl":"http://img.shyqyl.com","IsJump":true},"PaymentModeId":0}],"ImageName":"http://img.shyqyl.com/Content/UploadFile/Image/logo/BankQuick.81a1801.png","Code":"BankQuick","IsOnline":true,"ShortName":null,"Name":"银联快捷(扫码)","Id":9}]
     * clientip : 203.177.163.198
     * timestamp : 1548153243
     */

    private String clientip;
    private int timestamp;
    private List<DataBean> data;

    public String getClientip() {
        return clientip;
    }

    public void setClientip(String clientip) {
        this.clientip = clientip;
    }

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

    public static class DataBean implements Serializable {
        /**
         * ListPaymentModePaymentMethod : [{"PaymentMethodId":0,"PaymentMethod":{"MaxAmount":10000,"Sort":0,"UserDesc":null,"Code":"Zongbao","ListPaymentMethodBank":[{"BankId":0,"PaymentBank":{"Code":"zhongguoyinhang","Name":"中国银行","IsDisable":false,"Id":1},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"gongshangyinhang","Name":"工商银行","IsDisable":false,"Id":2},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"jiansheyinhang","Name":"建设银行","IsDisable":false,"Id":3},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"nongyeyinhang","Name":"农业银行","IsDisable":false,"Id":4},"PaymentMethodId":0}],"AccountName":null,"AllowSourceType":null,"AccountDesc":null,"BankCode":null,"Name":"众宝网银","BankUrl":null,"AccountNum":"88999917","Id":1046,"MinAmount":10,"AccountAddress":null,"QrcodeUrl":"http://img.shyqyl.com","IsJump":true},"PaymentModeId":0}]
         * ImageName : http://img.shyqyl.com/Content/UploadFile/Image/logo/BankQuick.81a1801.png
         * Code : BankOnline
         * IsOnline : true
         * ShortName : null
         * Name : 网银支付
         * Id : 1
         */

        private String ImageName;
        private String Code;
        private boolean IsOnline;
        private Object ShortName;
        private String Name;
        private int Id;
        private List<ListPaymentModePaymentMethodBean> ListPaymentModePaymentMethod;

        public String getImageName() {
            return ImageName;
        }

        public void setImageName(String ImageName) {
            this.ImageName = ImageName;
        }

        public String getCode() {
            return Code;
        }

        public void setCode(String Code) {
            this.Code = Code;
        }

        public boolean isIsOnline() {
            return IsOnline;
        }

        public void setIsOnline(boolean IsOnline) {
            this.IsOnline = IsOnline;
        }

        public Object getShortName() {
            return ShortName;
        }

        public void setShortName(Object ShortName) {
            this.ShortName = ShortName;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public List<ListPaymentModePaymentMethodBean> getListPaymentModePaymentMethod() {
            return ListPaymentModePaymentMethod;
        }

        public void setListPaymentModePaymentMethod(List<ListPaymentModePaymentMethodBean> ListPaymentModePaymentMethod) {
            this.ListPaymentModePaymentMethod = ListPaymentModePaymentMethod;
        }

        public static class ListPaymentModePaymentMethodBean implements Serializable {
            /**
             * PaymentMethodId : 0
             * PaymentMethod : {"MaxAmount":10000,"Sort":0,"UserDesc":null,"Code":"Zongbao","ListPaymentMethodBank":[{"BankId":0,"PaymentBank":{"Code":"zhongguoyinhang","Name":"中国银行","IsDisable":false,"Id":1},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"gongshangyinhang","Name":"工商银行","IsDisable":false,"Id":2},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"jiansheyinhang","Name":"建设银行","IsDisable":false,"Id":3},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"nongyeyinhang","Name":"农业银行","IsDisable":false,"Id":4},"PaymentMethodId":0}],"AccountName":null,"AllowSourceType":null,"AccountDesc":null,"BankCode":null,"Name":"众宝网银","BankUrl":null,"AccountNum":"88999917","Id":1046,"MinAmount":10,"AccountAddress":null,"QrcodeUrl":"http://img.shyqyl.com","IsJump":true}
             * PaymentModeId : 0
             */

            private int PaymentMethodId;
            private PaymentMethodBean PaymentMethod;
            private int PaymentModeId;

            public int getPaymentMethodId() {
                return PaymentMethodId;
            }

            public void setPaymentMethodId(int PaymentMethodId) {
                this.PaymentMethodId = PaymentMethodId;
            }

            public PaymentMethodBean getPaymentMethod() {
                return PaymentMethod;
            }

            public void setPaymentMethod(PaymentMethodBean PaymentMethod) {
                this.PaymentMethod = PaymentMethod;
            }

            public int getPaymentModeId() {
                return PaymentModeId;
            }

            public void setPaymentModeId(int PaymentModeId) {
                this.PaymentModeId = PaymentModeId;
            }

            public static class PaymentMethodBean implements Serializable{
                /**
                 * MaxAmount : 10000
                 * Sort : 0
                 * UserDesc : null
                 * Code : Zongbao
                 * ListPaymentMethodBank : [{"BankId":0,"PaymentBank":{"Code":"zhongguoyinhang","Name":"中国银行","IsDisable":false,"Id":1},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"gongshangyinhang","Name":"工商银行","IsDisable":false,"Id":2},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"jiansheyinhang","Name":"建设银行","IsDisable":false,"Id":3},"PaymentMethodId":0},{"BankId":0,"PaymentBank":{"Code":"nongyeyinhang","Name":"农业银行","IsDisable":false,"Id":4},"PaymentMethodId":0}]
                 * AccountName : null
                 * AllowSourceType : null
                 * AccountDesc : null
                 * BankCode : null
                 * Name : 众宝网银
                 * BankUrl : null
                 * AccountNum : 88999917
                 * Id : 1046
                 * MinAmount : 10
                 * AccountAddress : null
                 * QrcodeUrl : http://img.shyqyl.com
                 * IsJump : true
                 */

                private int MaxAmount;
                private int Sort;
                private Object UserDesc;
                private String Code;
                private Object AccountName;
                private Object AllowSourceType;
                private Object AccountDesc;
                private Object BankCode;
                private String Name;
                private Object BankUrl;
                private String AccountNum;
                private int Id;
                private int MinAmount;
                private Object AccountAddress;
                private String QrcodeUrl;
                private boolean IsJump;
                private List<ListPaymentMethodBankBean> ListPaymentMethodBank;

                public int getMaxAmount() {
                    return MaxAmount;
                }

                public void setMaxAmount(int MaxAmount) {
                    this.MaxAmount = MaxAmount;
                }

                public int getSort() {
                    return Sort;
                }

                public void setSort(int Sort) {
                    this.Sort = Sort;
                }

                public Object getUserDesc() {
                    return UserDesc;
                }

                public void setUserDesc(Object UserDesc) {
                    this.UserDesc = UserDesc;
                }

                public String getCode() {
                    return Code;
                }

                public void setCode(String Code) {
                    this.Code = Code;
                }

                public Object getAccountName() {
                    return AccountName;
                }

                public void setAccountName(Object AccountName) {
                    this.AccountName = AccountName;
                }

                public Object getAllowSourceType() {
                    return AllowSourceType;
                }

                public void setAllowSourceType(Object AllowSourceType) {
                    this.AllowSourceType = AllowSourceType;
                }

                public Object getAccountDesc() {
                    return AccountDesc;
                }

                public void setAccountDesc(Object AccountDesc) {
                    this.AccountDesc = AccountDesc;
                }

                public Object getBankCode() {
                    return BankCode;
                }

                public void setBankCode(Object BankCode) {
                    this.BankCode = BankCode;
                }

                public String getName() {
                    return Name;
                }

                public void setName(String Name) {
                    this.Name = Name;
                }

                public Object getBankUrl() {
                    return BankUrl;
                }

                public void setBankUrl(Object BankUrl) {
                    this.BankUrl = BankUrl;
                }

                public String getAccountNum() {
                    return AccountNum;
                }

                public void setAccountNum(String AccountNum) {
                    this.AccountNum = AccountNum;
                }

                public int getId() {
                    return Id;
                }

                public void setId(int Id) {
                    this.Id = Id;
                }

                public int getMinAmount() {
                    return MinAmount;
                }

                public void setMinAmount(int MinAmount) {
                    this.MinAmount = MinAmount;
                }

                public Object getAccountAddress() {
                    return AccountAddress;
                }

                public void setAccountAddress(Object AccountAddress) {
                    this.AccountAddress = AccountAddress;
                }

                public String getQrcodeUrl() {
                    return QrcodeUrl;
                }

                public void setQrcodeUrl(String QrcodeUrl) {
                    this.QrcodeUrl = QrcodeUrl;
                }

                public boolean isIsJump() {
                    return IsJump;
                }

                public void setIsJump(boolean IsJump) {
                    this.IsJump = IsJump;
                }

                public List<ListPaymentMethodBankBean> getListPaymentMethodBank() {
                    return ListPaymentMethodBank;
                }

                public void setListPaymentMethodBank(List<ListPaymentMethodBankBean> ListPaymentMethodBank) {
                    this.ListPaymentMethodBank = ListPaymentMethodBank;
                }

                public static class ListPaymentMethodBankBean implements Serializable {
                    /**
                     * BankId : 0
                     * PaymentBank : {"Code":"zhongguoyinhang","Name":"中国银行","IsDisable":false,"Id":1}
                     * PaymentMethodId : 0
                     */

                    private int BankId;
                    private PaymentBankBean PaymentBank;
                    private int PaymentMethodId;

                    public int getBankId() {
                        return BankId;
                    }

                    public void setBankId(int BankId) {
                        this.BankId = BankId;
                    }

                    public PaymentBankBean getPaymentBank() {
                        return PaymentBank;
                    }

                    public void setPaymentBank(PaymentBankBean PaymentBank) {
                        this.PaymentBank = PaymentBank;
                    }

                    public int getPaymentMethodId() {
                        return PaymentMethodId;
                    }

                    public void setPaymentMethodId(int PaymentMethodId) {
                        this.PaymentMethodId = PaymentMethodId;
                    }

                    public static class PaymentBankBean implements Serializable{
                        /**
                         * Code : zhongguoyinhang
                         * Name : 中国银行
                         * IsDisable : false
                         * Id : 1
                         */

                        private String Code;
                        private String Name;
                        private boolean IsDisable;
                        private int Id;

                        public String getCode() {
                            return Code;
                        }

                        public void setCode(String Code) {
                            this.Code = Code;
                        }

                        public String getName() {
                            return Name;
                        }

                        public void setName(String Name) {
                            this.Name = Name;
                        }

                        public boolean isIsDisable() {
                            return IsDisable;
                        }

                        public void setIsDisable(boolean IsDisable) {
                            this.IsDisable = IsDisable;
                        }

                        public int getId() {
                            return Id;
                        }

                        public void setId(int Id) {
                            this.Id = Id;
                        }
                    }
                }
            }
        }
    }
}
