package com.cyh.sell.enums;

import lombok.Getter;

/**
 * 商品状态
 */

@Getter
public enum ProductStatusEnums implements CodeEnums{
    UP(0,"在架"),
    DOWN(1,"下架")

    ;
    private Integer code;

    private String message;

    @Override
    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    ProductStatusEnums(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
