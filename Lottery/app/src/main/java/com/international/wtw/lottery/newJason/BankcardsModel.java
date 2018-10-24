package com.international.wtw.lottery.newJason;

import com.international.wtw.lottery.json.BaseModel;

public class BankcardsModel extends BaseModel {

    /**
     * data : {"Sort":0,"IsDisable":false,"IsDelete":false,"CreateBy":181005161602202088,"CreateTime":"2018-10-23T16:00:02.747","UpdateBy":181005161602202088,"UpdateTime":"2018-10-23T16:00:03.433","Id":181023160002745700,"UserId":181005161602202088,"BankId":191,"BankName":"兴业银行","BankAccount":"5566666666666665888","BankAccountName":"小胖子","BankAddress":"寂寞吗","Description":"","IsDefault":false}
     * timestamp : 1540282859
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
         * Sort : 0
         * IsDisable : false
         * IsDelete : false
         * CreateBy : 181005161602202088
         * CreateTime : 2018-10-23T16:00:02.747
         * UpdateBy : 181005161602202088
         * UpdateTime : 2018-10-23T16:00:03.433
         * Id : 181023160002745700
         * UserId : 181005161602202088
         * BankId : 191
         * BankName : 兴业银行
         * BankAccount : 5566666666666665888
         * BankAccountName : 小胖子
         * BankAddress : 寂寞吗
         * Description :
         * IsDefault : false
         */

        private int Sort;
        private boolean IsDisable;
        private boolean IsDelete;
        private long CreateBy;
        private String CreateTime;
        private long UpdateBy;
        private String UpdateTime;
        private long Id;
        private long UserId;
        private int BankId;
        private String BankName;
        private String BankAccount;
        private String BankAccountName;
        private String BankAddress;
        private String Description;
        private boolean IsDefault;

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

        public boolean isIsDelete() {
            return IsDelete;
        }

        public void setIsDelete(boolean IsDelete) {
            this.IsDelete = IsDelete;
        }

        public long getCreateBy() {
            return CreateBy;
        }

        public void setCreateBy(long CreateBy) {
            this.CreateBy = CreateBy;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public long getUpdateBy() {
            return UpdateBy;
        }

        public void setUpdateBy(long UpdateBy) {
            this.UpdateBy = UpdateBy;
        }

        public String getUpdateTime() {
            return UpdateTime;
        }

        public void setUpdateTime(String UpdateTime) {
            this.UpdateTime = UpdateTime;
        }

        public long getId() {
            return Id;
        }

        public void setId(long Id) {
            this.Id = Id;
        }

        public long getUserId() {
            return UserId;
        }

        public void setUserId(long UserId) {
            this.UserId = UserId;
        }

        public int getBankId() {
            return BankId;
        }

        public void setBankId(int BankId) {
            this.BankId = BankId;
        }

        public String getBankName() {
            return BankName;
        }

        public void setBankName(String BankName) {
            this.BankName = BankName;
        }

        public String getBankAccount() {
            return BankAccount;
        }

        public void setBankAccount(String BankAccount) {
            this.BankAccount = BankAccount;
        }

        public String getBankAccountName() {
            return BankAccountName;
        }

        public void setBankAccountName(String BankAccountName) {
            this.BankAccountName = BankAccountName;
        }

        public String getBankAddress() {
            return BankAddress;
        }

        public void setBankAddress(String BankAddress) {
            this.BankAddress = BankAddress;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public boolean isIsDefault() {
            return IsDefault;
        }

        public void setIsDefault(boolean IsDefault) {
            this.IsDefault = IsDefault;
        }
    }
}
