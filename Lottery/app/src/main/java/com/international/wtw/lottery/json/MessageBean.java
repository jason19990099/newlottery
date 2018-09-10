package com.international.wtw.lottery.json;

import java.util.List;

/**
 * Created by XIAOYAN on 2017/12/5.
 */

public class MessageBean extends BaseModel{


    private List<MsgListBean> msgList;

    public List<MsgListBean> getMsgList() {
        return msgList;
    }

    public void setMsgList(List<MsgListBean> msgList) {
        this.msgList = msgList;
    }

    public static class MsgListBean {
        /**
         * id : 95
         * comment : 消息内容
         * addtime : 2017-08-13 00:04:04
         * isRead : 0
         */

        private String id;
        private String comment;
        private String addtime;
        private int isRead;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public int getIsRead() {
            return isRead;
        }

        public void setIsRead(int isRead) {
            this.isRead = isRead;
        }
    }
}
