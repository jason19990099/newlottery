package com.international.wtw.lottery.event;

import com.international.wtw.lottery.newJason.PK10Rate;

public class Pk10RateEvent extends  BaseEvent {
    private PK10Rate pk10Rate;

    public Pk10RateEvent(PK10Rate data) {
        this.pk10Rate=data;
    }

    public PK10Rate getPk10Rate() {
        return pk10Rate;
    }

    public void setPk10Rate(PK10Rate pk10Rate) {
        this.pk10Rate = pk10Rate;
    }
}
