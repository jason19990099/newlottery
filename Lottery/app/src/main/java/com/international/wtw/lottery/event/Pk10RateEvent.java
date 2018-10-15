package com.international.wtw.lottery.event;

import com.international.wtw.lottery.newJason.PK10RateModel;

public class Pk10RateEvent extends  BaseEvent {
    private PK10RateModel pk10Rate;

    public Pk10RateEvent(PK10RateModel data) {
        this.pk10Rate=data;
    }

    public PK10RateModel getPk10Rate() {
        return pk10Rate;
    }

    public void setPk10Rate(PK10RateModel pk10Rate) {
        this.pk10Rate = pk10Rate;
    }
}
