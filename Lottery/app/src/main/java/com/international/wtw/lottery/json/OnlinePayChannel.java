package com.international.wtw.lottery.json;


import java.io.Serializable;

public class OnlinePayChannel implements Serializable {
    /**
     * 支付类型 0-银行卡,1-支付宝,2-微信,3-财付通,4-快捷支付
     */
    public int type;
    /**
     * 通道名称
     */
    public String channelName;
    /**
     * 支付链接
     */
    public String payUrl;
    /**
     * payId
     */
    public String payId;
    /**
     * 支付页面logo图片resIdl
     */
    public int largeLogoRes;

    public String getTypeName() {
        switch (type) {
            case 0:
                return "网银";
            case 1:
                return "支付宝";
            case 2:
                return "微信";
            case 3:
                return "QQ钱包";
            default:
                return "在线支付";
        }
    }
}
