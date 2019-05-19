package com.cyh.sell.dto;

import com.cyh.sell.dataobject.OrderDetail;
import com.cyh.sell.enums.OrderStatusEnums;
import com.cyh.sell.enums.PayStatusEnums;
import com.cyh.sell.enums.ProductStatusEnums;
import com.cyh.sell.utils.EnumsUtils;
import com.cyh.sell.utils.serializer.Date2LongSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Component
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    @Id
    /**订单id*/
    private String orderId;

    /**买家姓名*/
    private String buyerName;

    /**买家手机号码*/
    private String buyerPhone;

    /**买家送货地址*/
    private String buyerAddress;

    /**买家微信openid*/
    private String buyerOpenid;

    /**订单总额*/
    private BigDecimal orderAmount;

    /**订单状态 默认新下单*/
    private Integer orderStatus;

    /**支付状态 默认等待*/
    private Integer payStatus;

    /**创建时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**更新时间*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetails;


    @JsonIgnore
    public OrderStatusEnums getOrderStatusEnums(){
        return EnumsUtils.getEnumsByCode(orderStatus,OrderStatusEnums.class);
    }
    @JsonIgnore
    public PayStatusEnums getPayStatusEnums(){
        return EnumsUtils.getEnumsByCode(payStatus,PayStatusEnums.class);
    }
    @JsonIgnore
    public ProductStatusEnums getProductStatusEnums(){
        return EnumsUtils.getEnumsByCode(payStatus,ProductStatusEnums.class);
    }
}
