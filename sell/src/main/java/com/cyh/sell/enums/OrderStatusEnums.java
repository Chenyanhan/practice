package com.cyh.sell.enums;

public enum OrderStatusEnums implements CodeEnums{
    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"取消订单");

    private Integer code;

    private String message;

    OrderStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {

        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
