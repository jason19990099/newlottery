package com.international.wtw.lottery.json;


/**
 *
 */
public class QRModel extends BaseModel {


    /**
     * 订单号
     */
    private String order;

    /**
     * 二维码图片地址  //地址有可能为空  但jump_url跟code_url字段必有一个不为空
     */
    private String jump_url;

    /**
     * 可以生成二维码的有效连接   //地址有可能为空 但jump_url跟code_url字段必有一个不为空
     */
    private String code_url;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getJump_url() {
        return jump_url;
    }

    public void setJump_url(String jump_url) {
        this.jump_url = jump_url;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }
}
