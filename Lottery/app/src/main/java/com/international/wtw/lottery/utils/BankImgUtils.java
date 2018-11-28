package com.international.wtw.lottery.utils;

import com.international.wtw.lottery.R;

public class BankImgUtils {
    private int getImageRes(String bankName) {
        switch (bankName) {
            case "工商银行":
                return R.mipmap.icon_bank_logo_icbc;
            case "建设银行":
                return R.mipmap.icon_bank_logo_ccb;
            case "招商银行":
                return R.mipmap.icon_bank_logo_cmb;
            case "农业银行":
                return R.mipmap.icon_bank_logo_abc;
            case "中国银行":
                return R.mipmap.icon_bank_logo_bc;
            case "邮政储蓄银行":
                return R.mipmap.icon_bank_logo_psbc;
            case "民生银行":
                return R.mipmap.icon_bank_logo_cmbc;
            case "兴业银行":
                return R.mipmap.icon_bank_logo_ibc;
            case "中信银行":
                return R.mipmap.icon_bank_logo_cib;
            case "渤海银行":
                return R.mipmap.icon_bank_logo_cbhb;
            case "光大银行":
                return R.mipmap.icon_bank_logo_ceb;
            case "广发银行":
                return R.mipmap.icon_bank_logo_gdb;
            case "华夏银行":
                return R.mipmap.icon_bank_logo_hxb;
            case "平安银行":
                return R.mipmap.icon_bank_logo_pab;
            case "浦发银行":
                return R.mipmap.icon_bank_logo_spdb;
            case "北京农商银行":
                return R.mipmap.icon_bank_logo_brcb;
            case "上海银行":
                return R.mipmap.icon_bank_logo_bs;
            case "交通银行":
                return R.mipmap.icon_bank_logo_bcomm;
        }
        return R.mipmap.bank_car_icon;
    }

}
