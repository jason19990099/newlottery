package com.international.wtw.lottery.json;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.international.wtw.lottery.R;
import com.international.wtw.lottery.adapter.PayWaysAdapter;

import java.io.Serializable;
import java.util.List;

/**
 * Created by XiaoXin on 2017/10/9.
 * 描述：
 */

public class OnlinePayModel implements MultiItemEntity, Serializable {
    /**
     * 支付类型 0-银行卡,1-支付宝,2-微信,3-财付通,4-快捷支付
     */
    public int type;
    /**
     * 充值最小金额
     */
    public int minMoney;
    /**
     * 充值最大金额
     */
    public int maxMoney;

    public List<OnlinePayChannel> channels;

    public String gameName;

    public String getPayName() {
        switch (type) {
            case 0:
                return "网银在线充值";
            case 1:
                return "支付宝在线充值";
            case 2:
                return "微信在线充值";
            case 3:
                return "QQ钱包在线充值";
            case 4:
                return "快捷支付";
        }
        return "网银在线充值";
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
                return R.drawable.img_logo_quick_small;
        }
        return R.drawable.img_logo_union_small;
    }

    public int getLargeLogoRes() {
        switch (type) {
            case 0://在线
                return R.drawable.img_logo_union_large;
            case 1://支付宝
                return R.drawable.img_logo_alipay_large;
            case 2://微信
                return R.drawable.img_logo_wecaht_large;
            case 3://财付通
                return R.drawable.img_logo_cft_large;
            case 4://快捷
                return R.drawable.img_logo_quick_large;
        }
        return R.drawable.img_logo_union_large;
    }

    @Override
    public int getItemType() {
        return PayWaysAdapter.TYPE_ONLINE_PAY;
    }
}
