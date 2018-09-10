package com.international.wtw.lottery.json;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/9/23.
 */

public class SummaryDetailsBean extends BaseModel{

    private List<SummaryDetailsResBean> res;
    private SummaryDetailsPageBean page;

    public List<SummaryDetailsResBean> getRes() {
        return res;
    }

    public void setRes(List<SummaryDetailsResBean> res) {
        this.res = res;
    }

    public SummaryDetailsPageBean getPage() {
        return page;
    }

    public void setPage(SummaryDetailsPageBean page) {
        this.page = page;
    }

    public SummaryDetailsBean() {
        super();
    }

    public SummaryDetailsBean(List<SummaryDetailsResBean> res, SummaryDetailsPageBean page) {
        this.res = res;
        this.page = page;
    }

    @Override
    public String toString() {
        return "SummaryDetailsBean{" +
                "res=" + res +
                ", page=" + page +
                '}';
    }
}
