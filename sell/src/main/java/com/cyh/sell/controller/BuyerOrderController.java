package com.cyh.sell.controller;

import com.cyh.sell.convert.ConvertOrderForm2OrderDTO;
import com.cyh.sell.dto.OrderDTO;
import com.cyh.sell.enums.ResultEnums;
import com.cyh.sell.exception.SellException;
import com.cyh.sell.form.OrderForm;
import com.cyh.sell.service.BuyerService;
import com.cyh.sell.service.OrderService;
import com.cyh.sell.utils.ResultUtils;
import com.cyh.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {
    @Autowired
    OrderService orderService;

    @Autowired
    BuyerService buyerService;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> create(@Valid OrderForm orderForm,
                                               BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnums.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        OrderDTO convert = ConvertOrderForm2OrderDTO.convert(orderForm);
        if (CollectionUtils.isEmpty(convert.getOrderDetails())){
            log.error("购物车不能为空");
            throw new SellException(ResultEnums.CART_EMPTY);
        }
        OrderDTO orderDTO = orderService.create(convert);

        Map<String,String> map = new HashMap<>();

        map.put("OrderId",orderDTO.getOrderId());

        return ResultUtils.success(map);
    }
    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size){
        if (StringUtils.isEmpty(openid)){
            log.error("openid为空");
            throw new SellException(ResultEnums.PARAM_ERROR);
        }
        PageRequest pageRequest = PageRequest.of(page,size);
        Page<OrderDTO> list = orderService.findList(openid, pageRequest);

        return ResultUtils.success(list.getContent());
    }
    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){

        OrderDTO orderOne = buyerService.findOrderOne(openid,orderId);
        ResultVO<OrderDTO> orderDTOResultVO = new ResultVO<>();

        return ResultUtils.success(orderDTOResultVO);
    }
    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){
        //TODO
        OrderDTO orderDTO = buyerService.cancelOrder(openid, orderId);

        return ResultUtils.success();
    }
}
