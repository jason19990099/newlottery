package com.international.wtw.lottery.newJason;

import java.io.Serializable;

public class BetData implements Serializable {
    private String PlayGroupCode;
    private String PlayCode;
    private String PlayRateCode;
    private String PlayRateValueType;
    private String PlayRateValueId;
    private String PlayRateValue;
    private String Amount;

    public String getPlayGroupCode() {
        return PlayGroupCode;
    }

    public void setPlayGroupCode(String playGroupCode) {
        PlayGroupCode = playGroupCode;
    }

    public String getPlayCode() {
        return PlayCode;
    }

    public void setPlayCode(String playCode) {
        PlayCode = playCode;
    }

    public String getPlayRateCode() {
        return PlayRateCode;
    }

    public void setPlayRateCode(String playRateCode) {
        PlayRateCode = playRateCode;
    }

    public String getPlayRateValueType() {
        return PlayRateValueType;
    }

    public void setPlayRateValueType(String playRateValueType) {
        PlayRateValueType = playRateValueType;
    }

    public String getPlayRateValueId() {
        return PlayRateValueId;
    }

    public void setPlayRateValueId(String playRateValueId) {
        PlayRateValueId = playRateValueId;
    }

    public String getPlayRateValue() {
        return PlayRateValue;
    }

    public void setPlayRateValue(String playRateValue) {
        PlayRateValue = playRateValue;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }
}
