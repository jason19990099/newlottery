package com.international.wtw.lottery.json;

/**
 * Created by XIAOYAN on 2017/9/23.
 */

public class SummaryBean extends BaseModel{

    private SummaryInBean summary;

    public SummaryInBean getSummary() {
        return summary;
    }

    public void setSummary(SummaryInBean summary) {
        this.summary = summary;
    }

    public SummaryBean() {
        super();
    }

    public SummaryBean(SummaryInBean summary) {
        this.summary = summary;
    }

    @Override
    public String toString() {
        return "SummaryBean{" +
                "summary=" + summary +
                '}';
    }
}
