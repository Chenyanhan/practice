package com.cyh.sell.enums;

public enum ResultEnums {
    SUCCESS(0,"操作成功"),

    PRODUCT_NOT_EXIST(20000,"商品不存在"),

    PRODUCT_STOCK_ERRO(30000,"库存不足"),

    ORDER_NOT_EXIST(40000,"订单不存在"),

    ORDERDETAIL_NOT_EXIST(50000,"订单详情不存在"),

    ORDER_UPDATE_FAIL(60000,"订单更新失败"),

    ORDER_PAY_FAIL(70000,"订单已完结不能取消"),

    ORDER_STATUS_ERRO(80000,"订单状态错误"),

    PARAM_ERROR(1000,"参数错误"),

    CART_EMPTY(2000,"购物车不能为空"),

    ORDER_OWNER_ERRO(3000,"该订单不属于当前用户"),

    PRODUCT_STATUS_ERRO(4000,"商品状态错误"),

    WECHAT_MP_ERROR(20,"微信公众账号方面错误")
    ;
    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    ResultEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    ResultEnums() {
    }
}
