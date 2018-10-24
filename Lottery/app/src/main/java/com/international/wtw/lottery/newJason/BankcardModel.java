package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

import java.util.List;

public class BankcardModel extends BaseModel {

    /**
     * data : [{"Id":180,"DictionaryGroupId":11,"DictionaryGroupName":null,"KeyName":"中国银行","KeyValue":"zhongguoyinhang","Description":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":-2209017600,"UpdateBy":"0","UpdateTime":-2209017600},{"Id":181,"DictionaryGroupId":11,"DictionaryGroupName":null,"KeyName":"工商银行","KeyValue":"gongshangyinhang","Description":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":-2209017600,"UpdateBy":"0","UpdateTime":-2209017600},{"Id":182,"DictionaryGroupId":11,"DictionaryGroupName":null,"KeyName":"建设银行","KeyValue":"jiansheyinhang","Description":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":-2209017600,"UpdateBy":"0","UpdateTime":-2209017600},{"Id":183,"DictionaryGroupId":11,"DictionaryGroupName":null,"KeyName":"农业银行","KeyValue":"nongyeyinhang","Description":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":-2209017600,"UpdateBy":"0","UpdateTime":-2209017600},{"Id":184,"DictionaryGroupId":11,"DictionaryGroupName":null,"KeyName":"招商银行","KeyValue":"zhaoshangyinhang","Description":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":-2209017600,"UpdateBy":"0","UpdateTime":-2209017600},{"Id":185,"DictionaryGroupId":11,"DictionaryGroupName":null,"KeyName":"交通银行","KeyValue":"jiaotongyinhang","Description":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":-2209017600,"UpdateBy":"0","UpdateTime":-2209017600},{"Id":186,"DictionaryGroupId":11,"DictionaryGroupName":null,"KeyName":"民生银行","KeyValue":"minshengyinhang","Description":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":-2209017600,"UpdateBy":"0","UpdateTime":-2209017600},{"Id":188,"DictionaryGroupId":11,"DictionaryGroupName":null,"KeyName":"平安银行","KeyValue":"pinganyinhang","Description":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":-2209017600,"UpdateBy":"0","UpdateTime":-2209017600},{"Id":189,"DictionaryGroupId":11,"DictionaryGroupName":null,"KeyName":"邮政银行","KeyValue":"youzhengyinhang","Description":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":-2209017600,"UpdateBy":"0","UpdateTime":-2209017600},{"Id":190,"DictionaryGroupId":11,"DictionaryGroupName":null,"KeyName":"北京银行","KeyValue":"beijingyinhang","Description":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":-2209017600,"UpdateBy":"0","UpdateTime":-2209017600},{"Id":191,"DictionaryGroupId":11,"DictionaryGroupName":null,"KeyName":"兴业银行","KeyValue":"xingyeyinhang","Description":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":-2209017600,"UpdateBy":"0","UpdateTime":-2209017600}]
     * timestamp  : 1540269355
     */

    private int timestamp;
    private List<DataBean> data;

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * Id : 180
         * DictionaryGroupId : 11
         * DictionaryGroupName : null
         * KeyName : 中国银行
         * KeyValue : zhongguoyinhang
         * Description : null
         * pageIndex : 1
         * pageSize : 10
         * pageSort : null
         * minAmount : 0
         * maxAmount : 0
         * beginTime : -2209017600
         * endTime : -2209017600
         * Sort : 0
         * IsDisable : false
         * CreateBy : 0
         * CreateTime : -2209017600
         * UpdateBy : 0
         * UpdateTime : -2209017600
         */

        private String Id;
        private int DictionaryGroupId;
        private Object DictionaryGroupName;
        private String KeyName;
        private String KeyValue;
        private Object Description;
        private int pageIndex;
        private int pageSize;
        private Object pageSort;
        private int minAmount;
        private int maxAmount;
        private long beginTime;
        private long endTime;
        private int Sort;
        private boolean IsDisable;
        private String CreateBy;
        private long CreateTime;
        private String UpdateBy;
        private long UpdateTime;

        public String getId() {
            return Id;
        }

        public void setId(String id) {
            Id = id;
        }

        public int getDictionaryGroupId() {
            return DictionaryGroupId;
        }

        public void setDictionaryGroupId(int DictionaryGroupId) {
            this.DictionaryGroupId = DictionaryGroupId;
        }

        public Object getDictionaryGroupName() {
            return DictionaryGroupName;
        }

        public void setDictionaryGroupName(Object DictionaryGroupName) {
            this.DictionaryGroupName = DictionaryGroupName;
        }

        public String getKeyName() {
            return KeyName;
        }

        public void setKeyName(String KeyName) {
            this.KeyName = KeyName;
        }

        public String getKeyValue() {
            return KeyValue;
        }

        public void setKeyValue(String KeyValue) {
            this.KeyValue = KeyValue;
        }

        public Object getDescription() {
            return Description;
        }

        public void setDescription(Object Description) {
            this.Description = Description;
        }

        public int getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(int pageIndex) {
            this.pageIndex = pageIndex;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public Object getPageSort() {
            return pageSort;
        }

        public void setPageSort(Object pageSort) {
            this.pageSort = pageSort;
        }

        public int getMinAmount() {
            return minAmount;
        }

        public void setMinAmount(int minAmount) {
            this.minAmount = minAmount;
        }

        public int getMaxAmount() {
            return maxAmount;
        }

        public void setMaxAmount(int maxAmount) {
            this.maxAmount = maxAmount;
        }

        public long getBeginTime() {
            return beginTime;
        }

        public void setBeginTime(long beginTime) {
            this.beginTime = beginTime;
        }

        public long getEndTime() {
            return endTime;
        }

        public void setEndTime(long endTime) {
            this.endTime = endTime;
        }

        public int getSort() {
            return Sort;
        }

        public void setSort(int Sort) {
            this.Sort = Sort;
        }

        public boolean isIsDisable() {
            return IsDisable;
        }

        public void setIsDisable(boolean IsDisable) {
            this.IsDisable = IsDisable;
        }

        public String getCreateBy() {
            return CreateBy;
        }

        public void setCreateBy(String CreateBy) {
            this.CreateBy = CreateBy;
        }

        public long getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(long CreateTime) {
            this.CreateTime = CreateTime;
        }

        public String getUpdateBy() {
            return UpdateBy;
        }

        public void setUpdateBy(String UpdateBy) {
            this.UpdateBy = UpdateBy;
        }

        public long getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(long UpdateTime) {
            this.UpdateTime = UpdateTime;
        }
    }
}
