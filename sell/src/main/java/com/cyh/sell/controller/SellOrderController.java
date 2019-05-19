package com.cyh.sell.controller;


import com.cyh.sell.dto.OrderDTO;
import com.cyh.sell.enums.ResultEnums;
import com.cyh.sell.exception.SellException;
import com.cyh.sell.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
@RequestMapping("/seller/order")
public class SellOrderController {
    @Autowired
    private OrderService orderService;
    /**
     *
     * @param page 当前页
     * @param size 一页多少条数据
     * @return
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(defaultValue = "1") String page,
                             @RequestParam(defaultValue = "10") String size,
                             Map<String,Object> map){

        PageRequest request = PageRequest.of(Integer.parseInt(page)-1,Integer.parseInt(size));
        Page<OrderDTO> list = orderService.findList(request);
        map.put("orderDTOPage",list);
        map.put("currentPage",Integer.parseInt(page));
        map.put("size",Integer.parseInt(size));
        return new ModelAndView("order/list",map);
    }


    /**
     *
     * @param orderId 订单Id
     * @param map
     * @return
     */
    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam String orderId,
                                Map<String,Object> map){

        ModelAndView modelAndView = new ModelAndView();
        try {
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancelOrder(orderDTO);
            map.put("msg",ResultEnums.SUCCESS.getMessage());
            map.put("url","/seller/order/list");
            return new ModelAndView("common/success",map);
        }
        catch (Exception e){
            map.put("msg",e.getMessage());
            map.put("url","/seller/order/list");
            return new ModelAndView("common/erro",map);
        }

    }

    /**
     * 订单详情
     * @param orderId 订单Id
     * @param map
     * @return
     */
    @GetMapping("/detail")
    public ModelAndView detai(@RequestParam String orderId,
                              Map<String,Object> map){
        OrderDTO orderDTO;
        try {
            orderDTO = orderService.findOne(orderId);
        }
        catch (SellException e){
            String message = e.getMessage();
            map.put("msg",message);
            map.put("url","/seller/order/list");
            return new ModelAndView("common/erro",map);
        }
        map.put("orderDTO",orderDTO);
        return new ModelAndView("order/detail",map);
    }
    @GetMapping("/finish")
    public ModelAndView finish(@RequestParam String orderId,
                               Map<String,Object> map){
        OrderDTO orderDTO = null;
        try {
            OrderDTO one = orderService.findOne(orderId);
            orderDTO = orderService.finished(one);
        }
        catch (SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/seller/order/list");
            return new ModelAndView("common/erro",map);
        }
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("msg","操作成功");
//        modelAndView.addObject("url","/seller/order/detail");
//        modelAndView.addObject(orderDTO);
//        modelAndView.setViewName("common/finish");

        map.put("msg","操作成功");
        map.put("orderDTO",orderDTO);
        map.put("url","/seller/order/detail");
        return new ModelAndView("common/finish",map);
    }
}
