package com.international.wtw.lottery.json;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/9/23.
 */

public class SummaryInBean {

    private List<UnSettlementBean> unSettlement;
    private List<HasCloseBean> hasClose;

    public List<UnSettlementBean> getUnSettlement() {
        return unSettlement;
    }

    public void setUnSettlement(List<UnSettlementBean> unSettlement) {
        this.unSettlement = unSettlement;
    }

    public List<HasCloseBean> getHasClose() {
        return hasClose;
    }

    public void setHasClose(List<HasCloseBean> hasClose) {
        this.hasClose = hasClose;
    }

    public SummaryInBean() {
        super();
    }

    public SummaryInBean(List<UnSettlementBean> unSettlement, List<HasCloseBean> hasClose) {
        this.unSettlement = unSettlement;
        this.hasClose = hasClose;
    }

    @Override
    public String toString() {
        return "SummaryInBean{" +
                "unSettlement=" + unSettlement +
                ", hasClose=" + hasClose +
                '}';
    }
}
