package com.cyh.sell.dataobject;

//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.gzcstec.enums.ProductStatusEnums;
//import com.gzcstec.utils.serializer.Date2LongSerializer;
import com.cyh.sell.enums.ProductStatusEnums;
import com.cyh.sell.utils.EnumsUtils;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品
 * Created by Administrator on 2017/10/13 0013.
 */
@Entity
@Data
@DynamicUpdate
public class ProductInfo implements Serializable{
    private static final long serialVersionUID = 8445223399820083063L;
    /** 商品id .*/
    @Id
    private String productId;

    /**商品名称*/
    private String productName;

    /**商品单价*/
    private BigDecimal productPrice;

    /**商品库存*/
    private Integer productStock;

    public ProductInfo() {
    }

    public ProductInfo(String productId, String productName, BigDecimal productPrice, Integer productStock, String productDescription, String productIcon, Integer categoryType, Integer productStatus) {
        this.productId = productId;
        this.productName = productName;
        this.productPrice = productPrice;
        this.productStock = productStock;
        this.productDescription = productDescription;
        this.productIcon = productIcon;
        this.categoryType = categoryType;
        this.productStatus = productStatus;
    }

    /**商品描述*/
    private String productDescription;

    /**商品小图*/
    private String productIcon;

    /**商品类目*/
    private Integer categoryType;

    /**商品状态*/
    private Integer productStatus = ProductStatusEnums.UP.getCode();

    /**创建时间*/
//    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /**更新时间*/
//    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;

    public ProductStatusEnums getProductStatusEnums(){
        return EnumsUtils.getEnumsByCode(productStatus,ProductStatusEnums.class);
    }
}
