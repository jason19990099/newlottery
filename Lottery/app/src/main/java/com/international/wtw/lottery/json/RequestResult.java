package com.international.wtw.lottery.json;

import java.io.Serializable;

/**
 * Created by wuya on 2017/5/8.
 */

public class RequestResult  implements Serializable {
    /**
     * error_no : 22
     * error_info : Register succend!
     * info : 注册成功！
     */

    private String error_no;
    private String error_info;
    private String info;

    public String getError_no() {
        return error_no;
    }

    public void setError_no(String error_no) {
        this.error_no = error_no;
    }

    public String getError_info() {
        return error_info;
    }

    public void setError_info(String error_info) {
        this.error_info = error_info;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
