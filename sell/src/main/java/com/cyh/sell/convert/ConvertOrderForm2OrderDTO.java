package com.cyh.sell.convert;


import com.cyh.sell.dataobject.OrderDetail;
import com.cyh.sell.dto.OrderDTO;
import com.cyh.sell.exception.SellException;
import com.cyh.sell.form.OrderForm;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 买家订单表单转换orderDTO
 * Created by Administrator on 2017/10/18 0018.
 */
@Slf4j
public class ConvertOrderForm2OrderDTO {
    private static ObjectMapper objectMapper = new ObjectMapper();

//    @SuppressWarnings("unchecked")
    public static OrderDTO convert(OrderForm orderForm) {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        Gson gson = new Gson();
        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
//            Gson反序列化
            orderDetailList = gson.fromJson(orderForm.getItems() ,
                    new TypeToken<List<OrderDetail>>(){}.getType());

//            Jackson反序列化
//            orderDetailList = objectMapper.readValue(orderForm.getItems(), new TypeReference<List<OrderDetail>>() {});
//            System.out.println(orderDetailList);

        }catch (Exception e) {
            log.error("【对象装换】错误，string={}" , orderForm.getItems());
//            throw new RuntimeException();

            e.printStackTrace();
        }

        orderDTO.setOrderDetails(orderDetailList);

        return orderDTO;
    }

}
