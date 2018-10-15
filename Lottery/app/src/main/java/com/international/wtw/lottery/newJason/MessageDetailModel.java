package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

public class MessageDetailModel extends BaseModel {


    /**
     * data : {"Id":"181012180114350009","ToUserId":"181005161602202088","FromUserId":"1806020109265233","FromUsername":"123","DictionaryId":25,"DictionaryName":null,"Title":"000000000000000","Content":"000000000000000","PushNum":3,"IsView":false,"Dictionary":null,"pageIndex":1,"pageSize":10,"pageSort":null,"minAmount":0,"maxAmount":0,"beginTime":-2209017600,"endTime":-2209017600,"Sort":0,"IsDisable":false,"CreateBy":"0","CreateTime":1539338474,"UpdateBy":"0","UpdateTime":-2209017600}
     * timestamp : 1539589453
     */

    private DataBean data;
    private int timestamp;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public static class DataBean {
        /**
         * Id : 181012180114350009
         * ToUserId : 181005161602202088
         * FromUserId : 1806020109265233
         * FromUsername : 123
         * DictionaryId : 25
         * DictionaryName : null
         * Title : 000000000000000
         * Content : 000000000000000
         * PushNum : 3
         * IsView : false
         * Dictionary : null
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
         * CreateTime : 1539338474
         * UpdateBy : 0
         * UpdateTime : -2209017600
         */

        private String Id;
        private String ToUserId;
        private String FromUserId;
        private String FromUsername;
        private int DictionaryId;
        private Object DictionaryName;
        private String Title;
        private String Content;
        private int PushNum;
        private boolean IsView;
        private Object Dictionary;
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

        public void setId(String Id) {
            this.Id = Id;
        }

        public String getToUserId() {
            return ToUserId;
        }

        public void setToUserId(String ToUserId) {
            this.ToUserId = ToUserId;
        }

        public String getFromUserId() {
            return FromUserId;
        }

        public void setFromUserId(String FromUserId) {
            this.FromUserId = FromUserId;
        }

        public String getFromUsername() {
            return FromUsername;
        }

        public void setFromUsername(String FromUsername) {
            this.FromUsername = FromUsername;
        }

        public int getDictionaryId() {
            return DictionaryId;
        }

        public void setDictionaryId(int DictionaryId) {
            this.DictionaryId = DictionaryId;
        }

        public Object getDictionaryName() {
            return DictionaryName;
        }

        public void setDictionaryName(Object DictionaryName) {
            this.DictionaryName = DictionaryName;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public String getContent() {
            return Content;
        }

        public void setContent(String Content) {
            this.Content = Content;
        }

        public int getPushNum() {
            return PushNum;
        }

        public void setPushNum(int PushNum) {
            this.PushNum = PushNum;
        }

        public boolean isIsView() {
            return IsView;
        }

        public void setIsView(boolean IsView) {
            this.IsView = IsView;
        }

        public Object getDictionary() {
            return Dictionary;
        }

        public void setDictionary(Object Dictionary) {
            this.Dictionary = Dictionary;
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

        public void setCreateTime(long createTime) {
            CreateTime = createTime;
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
