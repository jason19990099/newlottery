package com.international.wtw.lottery.json;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.PayWaysAdapter;

import java.io.Serializable;

/**
 * Created by XiaoXin on 2017/10/9.
 * 描述：
 */

public class OfflinePayModel implements MultiItemEntity, Serializable {
    /**
     * 支付类型 0-银行卡,1-支付宝,2-微信,3-财付通,4-快捷支付
     */
    public int type;
    /**
     * 最低金额
     */
    public int minMoney;

    public String gameName;

    /**
     * id : 86
     * bank_name : 微信
     * bank_user : 永安市辉隆达贸易有限公司
     * bank_addres : 提交订单备注商户单号后四位
     * bank_account : 转账请备注会员账号
     * level : 0
     * status : 1
     * bank_image_url : http://ockag92kg.bkt.clouddn.com/hld-pingan.png
     * bank_image_url2 :
     * url_type : 1
     * recharge_offer : 0.00
     */
    public String id;
    public String bank_name;
    public String bank_user;
    public String bank_addres;
    public String bank_account;
    public String level;
    public String status;
    public String bank_image_url;
    public String bank_image_url2;
    public String url_type;
    public String recharge_offer;

    public String getPayName() {
        switch (type) {
            case 0:
                return "银行转账";
            case 1:
                return "支付宝转账";
            case 2:
                return "微信转账";
            case 3:
                return "QQ钱包转账";
            case 4:
                return "快捷转账";
        }
        return "银行转账";
    }

    public int getSmallLogoRes() {
        switch (type) {
            case 0:
                return R.drawable.img_logo_union_small;
            case 1:
                return R.drawable.img_logo_alipay_small;
            case 2:
                return R.drawable.img_logo_wecaht_small;
            case 3:
                return R.drawable.img_logo_cft_small;
            case 4:
                return R.drawable.img_logo_union_small;
        }
        return R.drawable.img_logo_union_small;
    }

    public int getLargeLogoRes() {
        switch (type) {
            case 0://在线
                return R.drawable.selector_offline_unionpay;
            case 1://支付宝
                return R.drawable.selector_offline_alipay;
            case 2://微信
                return R.drawable.selector_offline_wechatpay;
            case 3://财付通
                return R.drawable.selector_offline_cftpay;
            case 4://快捷
                return R.drawable.selector_offline_unionpay;
        }
        return R.drawable.selector_offline_unionpay;
    }

    @Override
    public int getItemType() {
        return PayWaysAdapter.TYPE_OFFLINE_PAY;
    }
}
